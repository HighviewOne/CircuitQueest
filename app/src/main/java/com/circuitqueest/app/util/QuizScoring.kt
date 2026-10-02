package com.circuitqueest.app.util

import com.circuitqueest.app.data.content.Question
import kotlin.math.abs

object QuizScoring {

    /** Minimum percentage needed to pass a quiz and unlock the next quest. */
    const val PASS_PERCENT = 60
    const val XP_PER_POINT = 10
    const val FIRST_PASS_BONUS = 100
    const val LESSON_XP = 50

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

    fun percentage(score: Int, totalQuestions: Int): Int =
        if (totalQuestions > 0) score * 100 / totalQuestions else 0

    fun isPassing(score: Int, totalQuestions: Int): Boolean =
        totalQuestions > 0 && percentage(score, totalQuestions) >= PASS_PERCENT

    /**
     * XP for one attempt. Points only count when they beat the previous best, so
     * retaking a quiz can't farm XP; the bonus is paid once, on the first pass.
     * Over any sequence of attempts a topic's quiz XP totals
     * `best * XP_PER_POINT + (FIRST_PASS_BONUS if ever passed)`.
     */
    fun calculateXp(score: Int, previousBest: Int, isFirstPass: Boolean): Int {
        val improvementXp = (score - previousBest).coerceAtLeast(0) * XP_PER_POINT
        return improvementXp + if (isFirstPass) FIRST_PASS_BONUS else 0
    }
}
