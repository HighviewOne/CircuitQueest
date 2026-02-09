package com.circuitqueest.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.circuitqueest.app.data.content.Question
import com.circuitqueest.app.data.repository.ProgressRepository
import com.circuitqueest.app.util.QuizScoring
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class QuizState(
    val topicId: String = "",
    val quizTitle: String = "",
    val currentIndex: Int = 0,
    val score: Int = 0,
    val totalQuestions: Int = 0
)

data class QuizFeedback(
    val isCorrect: Boolean,
    val explanation: String
)

class QuizViewModel(
    private val topicId: String,
    private val repository: ProgressRepository
) : ViewModel() {

    private val questions: List<Question>
    private val _quizState = MutableStateFlow(QuizState())
    val quizState: StateFlow<QuizState> = _quizState.asStateFlow()

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion.asStateFlow()

    private val _feedback = MutableStateFlow<QuizFeedback?>(null)
    val feedback: StateFlow<QuizFeedback?> = _feedback.asStateFlow()

    private val _quizComplete = MutableStateFlow(false)
    val quizComplete: StateFlow<Boolean> = _quizComplete.asStateFlow()

    init {
        val topic = HomeViewModel.allTopics.find { it.id == topicId }
        questions = topic?.quiz?.questions ?: emptyList()

        _quizState.value = QuizState(
            topicId = topicId,
            quizTitle = topic?.quiz?.title ?: "Quiz",
            currentIndex = 0,
            score = 0,
            totalQuestions = questions.size
        )

        if (questions.isNotEmpty()) {
            _currentQuestion.value = questions[0]
        }
    }

    fun answerMultipleChoice(selectedIndex: Int) {
        val question = _currentQuestion.value ?: return
        if (_feedback.value != null) return

        val isCorrect = QuizScoring.checkAnswer(question, selectedIndex)
        if (isCorrect) {
            _quizState.value = _quizState.value.copy(
                score = _quizState.value.score + question.points
            )
        }

        _feedback.value = QuizFeedback(
            isCorrect = isCorrect,
            explanation = question.explanation
        )
    }

    fun answerNumeric(value: Double?) {
        val question = _currentQuestion.value ?: return
        if (_feedback.value != null) return

        val isCorrect = value != null && QuizScoring.checkAnswer(question, value)
        if (isCorrect) {
            _quizState.value = _quizState.value.copy(
                score = _quizState.value.score + question.points
            )
        }

        _feedback.value = QuizFeedback(
            isCorrect = isCorrect,
            explanation = question.explanation
        )
    }

    fun nextQuestion() {
        val nextIndex = _quizState.value.currentIndex + 1

        if (nextIndex >= questions.size) {
            viewModelScope.launch {
                repository.saveQuizResult(
                    topicId = topicId,
                    score = _quizState.value.score,
                    totalQuestions = _quizState.value.totalQuestions
                )
                _quizComplete.value = true
            }
            return
        }

        _quizState.value = _quizState.value.copy(currentIndex = nextIndex)
        _currentQuestion.value = questions[nextIndex]
        _feedback.value = null
    }

    class Factory(
        private val topicId: String,
        private val repository: ProgressRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QuizViewModel(topicId, repository) as T
        }
    }
}
