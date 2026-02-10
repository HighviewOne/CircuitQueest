package com.circuitqueest.app.data.content

object TheveninNortonContent {
    val topic = Topic(
        id = "thevenin_norton",
        title = "Th\u00E9venin & Norton",
        subtitle = "Simplify any circuit to its equivalent",
        icon = "\uD83D\uDD04",
        order = 6,
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
                Question.NumericInput(
                    id = "tn_ni1",
                    questionText = "A circuit has V_th = 10V and R_th = 5\u03A9. What is the Norton current (I_N) in Amps?",
                    correctAnswer = 2.0,
                    tolerance = 0.1,
                    unit = "A",
                    explanation = "I_N = V_th / R_th = 10 / 5 = 2A"
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
                Question.NumericInput(
                    id = "tn_ni2",
                    questionText = "A Th\u00E9venin circuit has V_th = 20V and R_th = 10\u03A9. What load resistance (in \u03A9) maximizes power transfer?",
                    correctAnswer = 10.0,
                    tolerance = 0.1,
                    unit = "\u03A9",
                    explanation = "Maximum power transfer occurs when R_load = R_th = 10\u03A9."
                ),
                Question.NumericInput(
                    id = "tn_ni3",
                    questionText = "A Th\u00E9venin circuit has V_th = 12V and R_th = 6\u03A9. What is the maximum power (in Watts) that can be delivered to a load?",
                    correctAnswer = 6.0,
                    tolerance = 0.1,
                    unit = "W",
                    explanation = "P_max = V_th\u00B2 / (4 \u00D7 R_th) = 144 / 24 = 6W"
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
                Question.NumericInput(
                    id = "tn_ni4",
                    questionText = "A Norton circuit has I_N = 3A and R_N = 8\u03A9. What is the Th\u00E9venin voltage (V_th) in Volts?",
                    correctAnswer = 24.0,
                    tolerance = 0.1,
                    unit = "V",
                    explanation = "V_th = I_N \u00D7 R_N = 3 \u00D7 8 = 24V"
                )
            )
        )
    )
}
