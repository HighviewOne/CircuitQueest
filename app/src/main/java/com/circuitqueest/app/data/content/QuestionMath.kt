package com.circuitqueest.app.data.content

import java.util.Locale
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToLong
import kotlin.random.Random

/** Helpers for writing [QuestionGenerator]s: pick values, format numbers, build the question. */
object QuestionMath {

    /** Relative tolerance: answers within 1% of the exact value are accepted. */
    private const val RELATIVE_TOLERANCE = 0.01
    private const val HALF = 0.5
    private const val EPSILON = 1e-9
    private const val THREE = 3

    fun <T> List<T>.pick(random: Random): T = this[random.nextInt(size)]

    /** Two different values from the list. */
    fun <T> List<T>.pickTwo(random: Random): Pair<T, T> {
        val (a, b) = shuffled(random).take(2)
        return a to b
    }

    /** Three different values from the list. */
    fun <T> List<T>.pickThree(random: Random): Triple<T, T, T> {
        val (a, b, c) = shuffled(random).take(THREE)
        return Triple(a, b, c)
    }

    /** A number for display: whole numbers without decimals, others to at most [decimals] places. */
    fun fmt(value: Double, decimals: Int = 3): String {
        val rounded = round(value, decimals)
        if (rounded == rounded.roundToLong().toDouble()) return rounded.roundToLong().toString()
        return String.format(Locale.US, "%.${decimals}f", rounded).trimEnd('0').trimEnd('.')
    }

    /** "a" or "an" before a number as it's read aloud: an 8Ω resistor, an 11V source, a 12V source. */
    fun article(value: Double): String {
        val s = fmt(value)
        return if (s.startsWith("8") || Regex("^1[18](\\.|$|000)").containsMatchIn(s)) "an" else "a"
    }

    private const val SUPERSCRIPTS = "⁰¹²³⁴⁵⁶⁷⁸⁹"

    /** A power of ten for display: pow10(17) = "10¹⁷", pow10(-6) = "10⁻⁶". */
    fun pow10(exponent: Int): String =
        "10" + (if (exponent < 0) "⁻" else "") + abs(exponent).toString().map { SUPERSCRIPTS[it - '0'] }.joinToString("")

    /** [article] for the start of a sentence. */
    fun articleCap(value: Double): String = article(value).replaceFirstChar { it.uppercase() }

    fun round(value: Double, decimals: Int): Double {
        val scale = 10.0.pow(decimals)
        return (value * scale).roundToLong() / scale
    }

    /**
     * A numeric question. The tolerance accepts anything within 1% of the exact answer, or
     * anything that rounds correctly to [decimals] places if the question asks for rounding.
     */
    fun numeric(
        id: String,
        text: String,
        answer: Double,
        unit: String,
        explanation: String,
        decimals: Int = 2
    ): Question.NumericInput {
        val roundingSlack = HALF * 10.0.pow(-decimals) + EPSILON
        return Question.NumericInput(
            id = id,
            questionText = text,
            correctAnswer = answer,
            tolerance = maxOf(abs(answer) * RELATIVE_TOLERANCE, roundingSlack),
            unit = unit,
            explanation = explanation
        )
    }

    /** The parts of a generated numeric question; [generator] fills in the id and tolerance. */
    data class Numeric(
        val text: String,
        val answer: Double,
        val unit: String,
        val explanation: String,
        val decimals: Int = 2
    )

    /** A generator for a numeric question with this [id] (kept on every draw, for review). */
    fun generator(id: String, make: (Random) -> Numeric): QuestionGenerator =
        QuestionGenerator(id) { random ->
            val q = make(random)
            numeric(id, q.text, q.answer, q.unit, q.explanation, q.decimals)
        }
}
