package com.circuitqueest.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.circuitqueest.app.data.content.Question
import com.circuitqueest.app.data.content.TopicsService
import com.circuitqueest.app.data.repository.ProgressRepository
import com.circuitqueest.app.util.QuizScoring
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizState(
    val topicId: String = "",
    val quizTitle: String = "",
    val currentIndex: Int = 0,
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val xpEarned: Int = 0,
    /** Review mode: replays missed questions; awards no XP and records no attempt. */
    val isReview: Boolean = false,
    /** True while review questions load from the database. */
    val isLoading: Boolean = false
)

data class QuizFeedback(
    val isCorrect: Boolean,
    val explanation: String
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: ProgressRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /** A topic id, or [REVIEW_ALL] for a review across every topic. */
    private val topicId: String = checkNotNull(savedStateHandle["topicId"])
    private val isReview: Boolean = savedStateHandle.get<Boolean>("review") ?: false

    /** Questions paired with the topic they belong to (ids are only unique per topic). */
    private var questions: List<Pair<String, Question>> = emptyList()
    private val _quizState = MutableStateFlow(QuizState())
    val quizState: StateFlow<QuizState> = _quizState.asStateFlow()

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion.asStateFlow()

    private val _feedback = MutableStateFlow<QuizFeedback?>(null)
    val feedback: StateFlow<QuizFeedback?> = _feedback.asStateFlow()

    private val _quizComplete = MutableStateFlow(false)
    val quizComplete: StateFlow<Boolean> = _quizComplete.asStateFlow()

    private var isSaving = false

    init {
        if (isReview) {
            _quizState.value = QuizState(topicId = topicId, quizTitle = "Review", isReview = true, isLoading = true)
            viewModelScope.launch { start(loadReviewQuestions()) }
        } else {
            val topic = TopicsService.allTopics.find { it.id == topicId }
            _quizState.value = QuizState(topicId = topicId, quizTitle = topic?.quiz?.title ?: "Quiz")
            start(topic?.quiz?.questions.orEmpty().map { topicId to it })
        }
    }

    private suspend fun loadReviewQuestions(): List<Pair<String, Question>> {
        val byTopic = TopicsService.allTopics.associateBy { it.id }
        val scope = topicId.takeUnless { it == REVIEW_ALL }
        // Skip entries whose question no longer exists in the content.
        return repository.getMissedQuestions(scope).mapNotNull { missed ->
            byTopic[missed.topicId]?.quiz?.questions
                ?.firstOrNull { it.id == missed.questionId }
                ?.let { missed.topicId to it }
        }
    }

    private fun start(loaded: List<Pair<String, Question>>) {
        questions = loaded
        _quizState.value = _quizState.value.copy(totalQuestions = loaded.size, isLoading = false)
        _currentQuestion.value = loaded.firstOrNull()?.second
    }

    fun answerMultipleChoice(selectedIndex: Int) {
        val question = _currentQuestion.value ?: return
        if (_feedback.value != null) return
        onAnswered(question, QuizScoring.checkAnswer(question, selectedIndex))
    }

    fun answerNumeric(value: Double?) {
        val question = _currentQuestion.value ?: return
        if (_feedback.value != null) return
        onAnswered(question, value != null && QuizScoring.checkAnswer(question, value))
    }

    private fun onAnswered(question: Question, isCorrect: Boolean) {
        if (isCorrect) {
            _quizState.value = _quizState.value.copy(
                score = _quizState.value.score + question.points
            )
        }
        _feedback.value = QuizFeedback(isCorrect = isCorrect, explanation = question.explanation)

        // Wrong answers queue the question for review; right ones clear it.
        val questionTopic = questions[_quizState.value.currentIndex].first
        viewModelScope.launch { repository.recordAnswer(questionTopic, question.id, isCorrect) }
    }

    fun nextQuestion() {
        // Only advance from an answered question, and save the attempt once even if
        // "Next" is tapped repeatedly while the write is in flight.
        if (_feedback.value == null || isSaving) return
        val nextIndex = _quizState.value.currentIndex + 1

        if (nextIndex >= questions.size) {
            isSaving = true
            if (isReview) {
                // Reviews only clear mistakes (done per answer); no XP, no attempt record.
                _quizComplete.value = true
                return
            }
            viewModelScope.launch {
                val xp = repository.recordQuizResult(
                    topicId = topicId,
                    score = _quizState.value.score,
                    totalQuestions = _quizState.value.totalQuestions
                )
                _quizState.value = _quizState.value.copy(xpEarned = xp)
                _quizComplete.value = true
            }
            return
        }

        _quizState.value = _quizState.value.copy(currentIndex = nextIndex)
        _currentQuestion.value = questions[nextIndex].second
        _feedback.value = null
    }

    companion object {
        /** Route topic id meaning "review missed questions from every topic". */
        const val REVIEW_ALL = "all"
    }
}
