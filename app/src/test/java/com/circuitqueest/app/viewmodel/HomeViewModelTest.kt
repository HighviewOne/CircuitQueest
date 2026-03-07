package com.circuitqueest.app.viewmodel

import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.data.repository.ProgressRepository
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HomeViewModelTest {

    private lateinit var repository: ProgressRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        repository = mock()
    }

    @Test
    fun topicStates_firstTopicNeverLocked() {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        
        viewModel = HomeViewModel(repository)

        val firstTopic = viewModel.topicStates.value.firstOrNull()
        assertFalse(firstTopic?.isLocked == true)
    }

    @Test
    fun topicStates_secondTopicLockedInitially() {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        
        viewModel = HomeViewModel(repository)

        val secondTopic = viewModel.topicStates.value.getOrNull(1)
        assertTrue(secondTopic?.isLocked == true)
    }

    @Test
    fun topicStates_topicUnlocksAfterPreviousCompletion() {
        val firstTopicProgress = TopicProgress(
            topicId = "ohms-law",
            lessonCompleted = true,
            quizCompleted = true
        )
        whenever(repository.getAllProgress()).thenReturn(flowOf(listOf(firstTopicProgress)))
        whenever(repository.getTotalXp()).thenReturn(flowOf(200))
        
        viewModel = HomeViewModel(repository)

        val secondTopic = viewModel.topicStates.value.getOrNull(1)
        assertFalse(secondTopic?.isLocked == true)
    }

    @Test
    fun topicStates_topicLockedIfPreviousQuizNotCompleted() {
        val firstTopicProgress = TopicProgress(
            topicId = "ohms-law",
            lessonCompleted = true,
            quizCompleted = false
        )
        whenever(repository.getAllProgress()).thenReturn(flowOf(listOf(firstTopicProgress)))
        whenever(repository.getTotalXp()).thenReturn(flowOf(50))
        
        viewModel = HomeViewModel(repository)

        val secondTopic = viewModel.topicStates.value.getOrNull(1)
        assertTrue(secondTopic?.isLocked == true)
    }

    @Test
    fun totalXp_returnsAggregatedXp() {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(500))
        
        viewModel = HomeViewModel(repository)

        assertEquals(500, viewModel.totalXp.value)
    }

    @Test
    fun totalXp_returnsZeroWhenEmpty() {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        
        viewModel = HomeViewModel(repository)

        assertEquals(0, viewModel.totalXp.value)
    }

    @Test
    fun categorizedTopics_groupsTopicsByCategory() {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        
        viewModel = HomeViewModel(repository)

        val categorized = viewModel.categorizedTopics.value
        
        assertTrue(categorized.isNotEmpty())
        assertTrue(categorized.all { it.category != null })
        assertTrue(categorized.all { it.topics.isNotEmpty() })
    }

    @Test
    fun topicStates_reactsToProgressUpdates() {
        val initialProgress = listOf(
            TopicProgress("ohms-law", quizCompleted = false)
        )
        val updatedProgress = listOf(
            TopicProgress("ohms-law", quizCompleted = true)
        )
        
        whenever(repository.getAllProgress()).thenReturn(
            flowOf(initialProgress, updatedProgress)
        )
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        
        viewModel = HomeViewModel(repository)

        assertTrue(viewModel.topicStates.value.getOrNull(1)?.isLocked == true)
    }

    @Test
    fun topicStates_includesProgressData() {
        val progress = TopicProgress(
            topicId = "ohms-law",
            lessonCompleted = true,
            quizCompleted = true,
            bestScore = 9,
            xpEarned = 250
        )
        whenever(repository.getAllProgress()).thenReturn(flowOf(listOf(progress)))
        whenever(repository.getTotalXp()).thenReturn(flowOf(250))
        
        viewModel = HomeViewModel(repository)

        val topicState = viewModel.topicStates.value.first()
        assertEquals(progress.bestScore, topicState.progress?.bestScore)
        assertEquals(progress.xpEarned, topicState.progress?.xpEarned)
    }

    @Test
    fun categorizedTopics_allTopicsRepresented() {
        whenever(repository.getAllProgress()).thenReturn(flowOf(emptyList()))
        whenever(repository.getTotalXp()).thenReturn(flowOf(0))
        
        viewModel = HomeViewModel(repository)

        val allTopicsFromCategories = viewModel.categorizedTopics.value
            .flatMap { it.topics }
            .map { it.topic.id }
        
        val allTopics = HomeViewModel.allTopics.map { it.id }
        
        assertEquals(allTopics.toSet(), allTopicsFromCategories.toSet())
    }
}
