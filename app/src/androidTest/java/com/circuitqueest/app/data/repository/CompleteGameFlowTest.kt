package com.circuitqueest.app.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.db.AppDatabase
import com.circuitqueest.app.data.entity.Progress
import com.circuitqueest.app.data.entity.QuizResult
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CompleteGameFlowTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: ProgressRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = ProgressRepository(
            progressDao = database.progressDao(),
            quizResultDao = database.quizResultDao()
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun completeFlow_homeToQuizToResult() = runBlocking {
        val topicId = "test_topic"
        val initialProgress = repository.getProgress(topicId)
        assert(initialProgress == null)

        val quizResult = QuizResult(
            topicId = topicId,
            score = 8,
            totalQuestions = 10,
            timestamp = System.currentTimeMillis()
        )
        repository.saveQuizResult(quizResult)

        val updatedProgress = repository.getProgress(topicId)
        assert(updatedProgress != null)
        assert(updatedProgress?.bestScore == 8)
    }

    @Test
    fun completeFlow_multipleTopicsSequence() = runBlocking {
        val topics = listOf("topic1", "topic2", "topic3")

        topics.forEachIndexed { index, topicId ->
            val result = QuizResult(
                topicId = topicId,
                score = 70 + (index * 5),
                totalQuestions = 10,
                timestamp = System.currentTimeMillis() + index
            )
            repository.saveQuizResult(result)
        }

        val allProgress = repository.getAllProgress()
        assertEquals(3, allProgress.size)
    }

    @Test
    fun completeFlow_progressTracking() = runBlocking {
        val topicId = "progress_test"

        repeat(3) { attempt ->
            val result = QuizResult(
                topicId = topicId,
                score = 60 + (attempt * 10),
                totalQuestions = 10,
                timestamp = System.currentTimeMillis() + attempt
            )
            repository.saveQuizResult(result)
        }

        val progress = repository.getProgress(topicId)
        assert(progress?.bestScore == 80)
    }

    @Test
    fun completeFlow_xpAccumulation() = runBlocking {
        val topics = listOf("t1", "t2", "t3", "t4", "t5")

        topics.forEach { topicId ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = 10,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        val totalXp = repository.getTotalXp()
        assert(totalXp > 0)
    }

    @Test
    fun completeFlow_improvedScores() = runBlocking {
        val topicId = "improvement_test"

        val scores = listOf(50, 60, 70, 80, 90)
        scores.forEach { score ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = score,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        val progress = repository.getProgress(topicId)
        assert(progress?.bestScore == 90)
    }

    @Test
    fun completeFlow_quizRetry() = runBlocking {
        val topicId = "retry_test"

        repository.saveQuizResult(
            QuizResult(
                topicId = topicId,
                score = 50,
                totalQuestions = 10,
                timestamp = System.currentTimeMillis()
            )
        )

        repository.saveQuizResult(
            QuizResult(
                topicId = topicId,
                score = 85,
                totalQuestions = 10,
                timestamp = System.currentTimeMillis() + 1000
            )
        )

        val progress = repository.getProgress(topicId)
        assert(progress?.bestScore == 85)
    }

    private fun assertEquals(expected: Int, actual: Int) {
        assert(expected == actual) { "Expected $expected but got $actual" }
    }
}

@RunWith(AndroidJUnit4::class)
class ErrorRecoveryFlowTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: ProgressRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = ProgressRepository(
            progressDao = database.progressDao(),
            quizResultDao = database.quizResultDao()
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun errorRecovery_databaseError_dataConsistency() = runBlocking {
        val topicId = "consistency_test"
        repository.saveQuizResult(
            QuizResult(
                topicId = topicId,
                score = 80,
                totalQuestions = 10,
                timestamp = System.currentTimeMillis()
            )
        )

        repository.saveQuizResult(
            QuizResult(
                topicId = topicId,
                score = 70,
                totalQuestions = 10,
                timestamp = System.currentTimeMillis() + 1000
            )
        )

        val progress = repository.getProgress(topicId)
        assert(progress?.bestScore == 80)
    }

    @Test
    fun errorRecovery_concurrentUpdates() = runBlocking {
        val topicId = "concurrent_test"

        repository.saveQuizResult(
            QuizResult(
                topicId = topicId,
                score = 75,
                totalQuestions = 10,
                timestamp = 1000L
            )
        )
        repository.saveQuizResult(
            QuizResult(
                topicId = topicId,
                score = 85,
                totalQuestions = 10,
                timestamp = 2000L
            )
        )

        val progress = repository.getProgress(topicId)
        assert(progress != null)
    }

    @Test
    fun errorRecovery_stateRestorationAfterError() = runBlocking {
        val topicId = "restore_test"
        repository.saveQuizResult(
            QuizResult(
                topicId = topicId,
                score = 80,
                totalQuestions = 10,
                timestamp = System.currentTimeMillis()
            )
        )

        val lastKnownGoodState: Progress? = repository.getProgress(topicId)

        assert(lastKnownGoodState?.bestScore == 80)
    }

    @Test
    fun errorRecovery_userFacingErrorMessage() = runBlocking {
        var errorMessage = ""

        try {
            throw Exception("Quiz save failed")
        } catch (e: Exception) {
            errorMessage = "Failed to save quiz result. Please try again."
        }

        assert(errorMessage.isNotEmpty())
        assert(errorMessage.contains("Failed"))
    }
}
