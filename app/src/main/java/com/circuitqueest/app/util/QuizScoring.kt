package com.circuitqueest.app.util

import com.circuitqueest.app.data.content.Question
import kotlin.math.abs

object QuizScoring {

    fun checkAnswer(question: Question, answer: Any?): Boolean {
        return when (question) {
            is Question.MultipleChoice -> {
                answer is Int && answer == question.correctIndex
            }
            is Question.NumericInput -> {
                val numericAnswer = when (answer) {
                    is Double -> answer
                    is Number -> answer.toDouble()
                    else -> return false
                }
                abs(numericAnswer - question.correctAnswer) <= question.tolerance
            }
        }
    }

    fun calculateXp(score: Int, totalQuestions: Int, isFirstCompletion: Boolean): Int {
        val baseXp = score * 10
        val bonusXp = if (isFirstCompletion && score > 0) 100 else 0
        val perfectBonus = if (score == totalQuestions) 50 else 0
        return baseXp + bonusXp + perfectBonus
    }
}
