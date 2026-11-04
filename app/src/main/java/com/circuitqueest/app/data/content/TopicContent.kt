package com.circuitqueest.app.data.content

import kotlin.random.Random

data class Topic(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: String,
    val order: Int,
    val lesson: Lesson,
    val quiz: Quiz
)

data class Lesson(
    val title: String,
    val sections: List<LessonSection>
)

data class LessonSection(
    val heading: String,
    val content: String,
    val formula: String? = null,
    val keyPoint: String? = null
)

/**
 * A topic's question pool. Each attempt draws [QUESTIONS_PER_ATTEMPT] questions from the fixed
 * [questions] plus the [generators] (numeric questions with fresh values every time), in random
 * order, with multiple-choice options shuffled. So a re-take tests understanding, not memory.
 */
data class Quiz(
    val title: String,
    val questions: List<Question>,
    val generators: List<QuestionGenerator> = emptyList()
) {
    /** How many different questions this quiz can ask. */
    val poolSize: Int get() = questions.size + generators.size

    /** The number of questions in one attempt: the full attempt size, or fewer if the pool is small. */
    val attemptSize: Int get() = minOf(QUESTIONS_PER_ATTEMPT, poolSize)

    /** Every question id in the pool (a generator's id stays the same whatever values it draws). */
    val questionIds: List<String> get() = questions.map { it.id } + generators.map { it.id }

    fun drawAttempt(random: Random = Random.Default): List<Question> {
        val pool: List<(Random) -> Question> =
            questions.map { q -> { _: Random -> q } } + generators.map { g -> { r: Random -> g.generate(r) } }
        return pool.shuffled(random).take(attemptSize).map { shuffleOptions(it(random), random) }
    }

    /** The question with this id, for review: a generator's question gets fresh values. */
    fun find(id: String, random: Random = Random.Default): Question? =
        (questions.firstOrNull { it.id == id } ?: generators.firstOrNull { it.id == id }?.generate(random))
            ?.let { shuffleOptions(it, random) }

    companion object {
        /** Kept at 7 (the original quiz length) so scores, best scores and XP stay comparable. */
        const val QUESTIONS_PER_ATTEMPT = 7
    }
}

/** A question whose numbers change on every attempt. [id] stays fixed so a missed one can be reviewed. */
class QuestionGenerator(val id: String, private val make: (Random) -> Question) {
    fun generate(random: Random): Question {
        val question = make(random)
        require(question.id == id) { "Generator $id produced a question with id ${question.id}" }
        return question
    }
}

/** Multiple-choice options in a random order (so the answer isn't always the same letter). */
fun shuffleOptions(question: Question, random: Random): Question {
    if (question !is Question.MultipleChoice) return question
    val order = question.options.indices.shuffled(random)
    return question.copy(
        options = order.map { question.options[it] },
        correctIndex = order.indexOf(question.correctIndex)
    )
}

sealed class Question {
    abstract val id: String
    abstract val questionText: String
    abstract val explanation: String
    abstract val points: Int

    data class MultipleChoice(
        override val id: String,
        override val questionText: String,
        val options: List<String>,
        val correctIndex: Int,
        override val explanation: String,
        override val points: Int = 1
    ) : Question()

    data class NumericInput(
        override val id: String,
        override val questionText: String,
        val correctAnswer: Double,
        val tolerance: Double = 0.01,
        val unit: String = "",
        override val explanation: String,
        override val points: Int = 1
    ) : Question()
}
