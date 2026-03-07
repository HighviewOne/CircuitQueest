package com.circuitqueest.app.viewmodel

import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.data.repository.ProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: ProgressRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun topicStates_firstTopicNeverLocked() {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        viewModel = HomeViewModel(repository)

        assertFalse(viewModel.topicStates.value.first().isLocked)
    }

    @Test
    fun topicStates_secondTopicLockedInitially() {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        viewModel = HomeViewModel(repository)

        assertTrue(viewModel.topicStates.value.getOrNull(1)?.isLocked == true)
    }

    @Test
    fun topicStates_topicUnlocksAfterPreviousCompletion() = runTest {
        val firstTopicProgress = TopicProgress(
            topicId = "ohms_law",
            lessonCompleted = true,
            quizCompleted = true
        )
        whenever(repository.getAllProgress()).thenReturn(flowOf(listOf(firstTopicProgress)))
        whenever(repository.getTotalXp()).thenReturn(flowOf(200))
        viewModel = HomeViewModel(repository)

        val job = launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.topicStates.collect {} }
        assertFalse(viewModel.topicStates.value.getOrNull(1)?.isLocked == true)
        job.cancel()
    }

    @Test
    fun topicStates_topicLockedIfPreviousQuizNotCompleted() {
        val firstTopicProgress = TopicProgress(
            topicId = "ohms_law",
            lessonCompleted = true,
            quizCompleted = false
        )
        whenever(repository.getAllProgress()).thenReturn(flowOf(listOf(firstTopicProgress)))
        whenever(repository.getTotalXp()).thenReturn(flowOf(50))
        viewModel = HomeViewModel(repository)

        assertTrue(viewModel.topicStates.value.getOrNull(1)?.isLocked == true)
    }

    @Test
    fun totalXp_returnsAggregatedXp() = runTest {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(500))
        viewModel = HomeViewModel(repository)

        val job = launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.totalXp.collect {} }
        assertEquals(500, viewModel.totalXp.value)
        job.cancel()
    }

    @Test
    fun totalXp_returnsZeroWhenEmpty() {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        viewModel = HomeViewModel(repository)

        assertEquals(0, viewModel.totalXp.value)
    }

    @Test
    fun categorizedTopics_groupsTopicsByCategory() = runTest {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        viewModel = HomeViewModel(repository)

        val job = launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.categorizedTopics.collect {} }
        val categorized = viewModel.categorizedTopics.value
        assertTrue(categorized.isNotEmpty())
        assertTrue(categorized.all { it.topics.isNotEmpty() })
        job.cancel()
    }

    @Test
    fun topicStates_reactsToProgressUpdates() {
        whenever(repository.getAllProgress()).thenReturn(
            flowOf(listOf(TopicProgress("ohms_law", quizCompleted = false)))
        )
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        viewModel = HomeViewModel(repository)

        assertTrue(viewModel.topicStates.value.getOrNull(1)?.isLocked == true)
    }

    @Test
    fun topicStates_includesProgressData() = runTest {
        val progress = TopicProgress(
            topicId = "ohms_law",
            lessonCompleted = true,
            quizCompleted = true,
            bestScore = 9,
            xpEarned = 250
        )
        whenever(repository.getAllProgress()).thenReturn(flowOf(listOf(progress)))
        whenever(repository.getTotalXp()).thenReturn(flowOf(250))
        viewModel = HomeViewModel(repository)

        val job = launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.topicStates.collect {} }
        val topicState = viewModel.topicStates.value.first()
        assertEquals(progress.bestScore, topicState.progress?.bestScore)
        assertEquals(progress.xpEarned, topicState.progress?.xpEarned)
        job.cancel()
    }

    @Test
    fun categorizedTopics_allTopicsRepresented() = runTest {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        viewModel = HomeViewModel(repository)

        val job = launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.categorizedTopics.collect {} }
        val allTopicsFromCategories = viewModel.categorizedTopics.value
            .flatMap { it.topics }
            .map { it.topic.id }
        val allTopics = HomeViewModel.allTopics.map { it.id }
        assertEquals(allTopics.toSet(), allTopicsFromCategories.toSet())
        job.cancel()
    }
}
