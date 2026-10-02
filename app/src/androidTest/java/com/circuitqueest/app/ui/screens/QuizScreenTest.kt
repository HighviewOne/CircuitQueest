package com.circuitqueest.app.ui.screens

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.content.Question
import com.circuitqueest.app.data.content.TopicsService
import com.circuitqueest.app.data.db.AppDatabase
import com.circuitqueest.app.data.repository.ProgressRepository
import com.circuitqueest.app.viewmodel.QuizViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertFalse

@RunWith(AndroidJUnit4::class)
class QuizScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var database: AppDatabase
    private lateinit var repository: ProgressRepository

    // A topic whose first two questions are multiple choice, so the same
    // composable is reused across the transition.
    private val topic = TopicsService.allTopics.first { topic ->
        topic.quiz.questions.take(2).all { it is Question.MultipleChoice }
    }
    private val firstQuestion = topic.quiz.questions[0] as Question.MultipleChoice

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repository = ProgressRepository(database.progressDao(), database.quizResultDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun showQuiz(onBack: () -> Unit = {}) {
        val viewModel = QuizViewModel(repository, SavedStateHandle(mapOf("topicId" to topic.id)))
        composeTestRule.setContent {
            QuizScreen(viewModel = viewModel, onBack = onBack, onQuizComplete = { _, _, _, _ -> })
        }
    }

    private fun answerFirstQuestion() {
        composeTestRule.onAllNodesWithText(firstQuestion.options[0]).onFirst().performClick()
        composeTestRule.onNodeWithText("Submit Answer").performScrollTo().assertIsEnabled()
            .performClick()
    }

    @Test
    fun selection_doesNotCarryOverToNextQuestion() {
        showQuiz()
        answerFirstQuestion()

        composeTestRule.onNodeWithText("Next  →").performClick()

        composeTestRule.onNodeWithText("Question 2 of ${topic.quiz.questions.size}")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Submit Answer").performScrollTo().assertIsNotEnabled()
    }

    @Test
    fun closingMidQuiz_asksForConfirmation() {
        var left = false
        showQuiz(onBack = { left = true })
        answerFirstQuestion()

        composeTestRule.onNodeWithContentDescription("Close").performClick()

        composeTestRule.onNodeWithText("Leave this quiz?").assertIsDisplayed()
        assertFalse(left)
        composeTestRule.onNodeWithText("Keep going").performClick()
        composeTestRule.onNodeWithText("Leave this quiz?").assertDoesNotExist()
    }

    @Test
    fun closingBeforeAnswering_leavesImmediately() {
        var left = false
        showQuiz(onBack = { left = true })

        composeTestRule.onNodeWithContentDescription("Close").performClick()

        composeTestRule.runOnIdle { kotlin.test.assertTrue(left) }
    }
}
