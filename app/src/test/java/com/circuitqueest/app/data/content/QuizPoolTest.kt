package com.circuitqueest.app.data.content

import com.circuitqueest.app.util.QuizScoring
import org.junit.Test
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class QuizPoolTest {

    private val topics = TopicsService.allTopics
    /** Categories converted to deeper practice so far. */
    private val deepened = TopicCategories.categories
        .filter { it.name in setOf("Circuit Fundamentals", "Semiconductor Devices", "Analog Design", "Digital Systems", "Signals & Control") }
        .flatMap { it.topicIds }.map { id -> topics.first { it.id == id } }

    /** Every question a generator can produce, over many draws. */
    private fun samples(gen: QuestionGenerator, n: Int = 300) = (0 until n).map { gen.generate(Random(it)) }

    @Test
    fun everyQuiz_canFillAFullAttempt_withUniqueIds() {
        for (topic in topics) {
            val ids = topic.quiz.questionIds
            assertTrue(topic.quiz.poolSize >= Quiz.QUESTIONS_PER_ATTEMPT, "${topic.id} pool is too small")
            assertEquals(ids.size, ids.toSet().size, "${topic.id} has duplicate question ids")
        }
    }

    @Test
    fun deepenedCategories_haveDeeperPools_withGeneratedNumbers() {
        assertEquals(21, deepened.size)
        for (topic in deepened) {
            assertTrue(topic.quiz.poolSize >= 10, "${topic.id} pool should be at least 10")
            assertTrue(topic.quiz.generators.size >= 5, "${topic.id} should have generated numeric questions")
            assertTrue(topic.quiz.questions.none { it is Question.NumericInput }, "${topic.id} numerics are generated")
        }
    }

    @Test
    fun drawAttempt_givesSevenDistinctQuestions_andVariesBetweenAttempts() {
        val quiz = topics.first { it.id == "ohms_law" }.quiz
        val draws = (0 until 50).map { seed -> quiz.drawAttempt(Random(seed)) }
        for (draw in draws) {
            assertEquals(Quiz.QUESTIONS_PER_ATTEMPT, draw.size)
            assertEquals(draw.size, draw.map { it.id }.toSet().size)
        }
        // Over many attempts the whole pool is used, in different orders
        assertEquals(quiz.questionIds.toSet(), draws.flatten().map { it.id }.toSet())
        assertTrue(draws.map { d -> d.map { it.id } }.toSet().size > 1)
    }

    @Test
    fun shuffledOptions_keepTheCorrectAnswer() {
        for (topic in topics) for (q in topic.quiz.questions.filterIsInstance<Question.MultipleChoice>()) {
            repeat(5) { seed ->
                val s = shuffleOptions(q, Random(seed)) as Question.MultipleChoice
                assertEquals(q.options.toSet(), s.options.toSet())
                assertEquals(q.options[q.correctIndex], s.options[s.correctIndex], q.id)
            }
        }
    }

    @Test
    fun generators_produceSaneQuestions() {
        for (topic in topics) for (gen in topic.quiz.generators) for (q in samples(gen)) {
            q as Question.NumericInput
            assertEquals(gen.id, q.id)
            assertTrue(q.correctAnswer.isFinite() && q.correctAnswer >= 0, "${q.id}: ${q.correctAnswer}")
            assertTrue(q.tolerance > 0 && q.tolerance <= abs(q.correctAnswer) * 0.05 + 0.51, "${q.id} tolerance")
            for (text in listOf(q.questionText, q.explanation)) {
                assertFalse(Regex("NaN|Infinity|\\d[Ee][-+]?\\d").containsMatchIn(text), "${q.id}: $text")
            }
            assertTrue(QuizScoring.checkAnswer(q, q.correctAnswer))
            val wrong = if (q.correctAnswer == 0.0) 1.0 else q.correctAnswer * 2
            assertFalse(QuizScoring.checkAnswer(q, wrong), "${q.id} accepts $wrong for ${q.correctAnswer}")
        }
    }

    @Test
    fun roundingAsInstructed_isAlwaysAccepted() {
        val places = Regex("""Round(?:ed)? to (\d) decimal place""")
        for (topic in topics) for (gen in topic.quiz.generators) for (q in samples(gen)) {
            q as Question.NumericInput
            val decimals = when {
                "nearest whole number" in q.questionText -> 0
                else -> places.find(q.questionText)?.groupValues?.get(1)?.toInt()
            } ?: continue
            val typed = QuestionMath.round(q.correctAnswer, decimals)
            assertTrue(QuizScoring.checkAnswer(q, typed), "${q.id}: typing $typed for ${q.correctAnswer} is marked wrong")
        }
    }

    @Test
    fun explanations_showTheSameAnswerAsTheGrader() {
        // The worked answer at the end of each explanation must match what's graded
        for (topic in topics) for (gen in topic.quiz.generators) for (q in samples(gen, 100)) {
            q as Question.NumericInput
            val withUnit = Regex("""(\d+(?:\.\d+)?)\s?${Regex.escape(q.unit)}(?![A-Za-zΩ])""")
            val shown = withUnit.findAll(q.explanation).lastOrNull()?.groupValues?.get(1)?.toDoubleOrNull()
            assertNotNull(shown, "${q.id}: no final value in '${q.explanation}'")
            assertTrue(QuizScoring.checkAnswer(q, shown), "${q.id}: explanation shows $shown, answer ${q.correctAnswer}")
        }
    }

    @Test
    fun article_matchesHowTheNumberIsSaid() {
        for (v in listOf(8.0, 8.2, 11.0, 18.0, 80.0, 800.0, 11000.0)) assertEquals("an", QuestionMath.article(v), "$v")
        for (v in listOf(1.0, 4.7, 10.0, 12.0, 110.0, 180.0)) assertEquals("a", QuestionMath.article(v), "$v")
    }

    @Test
    fun pow10_writesSuperscripts() {
        assertEquals("10¹⁷", QuestionMath.pow10(17))
        assertEquals("10⁻⁶", QuestionMath.pow10(-6))
        assertEquals("10²⁰", QuestionMath.pow10(20))
    }

    @Test
    fun find_returnsFixedAndGeneratedQuestions_byId() {
        val quiz = topics.first { it.id == "ohms_law" }.quiz
        assertEquals("ohm_mc1", quiz.find("ohm_mc1")?.id)
        assertTrue(quiz.find("ohm_ni3") is Question.NumericInput)
        assertEquals(null, quiz.find("gone"))
    }
}
