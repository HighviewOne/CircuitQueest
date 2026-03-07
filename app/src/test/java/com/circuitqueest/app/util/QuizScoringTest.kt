package com.circuitqueest.app.util

import com.circuitqueest.app.data.db.entity.QuestionType
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertEquals

class QuizScoringTest {

    @Test
    fun checkAnswer_multipleChoice_correctAnswer_returnsTrue() {
        val question = object : QuestionType.MultipleChoice {
            override val correctAnswerIndex: Int = 1
            override val options: List<String> = listOf("A", "B", "C")
        }
        
        assertTrue(QuizScoring.checkAnswer(question, 1))
    }

    @Test
    fun checkAnswer_multipleChoice_wrongAnswer_returnsFalse() {
        val question = object : QuestionType.MultipleChoice {
            override val correctAnswerIndex: Int = 1
            override val options: List<String> = listOf("A", "B", "C")
        }
        
        assertFalse(QuizScoring.checkAnswer(question, 0))
        assertFalse(QuizScoring.checkAnswer(question, 2))
    }

    @Test
    fun checkAnswer_numericInput_withinTolerance_returnsTrue() {
        val question = object : QuestionType.NumericInput {
            override val correctAnswer: Double = 10.0
            override val tolerance: Double = 0.5
            override val unit: String = "V"
        }
        
        assertTrue(QuizScoring.checkAnswer(question, 9.5))
        assertTrue(QuizScoring.checkAnswer(question, 10.0))
        assertTrue(QuizScoring.checkAnswer(question, 10.5))
    }

    @Test
    fun checkAnswer_numericInput_outsideTolerance_returnsFalse() {
        val question = object : QuestionType.NumericInput {
            override val correctAnswer: Double = 10.0
            override val tolerance: Double = 0.5
            override val unit: String = "V"
        }
        
        assertFalse(QuizScoring.checkAnswer(question, 9.0))
        assertFalse(QuizScoring.checkAnswer(question, 11.0))
    }

    @Test
    fun checkAnswer_numericInput_exactValue_returnsTrue() {
        val question = object : QuestionType.NumericInput {
            override val correctAnswer: Double = 5.0
            override val tolerance: Double = 0.1
            override val unit: String = "A"
        }
        
        assertTrue(QuizScoring.checkAnswer(question, 5.0))
    }

    @Test
    fun checkAnswer_numericInput_largeToleranceMargin_returnsTrue() {
        val question = object : QuestionType.NumericInput {
            override val correctAnswer: Double = 100.0
            override val tolerance: Double = 10.0
            override val unit: String = "Ω"
        }
        
        assertTrue(QuizScoring.checkAnswer(question, 95.0))
        assertTrue(QuizScoring.checkAnswer(question, 110.0))
    }

    @Test
    fun calculateXp_baseScoreOnly_correctCalculation() {
        // score * 10
        val xp = QuizScoring.calculateXp(score = 5, totalQuestions = 10, isFirstCompletion = false)
        assertEquals(50, xp)
    }

    @Test
    fun calculateXp_zeroScore_returnsZero() {
        val xp = QuizScoring.calculateXp(score = 0, totalQuestions = 10, isFirstCompletion = false)
        assertEquals(0, xp)
    }

    @Test
    fun calculateXp_perfectScore_withFirstCompletionBonus_correctCalculation() {
        // (score * 10) + 100 (first completion) + 50 (perfect)
        val xp = QuizScoring.calculateXp(score = 10, totalQuestions = 10, isFirstCompletion = true)
        assertEquals(200, xp)
    }

    @Test
    fun calculateXp_perfectScore_withoutFirstCompletionBonus_correctCalculation() {
        // (score * 10) + 50 (perfect only)
        val xp = QuizScoring.calculateXp(score = 10, totalQuestions = 10, isFirstCompletion = false)
        assertEquals(100, xp)
    }

    @Test
    fun calculateXp_imperfectScore_withFirstCompletionBonus_correctCalculation() {
        // (score * 10) + 100 (first completion), no perfect bonus
        val xp = QuizScoring.calculateXp(score = 7, totalQuestions = 10, isFirstCompletion = true)
        assertEquals(170, xp)
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
        val xp = QuizScoring.calculateXp(score = 8, totalQuestions = 10, isFirstCompletion = false)
        assertEquals(80, xp)
    }

    @Test
    fun calculateXp_partialScore_firstCompletion_exactCalculation() {
        val xp = QuizScoring.calculateXp(score = 8, totalQuestions = 10, isFirstCompletion = true)
        assertEquals(180, xp)
    }

    @Test
    fun calculateXp_singleQuestion_perfectScore_correctCalculation() {
        // Edge case: 1 question, perfect score
        val xp = QuizScoring.calculateXp(score = 1, totalQuestions = 1, isFirstCompletion = true)
        assertEquals(151, xp) // 10 + 100 (first) + 50 (perfect)
    }

    @Test
    fun calculateXp_largeQuizSet_correctCalculation() {
        // 100 questions, 95 score, first completion
        val xp = QuizScoring.calculateXp(score = 95, totalQuestions = 100, isFirstCompletion = true)
        assertEquals(950, xp) // (95 * 10) + 100
    }
}
