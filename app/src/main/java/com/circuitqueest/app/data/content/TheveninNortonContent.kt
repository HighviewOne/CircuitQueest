package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import com.circuitqueest.app.data.content.QuestionMath.pickTwo

object TheveninNortonContent {
    private val theveninVoltages = listOf(6.0, 9.0, 10.0, 12.0, 15.0, 20.0, 24.0)
    private val theveninResistances = listOf(2.0, 3.0, 4.0, 5.0, 6.0, 8.0, 10.0)
    private val matchingResistances = listOf(4.7, 10.0, 22.0, 47.0, 50.0, 75.0, 100.0)
    private val nortonCurrents = listOf(0.5, 1.0, 2.0, 3.0, 4.0)
    private val nortonResistances = listOf(2.0, 4.0, 5.0, 8.0, 10.0, 12.0)
    private val dividerSupplies = listOf(9.0, 12.0, 15.0, 24.0)
    private val dividerResistances = listOf(1.0, 2.0, 3.0, 4.0, 6.0, 12.0)
    private val loadResistances = listOf(2.0, 4.0, 6.0, 10.0, 20.0)

    val topic = Topic(
        id = "thevenin_norton",
        title = "Th\u00E9venin & Norton",
        subtitle = "Simplify any circuit to its equivalent",
        icon = "\uD83D\uDD04",
        order = 5,
        lesson = Lesson(
            title = "Th\u00E9venin & Norton: Circuit Simplification",
            sections = listOf(
                LessonSection(
                    heading = "Why Simplify Circuits?",
                    content = "Complex circuits with many components can be difficult to analyze, especially when you only care about the behavior at two terminals. Th\u00E9venin's and Norton's theorems let you replace an entire network with a simple equivalent circuit.\n\nThis is incredibly powerful for analyzing how a circuit interacts with different loads without re-solving the whole network each time.",
                    keyPoint = "Any linear circuit can be replaced by a simple equivalent at two terminals"
                ),
                LessonSection(
                    heading = "Th\u00E9venin's Theorem",
                    content = "Th\u00E9venin's theorem states that any linear circuit can be replaced by a single voltage source (V_th) in series with a single resistance (R_th).\n\nTo find the Th\u00E9venin equivalent:\n1. V_th = open-circuit voltage across the terminals (remove the load)\n2. R_th = resistance seen from the terminals with all independent sources turned off (voltage sources \u2192 short, current sources \u2192 open)",
                    formula = "V_th = V_open-circuit\nR_th = V_oc / I_sc",
                    keyPoint = "Th\u00E9venin = voltage source + series resistance"
                ),
                LessonSection(
                    heading = "Norton's Theorem",
                    content = "Norton's theorem is the dual of Th\u00E9venin's. It replaces the circuit with a current source (I_N) in parallel with a resistance (R_N).\n\nTo find the Norton equivalent:\n1. I_N = short-circuit current through the terminals\n2. R_N = same as R_th (identical resistance)\n\nNorton and Th\u00E9venin equivalents are interchangeable: I_N = V_th / R_th.",
                    formula = "I_N = I_short-circuit\nR_N = R_th",
                    keyPoint = "Norton = current source + parallel resistance"
                ),
                LessonSection(
                    heading = "Source Transformation",
                    content = "You can convert between Th\u00E9venin and Norton forms at any time:\n\n\u2022 Th\u00E9venin \u2192 Norton: I_N = V_th / R_th, R_N = R_th\n\u2022 Norton \u2192 Th\u00E9venin: V_th = I_N \u00D7 R_N, R_th = R_N\n\nThis technique, called source transformation, is useful for simplifying circuits step by step before applying the final theorem.",
                    formula = "V_th = I_N \u00D7 R_N\nI_N = V_th / R_th",
                    keyPoint = "Source transformation freely converts between voltage and current source models"
                ),
                LessonSection(
                    heading = "Maximum Power Transfer",
                    content = "An important application of Th\u00E9venin's theorem is determining the load that extracts maximum power from a source.\n\nMaximum power is delivered to the load when the load resistance equals the Th\u00E9venin resistance. This principle is critical in audio systems, communications, and antenna design.\n\nThe maximum power delivered is P_max = V_th\u00B2 / (4 \u00D7 R_th).",
                    formula = "R_load = R_th (for max power)\nP_max = V_th\u00B2 / (4R_th)",
                    keyPoint = "Match load resistance to source resistance for maximum power transfer"
                ),
                LessonSection(
                    heading = "Practical Example",
                    content = "Consider a circuit with a 12V source, a 4\u03A9 resistor in series, and a 6\u03A9 resistor in parallel with the output terminals:\n\n1. V_th: Use voltage divider \u2192 V_th = 12 \u00D7 6/(4+6) = 7.2V\n2. R_th: Turn off the 12V source (short it) \u2192 R_th = 4\u03A9 \u2016 6\u03A9 = 2.4\u03A9\n\nNow any load analysis uses just V_th = 7.2V and R_th = 2.4\u03A9.",
                    keyPoint = "Practice: find V_th by open-circuit, R_th by deactivating sources"
                )
            )
        ),
        quiz = Quiz(
            title = "Th\u00E9venin & Norton Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "tn_mc1",
                    questionText = "A Th\u00E9venin equivalent circuit consists of:",
                    options = listOf(
                        "A voltage source in series with a resistance",
                        "A current source in parallel with a resistance",
                        "A voltage source in parallel with a resistance",
                        "Two resistors in series"
                    ),
                    correctIndex = 0,
                    explanation = "Th\u00E9venin's equivalent is a voltage source (V_th) in series with a resistance (R_th)."
                ),
                Question.MultipleChoice(
                    id = "tn_mc2",
                    questionText = "To find R_th, you turn off all independent sources. How do you turn off a voltage source?",
                    options = listOf(
                        "Remove it entirely",
                        "Replace it with an open circuit",
                        "Replace it with a short circuit",
                        "Set it to 1V"
                    ),
                    correctIndex = 2,
                    explanation = "An ideal voltage source turned off has 0V across it, which behaves as a short circuit (wire)."
                ),
                Question.MultipleChoice(
                    id = "tn_mc3",
                    questionText = "Norton's equivalent uses a current source in which configuration?",
                    options = listOf(
                        "In series with a resistance",
                        "In parallel with a resistance",
                        "Alone with no resistance",
                        "In series with a capacitor"
                    ),
                    correctIndex = 1,
                    explanation = "Norton's equivalent is a current source (I_N) in parallel with a resistance (R_N)."
                ),
                Question.MultipleChoice(
                    id = "tn_mc4",
                    questionText = "When finding R_th, how do you turn off an independent current source?",
                    options = listOf(
                        "Replace it with a short circuit",
                        "Replace it with an open circuit",
                        "Leave it as it is",
                        "Replace it with a 1Ω resistor"
                    ),
                    correctIndex = 1,
                    explanation = "A current source turned off delivers 0A, which is an open circuit. (A voltage source turned off becomes a short circuit.)"
                )
            ),
            generators = listOf(
                generator("tn_ni1") { r ->
                    val v = theveninVoltages.pick(r)
                    val res = theveninResistances.pick(r)
                    val i = v / res
                    Numeric(
                        text = "A circuit has V_th = ${fmt(v)}V and R_th = ${fmt(res)}Ω. What is the Norton current (I_N) " +
                            "in Amps? Round to 2 decimal places.",
                        answer = i, unit = "A",
                        explanation = "I_N = V_th / R_th = ${fmt(v)} / ${fmt(res)} = ${fmt(i, 2)}A"
                    )
                },
                generator("tn_ni2") { r ->
                    val v = theveninVoltages.pick(r)
                    val res = matchingResistances.pick(r)
                    Numeric(
                        text = "A Thévenin circuit has V_th = ${fmt(v)}V and R_th = ${fmt(res)}Ω. What load resistance " +
                            "(in Ω) maximizes power transfer?",
                        answer = res, unit = "Ω", decimals = 1,
                        explanation = "Maximum power transfer occurs when R_load = R_th = ${fmt(res)}Ω. The source voltage doesn't matter."
                    )
                },
                generator("tn_ni3") { r ->
                    val v = theveninVoltages.pick(r)
                    val res = theveninResistances.pick(r)
                    val p = (v / 2) * (v / 2) / res
                    Numeric(
                        text = "A Thévenin circuit has V_th = ${fmt(v)}V and R_th = ${fmt(res)}Ω. What is the maximum power " +
                            "(in Watts) that can be delivered to a load? Round to 2 decimal places.",
                        answer = p, unit = "W",
                        explanation = "With R_load = R_th the load gets half of V_th: P_max = V_th²/(4R_th) = " +
                            "${fmt(v * v)}/(4 × ${fmt(res)}) = ${fmt(p, 2)}W"
                    )
                },
                generator("tn_ni4") { r ->
                    val i = nortonCurrents.pick(r)
                    val res = nortonResistances.pick(r)
                    Numeric(
                        text = "A Norton circuit has I_N = ${fmt(i)}A and R_N = ${fmt(res)}Ω. What is the Thévenin voltage " +
                            "(V_th) in Volts?",
                        answer = i * res, unit = "V",
                        explanation = "V_th = I_N × R_N = ${fmt(i)} × ${fmt(res)} = ${fmt(i * res)}V"
                    )
                },
                generator("tn_ni5") { r ->
                    val vs = dividerSupplies.pick(r)
                    val (r1, r2) = dividerResistances.pickTwo(r)
                    val vth = vs * r2 / (r1 + r2)
                    Numeric(
                        text = "A ${fmt(vs)}V source feeds R₁ = ${fmt(r1)}kΩ in series with R₂ = ${fmt(r2)}kΩ, and the output " +
                            "is taken across R₂. What is the Thévenin voltage at the output, in Volts? Round to 2 decimal places.",
                        answer = vth, unit = "V",
                        explanation = "V_th is the open-circuit output, a voltage divider: ${fmt(vs)} × ${fmt(r2)}/${fmt(r1 + r2)} = " +
                            "${fmt(vth, 2)}V"
                    )
                },
                generator("tn_ni6") { r ->
                    val vs = dividerSupplies.pick(r)
                    val (r1, r2) = dividerResistances.pickTwo(r)
                    val rth = r1 * r2 / (r1 + r2)
                    Numeric(
                        text = "A ${fmt(vs)}V source feeds R₁ = ${fmt(r1)}kΩ in series with R₂ = ${fmt(r2)}kΩ, and the output " +
                            "is taken across R₂. What is the Thévenin resistance at the output, in kΩ? Round to 2 decimal places.",
                        answer = rth, unit = "kΩ",
                        explanation = "Turn the source off (a short): R₁ and R₂ are then in parallel, so R_th = " +
                            "(${fmt(r1)}×${fmt(r2)})/(${fmt(r1)}+${fmt(r2)}) = ${fmt(rth, 2)}kΩ"
                    )
                },
                generator("tn_ni7") { r ->
                    val v = theveninVoltages.pick(r)
                    val res = theveninResistances.pick(r)
                    val load = loadResistances.pick(r)
                    val i = v / (res + load)
                    Numeric(
                        text = "A Thévenin source of V_th = ${fmt(v)}V and R_th = ${fmt(res)}Ω drives a ${fmt(load)}Ω load. " +
                            "What current flows through the load, in Amps? Round to 2 decimal places.",
                        answer = i, unit = "A",
                        explanation = "I = V_th/(R_th + R_load) = ${fmt(v)}/(${fmt(res)} + ${fmt(load)}) = ${fmt(i, 2)}A"
                    )
                }
            )
        )
    )
}
