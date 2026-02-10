package com.circuitqueest.app.data.content

object OpAmpsContent {
    val topic = Topic(
        id = "op_amps",
        title = "Op-Amps",
        subtitle = "Operational amplifier circuits and gain",
        icon = "\uD83D\uDD3A",
        order = 5,
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
                Question.NumericInput(
                    id = "oa_ni1",
                    questionText = "An inverting amplifier has R_f = 47k\u03A9 and R_in = 10k\u03A9. If the input voltage is 0.5V, what is the magnitude of the output voltage in Volts?",
                    correctAnswer = 2.35,
                    tolerance = 0.05,
                    unit = "V",
                    explanation = "Gain = -R_f/R_in = -47k/10k = -4.7. V_out = -4.7 \u00D7 0.5 = -2.35V. Magnitude = 2.35V."
                ),
                Question.NumericInput(
                    id = "oa_ni2",
                    questionText = "A non-inverting amplifier has R_f = 15k\u03A9 and R_in = 5k\u03A9. What is the gain?",
                    correctAnswer = 4.0,
                    tolerance = 0.1,
                    unit = "",
                    explanation = "Gain = 1 + R_f/R_in = 1 + 15k/5k = 1 + 3 = 4"
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
                Question.NumericInput(
                    id = "oa_ni3",
                    questionText = "An inverting amplifier has a gain of -8. If R_in = 2k\u03A9, what is R_f in k\u03A9?",
                    correctAnswer = 16.0,
                    tolerance = 0.1,
                    unit = "k\u03A9",
                    explanation = "Gain = -R_f/R_in. |-8| = R_f/2k\u03A9. R_f = 8 \u00D7 2k = 16k\u03A9."
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
                Question.NumericInput(
                    id = "oa_ni4",
                    questionText = "A non-inverting amplifier with gain 6 receives a 2V input. What is the output voltage in Volts?",
                    correctAnswer = 12.0,
                    tolerance = 0.1,
                    unit = "V",
                    explanation = "V_out = Gain \u00D7 V_in = 6 \u00D7 2 = 12V"
                )
            )
        )
    )
}
