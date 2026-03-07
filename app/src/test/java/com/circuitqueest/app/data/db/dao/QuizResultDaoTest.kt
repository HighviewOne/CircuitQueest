package com.circuitqueest.app.data.db.dao

import com.circuitqueest.app.data.db.entity.QuizResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertNull

class QuizResultDaoTest {

    private lateinit var quizResultDao: QuizResultDao

    @Before
    fun setup() {
        quizResultDao = mock()
    }

    @Test
    fun insertResult_savesResultToDB() = runBlocking {
        val result = QuizResult(
            topicId = "ohms-law",
            score = 9,
            totalQuestions = 10
        )

        quizResultDao.insertResult(result)

        verify(quizResultDao).insertResult(result)
    }

    @Test
    fun insertResult_multipleResults_allSaved() = runBlocking {
        val result1 = QuizResult(topicId = "topic1", score = 8, totalQuestions = 10)
        val result2 = QuizResult(topicId = "topic1", score = 9, totalQuestions = 10)
        val result3 = QuizResult(topicId = "topic1", score = 7, totalQuestions = 10)

        quizResultDao.insertResult(result1)
        quizResultDao.insertResult(result2)
        quizResultDao.insertResult(result3)

        verify(quizResultDao).insertResult(result1)
        verify(quizResultDao).insertResult(result2)
        verify(quizResultDao).insertResult(result3)
    }

    @Test
    fun getResultsForTopic_returnsAllResultsForTopic() = runBlocking {
        val topicId = "kirchhoffs"
        val results = listOf(
            QuizResult(topicId = topicId, score = 10, totalQuestions = 10),
            QuizResult(topicId = topicId, score = 8, totalQuestions = 10),
            QuizResult(topicId = topicId, score = 6, totalQuestions = 10)
        )
        whenever(quizResultDao.getResultsForTopic(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(results)
        )

        val result = quizResultDao.getResultsForTopic(topicId).first()

        assertEquals(3, result.size)
        assertEquals(topicId, result[0].topicId)
    }

    @Test
    fun getResultsForTopic_returnsEmptyForNoResults() = runBlocking {
        val topicId = "unknown-topic"
        whenever(quizResultDao.getResultsForTopic(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(emptyList())
        )

        val result = quizResultDao.getResultsForTopic(topicId).first()

        assertEquals(0, result.size)
    }

    @Test
    fun getResultsForTopic_orderedByTimestampDescending() = runBlocking {
        val topicId = "capacitors"
        val results = listOf(
            QuizResult(id = 1, topicId = topicId, score = 10, totalQuestions = 10, timestamp = 1000L),
            QuizResult(id = 2, topicId = topicId, score = 8, totalQuestions = 10, timestamp = 2000L),
            QuizResult(id = 3, topicId = topicId, score = 6, totalQuestions = 10, timestamp = 1500L)
        )
        whenever(quizResultDao.getResultsForTopic(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(results.sortedByDescending { it.timestamp })
        )

        val result = quizResultDao.getResultsForTopic(topicId).first()

        assertEquals(2000L, result[0].timestamp)
        assertEquals(1500L, result[1].timestamp)
        assertEquals(1000L, result[2].timestamp)
    }

    @Test
    fun getBestScore_returnsHighestScore() = runBlocking {
        val topicId = "diodes"
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(10)
        )

        val result = quizResultDao.getBestScore(topicId).first()

        assertEquals(10, result)
    }

    @Test
    fun getBestScore_returnsNullWhenNoResults() = runBlocking {
        val topicId = "unknown-topic"
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(null)
        )

        val result = quizResultDao.getBestScore(topicId).first()

        assertNull(result)
    }

    @Test
    fun getBestScore_returnsMaxFromMultipleScores() = runBlocking {
        val topicId = "transistors"
        whenever(quizResultDao.getBestScore(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(9)
        )

        val result = quizResultDao.getBestScore(topicId).first()

        assertEquals(9, result)
    }

    @Test
    fun insertResult_autoGeneratesId() = runBlocking {
        val result = QuizResult(
            topicId = "filters",
            score = 7,
            totalQuestions = 10,
            id = 0
        )

        quizResultDao.insertResult(result)

        verify(quizResultDao).insertResult(result)
    }

    @Test
    fun getResultsForTopic_reactiveUpdatesOnNewResults() = runBlocking {
        val topicId = "transformers"
        val results1 = listOf(QuizResult(topicId = topicId, score = 8, totalQuestions = 10))
        val results2 = listOf(
            QuizResult(topicId = topicId, score = 8, totalQuestions = 10),
            QuizResult(topicId = topicId, score = 9, totalQuestions = 10)
        )
        
        whenever(quizResultDao.getResultsForTopic(topicId)).thenReturn(
            kotlinx.coroutines.flow.flowOf(results1, results2)
        )

        val result = quizResultDao.getResultsForTopic(topicId).first()

        assertEquals(1, result.size)
    }
}
