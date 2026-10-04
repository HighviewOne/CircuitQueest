package com.circuitqueest.app.util

import com.circuitqueest.app.data.content.Question
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertEquals

class QuizScoringTest {

    private fun mcQuestion(correctIndex: Int) = Question.MultipleChoice(
        id = "q1",
        questionText = "Test question",
        options = listOf("A", "B", "C", "D"),
        correctIndex = correctIndex,
        explanation = "Test explanation"
    )

    private fun numericQuestion(correctAnswer: Double, tolerance: Double = 0.5) =
        Question.NumericInput(
            id = "q2",
            questionText = "Test numeric question",
            correctAnswer = correctAnswer,
            tolerance = tolerance,
            unit = "V",
            explanation = "Test explanation"
        )

    @Test
    fun checkAnswer_multipleChoice_correctAnswer_returnsTrue() {
        assertTrue(QuizScoring.checkAnswer(mcQuestion(1), 1))
    }

    @Test
    fun checkAnswer_multipleChoice_wrongAnswer_returnsFalse() {
        assertFalse(QuizScoring.checkAnswer(mcQuestion(1), 0))
        assertFalse(QuizScoring.checkAnswer(mcQuestion(1), 2))
    }

    @Test
    fun checkAnswer_numericInput_withinTolerance_returnsTrue() {
        val question = numericQuestion(correctAnswer = 10.0, tolerance = 0.5)
        assertTrue(QuizScoring.checkAnswer(question, 9.5))
        assertTrue(QuizScoring.checkAnswer(question, 10.0))
        assertTrue(QuizScoring.checkAnswer(question, 10.5))
    }

    @Test
    fun checkAnswer_numericInput_outsideTolerance_returnsFalse() {
        val question = numericQuestion(correctAnswer = 10.0, tolerance = 0.5)
        assertFalse(QuizScoring.checkAnswer(question, 9.0))
        assertFalse(QuizScoring.checkAnswer(question, 11.0))
    }

    @Test
    fun checkAnswer_numericInput_exactValue_returnsTrue() {
        assertTrue(QuizScoring.checkAnswer(numericQuestion(5.0, 0.1), 5.0))
    }

    @Test
    fun checkAnswer_numericInput_largeToleranceMargin_returnsTrue() {
        val question = numericQuestion(correctAnswer = 100.0, tolerance = 10.0)
        assertTrue(QuizScoring.checkAnswer(question, 95.0))
        assertTrue(QuizScoring.checkAnswer(question, 110.0))
    }

    @Test
    fun calculateXp_firstAttempt_paysEveryPoint() {
        assertEquals(50, QuizScoring.calculateXp(score = 5, previousBest = 0, isFirstPass = false))
    }

    @Test
    fun calculateXp_firstPass_addsOneTimeBonus() {
        assertEquals(170, QuizScoring.calculateXp(score = 7, previousBest = 0, isFirstPass = true))
    }

    @Test
    fun calculateXp_retakeAtOrBelowBest_paysNothing() {
        assertEquals(0, QuizScoring.calculateXp(score = 8, previousBest = 8, isFirstPass = false))
        assertEquals(0, QuizScoring.calculateXp(score = 4, previousBest = 8, isFirstPass = false))
    }

    @Test
    fun calculateXp_improvement_paysOnlyTheGain() {
        assertEquals(20, QuizScoring.calculateXp(score = 8, previousBest = 6, isFirstPass = false))
    }

    @Test
    fun calculateXp_failThenPass_totalsBestPlusBonus() {
        val first = QuizScoring.calculateXp(score = 3, previousBest = 0, isFirstPass = false)
        val second = QuizScoring.calculateXp(score = 7, previousBest = 3, isFirstPass = true)
        assertEquals(7 * 10 + 100, first + second)
    }

    @Test
    fun isPassing_atAndAroundThreshold() {
        assertTrue(QuizScoring.isPassing(score = 6, totalQuestions = 10))
        assertFalse(QuizScoring.isPassing(score = 5, totalQuestions = 10))
        assertTrue(QuizScoring.isPassing(score = 3, totalQuestions = 5))
        assertFalse(QuizScoring.isPassing(score = 0, totalQuestions = 0))
    }

}
