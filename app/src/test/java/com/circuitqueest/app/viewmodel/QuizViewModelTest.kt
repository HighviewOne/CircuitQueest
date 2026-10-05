package com.circuitqueest.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.circuitqueest.app.data.content.Question
import com.circuitqueest.app.data.content.TopicsService
import com.circuitqueest.app.data.db.entity.MissedQuestion
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
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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

    // ── Review mode ─────────────────────────────────────────────────────────

    private fun makeReviewViewModel(repository: ProgressRepository, scope: String = QuizViewModel.REVIEW_ALL) =
        QuizViewModel(repository, SavedStateHandle(mapOf("topicId" to scope, "review" to true)))

    private val otherTopic = TopicsService.allTopics.first { it.id == "series_parallel" }

    @Test
    fun wrongAnswer_inNormalQuiz_isQueuedForReview() = runTest {
        val repository: ProgressRepository = mock()
        val viewModel = makeViewModel(repository)
        val first = questions.first()

        when (first) {
            is Question.MultipleChoice -> viewModel.answerMultipleChoice((first.correctIndex + 1) % first.options.size)
            is Question.NumericInput -> viewModel.answerNumeric(first.correctAnswer + 1_000)
        }

        verify(repository).recordAnswer(eq(topicId), eq(first.id), eq(false))
    }

    @Test
    fun reviewMode_loadsMissedQuestions_acrossTopics_skippingStale() = runTest {
        val repository: ProgressRepository = mock()
        whenever(repository.getMissedQuestions(null)).thenReturn(
            listOf(
                MissedQuestion(topicId, questions[2].id),
                MissedQuestion("removed_topic", "gone"),
                MissedQuestion(otherTopic.id, otherTopic.quiz.questions[0].id)
            )
        )

        val viewModel = makeReviewViewModel(repository)

        val state = viewModel.quizState.value
        assertTrue(state.isReview)
        assertFalse(state.isLoading)
        assertEquals(2, state.totalQuestions)
        assertEquals(questions[2].id, viewModel.currentQuestion.value?.id)
    }

    @Test
    fun reviewMode_recordsAnswersAgainstEachQuestionsOwnTopic() = runTest {
        val repository: ProgressRepository = mock()
        val otherQuestion = otherTopic.quiz.questions[0]
        whenever(repository.getMissedQuestions(null)).thenReturn(
            listOf(MissedQuestion(otherTopic.id, otherQuestion.id))
        )
        val viewModel = makeReviewViewModel(repository)

        viewModel.answerCurrentCorrectly()

        verify(repository).recordAnswer(eq(otherTopic.id), eq(otherQuestion.id), eq(true))
    }

    @Test
    fun reviewMode_finishes_withoutRecordingAttemptOrXp() = runTest {
        val repository: ProgressRepository = mock()
        whenever(repository.getMissedQuestions(topicId)).thenReturn(
            listOf(MissedQuestion(topicId, questions[0].id))
        )
        val viewModel = makeReviewViewModel(repository, scope = topicId)

        viewModel.answerCurrentCorrectly()
        viewModel.nextQuestion()

        assertTrue(viewModel.quizComplete.value)
        assertEquals(0, viewModel.quizState.value.xpEarned)
        verify(repository, never()).recordQuizResult(any(), any(), any())
    }

    @Test
    fun reviewMode_emptyQueue_hasNoQuestions() = runTest {
        val repository: ProgressRepository = mock()
        whenever(repository.getMissedQuestions(null)).thenReturn(emptyList())

        val viewModel = makeReviewViewModel(repository)

        assertEquals(0, viewModel.quizState.value.totalQuestions)
        assertFalse(viewModel.quizState.value.isLoading)
    }
}
