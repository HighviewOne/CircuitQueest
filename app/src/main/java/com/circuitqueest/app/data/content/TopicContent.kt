package com.circuitqueest.app.data.content

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

data class Quiz(
    val title: String,
    val questions: List<Question>
)

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
