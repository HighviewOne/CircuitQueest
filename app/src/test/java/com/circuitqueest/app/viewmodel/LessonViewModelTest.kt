package com.circuitqueest.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.data.repository.ProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LessonViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun makeViewModel(topicId: String, repository: ProgressRepository): LessonViewModel {
        val savedStateHandle = SavedStateHandle(mapOf("topicId" to topicId))
        return LessonViewModel(repository, savedStateHandle)
    }

    @Test
    fun topic_validId_returnsNonNullTopic() {
        val repository: ProgressRepository = mock()
        whenever(repository.getProgress("ohms_law")).thenReturn(flowOf(null))
        val viewModel = makeViewModel("ohms_law", repository)

        assertNotNull(viewModel.topic.value)
    }

    @Test
    fun lessonCompleted_initiallyFalse() {
        val repository: ProgressRepository = mock()
        whenever(repository.getProgress("ohms_law")).thenReturn(flowOf(null))
        val viewModel = makeViewModel("ohms_law", repository)

        assertFalse(viewModel.lessonCompleted.value)
    }

    @Test
    fun lessonCompleted_trueWhenProgressMarked() = runTest {
        val repository: ProgressRepository = mock()
        val completedProgress = TopicProgress(
            topicId = "ohms_law",
            lessonCompleted = true,
            quizCompleted = false
        )
        whenever(repository.getProgress("ohms_law")).thenReturn(flowOf(completedProgress))
        val viewModel = makeViewModel("ohms_law", repository)

        val job = launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.lessonCompleted.collect {} }
        assertTrue(viewModel.lessonCompleted.value)
        job.cancel()
    }

    @Test
    fun markLessonComplete_callsRepository() = runBlocking {
        val repository: ProgressRepository = mock()
        whenever(repository.getProgress("ohms_law")).thenReturn(flowOf(null))
        val viewModel = makeViewModel("ohms_law", repository)

        viewModel.markLessonComplete()

        verify(repository).markLessonCompleted("ohms_law")
    }
}
