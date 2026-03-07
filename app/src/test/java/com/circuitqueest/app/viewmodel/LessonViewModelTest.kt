package com.circuitqueest.app.viewmodel

import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.data.repository.ProgressRepository
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LessonViewModelTest {

    private lateinit var repository: ProgressRepository
    private lateinit var viewModel: LessonViewModel

    @Before
    fun setup() {
        repository = mock()
    }

    @Test
    fun topic_returnsCorrectTopic() {
        val topicId = "ohms-law"
        viewModel = LessonViewModel(topicId, repository)

        val topic = viewModel.topic.value
        
        assertEquals(topicId, topic?.id)
    }

    @Test
    fun lessonCompleted_initiallyFalse() {
        val topicId = "ohms-law"
        whenever(repository.getProgress(topicId)).thenReturn(flowOf(null))
        
        viewModel = LessonViewModel(topicId, repository)

        assertFalse(viewModel.lessonCompleted.value)
    }

    @Test
    fun lessonCompleted_trueWhenProgressMarked() {
        val topicId = "ohms-law"
        val completedProgress = TopicProgress(
            topicId = topicId,
            lessonCompleted = true,
            quizCompleted = false
        )
        whenever(repository.getProgress(topicId)).thenReturn(flowOf(completedProgress))
        
        viewModel = LessonViewModel(topicId, repository)

        assertTrue(viewModel.lessonCompleted.value)
    }

    @Test
    suspend fun markLessonComplete_callsRepository() {
        val topicId = "ohms-law"
        whenever(repository.getProgress(topicId)).thenReturn(flowOf(null))
        viewModel = LessonViewModel(topicId, repository)

        viewModel.markLessonComplete()

        verify(repository).markLessonCompleted(topicId)
    }

    @Test
    fun lessonCompleted_reactsToProgressChanges() {
        val topicId = "ohms-law"
        val incompletedProgress = TopicProgress(
            topicId = topicId,
            lessonCompleted = false
        )
        val completedProgress = TopicProgress(
            topicId = topicId,
            lessonCompleted = true
        )
        
        whenever(repository.getProgress(topicId)).thenReturn(
            flowOf(incompletedProgress, completedProgress)
        )
        
        viewModel = LessonViewModel(topicId, repository)

        assertFalse(viewModel.lessonCompleted.value)
    }
}
