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
    fun calculateXp_baseScoreOnly_correctCalculation() {
        // score * 10 = 5 * 10 = 50
        assertEquals(50, QuizScoring.calculateXp(score = 5, totalQuestions = 10, isFirstCompletion = false))
    }

    @Test
    fun calculateXp_zeroScore_returnsZero() {
        assertEquals(0, QuizScoring.calculateXp(score = 0, totalQuestions = 10, isFirstCompletion = false))
    }

    @Test
    fun calculateXp_perfectScore_withFirstCompletionBonus_correctCalculation() {
        // (10*10) + 100 (first) + 50 (perfect) = 250
        assertEquals(250, QuizScoring.calculateXp(score = 10, totalQuestions = 10, isFirstCompletion = true))
    }

    @Test
    fun calculateXp_perfectScore_withoutFirstCompletionBonus_correctCalculation() {
        // (10*10) + 50 (perfect) = 150
        assertEquals(150, QuizScoring.calculateXp(score = 10, totalQuestions = 10, isFirstCompletion = false))
    }

    @Test
    fun calculateXp_imperfectScore_withFirstCompletionBonus_correctCalculation() {
        // (7*10) + 100 (first) = 170
        assertEquals(170, QuizScoring.calculateXp(score = 7, totalQuestions = 10, isFirstCompletion = true))
    }

    @Test
    fun calculateXp_multipleScores_allCalculationsCorrect() {
        assertEquals(30, QuizScoring.calculateXp(3, 10, false))
        assertEquals(130, QuizScoring.calculateXp(3, 10, true))
        assertEquals(60, QuizScoring.calculateXp(6, 10, false))
        assertEquals(160, QuizScoring.calculateXp(6, 10, true))
    }

    @Test
    fun calculateXp_partialScore_noBonus_exactCalculation() {
        assertEquals(80, QuizScoring.calculateXp(score = 8, totalQuestions = 10, isFirstCompletion = false))
    }

    @Test
    fun calculateXp_partialScore_firstCompletion_exactCalculation() {
        // (8*10) + 100 (first) = 180
        assertEquals(180, QuizScoring.calculateXp(score = 8, totalQuestions = 10, isFirstCompletion = true))
    }

    @Test
    fun calculateXp_singleQuestion_perfectScore_correctCalculation() {
        // (1*10) + 100 (first) + 50 (perfect) = 160
        assertEquals(160, QuizScoring.calculateXp(score = 1, totalQuestions = 1, isFirstCompletion = true))
    }

    @Test
    fun calculateXp_largeQuizSet_correctCalculation() {
        // (95*10) + 100 (first) = 1050, no perfect bonus (95 != 100)
        assertEquals(1050, QuizScoring.calculateXp(score = 95, totalQuestions = 100, isFirstCompletion = true))
    }
}
