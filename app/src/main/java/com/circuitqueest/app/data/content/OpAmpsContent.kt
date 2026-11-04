package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import com.circuitqueest.app.data.content.QuestionMath.pickTwo

object OpAmpsContent {
    private const val MAX_OUTPUT = 12.0
    private val feedbackResistors = listOf(10.0, 22.0, 33.0, 47.0, 100.0)
    private val inputResistors = listOf(1.0, 2.2, 4.7, 10.0)
    private val smallInputs = listOf(0.05, 0.1, 0.2, 0.5, 1.0)
    private val nonInvFeedback = listOf(10.0, 15.0, 22.0, 47.0, 100.0)
    private val nonInvInput = listOf(1.0, 2.2, 4.7, 5.0, 10.0)
    private val invertingGains = listOf(2.0, 5.0, 8.0, 10.0, 20.0, 50.0)
    private val gainInputResistors = listOf(1.0, 2.0, 4.7, 10.0)
    private val nonInvGains = listOf(2.0, 3.0, 4.0, 6.0, 10.0)
    private val nonInvInputs = listOf(0.1, 0.25, 0.5, 1.0, 2.0)
    private val summingInputs = listOf(0.2, 0.5, 1.0, 1.5, 2.0)
    private val summingRatios = listOf(1.0, 2.0)
    private val diffRatios = listOf(1.0, 2.0, 5.0, 10.0)
    private val diffBases = listOf(0.5, 1.0, 1.5)
    private val diffSteps = listOf(0.1, 0.2, 0.5)

