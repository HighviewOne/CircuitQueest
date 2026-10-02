package com.circuitqueest.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.circuitqueest.app.data.content.Question
import com.circuitqueest.app.data.content.TopicsService
import com.circuitqueest.app.data.repository.ProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val topicId = "ohms_law"
    private val questions = TopicsService.allTopics.first { it.id == topicId }.quiz.questions

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun makeViewModel(repository: ProgressRepository) =
        QuizViewModel(repository, SavedStateHandle(mapOf("topicId" to topicId)))

    private fun QuizViewModel.answerCurrentCorrectly() {
        when (val q = currentQuestion.value!!) {
            is Question.MultipleChoice -> answerMultipleChoice(q.correctIndex)
            is Question.NumericInput -> answerNumeric(q.correctAnswer)
        }
    }

    @Test
    fun nextQuestion_withoutAnswer_doesNotAdvance() {
        val viewModel = makeViewModel(mock())

        viewModel.nextQuestion()

        assertEquals(0, viewModel.quizState.value.currentIndex)
    }

    @Test
    fun nextQuestion_tappedRepeatedlyOnLastQuestion_savesOnce() = runTest {
        val repository: ProgressRepository = mock()
        whenever(repository.recordQuizResult(any(), any(), any())).thenReturn(0)
        val viewModel = makeViewModel(repository)

        repeat(questions.size - 1) {
            viewModel.answerCurrentCorrectly()
            viewModel.nextQuestion()
        }
        viewModel.answerCurrentCorrectly()
        viewModel.nextQuestion()
        viewModel.nextQuestion()
        viewModel.nextQuestion()

        verify(repository, times(1)).recordQuizResult(any(), any(), any())
    }

    @Test
    fun quizComplete_carriesXpAwardedByRepository() = runTest {
        val repository: ProgressRepository = mock()
        whenever(repository.recordQuizResult(any(), any(), any())).thenReturn(170)
        val viewModel = makeViewModel(repository)

        repeat(questions.size) {
            viewModel.answerCurrentCorrectly()
            viewModel.nextQuestion()
        }

        assertTrue(viewModel.quizComplete.value)
        assertEquals(170, viewModel.quizState.value.xpEarned)
        assertEquals(questions.sumOf { it.points }, viewModel.quizState.value.score)
    }

    @Test
    fun nextQuestion_midQuiz_doesNotSave() = runTest {
        val repository: ProgressRepository = mock()
        val viewModel = makeViewModel(repository)

        viewModel.answerCurrentCorrectly()
        viewModel.nextQuestion()

        assertEquals(1, viewModel.quizState.value.currentIndex)
        verify(repository, never()).recordQuizResult(any(), any(), any())
    }
}
