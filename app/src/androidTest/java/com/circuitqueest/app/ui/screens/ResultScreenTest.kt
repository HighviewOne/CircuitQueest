package com.circuitqueest.app.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.content.TopicsService
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class ResultScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val firstTopic = TopicsService.allTopics[0]
    private val secondTopic = TopicsService.allTopics[1]

    private fun show(
        score: Int,
        total: Int = 10,
        xp: Int = 0,
        onRetry: (String) -> Unit = {},
        onHome: () -> Unit = {},
        onNextLesson: (String) -> Unit = {}
    ) {
        composeTestRule.setContent {
            ResultScreen(
                topicId = firstTopic.id,
                score = score,
                totalQuestions = total,
                xpEarned = xp,
                onRetry = onRetry,
                onHome = onHome,
                onNextLesson = onNextLesson
            )
        }
    }

    @Test
    fun passingScore_showsCompleteAndUpNext() {
        show(score = 8)

        composeTestRule.onNodeWithText("QUEST COMPLETE").assertIsDisplayed()
        composeTestRule.onNodeWithText("80%").assertIsDisplayed()
        composeTestRule.onNodeWithText(secondTopic.title).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun failingScore_hidesUpNext() {
        show(score = 5)

        composeTestRule.onNodeWithText("KEEP TRAINING").assertIsDisplayed()
        composeTestRule.onNodeWithText("UP NEXT").assertDoesNotExist()
    }

    @Test
    fun showsXpPassedIn_notARecomputedValue() {
        show(score = 8, xp = 20)

        composeTestRule.onNodeWithText("+20").assertIsDisplayed()
    }

    @Test
    fun buttons_invokeCallbacks() {
        var retried: String? = null
        var wentHome = false
        var next: String? = null
        show(
            score = 9,
            onRetry = { retried = it },
            onHome = { wentHome = true },
            onNextLesson = { next = it }
        )

        composeTestRule.onNodeWithText("Retry Quiz").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Back to Quest Map").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Continue  →").performScrollTo().performClick()

        assertEquals(firstTopic.id, retried)
        assertEquals(true, wentHome)
        assertEquals(secondTopic.id, next)
    }
}
