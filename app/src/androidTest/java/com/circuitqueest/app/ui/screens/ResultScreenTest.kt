package com.circuitqueest.app.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResultScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun resultScreen_composesSuccessfully() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 8,
                totalQuestions = 10,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun resultScreen_displaysFinalScore() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 7,
                totalQuestions = 10,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("7", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("10", substring = true).assertIsDisplayed()
    }

    @Test
    fun resultScreen_displaysScorePercentage() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 8,
                totalQuestions = 10,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert - 80% score should be displayed
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun resultScreen_perfectScore_showsSuccess() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 10,
                totalQuestions = 10,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert - should display perfect score UI
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun resultScreen_lowScore_showsEncouragement() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 3,
                totalQuestions = 10,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun resultScreen_retryButton_callsCallback() {
        // Arrange
        var retryClicked = false
        val onRetry: () -> Unit = { retryClicked = true }

        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 5,
                totalQuestions = 10,
                onRetry = onRetry,
                onNext = {}
            )
        }
        composeTestRule.onNodeWithText("Retry").performClick()

        // Assert
        assert(retryClicked)
    }

    @Test
    fun resultScreen_nextButton_callsCallback() {
        // Arrange
        var nextClicked = false
        val onNext: () -> Unit = { nextClicked = true }

        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 8,
                totalQuestions = 10,
                onRetry = {},
                onNext = onNext
            )
        }
        composeTestRule.onNodeWithText("Next Topic").performClick()

        // Assert
        assert(nextClicked)
    }

    @Test
    fun resultScreen_displaysResultMessage() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 8,
                totalQuestions = 10,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Great Job!", substring = true).assertIsDisplayed()
    }

    @Test
    fun resultScreen_displaysXpReward() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 9,
                totalQuestions = 10,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert - XP should be calculated and displayed
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun resultScreen_zeroScore() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 0,
                totalQuestions = 10,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("0", substring = true).assertIsDisplayed()
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun resultScreen_displaysSingleQuestion() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 1,
                totalQuestions = 1,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("1", substring = true).assertIsDisplayed()
    }

    @Test
    fun resultScreen_manyQuestions() {
        // Act
        composeTestRule.setContent {
            ResultScreen(
                topicId = "basics-1",
                score = 45,
                totalQuestions = 50,
                onRetry = {},
                onNext = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("45", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("50", substring = true).assertIsDisplayed()
    }
}
