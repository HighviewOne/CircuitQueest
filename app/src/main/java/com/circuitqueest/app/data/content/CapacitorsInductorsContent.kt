package com.circuitqueest.app.data.content

object CapacitorsInductorsContent {
    val topic = Topic(
        id = "capacitors_inductors",
        title = "Capacitors & Inductors",
        subtitle = "Energy storage in electric fields and magnetic fields",
        icon = "\uD83D\uDD0B",
        order = 3,
        lesson = Lesson(
            title = "Capacitors & Inductors: Storing Energy",
            sections = listOf(
                LessonSection(
                    heading = "What is Capacitance?",
                    content = "A capacitor stores energy in an electric field between two conductive plates separated by an insulator (dielectric). Capacitance measures how much charge a capacitor can store per volt of potential difference.\n\nThe unit of capacitance is the Farad (F). In practice, most capacitors are measured in microfarads (\u00B5F), nanofarads (nF), or picofarads (pF).",
                    formula = "C = Q / V",
                    keyPoint = "Capacitance (Farads) = Charge (Coulombs) / Voltage (Volts)"
                ),
                LessonSection(
                    heading = "Capacitors in Series and Parallel",
                    content = "Capacitors combine in the opposite way to resistors:\n\n\u2022 In parallel: capacitances add directly (C_total = C\u2081 + C\u2082 + ...)\n\u2022 In series: reciprocals add (1/C_total = 1/C\u2081 + 1/C\u2082 + ...)\n\nFor two capacitors in series: C_total = (C\u2081 \u00D7 C\u2082) / (C\u2081 + C\u2082)\n\nRemember: series reduces total capacitance, parallel increases it.",
                    formula = "Parallel: C = C\u2081 + C\u2082\nSeries: 1/C = 1/C\u2081 + 1/C\u2082",
                    keyPoint = "Capacitors combine opposite to resistors"
                ),
                LessonSection(
                    heading = "What is Inductance?",
                    content = "An inductor stores energy in a magnetic field created by current flowing through a coil of wire. Inductance measures the inductor's opposition to changes in current.\n\nThe voltage across an inductor is proportional to the rate of change of current. The unit of inductance is the Henry (H).",
                    formula = "V = L \u00D7 (dI/dt)",
                    keyPoint = "Inductors resist changes in current, not steady current"
                ),
                LessonSection(
                    heading = "RC Time Constants",
                    content = "When a capacitor charges or discharges through a resistor, the time constant \u03C4 (tau) determines how quickly the voltage changes.\n\nAfter one time constant, the capacitor reaches about 63.2% of its final value. After 5\u03C4, it's considered fully charged (>99%).\n\n\u2022 Charging: V(t) = V_source \u00D7 (1 - e^(-t/\u03C4))\n\u2022 Discharging: V(t) = V\u2080 \u00D7 e^(-t/\u03C4)",
                    formula = "\u03C4 = R \u00D7 C",
                    keyPoint = "One time constant = 63.2% of the way to final value"
                ),
                LessonSection(
                    heading = "RL Time Constants",
                    content = "Inductors paired with resistors also have a time constant. In an RL circuit, the time constant determines how quickly current rises or falls.\n\nThe behavior mirrors RC circuits: after one \u03C4 the current reaches 63.2% of its final value.\n\n\u2022 Current rise: I(t) = (V/R) \u00D7 (1 - e^(-t/\u03C4))\n\u2022 Current decay: I(t) = I\u2080 \u00D7 e^(-t/\u03C4)",
                    formula = "\u03C4 = L / R",
                    keyPoint = "RC: \u03C4 = RC (seconds) | RL: \u03C4 = L/R (seconds)"
                ),
                LessonSection(
                    heading = "Energy Storage",
                    content = "Both capacitors and inductors store energy, but in different forms:\n\n\u2022 Capacitor: stores energy in an electric field\n\u2022 Inductor: stores energy in a magnetic field\n\nNeither component dissipates energy (ideal) \u2014 they store it and release it. This makes them essential in filters, oscillators, and power supplies.",
                    formula = "E_cap = \u00BDCV\u00B2\nE_ind = \u00BDLI\u00B2",
                    keyPoint = "Capacitors store E in electric fields, inductors in magnetic fields"
                )
            )
        ),
        quiz = Quiz(
            title = "Capacitors & Inductors Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ci_mc1",
                    questionText = "How do capacitors combine in parallel?",
                    options = listOf(
                        "Capacitances add directly",
                        "Reciprocals add",
                        "They cancel out",
                        "Only the largest one matters"
                    ),
                    correctIndex = 0,
                    explanation = "In parallel, capacitances add directly: C_total = C\u2081 + C\u2082 + ... (opposite to resistors)."
                ),
                Question.NumericInput(
                    id = "ci_ni1",
                    questionText = "A 10\u00B5F capacitor is charged to 5V. How much charge does it store in microcoulombs (\u00B5C)?",
                    correctAnswer = 50.0,
                    tolerance = 0.5,
                    unit = "\u00B5C",
                    explanation = "Q = C \u00D7 V = 10\u00B5F \u00D7 5V = 50\u00B5C"
                ),
                Question.NumericInput(
                    id = "ci_ni2",
                    questionText = "What is the time constant (in milliseconds) of a 1k\u03A9 resistor in series with a 100\u00B5F capacitor?",
                    correctAnswer = 100.0,
                    tolerance = 1.0,
                    unit = "ms",
                    explanation = "\u03C4 = R \u00D7 C = 1000 \u00D7 100\u00D710\u207B\u2076 = 0.1s = 100ms"
                ),
                Question.MultipleChoice(
                    id = "ci_mc2",
                    questionText = "What does an inductor resist?",
                    options = listOf(
                        "Steady-state current",
                        "Changes in voltage",
                        "Changes in current",
                        "Static electric fields"
                    ),
                    correctIndex = 2,
                    explanation = "An inductor opposes changes in current through it (V = L \u00D7 dI/dt). It passes steady-state DC freely."
                ),
                Question.NumericInput(
                    id = "ci_ni3",
                    questionText = "Two capacitors of 6\u00B5F and 3\u00B5F are connected in series. What is the total capacitance in \u00B5F?",
                    correctAnswer = 2.0,
                    tolerance = 0.1,
                    unit = "\u00B5F",
                    explanation = "C_total = (C\u2081 \u00D7 C\u2082)/(C\u2081 + C\u2082) = (6 \u00D7 3)/(6 + 3) = 18/9 = 2\u00B5F"
                ),
                Question.NumericInput(
                    id = "ci_ni4",
                    questionText = "What is the energy stored in a 20\u00B5F capacitor charged to 10V? Answer in millijoules (mJ).",
                    correctAnswer = 1.0,
                    tolerance = 0.05,
                    unit = "mJ",
                    explanation = "E = \u00BDCV\u00B2 = \u00BD \u00D7 20\u00D710\u207B\u2076 \u00D7 10\u00B2 = \u00BD \u00D7 20\u00D710\u207B\u2076 \u00D7 100 = 0.001J = 1mJ"
                ),
                Question.MultipleChoice(
                    id = "ci_mc3",
                    questionText = "What is the time constant of an RL circuit?",
                    options = listOf(
                        "\u03C4 = R \u00D7 L",
                        "\u03C4 = L / R",
                        "\u03C4 = R / L",
                        "\u03C4 = \u221A(LC)"
                    ),
                    correctIndex = 1,
                    explanation = "The RL time constant is \u03C4 = L/R. Unlike RC circuits where \u03C4 = RC, here the inductance is divided by resistance."
                )
            )
        )
    )
}
