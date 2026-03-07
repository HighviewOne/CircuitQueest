package com.circuitqueest.app.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.db.dao.QuizResultDao
import com.circuitqueest.app.data.db.entity.QuizResult
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
class QuizResultDaoInstrumentedTest {

    private lateinit var database: AppDatabase
    private lateinit var quizResultDao: QuizResultDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        quizResultDao = database.quizResultDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertResult_success() = runBlocking {
        val result = QuizResult(
            topicId = "ohms-law",
            score = 9,
            totalQuestions = 10
        )

        quizResultDao.insertResult(result)
        val results = quizResultDao.getResultsForTopic("ohms-law").first()

        assertEquals(1, results.size)
        assertEquals("ohms-law", results[0].topicId)
        assertEquals(9, results[0].score)
    }

    @Test
    fun multipleInserts_allRetrieved() = runBlocking {
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 8, totalQuestions = 10))
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 9, totalQuestions = 10))
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 7, totalQuestions = 10))

        val results = quizResultDao.getResultsForTopic("topic1").first()

        assertEquals(3, results.size)
        assertEquals(8, results[0].score)
        assertEquals(9, results[1].score)
        assertEquals(7, results[2].score)
    }

    @Test
    fun getResultsForTopic_orderedByTimestampDesc() = runBlocking {
        val timestamp1 = System.currentTimeMillis()
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 8, totalQuestions = 10, timestamp = timestamp1))
        
        Thread.sleep(10)
        val timestamp2 = System.currentTimeMillis()
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 9, totalQuestions = 10, timestamp = timestamp2))
        
        Thread.sleep(10)
        val timestamp3 = System.currentTimeMillis()
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 7, totalQuestions = 10, timestamp = timestamp3))

        val results = quizResultDao.getResultsForTopic("topic1").first()

        assertEquals(3, results.size)
        assertTrue(results[0].timestamp >= results[1].timestamp)
        assertTrue(results[1].timestamp >= results[2].timestamp)
    }

    @Test
    fun getBestScore_returnsMax() = runBlocking {
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 6, totalQuestions = 10))
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 9, totalQuestions = 10))
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 8, totalQuestions = 10))

        val best = quizResultDao.getBestScore("topic1").first()

        assertEquals(9, best)
    }

    @Test
    fun getBestScore_noResults_returnsNull() = runBlocking {
        val best = quizResultDao.getBestScore("non-existent").first()

        assertNull(best)
    }

    @Test
    fun getResultsForTopic_differentTopics_isolated() = runBlocking {
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 8, totalQuestions = 10))
        quizResultDao.insertResult(QuizResult(topicId = "topic1", score = 9, totalQuestions = 10))
        quizResultDao.insertResult(QuizResult(topicId = "topic2", score = 7, totalQuestions = 10))
        quizResultDao.insertResult(QuizResult(topicId = "topic2", score = 8, totalQuestions = 10))

        val topic1Results = quizResultDao.getResultsForTopic("topic1").first()
        val topic2Results = quizResultDao.getResultsForTopic("topic2").first()

        assertEquals(2, topic1Results.size)
        assertEquals(2, topic2Results.size)
        assertTrue(topic1Results.all { it.topicId == "topic1" })
        assertTrue(topic2Results.all { it.topicId == "topic2" })
    }

    @Test
    fun autoIncrement_id_generated() = runBlocking {
        val result1 = QuizResult(topicId = "topic1", score = 8, totalQuestions = 10)
        val result2 = QuizResult(topicId = "topic1", score = 9, totalQuestions = 10)

        quizResultDao.insertResult(result1)
        quizResultDao.insertResult(result2)

        val results = quizResultDao.getResultsForTopic("topic1").first()

        assertEquals(2, results.size)
        assertTrue(results[0].id > 0)
        assertTrue(results[1].id > 0)
        assertTrue(results[1].id > results[0].id || results[0].id > results[1].id)
    }

    @Test
    fun getResults_emptyTopic_returnsEmpty() = runBlocking {
        val results = quizResultDao.getResultsForTopic("empty-topic").first()

        assertEquals(0, results.size)
    }

    @Test
    fun insertResult_preservesData_integrity() = runBlocking {
        val result = QuizResult(
            topicId = "test-topic",
            score = 95,
            totalQuestions = 100
        )

        quizResultDao.insertResult(result)
        val retrieved = quizResultDao.getResultsForTopic("test-topic").first().first()

        assertEquals(result.topicId, retrieved.topicId)
        assertEquals(result.score, retrieved.score)
        assertEquals(result.totalQuestions, retrieved.totalQuestions)
    }
}
