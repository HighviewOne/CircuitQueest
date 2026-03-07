package com.circuitqueest.app.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.entity.TopicProgress
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class ProgressDaoInstrumentedTest {

    private lateinit var database: AppDatabase
    private lateinit var progressDao: ProgressDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        progressDao = database.progressDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndRetrieve_success() = runBlocking {
        val progress = TopicProgress(
            topicId = "ohms-law",
            lessonCompleted = true,
            quizCompleted = false,
            bestScore = 8,
            xpEarned = 150
        )

        progressDao.upsertProgress(progress)
        val retrieved = progressDao.getProgressOnce("ohms-law")

        assertEquals(progress.topicId, retrieved?.topicId)
        assertEquals(progress.bestScore, retrieved?.bestScore)
        assertEquals(progress.xpEarned, retrieved?.xpEarned)
    }

    @Test
    fun upsert_updates_existingProgress() = runBlocking {
        val initial = TopicProgress(
            topicId = "series-parallel",
            lessonCompleted = false,
            xpEarned = 50
        )
        progressDao.upsertProgress(initial)

        val updated = TopicProgress(
            topicId = "series-parallel",
            lessonCompleted = true,
            quizCompleted = true,
            bestScore = 10,
            xpEarned = 200
        )
        progressDao.upsertProgress(updated)

        val result = progressDao.getProgressOnce("series-parallel")

        assertEquals(10, result?.bestScore)
        assertEquals(200, result?.xpEarned)
        assertTrue(result?.lessonCompleted == true)
        assertTrue(result?.quizCompleted == true)
    }

    @Test
    fun getAllProgress_returnsAll() = runBlocking {
        progressDao.upsertProgress(TopicProgress("topic1", lessonCompleted = true))
        progressDao.upsertProgress(TopicProgress("topic2", lessonCompleted = false))
        progressDao.upsertProgress(TopicProgress("topic3", lessonCompleted = true))

        val all = progressDao.getAllProgress().first()

        assertEquals(3, all.size)
        assertTrue(all.map { it.topicId }.contains("topic1"))
        assertTrue(all.map { it.topicId }.contains("topic2"))
        assertTrue(all.map { it.topicId }.contains("topic3"))
    }

    @Test
    fun getTotalXp_aggregatesCorrectly() = runBlocking {
        progressDao.upsertProgress(TopicProgress("topic1", xpEarned = 100))
        progressDao.upsertProgress(TopicProgress("topic2", xpEarned = 150))
        progressDao.upsertProgress(TopicProgress("topic3", xpEarned = 250))

        val total = progressDao.getTotalXp().first()

        assertEquals(500, total)
    }

    @Test
    fun getTotalXp_emptyDatabase_returnsZero() = runBlocking {
        val total = progressDao.getTotalXp().first()

        assertEquals(0, total)
    }

    @Test
    fun flowReactivity_updatesOnChange() = runBlocking {
        val topicId = "kirchhoffs"
        
        progressDao.upsertProgress(TopicProgress(topicId, xpEarned = 100))
        val initial = progressDao.getProgress(topicId).first()
        
        progressDao.upsertProgress(TopicProgress(topicId, xpEarned = 200))
        val updated = progressDao.getProgress(topicId).first()

        assertEquals(100, initial?.xpEarned)
        assertEquals(200, updated?.xpEarned)
    }

    @Test
    fun multipleUpdates_bestScoreTracking() = runBlocking {
        val topicId = "capacitors"
        
        progressDao.upsertProgress(TopicProgress(topicId, bestScore = 6, xpEarned = 100))
        progressDao.upsertProgress(TopicProgress(topicId, bestScore = 8, xpEarned = 160))
        progressDao.upsertProgress(TopicProgress(topicId, bestScore = 7, xpEarned = 210))

        val result = progressDao.getProgressOnce(topicId)

        assertEquals(7, result?.bestScore)
        assertEquals(210, result?.xpEarned)
    }

    @Test
    fun nonExistent_returnsNull() = runBlocking {
        val result = progressDao.getProgressOnce("non-existent-topic")

        assertNull(result)
    }
}
