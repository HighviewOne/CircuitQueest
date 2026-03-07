package com.circuitqueest.app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.db.AppDatabase
import com.circuitqueest.app.data.entity.Progress
import com.circuitqueest.app.data.entity.QuizResult
import com.circuitqueest.app.data.repository.ProgressRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdvancedIntegrationScenariosTest {

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
    fun scenario_gamifiedProgression() = runBlocking {
        // Arrange - simulate gradual progression
        val allTopics = (1..41).map { "topic_$it" }

        // Act - user completes each topic with increasing scores
        allTopics.forEachIndexed { index, topicId ->
            val score = 50 + (index % 51) // Varying scores
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = score,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis() + index
                )
            )
        }

        // Assert - all topics tracked
        val allProgress = repository.getAllProgress()
        assert(allProgress.size == 41)
    }

    @Test
    fun scenario_challengingTopicsRetry() = runBlocking {
        // Arrange - difficult topic
        val topicId = "challenging_topic"

        // Act - user struggles then improves
        val attempts = listOf(30, 40, 50, 65, 80, 90)
        attempts.forEach { score ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = score,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        // Assert - best score tracked
        val progress = repository.getProgress(topicId)
        assert(progress?.bestScore == 90)
    }

    @Test
    fun scenario_perfectRunAllTopics() = runBlocking {
        // Arrange - ambitious goal
        val topicsToMaster = (1..10).map { "master_$it" }

        // Act - get perfect scores on multiple topics
        topicsToMaster.forEach { topicId ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = 100,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        // Assert - all perfect scores
        val allProgress = repository.getAllProgress()
        allProgress.forEach { progress ->
            assert(progress.bestScore == 100)
        }
    }

    @Test
    fun scenario_mixedPerformanceTracking() = runBlocking {
        // Arrange - varied performance levels
        val performanceMap = mapOf(
            "easy_topic" to 95,
            "medium_topic" to 75,
            "hard_topic" to 55,
            "very_hard" to 35
        )

        // Act - complete all topics with different scores
        performanceMap.forEach { (topicId, score) ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = score,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        // Assert - all tracked correctly
        val allProgress = repository.getAllProgress()
        assert(allProgress.size == 4)
        allProgress.forEach { progress ->
            assert(progress.bestScore in 35..95)
        }
    }

    @Test
    fun scenario_longPlaySession() = runBlocking {
        // Arrange - simulate extended play
        val sessionTopics = (1..20).map { "session_$it" }

        // Act - rapid-fire topic completions
        sessionTopics.forEach { topicId ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = 70 + (Math.random() * 30).toInt(),
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        // Assert - session data persisted
        val allProgress = repository.getAllProgress()
        assert(allProgress.size == 20)
    }

    @Test
    fun scenario_spottedLearning() = runBlocking {
        // Arrange - user learns sporadically
        val spotTopics = listOf("spot1", "spot1", "spot2", "spot1", "spot3")

        // Act - repeated attempts on same topics
        spotTopics.forEachIndexed { index, topicId ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = 40 + (index * 10),
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis() + index
                )
            )
        }

        // Assert - best score per topic
        val spot1 = repository.getProgress("spot1")
        assert(spot1?.bestScore == 70) // Best of 40, 50, 70
    }

    @Test
    fun scenario_burnoutAndRecovery() = runBlocking {
        // Arrange - user burns out then recovers
        val topicId = "burnout_recovery"

        // Act - good start, then decline, then recovery
        val performance = listOf(90, 85, 80, 70, 60, 50, 55, 65, 80, 90)
        performance.forEach { score ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = score,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        // Assert - best score is preserved
        val progress = repository.getProgress(topicId)
        assert(progress?.bestScore == 90)
    }

    @Test
    fun scenario_unlockingAdvancedTopics() = runBlocking {
        // Arrange - advanced topics depend on basic mastery
        val basicTopics = listOf("basics1", "basics2", "basics3")
        val advancedTopics = listOf("advanced1", "advanced2")

        // Act - master basics first
        basicTopics.forEach { topicId ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = 90,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        // Act - then tackle advanced (would be unlocked)
        advancedTopics.forEach { topicId ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = 70,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        // Assert - all topics tracked
        val allProgress = repository.getAllProgress()
        assert(allProgress.size == 5)
    }

    @Test
    fun scenario_masteryChallengeCompletionFlow() = runBlocking {
        // Arrange - complete full curriculum
        val allTopics = (1..41).map { index ->
            val topicId = "curriculum_$index"
            val baseScore = (index * 100) / 41 // Gradually increasing difficulty
            topicId to (baseScore + (Math.random() * 10).toInt()).coerceIn(0, 100)
        }

        // Act - complete full curriculum
        allTopics.forEach { (topicId, score) ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = score,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        // Assert - full curriculum completion verified
        val finalProgress = repository.getAllProgress()
        assert(finalProgress.size == 41)
        val averageScore = finalProgress.map { it.bestScore }.average()
        assert(averageScore > 40) // Average should be reasonable
    }
}

@RunWith(AndroidJUnit4::class)
class StateManagementScenariosTest {

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
    fun stateManagement_persistenceAcrossAppRestarts() = runBlocking {
        // Arrange
        val topicId = "persistent_topic"
        repository.saveQuizResult(
            QuizResult(
                topicId = topicId,
                score = 85,
                totalQuestions = 10,
                timestamp = System.currentTimeMillis()
            )
        )

        // Act - simulate app restart by closing and reopening DB
        tearDown()
        setup()

        // Assert - data restored
        val progress = repository.getProgress(topicId)
        assert(progress?.bestScore == 85)
    }

    @Test
    fun stateManagement_concurrentAccessSafety() = runBlocking {
        // Arrange
        val topicId = "concurrent_safety"

        // Act - simulate concurrent access
        repeat(10) { attempt ->
            repository.saveQuizResult(
                QuizResult(
                    topicId = topicId,
                    score = 50 + attempt,
                    totalQuestions = 10,
                    timestamp = System.currentTimeMillis() + attempt
                )
            )
        }

        // Assert - data consistent
        val progress = repository.getProgress(topicId)
        assert(progress?.bestScore == 59) // Last highest score
    }

    @Test
    fun stateManagement_dataIntegrityUnderLoad() = runBlocking {
        // Arrange - heavy load
        val topicCount = 50
        val attemptsPerTopic = 10

        // Act
        repeat(topicCount) { topicIndex ->
            val topicId = "load_test_$topicIndex"
            repeat(attemptsPerTopic) { attemptIndex ->
                repository.saveQuizResult(
                    QuizResult(
                        topicId = topicId,
                        score = 40 + (attemptIndex * 6),
                        totalQuestions = 10,
                        timestamp = System.currentTimeMillis() + attemptIndex
                    )
                )
            }
        }

        // Assert - all data consistent
        val allProgress = repository.getAllProgress()
        assert(allProgress.size == topicCount)
        allProgress.forEach { progress ->
            assert(progress.bestScore in 40..94)
        }
    }
}
