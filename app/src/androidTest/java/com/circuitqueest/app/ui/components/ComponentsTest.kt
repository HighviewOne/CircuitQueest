package com.circuitqueest.app.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.ui.theme.CqBlue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class ComponentsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun topicCard(isLocked: Boolean, onClick: () -> Unit = {}) {
        composeTestRule.setContent {
            TopicCard(
                topicId = "ohms_law",
                topicNumber = 0,
                title = "Ohm's Law",
                subtitle = "The foundation of circuit analysis",
                topicIcon = "⚡",
                isLocked = isLocked,
                isCurrent = !isLocked,
                lessonCompleted = false,
                quizCompleted = false,
                quizScore = null,
                totalQuestions = null,
                accentColor = CqBlue,
                onClick = onClick
            )
        }
    }

    @Test
    fun topicCard_unlocked_showsSubtitleAndClicks() {
        var clicks = 0
        topicCard(isLocked = false, onClick = { clicks++ })

        composeTestRule.onNodeWithText("The foundation of circuit analysis").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ohm's Law").performClick()

        composeTestRule.runOnIdle { assertEquals(1, clicks) }
    }

    @Test
    fun topicCard_locked_explainsAndIgnoresClicks() {
        var clicks = 0
        topicCard(isLocked = true, onClick = { clicks++ })

        composeTestRule.onNodeWithText("Pass the previous quest to unlock").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ohm's Law").performClick()

        composeTestRule.runOnIdle { assertEquals(0, clicks) }
    }

    @Test
    fun xpBar_showsLevelAndXpToNext() {
        composeTestRule.setContent { XpBar(totalXp = 600) }

        composeTestRule.onNodeWithText("Level 2 · 600 XP").assertIsDisplayed()
        composeTestRule.onNodeWithText("→ 400 to Level 3").assertIsDisplayed()
    }

    @Test
    fun answerFeedback_correct_showsExplanationAndNext() {
        var next = 0
        composeTestRule.setContent {
            AnswerFeedback(isCorrect = true, explanation = "V = IR", onNext = { next++ })
        }

        composeTestRule.onNodeWithText("Circuit closed!").assertIsDisplayed()
        composeTestRule.onNodeWithText("V = IR").assertIsDisplayed()
        composeTestRule.onNodeWithText("Next  →").performClick()

        composeTestRule.runOnIdle { assertEquals(1, next) }
    }

    @Test
    fun answerFeedback_incorrect_showsOpenCircuit() {
        composeTestRule.setContent {
            AnswerFeedback(isCorrect = false, explanation = "Try again", onNext = {})
        }

        composeTestRule.onNodeWithText("Open circuit").assertIsDisplayed()
    }

    @Test
    fun multipleChoice_submitEnabledOnlyAfterSelection() {
        var answered: Int? = null
        composeTestRule.setContent {
            MultipleChoiceQuestion(
                questionText = "What does Ohm's Law state?",
                options = listOf("V = I × R", "V = I / R"),
                questionNumber = 1,
                correctIndex = 0,
                isSubmitted = false,
                onAnswer = { answered = it }
            )
        }

        composeTestRule.onNodeWithText("Submit Answer").assertIsNotEnabled()
        composeTestRule.onNodeWithText("V = I / R").performClick()
        composeTestRule.onNodeWithText("Submit Answer").assertIsEnabled().performClick()

        composeTestRule.runOnIdle { assertEquals(1, answered) }
    }

    @Test
    fun numericInput_acceptsCommaDecimal() {
        var answered: Double? = null
        composeTestRule.setContent {
            NumericInputQuestion(
                questionText = "Current in amps?",
                unit = "A",
                questionNumber = 7,
                isSubmitted = false,
                onAnswer = { answered = it }
            )
        }

        composeTestRule.onNodeWithText("Submit Answer").assertIsNotEnabled()
        composeTestRule.onNode(hasSetTextAction()).performTextInput("0,5")
        composeTestRule.onNodeWithText("Submit Answer").performClick()

        composeTestRule.runOnIdle { assertEquals(0.5, answered) }
    }

    @Test
    fun statusChip_lockedDefaultLabel() {
        composeTestRule.setContent { StatusChip(status = ChipStatus.LOCKED) }

        composeTestRule.onNodeWithText("⌗ Locked").assertIsDisplayed()
    }

    @Test
    fun questNotFound_backInvokesCallback() {
        var back = 0
        composeTestRule.setContent { QuestNotFound(onBack = { back++ }) }

        composeTestRule.onNodeWithText("Quest not found").assertIsDisplayed()
        composeTestRule.onNodeWithText("Back").performClick()

        composeTestRule.runOnIdle { assertEquals(1, back) }
    }
}
