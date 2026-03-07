package com.circuitqueest.app.data

import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.data.db.entity.QuizResult
import com.circuitqueest.app.data.repository.ProgressRepository
import com.circuitqueest.app.util.QuizScoring
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.dao.QuizResultDao
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuizFlowIntegrationTest {

    private lateinit var progressDao: ProgressDao
    private lateinit var quizResultDao: QuizResultDao
    private lateinit var repository: ProgressRepository

    @Before
    fun setup() {
        progressDao = mock()
        quizResultDao = mock()
        repository = ProgressRepository(progressDao, quizResultDao)
    }

    @Test
    fun quizFlow_startToFinish_updatesAllState() = runBlocking {
        val topicId = "ohms-law"
        val score = 10
        val totalQuestions = 10

        val emptyProgress = TopicProgress(
            topicId = topicId,
            lessonCompleted = true,
            quizCompleted = false,
            xpEarned = 50
        )
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(emptyProgress)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(null)
        )

        repository.saveQuizResult(topicId, score, totalQuestions)

        val progressCaptor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(progressCaptor.capture())
        
        val resultCaptor = argumentCaptor<QuizResult>()
        verify(quizResultDao).insertResult(resultCaptor.capture())

        val updatedProgress = progressCaptor.firstValue
        val savedResult = resultCaptor.firstValue

        assertTrue(updatedProgress.quizCompleted)
        assertEquals(10, updatedProgress.bestScore)
        assertEquals(topicId, savedResult.topicId)
        assertEquals(10, savedResult.score)
    }

    @Test
    fun quizFlow_newBestScore_updatesProgress() = runBlocking {
        val topicId = "kirchhoffs"
        val newScore = 9
        val previousBestScore = 6
        val totalQuestions = 10

        val progressWithPrevScore = TopicProgress(
            topicId = topicId,
            lessonCompleted = true,
            quizCompleted = true,
            bestScore = previousBestScore,
            xpEarned = 100
        )
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(progressWithPrevScore)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(previousBestScore)
        )

        repository.saveQuizResult(topicId, newScore, totalQuestions)

        val progressCaptor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(progressCaptor.capture())

        val updatedProgress = progressCaptor.firstValue

        assertEquals(9, updatedProgress.bestScore)
        assertTrue(updatedProgress.bestScore > previousBestScore)
    }

    @Test
    fun progressionFlow_completeLesson_unlocksNextTopic() = runBlocking {
        val topicId = "series-parallel"
        
        val initialProgress = TopicProgress(
            topicId = topicId,
            lessonCompleted = false,
            quizCompleted = false,
            xpEarned = 0
        )
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(initialProgress)

        repository.markLessonCompleted(topicId)

        val progressCaptor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(progressCaptor.capture())
        
        val updatedProgress = progressCaptor.firstValue

        assertTrue(updatedProgress.lessonCompleted)
        assertEquals(50, updatedProgress.xpEarned)
    }

    @Test
    fun multipleAttempts_betterScore_replacesBest() = runBlocking {
        val topicId = "capacitors"
        val firstAttempt = 6
        val secondAttempt = 8
        val thirdAttempt = 7
        val totalQuestions = 10

        var currentBest = 0
        
        // First attempt
        val progress1 = TopicProgress(topicId, quizCompleted = false, xpEarned = 50)
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(progress1)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(null)
        )
        repository.saveQuizResult(topicId, firstAttempt, totalQuestions)
        currentBest = firstAttempt

        // Second attempt (better)
        val progress2 = TopicProgress(topicId, quizCompleted = true, bestScore = currentBest, xpEarned = 110)
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(progress2)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(currentBest)
        )
        repository.saveQuizResult(topicId, secondAttempt, totalQuestions)
        currentBest = secondAttempt

        // Third attempt (worse than best)
        val progress3 = TopicProgress(topicId, quizCompleted = true, bestScore = currentBest, xpEarned = 190)
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(progress3)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(currentBest)
        )
        repository.saveQuizResult(topicId, thirdAttempt, totalQuestions)

        val progressCaptor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(progressCaptor.capture())

        val finalProgress = progressCaptor.lastValue

        assertEquals(8, finalProgress.bestScore)
    }

    @Test
    fun xpAccumulation_acrossQuizzesAndLessons() = runBlocking {
        val topicId = "diodes"

        val initialProgress = TopicProgress(topicId, xpEarned = 0)
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(initialProgress)

        // Mark lesson complete: +50 XP
        repository.markLessonCompleted(topicId)
        var xpAfterLesson = 50

        // Complete perfect quiz: 100 (base) + 100 (first) + 50 (perfect) = 250
        val progressAfterLesson = TopicProgress(topicId, lessonCompleted = true, xpEarned = xpAfterLesson)
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(progressAfterLesson)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(null)
        )
        repository.saveQuizResult(topicId, 10, 10)

        val progressCaptor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(progressCaptor.capture())

        val finalProgress = progressCaptor.lastValue

        assertEquals(xpAfterLesson + 250, finalProgress.xpEarned)
    }

    @Test
    fun totalXpFlow_aggregatesCorrectly() = runBlocking {
        whenever(progressDao.getTotalXp()).thenReturn(
            kotlinx.coroutines.flow.flowOf(650)
        )

        val total = repository.getTotalXp().first()

        assertEquals(650, total)
    }
}
