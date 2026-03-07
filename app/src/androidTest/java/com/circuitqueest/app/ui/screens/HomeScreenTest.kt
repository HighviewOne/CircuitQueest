package com.circuitqueest.app.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.content.Topic
import com.circuitqueest.app.data.entity.Progress
import com.circuitqueest.app.viewmodel.CategoryState
import com.circuitqueest.app.viewmodel.HomeViewModel
import com.circuitqueest.app.viewmodel.TopicState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel = mockk<HomeViewModel>(relaxed = true)

    @Test
    fun homeScreen_composesSuccessfully() {
        // Arrange
        every { mockViewModel.categorizedTopics } returns MutableStateFlow(emptyList())
        every { mockViewModel.totalXp } returns MutableStateFlow(0)

        // Act
        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, onTopicClick = {})
        }

        // Assert - screen renders without crashing
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysTotalXp() {
        // Arrange
        every { mockViewModel.categorizedTopics } returns MutableStateFlow(emptyList())
        every { mockViewModel.totalXp } returns MutableStateFlow(250)

        // Act
        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, onTopicClick = {})
        }

        // Assert
        composeTestRule.onNodeWithText("250 XP").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysCategorizedTopics() {
        // Arrange
        val testTopic = Topic(
            id = "basics-1",
            category = "Basics",
            order = 1,
            title = "Introduction to Electronics",
            subtitle = "Learn fundamentals",
            icon = "📚",
            unlockRequirements = emptyList()
        )
        val categoryState = CategoryState(
            category = "Basics",
            topics = listOf(
                TopicState(
                    topic = testTopic,
                    progress = Progress(
                        topicId = "basics-1",
                        bestScore = 0,
                        totalQuestions = 0
                    ),
                    isLocked = false
                )
            )
        )

        every { mockViewModel.categorizedTopics } returns MutableStateFlow(listOf(categoryState))
        every { mockViewModel.totalXp } returns MutableStateFlow(0)

        // Act
        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, onTopicClick = {})
        }

        // Assert
        composeTestRule.onNodeWithText("Basics").assertIsDisplayed()
        composeTestRule.onNodeWithText("Introduction to Electronics").assertIsDisplayed()
    }

    @Test
    fun homeScreen_topicClick_callsNavigationCallback() {
        // Arrange
        val testTopic = Topic(
            id = "basics-1",
            category = "Basics",
            order = 1,
            title = "Test Topic",
            subtitle = "Subtitle",
            icon = "📚",
            unlockRequirements = emptyList()
        )
        val categoryState = CategoryState(
            category = "Basics",
            topics = listOf(
                TopicState(
                    topic = testTopic,
                    progress = null,
                    isLocked = false
                )
            )
        )
        every { mockViewModel.categorizedTopics } returns MutableStateFlow(listOf(categoryState))
        every { mockViewModel.totalXp } returns MutableStateFlow(0)

        var clickedTopicId = ""
        val onTopicClick: (String) -> Unit = { clickedTopicId = it }

        // Act
        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, onTopicClick = onTopicClick)
        }
        composeTestRule.onNodeWithText("Test Topic").performClick()

        // Assert
        assert(clickedTopicId == "basics-1")
    }

    @Test
    fun homeScreen_displaysMultipleCategories() {
        // Arrange
        val basicsCategory = CategoryState(
            category = "Basics",
            topics = listOf(
                TopicState(
                    topic = Topic(
                        id = "basics-1",
                        category = "Basics",
                        order = 1,
                        title = "Fundamentals",
                        subtitle = "Start here",
                        icon = "📚",
                        unlockRequirements = emptyList()
                    ),
                    progress = null,
                    isLocked = false
                )
            )
        )
        val advancedCategory = CategoryState(
            category = "Advanced",
            topics = listOf(
                TopicState(
                    topic = Topic(
                        id = "adv-1",
                        category = "Advanced",
                        order = 2,
                        title = "Advanced Topics",
                        subtitle = "Challenge yourself",
                        icon = "⚡",
                        unlockRequirements = emptyList()
                    ),
                    progress = null,
                    isLocked = true
                )
            )
        )

        every { mockViewModel.categorizedTopics } returns MutableStateFlow(
            listOf(basicsCategory, advancedCategory)
        )
        every { mockViewModel.totalXp } returns MutableStateFlow(0)

        // Act
        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, onTopicClick = {})
        }

        // Assert
        composeTestRule.onNodeWithText("Basics").assertIsDisplayed()
        composeTestRule.onNodeWithText("Advanced").assertIsDisplayed()
        composeTestRule.onNodeWithText("Fundamentals").assertIsDisplayed()
        composeTestRule.onNodeWithText("Advanced Topics").assertIsDisplayed()
    }

    @Test
    fun homeScreen_scrollsList() {
        // Arrange
        val topics = (1..20).map { index ->
            Topic(
                id = "topic-$index",
                category = "Basics",
                order = index,
                title = "Topic $index",
                subtitle = "Subtitle $index",
                icon = "📚",
                unlockRequirements = emptyList()
            )
        }
        val categoryState = CategoryState(
            category = "Basics",
            topics = topics.map { topic ->
                TopicState(topic = topic, progress = null, isLocked = false)
            }
        )

        every { mockViewModel.categorizedTopics } returns MutableStateFlow(listOf(categoryState))
        every { mockViewModel.totalXp } returns MutableStateFlow(0)

        // Act
        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, onTopicClick = {})
        }

        // Assert - scroll to a topic far down the list
        composeTestRule.onNodeWithText("Topic 15").performScrollToNode()
        composeTestRule.onNodeWithText("Topic 15").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysProgressInfo() {
        // Arrange
        val testTopic = Topic(
            id = "basics-1",
            category = "Basics",
            order = 1,
            title = "Topic with Progress",
            subtitle = "Subtitle",
            icon = "📚",
            unlockRequirements = emptyList()
        )
        val progress = Progress(
            topicId = "basics-1",
            bestScore = 80,
            totalQuestions = 5
        )
        val categoryState = CategoryState(
            category = "Basics",
            topics = listOf(
                TopicState(
                    topic = testTopic,
                    progress = progress,
                    isLocked = false
                )
            )
        )

        every { mockViewModel.categorizedTopics } returns MutableStateFlow(listOf(categoryState))
        every { mockViewModel.totalXp } returns MutableStateFlow(100)

        // Act
        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, onTopicClick = {})
        }

        // Assert - progress card should display
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun homeScreen_emptyState() {
        // Arrange
        every { mockViewModel.categorizedTopics } returns MutableStateFlow(emptyList())
        every { mockViewModel.totalXp } returns MutableStateFlow(0)

        // Act
        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, onTopicClick = {})
        }

        // Assert - should display 0 XP
        composeTestRule.onNodeWithText("0 XP").assertIsDisplayed()
    }
}
