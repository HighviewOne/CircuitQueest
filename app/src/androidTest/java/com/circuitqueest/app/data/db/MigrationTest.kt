package com.circuitqueest.app.data.db

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.circuitqueest.app.data.db.entity.MissedQuestion
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Every installed copy of v2.4 has a version-1 database. If a migration is wrong,
 * Room throws on launch for all of them, so each schema step is tested here.
 */
@RunWith(AndroidJUnit4::class)
class MigrationTest {

    private val dbName = "migration-test"

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java,
        emptyList(),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrate1To2_keepsProgress_andAddsReviewQueue(): Unit = runBlocking {
        helper.createDatabase(dbName, 1).apply {
            execSQL(
                "INSERT INTO topic_progress (topicId, lessonCompleted, quizCompleted, bestScore, " +
                    "totalQuestions, xpEarned, lastAccessedTimestamp) " +
                    "VALUES ('ohms_law', 1, 1, 7, 7, 220, 123)"
            )
            execSQL(
                "INSERT INTO quiz_results (topicId, score, totalQuestions, timestamp) " +
                    "VALUES ('ohms_law', 7, 7, 456)"
            )
            close()
        }

        // Validates the migrated schema against app/schemas/.../2.json.
        helper.runMigrationsAndValidate(dbName, 2, true).close()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val db = Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
        try {
            val progress = db.progressDao().getProgressOnce("ohms_law")
            assertNotNull(progress)
            assertEquals(220, progress.xpEarned)
            assertTrue(progress.quizCompleted)
            assertEquals(1, db.quizResultDao().getResultsForTopic("ohms_law").first().size)

            db.missedQuestionDao().upsert(MissedQuestion("ohms_law", "ohm_q2"))
            assertEquals(1, db.missedQuestionDao().count().first())
        } finally {
            db.close()
            context.deleteDatabase(dbName)
        }
    }
}
