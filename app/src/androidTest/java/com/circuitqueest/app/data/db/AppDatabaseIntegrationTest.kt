package com.circuitqueest.app.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.data.db.entity.QuizResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class AppDatabaseIntegrationTest {

    private lateinit var database: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun databaseInitialization_success() {
        assertNotNull(database)
        assertNotNull(database.progressDao())
        assertNotNull(database.quizResultDao())
    }

    @Test
    fun completeWorkflow_saveQuizAndProgress() = runBlocking {
        val topicId = "ohms-law"
        
        // Create initial progress
        database.progressDao().upsertProgress(
            TopicProgress(
                topicId = topicId,
                lessonCompleted = true,
                xpEarned = 50
            )
        )

        // Save quiz results
        database.quizResultDao().insertResult(
            QuizResult(topicId = topicId, score = 8, totalQuestions = 10)
        )
        database.quizResultDao().insertResult(
            QuizResult(topicId = topicId, score = 9, totalQuestions = 10)
        )

        // Update progress with quiz completion
        database.progressDao().upsertProgress(
            TopicProgress(
                topicId = topicId,
                lessonCompleted = true,
                quizCompleted = true,
                bestScore = 9,
                xpEarned = 300
            )
        )

        // Verify state
        val progress = database.progressDao().getProgressOnce(topicId)
        val results = database.quizResultDao().getResultsForTopic(topicId).first()
        val bestScore = database.quizResultDao().getBestScore(topicId).first()

        assertNotNull(progress)
        assertEquals(true, progress!!.quizCompleted)
        assertEquals(9, progress.bestScore)
        assertEquals(300, progress.xpEarned)
        assertEquals(2, results.size)
        assertEquals(9, bestScore)
    }

    @Test
    fun multipleTopics_isolation() = runBlocking {
        // Insert data for two topics
        database.progressDao().upsertProgress(TopicProgress("topic1", xpEarned = 100))
        database.progressDao().upsertProgress(TopicProgress("topic2", xpEarned = 200))
        
        database.quizResultDao().insertResult(QuizResult(topicId = "topic1", score = 8, totalQuestions = 10))
        database.quizResultDao().insertResult(QuizResult(topicId = "topic2", score = 9, totalQuestions = 10))

        // Verify isolation
        val progress1 = database.progressDao().getProgressOnce("topic1")
        val progress2 = database.progressDao().getProgressOnce("topic2")
        val results1 = database.quizResultDao().getResultsForTopic("topic1").first()
        val results2 = database.quizResultDao().getResultsForTopic("topic2").first()

        assertEquals(100, progress1?.xpEarned)
        assertEquals(200, progress2?.xpEarned)
        assertEquals(1, results1.size)
        assertEquals(1, results2.size)
        assertEquals(8, results1[0].score)
        assertEquals(9, results2[0].score)
    }

    @Test
    fun totalXp_aggregation_acrossTopics() = runBlocking {
        database.progressDao().upsertProgress(TopicProgress("topic1", xpEarned = 100))
        database.progressDao().upsertProgress(TopicProgress("topic2", xpEarned = 150))
        database.progressDao().upsertProgress(TopicProgress("topic3", xpEarned = 250))

        val total = database.progressDao().getTotalXp().first()

        assertEquals(500, total)
    }

    @Test
    fun progressionFlow_unlocking() = runBlocking {
        val topic1 = "ohms-law"
        val topic2 = "series-parallel"

        // Mark topic1 incomplete
        database.progressDao().upsertProgress(
            TopicProgress(topic1, quizCompleted = false)
        )

        // Topic2 should be locked (topic1 not completed)
        val progress2Initial = database.progressDao().getProgressOnce(topic2)
        assertEquals(null, progress2Initial)

        // Complete topic1
        database.progressDao().upsertProgress(
            TopicProgress(topic1, quizCompleted = true)
        )

        // Now can unlock topic2
        database.progressDao().upsertProgress(
            TopicProgress(topic2, quizCompleted = false)
        )

        val progress1Final = database.progressDao().getProgressOnce(topic1)
        val progress2Final = database.progressDao().getProgressOnce(topic2)

        assertTrue(progress1Final?.quizCompleted == true)
        assertNotNull(progress2Final)
    }

    @Test
    fun bestScoreTracking_workflow() = runBlocking {
        val topicId = "test-topic"

        // First attempt
        database.quizResultDao().insertResult(QuizResult(topicId = topicId, score = 6, totalQuestions = 10))
        var best = database.quizResultDao().getBestScore(topicId).first()
        assertEquals(6, best)

        // Second attempt (better)
        database.quizResultDao().insertResult(QuizResult(topicId = topicId, score = 8, totalQuestions = 10))
        best = database.quizResultDao().getBestScore(topicId).first()
        assertEquals(8, best)

        // Third attempt (worse)
        database.quizResultDao().insertResult(QuizResult(topicId = topicId, score = 7, totalQuestions = 10))
        best = database.quizResultDao().getBestScore(topicId).first()
        assertEquals(8, best)

        val results = database.quizResultDao().getResultsForTopic(topicId).first()
        assertEquals(3, results.size)
    }

    @Test
    fun reactiveUpdates_progressFlow() = runBlocking {
        val topicId = "reactive-test"

        // Insert initial
        database.progressDao().upsertProgress(TopicProgress(topicId, xpEarned = 100))
        
        // Update via flow
        val progress1 = database.progressDao().getProgress(topicId).first()
        assertEquals(100, progress1?.xpEarned)

        // Update value
        database.progressDao().upsertProgress(TopicProgress(topicId, xpEarned = 200))
        
        val progress2 = database.progressDao().getProgress(topicId).first()
        assertEquals(200, progress2?.xpEarned)
    }
}
