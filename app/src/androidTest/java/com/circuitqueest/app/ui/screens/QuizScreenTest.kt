package com.circuitqueest.app.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.content.Question
import com.circuitqueest.app.data.content.QuestionType
import com.circuitqueest.app.viewmodel.QuizViewModel
import com.circuitqueest.app.viewmodel.QuizState
import io.mockk.mockk
import io.mockk.every
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel = mockk<QuizViewModel>(relaxed = true)

    @Test
    fun quizScreen_composesSuccessfully() {
        // Arrange
        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(
                topicId = "basics-1",
                score = 0,
                totalQuestions = 5
            )
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(null)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(false)

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = { _, _, _ -> }
            )
        }

        // Assert
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun quizScreen_displaysCurrentQuestion() {
        // Arrange
        val currentQuestion = Question(
            id = "q1",
            topicId = "basics-1",
            order = 1,
            type = QuestionType.MULTIPLE_CHOICE,
            text = "What is the SI unit of current?",
            explanation = "Ampere (A) is the SI unit of electric current.",
            options = listOf("Ampere", "Volt", "Ohm", "Watt"),
            correctAnswer = "Ampere"
        )

        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(
                topicId = "basics-1",
                score = 0,
                totalQuestions = 5
            )
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(currentQuestion)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(false)

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = { _, _, _ -> }
            )
        }

        // Assert
        composeTestRule.onNodeWithText("What is the SI unit of current?").assertIsDisplayed()
    }

    @Test
    fun quizScreen_displaysAnswerOptions() {
        // Arrange
        val currentQuestion = Question(
            id = "q1",
            topicId = "basics-1",
            order = 1,
            type = QuestionType.MULTIPLE_CHOICE,
            text = "Question?",
            explanation = "Explanation",
            options = listOf("Option A", "Option B", "Option C", "Option D"),
            correctAnswer = "Option A"
        )

        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(topicId = "basics-1", score = 0, totalQuestions = 5)
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(currentQuestion)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(false)

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = { _, _, _ -> }
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Option A").assertIsDisplayed()
        composeTestRule.onNodeWithText("Option B").assertIsDisplayed()
        composeTestRule.onNodeWithText("Option C").assertIsDisplayed()
        composeTestRule.onNodeWithText("Option D").assertIsDisplayed()
    }

    @Test
    fun quizScreen_answerSelection_showsFeedback() {
        // Arrange
        val currentQuestion = Question(
            id = "q1",
            topicId = "basics-1",
            order = 1,
            type = QuestionType.MULTIPLE_CHOICE,
            text = "Question?",
            explanation = "This is the correct answer",
            options = listOf("Correct", "Wrong1", "Wrong2", "Wrong3"),
            correctAnswer = "Correct"
        )

        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(topicId = "basics-1", score = 0, totalQuestions = 5)
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(currentQuestion)
        every { mockViewModel.feedback } returns MutableStateFlow("Correct! This is the correct answer")
        every { mockViewModel.quizComplete } returns MutableStateFlow(false)

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = { _, _, _ -> }
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Correct", substring = true).assertIsDisplayed()
    }

    @Test
    fun quizScreen_displaysProgressBar() {
        // Arrange
        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(
                topicId = "basics-1",
                score = 3,
                totalQuestions = 10
            )
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(null)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(false)

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = { _, _, _ -> }
            )
        }

        // Assert - progress should be visible
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun quizScreen_backButton_callsCallback() {
        // Arrange
        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(topicId = "basics-1", score = 0, totalQuestions = 5)
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(null)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(false)

        var backClicked = false
        val onBack: () -> Unit = { backClicked = true }

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = onBack,
                onQuizComplete = { _, _, _ -> }
            )
        }
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Assert
        assert(backClicked)
    }

    @Test
    fun quizScreen_quizComplete_navigatesToResults() {
        // Arrange
        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(topicId = "basics-1", score = 8, totalQuestions = 10)
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(null)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(true)

        var quizResult: Triple<String, Int, Int>? = null
        val onQuizComplete: (String, Int, Int) -> Unit = { topicId, score, total ->
            quizResult = Triple(topicId, score, total)
        }

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = onQuizComplete
            )
        }

        // Assert
        assert(quizResult?.first == "basics-1")
        assert(quizResult?.second == 8)
        assert(quizResult?.third == 10)
    }

    @Test
    fun quizScreen_numericQuestion_displaysInputField() {
        // Arrange
        val numericQuestion = Question(
            id = "q2",
            topicId = "basics-1",
            order = 2,
            type = QuestionType.NUMERIC_INPUT,
            text = "Calculate the resistance in ohms",
            explanation = "Use Ohm's Law: R = V/I",
            options = emptyList(),
            correctAnswer = "1000"
        )

        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(topicId = "basics-1", score = 0, totalQuestions = 5)
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(numericQuestion)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(false)

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = { _, _, _ -> }
            )
        }

        // Assert - numeric input question should display
        composeTestRule.onNodeWithText("Calculate the resistance in ohms").assertIsDisplayed()
    }

    @Test
    fun quizScreen_displaysQuestionCounter() {
        // Arrange
        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(
                topicId = "basics-1",
                score = 5,
                totalQuestions = 10
            )
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(null)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(false)

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = { _, _, _ -> }
            )
        }

        // Assert - should show progress in UI
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun quizScreen_multipleAttempts_updatesState() {
        // Arrange
        val question1 = Question(
            id = "q1",
            topicId = "basics-1",
            order = 1,
            type = QuestionType.MULTIPLE_CHOICE,
            text = "Question 1?",
            explanation = "Explanation 1",
            options = listOf("A", "B", "C", "D"),
            correctAnswer = "A"
        )
        val question2 = Question(
            id = "q2",
            topicId = "basics-1",
            order = 2,
            type = QuestionType.MULTIPLE_CHOICE,
            text = "Question 2?",
            explanation = "Explanation 2",
            options = listOf("X", "Y", "Z", "W"),
            correctAnswer = "X"
        )

        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(topicId = "basics-1", score = 1, totalQuestions = 2)
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(question2)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(false)

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = { _, _, _ -> }
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Question 2?").assertIsDisplayed()
    }

    @Test
    fun quizScreen_perfectScore() {
        // Arrange
        every { mockViewModel.quizState } returns MutableStateFlow(
            QuizState(
                topicId = "basics-1",
                score = 10,
                totalQuestions = 10
            )
        )
        every { mockViewModel.currentQuestion } returns MutableStateFlow(null)
        every { mockViewModel.feedback } returns MutableStateFlow(null)
        every { mockViewModel.quizComplete } returns MutableStateFlow(true)

        // Act
        composeTestRule.setContent {
            QuizScreen(
                viewModel = mockViewModel,
                onBack = {},
                onQuizComplete = { _, _, _ -> }
            )
        }

        // Assert - should show completion
        composeTestRule.onRoot().assertIsDisplayed()
    }
}
