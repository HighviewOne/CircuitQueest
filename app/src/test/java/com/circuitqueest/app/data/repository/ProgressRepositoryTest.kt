package com.circuitqueest.app.data.repository

import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.dao.QuizResultDao
import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.data.db.entity.QuizResult
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProgressRepositoryTest {

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
    fun getAllProgress_delegatesToDao() {
        val mockProgress = listOf(
            TopicProgress("topic1", lessonCompleted = true, quizCompleted = false),
            TopicProgress("topic2", lessonCompleted = false, quizCompleted = false)
        )
        whenever(progressDao.getAllProgress()).thenReturn(flowOf(mockProgress))

        val result = repository.getAllProgress()

        verify(progressDao).getAllProgress()
    }

    @Test
    fun getProgress_returnsProgressForTopic() {
        val mockProgress = TopicProgress("topic1", lessonCompleted = true, xpEarned = 100)
        whenever(progressDao.getProgress("topic1")).thenReturn(flowOf(mockProgress))

        val result = repository.getProgress("topic1")

        verify(progressDao).getProgress("topic1")
    }

    @Test
    fun getProgress_returnsNullWhenNoProgress() {
        whenever(progressDao.getProgress("unknown")).thenReturn(flowOf(null))

        val result = repository.getProgress("unknown")

        verify(progressDao).getProgress("unknown")
    }

    @Test
    fun getTotalXp_delegatesToDao() {
        whenever(progressDao.getTotalXp()).thenReturn(flowOf(500))

        val result = repository.getTotalXp()

        verify(progressDao).getTotalXp()
    }

    @Test
    fun getTotalXp_returnsZeroWhenEmpty() {
        whenever(progressDao.getTotalXp()).thenReturn(flowOf(0))

        val result = repository.getTotalXp()

        verify(progressDao).getTotalXp()
    }

    @Test
    fun markLessonCompleted_firstTime_addsXp() = runBlocking {
        val topicId = "topic1"
        val existingProgress = TopicProgress(topicId, lessonCompleted = false, xpEarned = 0)
        
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(existingProgress)

        repository.markLessonCompleted(topicId)

        val captor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(captor.capture())
        
        val updatedProgress = captor.firstValue
        assertEquals(true, updatedProgress.lessonCompleted)
        assertEquals(50, updatedProgress.xpEarned) // Lesson completion bonus
    }

    @Test
    fun markLessonCompleted_alreadyCompleted_noAdditionalXp() = runBlocking {
        val topicId = "topic1"
        val existingProgress = TopicProgress(topicId, lessonCompleted = true, xpEarned = 50)
        
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(existingProgress)

        repository.markLessonCompleted(topicId)

        val captor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(captor.capture())
        
        val updatedProgress = captor.firstValue
        assertEquals(50, updatedProgress.xpEarned) // No additional XP
    }

    @Test
    fun saveQuizResult_firstCompletion_addsXp() = runBlocking {
        val topicId = "topic1"
        val score = 10
        val totalQuestions = 10
        val existingProgress = TopicProgress(
            topicId, 
            lessonCompleted = true, 
            quizCompleted = false,
            bestScore = 0,
            xpEarned = 50
        )
        
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(existingProgress)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(flowOf(null))

        repository.saveQuizResult(topicId, score, totalQuestions)

        val progressCaptor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(progressCaptor.capture())
        
        val updatedProgress = progressCaptor.firstValue
        assertEquals(true, updatedProgress.quizCompleted)
        assertEquals(10, updatedProgress.bestScore)
        // XP: base score*10 (100) + first completion bonus (100) = 200
        assertEquals(250, updatedProgress.xpEarned) // 50 previous + 200 new
    }

    @Test
    fun saveQuizResult_notFirstCompletion_updatesIfBetterScore() = runBlocking {
        val topicId = "topic1"
        val score = 8
        val totalQuestions = 10
        val existingProgress = TopicProgress(
            topicId,
            lessonCompleted = true,
            quizCompleted = true,
            bestScore = 6,
            xpEarned = 200
        )
        
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(existingProgress)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(flowOf(6))

        repository.saveQuizResult(topicId, score, totalQuestions)

        val progressCaptor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(progressCaptor.capture())
        
        val updatedProgress = progressCaptor.firstValue
        assertEquals(8, updatedProgress.bestScore) // Updated to new best
        // XP: only the improvement over the previous best (8 - 6) * 10
        assertEquals(220, updatedProgress.xpEarned)
    }

    @Test
    fun saveQuizResult_worseScore_noUpdate() = runBlocking {
        val topicId = "topic1"
        val score = 4
        val totalQuestions = 10
        val existingProgress = TopicProgress(
            topicId,
            lessonCompleted = true,
            quizCompleted = true,
            bestScore = 8,
            xpEarned = 300
        )
        
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(existingProgress)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(flowOf(8))

        repository.saveQuizResult(topicId, score, totalQuestions)

        val progressCaptor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(progressCaptor.capture())
        
        val updatedProgress = progressCaptor.firstValue
        assertEquals(8, updatedProgress.bestScore) // Unchanged
        // Retaking below the best score earns nothing
        assertEquals(300, updatedProgress.xpEarned)
    }

    @Test
    fun saveQuizResult_savesQuizResultToDao() = runBlocking {
        val topicId = "topic1"
        val score = 7
        val totalQuestions = 10
        val existingProgress = TopicProgress(topicId, quizCompleted = false)
        
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(existingProgress)
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(flowOf(null))

        repository.saveQuizResult(topicId, score, totalQuestions)

        val resultCaptor = argumentCaptor<QuizResult>()
        verify(quizResultDao).insertResult(resultCaptor.capture())
        
        val savedResult = resultCaptor.firstValue
        assertEquals(topicId, savedResult.topicId)
        assertEquals(score, savedResult.score)
        assertEquals(totalQuestions, savedResult.totalQuestions)
    }

    @Test
    fun getQuizResults_delegatesToDao() {
        val mockResults = listOf(
            QuizResult(topicId = "topic1", score = 8, totalQuestions = 10),
            QuizResult(topicId = "topic1", score = 6, totalQuestions = 10)
        )
        whenever(quizResultDao.getResultsForTopic("topic1")).thenReturn(flowOf(mockResults))

        val result = repository.getQuizResults("topic1")

        verify(quizResultDao).getResultsForTopic("topic1")
    }

    @Test
    fun saveQuizResult_failingFirstAttempt_doesNotComplete() = runBlocking {
        val topicId = "topic1"
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(TopicProgress(topicId))

        val xp = repository.recordQuizResult(topicId, score = 3, totalQuestions = 10)

        val captor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(captor.capture())
        assertEquals(false, captor.firstValue.quizCompleted)
        assertEquals(3, captor.firstValue.bestScore)
        assertEquals(30, xp) // points, but no first-pass bonus
    }

    @Test
    fun saveQuizResult_passAfterFail_paysBonusOnce() = runBlocking {
        val topicId = "topic1"
        val afterFail = TopicProgress(topicId, quizCompleted = false, bestScore = 3, xpEarned = 30)
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(afterFail)

        val xp = repository.recordQuizResult(topicId, score = 7, totalQuestions = 10)

        val captor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(captor.capture())
        assertEquals(true, captor.firstValue.quizCompleted)
        assertEquals(140, xp) // (7 - 3) * 10 + 100
        assertEquals(170, captor.firstValue.xpEarned)
    }

    @Test
    fun saveQuizResult_failAfterPass_staysCompleted() = runBlocking {
        val topicId = "topic1"
        val passed = TopicProgress(topicId, quizCompleted = true, bestScore = 8, xpEarned = 180)
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(passed)

        val xp = repository.recordQuizResult(topicId, score = 2, totalQuestions = 10)

        val captor = argumentCaptor<TopicProgress>()
        verify(progressDao).upsertProgress(captor.capture())
        assertEquals(true, captor.firstValue.quizCompleted)
        assertEquals(8, captor.firstValue.bestScore)
        assertEquals(0, xp)
    }
}
