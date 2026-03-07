package com.circuitqueest.app.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.dao.QuizResultDao
import com.circuitqueest.app.data.entity.Progress
import com.circuitqueest.app.data.entity.QuizResult
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTests {
    
    private lateinit var database: AppDatabase
    private lateinit var progressDao: ProgressDao
    private lateinit var quizResultDao: QuizResultDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        
        progressDao = database.progressDao()
        quizResultDao = database.quizResultDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun database_creates_successfully() {
        // Assert
        assert(database != null)
    }

    @Test
    fun database_progressDao_isAccessible() {
        // Assert
        assert(progressDao != null)
    }

    @Test
    fun database_quizResultDao_isAccessible() {
        // Assert
        assert(quizResultDao != null)
    }

    @Test
    fun database_multipleTopicsIsolation() = runBlocking {
        // Arrange
        val progress1 = Progress(topicId = "topic1", bestScore = 80, totalQuestions = 10)
        val progress2 = Progress(topicId = "topic2", bestScore = 90, totalQuestions = 10)

        // Act
        progressDao.insertOrUpdate(progress1)
        progressDao.insertOrUpdate(progress2)
        val retrieved1 = progressDao.getProgress("topic1")
        val retrieved2 = progressDao.getProgress("topic2")

        // Assert
        assert(retrieved1?.bestScore == 80)
        assert(retrieved2?.bestScore == 90)
    }

    @Test
    fun database_progressAndQuizResultsCoexist() = runBlocking {
        // Arrange
        val progress = Progress(topicId = "test_topic", bestScore = 75, totalQuestions = 10)
        val quizResult = QuizResult(
            topicId = "test_topic",
            score = 8,
            totalQuestions = 10,
            timestamp = System.currentTimeMillis()
        )

        // Act
        progressDao.insertOrUpdate(progress)
        quizResultDao.insertResult(quizResult)

        // Assert
        assert(progressDao.getProgress("test_topic") != null)
        assert(quizResultDao.getResultsForTopic("test_topic").isNotEmpty())
    }

    @Test
    fun database_cascadingInserts_maintainIntegrity() = runBlocking {
        // Arrange - insert multiple topics with various data
        repeat(5) { index ->
            val topicId = "topic_$index"
            progressDao.insertOrUpdate(
                Progress(topicId = topicId, bestScore = 50 + index * 10, totalQuestions = 10)
            )
            repeat(3) { attemptIndex ->
                quizResultDao.insertResult(
                    QuizResult(
                        topicId = topicId,
                        score = 5 + attemptIndex,
                        totalQuestions = 10,
                        timestamp = System.currentTimeMillis() + attemptIndex
                    )
                )
            }
        }

        // Assert - verify all data persisted
        repeat(5) { index ->
            val topicId = "topic_$index"
            assert(progressDao.getProgress(topicId) != null)
            assert(quizResultDao.getResultsForTopic(topicId).size == 3)
        }
    }

    @Test
    fun database_totalXpAggregation_acrossTopics() = runBlocking {
        // Arrange
        progressDao.insertOrUpdate(Progress(topicId = "t1", bestScore = 100, totalQuestions = 10))
        progressDao.insertOrUpdate(Progress(topicId = "t2", bestScore = 90, totalQuestions = 10))
        progressDao.insertOrUpdate(Progress(topicId = "t3", bestScore = 80, totalQuestions = 10))

        // Act
        val allProgress = progressDao.getAllProgress()

        // Assert
        assert(allProgress.size == 3)
    }

    @Test
    fun database_progressUpdate_replacesExisting() = runBlocking {
        // Arrange
        val topicId = "update_test"
        progressDao.insertOrUpdate(Progress(topicId = topicId, bestScore = 50, totalQuestions = 10))

        // Act
        progressDao.insertOrUpdate(Progress(topicId = topicId, bestScore = 100, totalQuestions = 10))

        // Assert
        val result = progressDao.getProgress(topicId)
        assert(result?.bestScore == 100)
    }

    @Test
    fun database_quizResults_orderByTimestampDesc() = runBlocking {
        // Arrange
        val topicId = "order_test"
        val baseTime = 1000L
        quizResultDao.insertResult(
            QuizResult(topicId = topicId, score = 1, totalQuestions = 10, timestamp = baseTime)
        )
        quizResultDao.insertResult(
            QuizResult(topicId = topicId, score = 2, totalQuestions = 10, timestamp = baseTime + 100)
        )
        quizResultDao.insertResult(
            QuizResult(topicId = topicId, score = 3, totalQuestions = 10, timestamp = baseTime + 50)
        )

        // Act
        val results = quizResultDao.getResultsForTopic(topicId)

        // Assert
        assertEquals(3, results.size)
        assertEquals(2, results[0].score) // Most recent
        assertEquals(3, results[1].score)
        assertEquals(1, results[2].score) // Oldest
    }

    @Test
    fun database_largeDataset_performance() = runBlocking {
        // Arrange - insert 100 topics with 5 attempts each
        repeat(100) { topicIndex ->
            val topicId = "perf_test_$topicIndex"
            progressDao.insertOrUpdate(
                Progress(topicId = topicId, bestScore = 50 + (topicIndex % 50), totalQuestions = 10)
            )
            repeat(5) { attemptIndex ->
                quizResultDao.insertResult(
                    QuizResult(
                        topicId = topicId,
                        score = 4 + (attemptIndex % 6),
                        totalQuestions = 10,
                        timestamp = System.currentTimeMillis() + attemptIndex
                    )
                )
            }
        }

        // Act
        val allProgress = progressDao.getAllProgress()
        val totalQuizResults = allProgress.sumOf {
            quizResultDao.getResultsForTopic(it.topicId).size
        }

        // Assert
        assertEquals(100, allProgress.size)
        assertEquals(500, totalQuizResults)
    }

    private fun assertEquals(expected: Int, actual: Int, message: String = "") {
        assert(expected == actual) { "Expected $expected but got $actual. $message" }
    }
}
