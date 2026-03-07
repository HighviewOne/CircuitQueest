package com.circuitqueest.app.data.db.dao

import com.circuitqueest.app.data.db.entity.TopicProgress
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProgressDaoTest {

    private lateinit var progressDao: ProgressDao

    @Before
    fun setup() {
        progressDao = mock()
    }

    @Test
    fun getProgress_returnsProgressForValidTopicId() = runBlocking {
        val topicId = "ohms-law"
        val expectedProgress = TopicProgress(
            topicId = topicId,
            lessonCompleted = true,
            quizCompleted = false,
            bestScore = 8,
            xpEarned = 150
        )
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(expectedProgress)

        val result = progressDao.getProgressOnce(topicId)

        assertEquals(expectedProgress.topicId, result?.topicId)
        assertEquals(expectedProgress.bestScore, result?.bestScore)
        assertEquals(expectedProgress.xpEarned, result?.xpEarned)
    }

    @Test
    fun getProgress_returnsNullForUnknownTopicId() = runBlocking {
        val topicId = "unknown-topic"
        whenever(progressDao.getProgressOnce(topicId)).thenReturn(null)

        val result = progressDao.getProgressOnce(topicId)

        assertNull(result)
    }

    @Test
    fun upsertProgress_insertsNewProgress() = runBlocking {
        val progress = TopicProgress(
            topicId = "series-parallel",
            lessonCompleted = false,
            quizCompleted = false,
            xpEarned = 0
        )

        progressDao.upsertProgress(progress)

        verify(progressDao).upsertProgress(progress)
    }

    @Test
    fun upsertProgress_updatesExistingProgress() = runBlocking {
        val topicId = "ohms-law"
        val updatedProgress = TopicProgress(
            topicId = topicId,
            lessonCompleted = true,
            quizCompleted = true,
            bestScore = 10,
            xpEarned = 200
        )

        progressDao.upsertProgress(updatedProgress)

        verify(progressDao).upsertProgress(updatedProgress)
    }

    @Test
    fun getAllProgress_returnsAllProgressRecords() = runBlocking {
        val progressList = listOf(
            TopicProgress("topic1", lessonCompleted = true),
            TopicProgress("topic2", lessonCompleted = false),
            TopicProgress("topic3", lessonCompleted = true)
        )
        whenever(progressDao.getAllProgress()).thenReturn(kotlinx.coroutines.flow.flowOf(progressList))

        val result = progressDao.getAllProgress().first()

        assertEquals(3, result.size)
    }

    @Test
    fun getAllProgress_returnsEmptyListWhenNoProgress() = runBlocking {
        whenever(progressDao.getAllProgress()).thenReturn(kotlinx.coroutines.flow.flowOf(emptyList()))

        val result = progressDao.getAllProgress().first()

        assertEquals(0, result.size)
    }

    @Test
    fun getTotalXp_summsAllXpEarned() = runBlocking {
        whenever(progressDao.getTotalXp()).thenReturn(kotlinx.coroutines.flow.flowOf(500))

        val result = progressDao.getTotalXp().first()

        assertEquals(500, result)
    }

    @Test
    fun getTotalXp_returnsZeroWhenEmpty() = runBlocking {
        whenever(progressDao.getTotalXp()).thenReturn(kotlinx.coroutines.flow.flowOf(0))

        val result = progressDao.getTotalXp().first()

        assertEquals(0, result)
    }
}
