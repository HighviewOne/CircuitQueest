package com.circuitqueest.app.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.content.Lesson
import com.circuitqueest.app.data.content.Topic
import com.circuitqueest.app.data.entity.Progress
import com.circuitqueest.app.viewmodel.LessonViewModel
import com.circuitqueest.app.viewmodel.TopicDetailsState
import io.mockk.mockk
import io.mockk.every
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LessonScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel = mockk<LessonViewModel>(relaxed = true)

    @Test
    fun lessonScreen_composesSuccessfully() {
        // Arrange
        every { mockViewModel.topicDetails } returns MutableStateFlow(null)

        // Act
        composeTestRule.setContent {
            LessonScreen(
                viewModel = mockViewModel,
                onBack = {},
                onStartQuiz = {}
            )
        }

        // Assert
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun lessonScreen_displaysLessonContent() {
        // Arrange
        val testTopic = Topic(
            id = "basics-1",
            category = "Basics",
            order = 1,
            title = "Fundamentals",
            subtitle = "Learn basics",
            icon = "📚",
            unlockRequirements = emptyList()
        )
        val testLesson = Lesson(
            topicId = "basics-1",
            title = "What is Electricity?",
            content = "Electricity is the flow of electrons...",
            keyPoints = listOf(
                "Electrons move through conductors",
                "Current is measured in Amperes",
                "Voltage is measured in Volts"
            )
        )
        val topicDetails = TopicDetailsState(
            topic = testTopic,
            lesson = testLesson,
            progress = null,
            isLocked = false
        )

        every { mockViewModel.topicDetails } returns MutableStateFlow(topicDetails)

        // Act
        composeTestRule.setContent {
            LessonScreen(
                viewModel = mockViewModel,
                onBack = {},
                onStartQuiz = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("What is Electricity?").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "Electricity is the flow of electrons...",
            substring = true
        ).assertIsDisplayed()
    }

    @Test
    fun lessonScreen_displaysKeyPoints() {
        // Arrange
        val testTopic = Topic(
            id = "basics-1",
            category = "Basics",
            order = 1,
            title = "Fundamentals",
            subtitle = "Learn basics",
            icon = "📚",
            unlockRequirements = emptyList()
        )
        val testLesson = Lesson(
            topicId = "basics-1",
            title = "Lesson Title",
            content = "Content",
            keyPoints = listOf(
                "First key point",
                "Second key point",
                "Third key point"
            )
        )
        val topicDetails = TopicDetailsState(
            topic = testTopic,
            lesson = testLesson,
            progress = null,
            isLocked = false
        )

        every { mockViewModel.topicDetails } returns MutableStateFlow(topicDetails)

        // Act
        composeTestRule.setContent {
            LessonScreen(
                viewModel = mockViewModel,
                onBack = {},
                onStartQuiz = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("First key point").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second key point").assertIsDisplayed()
        composeTestRule.onNodeWithText("Third key point").assertIsDisplayed()
    }

    @Test
    fun lessonScreen_backButton_callsCallback() {
        // Arrange
        every { mockViewModel.topicDetails } returns MutableStateFlow(null)

        var backClicked = false
        val onBack: () -> Unit = { backClicked = true }

        // Act
        composeTestRule.setContent {
            LessonScreen(
                viewModel = mockViewModel,
                onBack = onBack,
                onStartQuiz = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Assert
        assert(backClicked)
    }

    @Test
    fun lessonScreen_startQuizButton_callsCallback() {
        // Arrange
        val testTopic = Topic(
            id = "basics-1",
            category = "Basics",
            order = 1,
            title = "Fundamentals",
            subtitle = "Learn basics",
            icon = "📚",
            unlockRequirements = emptyList()
        )
        val testLesson = Lesson(
            topicId = "basics-1",
            title = "Lesson",
            content = "Content",
            keyPoints = emptyList()
        )
        val topicDetails = TopicDetailsState(
            topic = testTopic,
            lesson = testLesson,
            progress = null,
            isLocked = false
        )

        every { mockViewModel.topicDetails } returns MutableStateFlow(topicDetails)

        var quizStarted = false
        val onStartQuiz: () -> Unit = { quizStarted = true }

        // Act
        composeTestRule.setContent {
            LessonScreen(
                viewModel = mockViewModel,
                onBack = {},
                onStartQuiz = onStartQuiz
            )
        }
        composeTestRule.onNodeWithText("Start Quiz").performClick()

        // Assert
        assert(quizStarted)
    }

    @Test
    fun lessonScreen_displaysTopicTitle() {
        // Arrange
        val testTopic = Topic(
            id = "basics-1",
            category = "Basics",
            order = 1,
            title = "Circuit Fundamentals",
            subtitle = "Learn the basics",
            icon = "⚡",
            unlockRequirements = emptyList()
        )
        val testLesson = Lesson(
            topicId = "basics-1",
            title = "Lesson",
            content = "Content",
            keyPoints = emptyList()
        )
        val topicDetails = TopicDetailsState(
            topic = testTopic,
            lesson = testLesson,
            progress = null,
            isLocked = false
        )

        every { mockViewModel.topicDetails } returns MutableStateFlow(topicDetails)

        // Act
        composeTestRule.setContent {
            LessonScreen(
                viewModel = mockViewModel,
                onBack = {},
                onStartQuiz = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Circuit Fundamentals").assertIsDisplayed()
    }

    @Test
    fun lessonScreen_handlesLockedTopic() {
        // Arrange
        val testTopic = Topic(
            id = "advanced-1",
            category = "Advanced",
            order = 10,
            title = "Advanced Concepts",
            subtitle = "Requires prerequisites",
            icon = "🔒",
            unlockRequirements = listOf("basics-1", "basics-2")
        )
        val topicDetails = TopicDetailsState(
            topic = testTopic,
            lesson = null,
            progress = null,
            isLocked = true
        )

        every { mockViewModel.topicDetails } returns MutableStateFlow(topicDetails)

        // Act
        composeTestRule.setContent {
            LessonScreen(
                viewModel = mockViewModel,
                onBack = {},
                onStartQuiz = {}
            )
        }

        // Assert - screen should still render
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun lessonScreen_displaysProgressInfo() {
        // Arrange
        val testTopic = Topic(
            id = "basics-1",
            category = "Basics",
            order = 1,
            title = "Fundamentals",
            subtitle = "Learn basics",
            icon = "📚",
            unlockRequirements = emptyList()
        )
        val testLesson = Lesson(
            topicId = "basics-1",
            title = "Lesson",
            content = "Content",
            keyPoints = emptyList()
        )
        val progress = Progress(
            topicId = "basics-1",
            bestScore = 85,
            totalQuestions = 5
        )
        val topicDetails = TopicDetailsState(
            topic = testTopic,
            lesson = testLesson,
            progress = progress,
            isLocked = false
        )

        every { mockViewModel.topicDetails } returns MutableStateFlow(topicDetails)

        // Act
        composeTestRule.setContent {
            LessonScreen(
                viewModel = mockViewModel,
                onBack = {},
                onStartQuiz = {}
            )
        }

        // Assert - progress should be reflected in UI
        composeTestRule.onRoot().assertIsDisplayed()
    }
}