    val topic = Topic(
        id = "op_amps",
        title = "Op-Amps",
        subtitle = "Operational amplifier circuits and gain",
        icon = "\uD83D\uDD3A",
        order = 10,
        lesson = Lesson(
            title = "Op-Amps: The Ultimate Amplifier",
            sections = listOf(
                LessonSection(
                    heading = "The Ideal Op-Amp",
                    content = "An operational amplifier (op-amp) is a high-gain voltage amplifier with differential inputs. The ideal op-amp has three key properties:\n\n\u2022 Infinite input impedance (no current flows into the inputs)\n\u2022 Zero output impedance (perfect voltage source)\n\u2022 Infinite open-loop gain\n\nReal op-amps like the classic LM741 closely approximate these ideals for most applications.",
                    keyPoint = "Ideal: infinite input impedance, zero output impedance, infinite gain"
                ),
                LessonSection(
                    heading = "The Golden Rules",
                    content = "When an op-amp is used with negative feedback (output connected back to the inverting input), two golden rules simplify all analysis:\n\n1. No current flows into either input terminal (infinite input impedance)\n2. The op-amp adjusts its output to make V+ = V\u207B (virtual short)\n\nThese two rules let you analyze any op-amp circuit using only Ohm's Law and KCL!",
                    keyPoint = "Golden Rules: (1) No input current, (2) V+ = V\u207B with negative feedback"
                ),
                LessonSection(
                    heading = "Inverting Amplifier",
                    content = "The inverting amplifier connects the input through R_in to the inverting (\u207B) terminal, with feedback resistor R_f from output to the inverting terminal. The non-inverting (+) terminal is grounded.\n\nThe output is an amplified, inverted version of the input. The gain is determined entirely by the two resistors.\n\nExample: R_f = 10k\u03A9, R_in = 1k\u03A9 \u2192 Gain = -10",
                    formula = "Gain = -R_f / R_in",
                    keyPoint = "Negative sign means the output is inverted (180\u00B0 phase shift)"
                ),
                LessonSection(
                    heading = "Non-Inverting Amplifier",
                    content = "The non-inverting amplifier applies the input to the non-inverting (+) terminal. R_in connects the inverting terminal to ground, and R_f provides feedback.\n\nThe output is in phase with the input (no inversion). The gain is always greater than or equal to 1.\n\nExample: R_f = 9k\u03A9, R_in = 1k\u03A9 \u2192 Gain = 1 + 9/1 = 10",
                    formula = "Gain = 1 + R_f / R_in",
                    keyPoint = "Non-inverting gain is always \u2265 1 (no sign flip)"
                ),
                LessonSection(
                    heading = "Voltage Follower (Buffer)",
                    content = "A voltage follower is a special case of the non-inverting amplifier where R_f = 0 and R_in = \u221E (no resistors at all). The output directly connects back to the inverting input.\n\nGain = 1 + 0/\u221E = 1. The output exactly follows the input.\n\nWhy is this useful? The voltage follower has extremely high input impedance and low output impedance, making it perfect for isolating sensitive circuits from heavy loads.",
                    formula = "V_out = V_in (Gain = 1)",
                    keyPoint = "Unity gain buffer: isolates circuits without changing the signal"
                ),
                LessonSection(
                    heading = "Summing Amplifier",
                    content = "The summing amplifier is an extension of the inverting amplifier with multiple inputs, each through its own resistor. The output is a weighted sum of all inputs.\n\nIf all input resistors are equal (R), the output is proportional to the sum of inputs. This circuit is used in audio mixers, DACs, and analog computing.\n\nFor equal input resistors: V_out = -(R_f/R)(V\u2081 + V\u2082 + V\u2083 + ...)",
                    formula = "V_out = -R_f(V\u2081/R\u2081 + V\u2082/R\u2082 + ...)",
                    keyPoint = "Each input is weighted by R_f / R_input"
                )
            )
        ),
        quiz = Quiz(
            title = "Op-Amps Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "oa_mc1",
                    questionText = "Which is NOT a property of an ideal op-amp?",
                    options = listOf(
                        "Infinite input impedance",
                        "Zero output impedance",
                        "Unity open-loop gain",
                        "Infinite open-loop gain"
                    ),
                    correctIndex = 2,
                    explanation = "An ideal op-amp has infinite open-loop gain, not unity gain. Unity gain only applies to a voltage follower configuration."
                ),
                Question.MultipleChoice(
                    id = "oa_mc2",
                    questionText = "What is the gain of a voltage follower?",
                    options = listOf(
                        "1",
                        "0",
                        "-1",
                        "Infinity"
                    ),
                    correctIndex = 0,
                    explanation = "A voltage follower has a gain of exactly 1. The output follows the input with no amplification or inversion."
                ),
                Question.MultipleChoice(
                    id = "oa_mc3",
                    questionText = "In a summing amplifier with equal input resistors, the output is proportional to:",
                    options = listOf(
                        "The product of all inputs",
                        "The sum of all inputs",
                        "The average of all inputs",
                        "The largest input only"
                    ),
                    correctIndex = 1,
                    explanation = "With equal input resistors, V_out = -(R_f/R)(V\u2081 + V\u2082 + ...). The output is proportional to the sum of all input voltages."
                ),
                Question.MultipleChoice(
                    id = "oa_mc4",
                    questionText = "With negative feedback and the output not saturated, an op-amp drives its output so that:",
                    options = listOf(
                        "The output current is zero",
                        "The voltage difference between its inputs is (nearly) zero",
                        "The output equals the supply voltage",
                        "Both inputs are at 0V"
                    ),
                    correctIndex = 1,
                    explanation = "This is the 'virtual short': with negative feedback the huge open-loop gain forces V+ ≈ V−. With the 'no input current' rule it's how you analyse op-amp circuits."
                )
            ),
            generators = listOf(
                generator("oa_ni1") { r ->
                    val rf = feedbackResistors.pick(r)
                    val rin = inputResistors.pick(r)
                    val gain = rf / rin
                    val vin = smallInputs.filter { it * gain <= MAX_OUTPUT }.pick(r)
                    val vout = gain * vin
                    Numeric(
                        text = "An inverting amplifier has R_f = ${fmt(rf)}kΩ and R_in = ${fmt(rin)}kΩ. If the input is " +
                            "${fmt(vin)}V, what is the magnitude of the output voltage, in Volts? Round to 2 decimal places.",
                        answer = vout, unit = "V",
                        explanation = "Gain = −R_f/R_in = −${fmt(rf)}/${fmt(rin)} = −${fmt(gain, 2)}. V_out = −${fmt(gain, 2)} × " +
                            "${fmt(vin)}, so its magnitude is ${fmt(vout, 2)}V"
                    )
                },
                generator("oa_ni2") { r ->
                    val rf = nonInvFeedback.pick(r)
                    val rin = nonInvInput.pick(r)
                    val gain = 1 + rf / rin
                    Numeric(
                        text = "A non-inverting amplifier has R_f = ${fmt(rf)}kΩ and R_in = ${fmt(rin)}kΩ. What is its voltage " +
                            "gain? Round to 2 decimal places.",
                        answer = gain, unit = "",
                        explanation = "Gain = 1 + R_f/R_in = 1 + ${fmt(rf)}/${fmt(rin)} = ${fmt(gain, 2)}"
                    )
                },
                generator("oa_ni3") { r ->
                    val g = invertingGains.pick(r)
                    val rin = gainInputResistors.pick(r)
                    Numeric(
                        text = "An inverting amplifier needs a gain of −${fmt(g)}. If R_in = ${fmt(rin)}kΩ, what is R_f in kΩ?",
                        answer = g * rin, unit = "kΩ",
                        explanation = "|Gain| = R_f/R_in, so R_f = ${fmt(g)} × ${fmt(rin)}kΩ = ${fmt(g * rin)}kΩ"
                    )
                },
                generator("oa_ni4") { r ->
                    val g = nonInvGains.pick(r)
                    val vin = nonInvInputs.filter { it * g <= MAX_OUTPUT }.pick(r)
                    Numeric(
                        text = "A non-inverting amplifier with a gain of ${fmt(g)} receives ${article(vin)} ${fmt(vin)}V input. " +
                            "What is the output voltage, in Volts?",
                        answer = g * vin, unit = "V",
                        explanation = "V_out = Gain × V_in = ${fmt(g)} × ${fmt(vin)} = ${fmt(g * vin)}V"
                    )
                },
                generator("oa_ni5") { r ->
                    val (v1, v2) = summingInputs.pickTwo(r)
                    val k = summingRatios.pick(r)
                    val vout = k * (v1 + v2)
                    Numeric(
                        text = "An inverting summing amplifier has two inputs, ${fmt(v1)}V and ${fmt(v2)}V, each through R = 10kΩ, " +
                            "and R_f = ${fmt(10 * k)}kΩ. What is the magnitude of the output voltage, in Volts?",
                        answer = vout, unit = "V",
                        explanation = "V_out = −(R_f/R)(V₁ + V₂) = −${fmt(k)} × (${fmt(v1)} + ${fmt(v2)}), so its magnitude is ${fmt(vout)}V"
                    )
                },
                generator("oa_ni6") { r ->
                    val ratio = diffRatios.pick(r)
                    val v1 = diffBases.pick(r)
                    val v2 = v1 + diffSteps.pick(r)
                    val vout = ratio * (v2 - v1)
                    Numeric(
                        text = "A difference amplifier has R₂/R₁ = ${fmt(ratio)} (matched pairs). With V₁ = ${fmt(v1)}V and " +
                            "V₂ = ${fmt(v2)}V, what is V_out = (R₂/R₁)(V₂ − V₁), in Volts?",
                        answer = vout, unit = "V",
                        explanation = "Only the difference is amplified: V_out = ${fmt(ratio)} × (${fmt(v2)} − ${fmt(v1)}) = ${fmt(vout)}V"
                    )
                }
            )
        )
    )
}
