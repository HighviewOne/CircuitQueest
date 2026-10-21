package com.circuitqueest.app.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.circuitqueest.app.data.db.entity.QuizResult
import com.circuitqueest.app.data.db.entity.TopicProgress
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Players who installed v1.0–v2.0 have their progress in the old "circuitqueest-db" file.
 * Opening the current database must pull it in, once, without lowering newer progress.
 */
@RunWith(AndroidJUnit4::class)
class LegacyDatabaseImportTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val dbName = "legacy-import-test"
    private val legacyName = "legacy-import-test-old"
    private lateinit var legacyFile: File

    @Before
    fun setUp() {
        cleanUp()
        legacyFile = context.getDatabasePath(legacyName)
    }

    @After
    fun cleanUp() {
        context.deleteDatabase(dbName)
        context.deleteDatabase(legacyName)
        context.deleteDatabase(legacyName + LegacyDatabaseImport.IMPORTED_SUFFIX)
    }

    /** A v2.0-era file: schema-1 tables in WAL mode, as Room left them. */
    private fun createLegacyDatabase() {
        legacyFile.parentFile?.mkdirs()
        val old = SQLiteDatabase.openOrCreateDatabase(legacyFile, null)
        old.enableWriteAheadLogging()
        old.execSQL(
            "CREATE TABLE topic_progress (topicId TEXT NOT NULL, lessonCompleted INTEGER NOT NULL, " +
                "quizCompleted INTEGER NOT NULL, bestScore INTEGER NOT NULL, totalQuestions INTEGER NOT NULL, " +
                "xpEarned INTEGER NOT NULL, lastAccessedTimestamp INTEGER NOT NULL, PRIMARY KEY(topicId))"
        )
        old.execSQL(
            "CREATE TABLE quiz_results (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, topicId TEXT NOT NULL, " +
                "score INTEGER NOT NULL, totalQuestions INTEGER NOT NULL, timestamp INTEGER NOT NULL)"
        )
        old.execSQL("INSERT INTO topic_progress VALUES ('ohms_law', 1, 1, 7, 7, 170, 100)")
        old.execSQL("INSERT INTO topic_progress VALUES ('series_parallel', 1, 1, 5, 7, 150, 200)")
        old.execSQL("INSERT INTO quiz_results (topicId, score, totalQuestions, timestamp) VALUES ('ohms_law', 7, 7, 100)")
        old.execSQL(
            "INSERT INTO quiz_results (topicId, score, totalQuestions, timestamp) VALUES ('series_parallel', 5, 7, 200)"
        )
        old.close()
    }

    private fun openDb(): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .addCallback(LegacyDatabaseImport(legacyFile))
            .build()

    @Test
    fun importsLegacyProgress_intoEmptyDatabase(): Unit = runBlocking {
        createLegacyDatabase()

        val db = openDb()
        try {
            val ohm = db.progressDao().getProgressOnce("ohms_law")
            assertNotNull(ohm)
            assertEquals(7, ohm.bestScore)
            assertTrue(ohm.quizCompleted)
            assertEquals(320, db.progressDao().getTotalXp().first())
            assertEquals(1, db.quizResultDao().getResultsForTopic("series_parallel").first().size)
        } finally {
            db.close()
        }
        assertFalse(legacyFile.exists(), "old file should be renamed after import")
        assertTrue(File(legacyFile.path + LegacyDatabaseImport.IMPORTED_SUFFIX).exists())
    }

    @Test
    fun mergesWithExistingProgress_keepingTheBetterResult(): Unit = runBlocking {
        // Progress made in v2.2+ before this fix: a better Ohm's Law score and a new topic.
        openDb().apply {
            progressDao().upsertProgress(TopicProgress("ohms_law", true, true, 7, 7, 200, 900))
            progressDao().upsertProgress(TopicProgress("kirchhoffs", true, false, 3, 7, 80, 950))
            quizResultDao().insertResult(QuizResult(topicId = "ohms_law", score = 7, totalQuestions = 7, timestamp = 900))
            close()
        }
        createLegacyDatabase()

        val db = openDb()
        try {
            assertEquals(200, db.progressDao().getProgressOnce("ohms_law")?.xpEarned)
            assertEquals(900L, db.progressDao().getProgressOnce("ohms_law")?.lastAccessedTimestamp)
            assertEquals(150, db.progressDao().getProgressOnce("series_parallel")?.xpEarned)
            assertEquals(80, db.progressDao().getProgressOnce("kirchhoffs")?.xpEarned)
            assertEquals(2, db.quizResultDao().getResultsForTopic("ohms_law").first().size)
        } finally {
            db.close()
        }
    }

    @Test
    fun runsOnce_evenIfOpenedAgain(): Unit = runBlocking {
        createLegacyDatabase()
        openDb().apply { progressDao().getProgressOnce("ohms_law"); close() }

        val db = openDb()
        try {
            assertEquals(1, db.quizResultDao().getResultsForTopic("ohms_law").first().size)
            assertEquals(320, db.progressDao().getTotalXp().first())
        } finally {
            db.close()
        }
    }

    @Test
    fun noLegacyFile_changesNothing(): Unit = runBlocking {
        val db = openDb()
        try {
            assertNull(db.progressDao().getProgressOnce("ohms_law"))
        } finally {
            db.close()
        }
    }
}
