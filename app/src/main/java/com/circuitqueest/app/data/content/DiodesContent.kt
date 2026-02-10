package com.circuitqueest.app.data.content

object DiodesContent {
    val topic = Topic(
        id = "diodes_rectifiers",
        title = "Diodes & Rectifiers",
        subtitle = "One-way gates for current flow",
        icon = "\u25B6\uFE0F",
        order = 7,
        lesson = Lesson(
            title = "Diodes & Rectifiers: The One-Way Street",
            sections = listOf(
                LessonSection(
                    heading = "The PN Junction",
                    content = "A diode is made by joining P-type and N-type semiconductor materials. At the junction, a depletion region forms where electrons and holes recombine, creating a barrier.\n\nThis barrier gives the diode its key property: it allows current to flow easily in one direction (forward bias) and blocks it in the other (reverse bias).",
                    keyPoint = "P-type has excess holes, N-type has excess electrons"
                ),
                LessonSection(
                    heading = "Forward and Reverse Bias",
                    content = "Forward bias: When the anode (+) is more positive than the cathode (\u2212) by at least the threshold voltage, the diode conducts. For silicon diodes, this threshold is approximately 0.7V.\n\nReverse bias: When the cathode is more positive than the anode, the depletion region widens and virtually no current flows.\n\nIn circuit analysis, a conducting silicon diode is often modeled as a 0.7V drop.",
                    formula = "V_forward \u2248 0.7V (silicon)\nV_forward \u2248 0.3V (germanium)",
                    keyPoint = "Silicon diode: 0.7V forward drop; blocks current in reverse"
                ),
                LessonSection(
                    heading = "Zener Diodes",
                    content = "A Zener diode is designed to operate in reverse breakdown at a specific voltage (V_Z). Unlike regular diodes, this breakdown is non-destructive and highly stable.\n\nZener diodes are used as voltage regulators: connect one in reverse bias with a series resistor, and the output voltage stays constant at V_Z regardless of load or input variations (within limits).",
                    formula = "V_out = V_Z (in breakdown)\nI_series = (V_in - V_Z) / R_series",
                    keyPoint = "Zener diodes regulate voltage using controlled reverse breakdown"
                ),
                LessonSection(
                    heading = "Half-Wave Rectifier",
                    content = "A rectifier converts AC to DC. The simplest rectifier uses a single diode.\n\nDuring the positive half-cycle, the diode conducts and the load sees the input voltage (minus 0.7V). During the negative half-cycle, the diode blocks and the load sees 0V.\n\nThe result is pulsating DC \u2014 only the positive halves of the AC waveform pass through. This is inefficient since half the input power is wasted.",
                    formula = "V_dc(avg) = V_peak / \u03C0",
                    keyPoint = "Half-wave: one diode, uses only positive half-cycles"
                ),
                LessonSection(
                    heading = "Full-Wave Bridge Rectifier",
                    content = "A bridge rectifier uses four diodes arranged in a diamond pattern. During each half-cycle, two diodes conduct and two block, routing current through the load in the same direction regardless of input polarity.\n\nThe output is smoother than half-wave and uses the full input waveform. The trade-off is two diode drops (1.4V for silicon) instead of one.\n\nAdding a capacitor across the output smooths the pulsations further.",
                    formula = "V_dc(avg) = 2 \u00D7 V_peak / \u03C0",
                    keyPoint = "Bridge rectifier: 4 diodes, both half-cycles used, smoother DC output"
                ),
                LessonSection(
                    heading = "LEDs and Photodiodes",
                    content = "Light-Emitting Diodes (LEDs) emit light when forward biased. Different semiconductor materials produce different colors. LEDs need a current-limiting resistor to prevent burnout.\n\nPhotodiodes work in reverse: they convert light into current. They are used in optical communications, solar cells, and light sensors.\n\nLED resistor calculation: R = (V_supply \u2212 V_LED) / I_LED",
                    formula = "R_LED = (V_supply \u2212 V_LED) / I_LED",
                    keyPoint = "Always use a resistor with LEDs to limit current"
                )
            )
        ),
        quiz = Quiz(
            title = "Diodes & Rectifiers Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "dr_mc1",
                    questionText = "What is the approximate forward voltage drop of a silicon diode?",
                    options = listOf(
                        "0.3V",
                        "0.7V",
                        "1.2V",
                        "5.0V"
                    ),
                    correctIndex = 1,
                    explanation = "A silicon diode has a forward voltage drop of approximately 0.7V. Germanium diodes have about 0.3V."
                ),
                Question.NumericInput(
                    id = "dr_ni1",
                    questionText = "A 5V source drives a silicon diode in series with a 430\u03A9 resistor. What is the current through the circuit in milliamps (mA)?",
                    correctAnswer = 10.0,
                    tolerance = 0.2,
                    unit = "mA",
                    explanation = "I = (V_source \u2212 V_diode) / R = (5 \u2212 0.7) / 430 = 4.3/430 = 0.01A = 10mA"
                ),
                Question.MultipleChoice(
                    id = "dr_mc2",
                    questionText = "How many diodes does a full-wave bridge rectifier use?",
                    options = listOf(
                        "1",
                        "2",
                        "4",
                        "6"
                    ),
                    correctIndex = 2,
                    explanation = "A bridge rectifier uses 4 diodes arranged in a diamond configuration to rectify both halves of the AC cycle."
                ),
                Question.NumericInput(
                    id = "dr_ni2",
                    questionText = "A Zener diode has V_Z = 5.1V. If the input is 12V with a 1k\u03A9 series resistor, what current flows through the resistor in milliamps?",
                    correctAnswer = 6.9,
                    tolerance = 0.1,
                    unit = "mA",
                    explanation = "I = (V_in \u2212 V_Z) / R = (12 \u2212 5.1) / 1000 = 6.9mA"
                ),
                Question.MultipleChoice(
                    id = "dr_mc3",
                    questionText = "What happens when a regular diode is reverse biased?",
                    options = listOf(
                        "It conducts normally",
                        "It emits light",
                        "It blocks current flow",
                        "It doubles the voltage"
                    ),
                    correctIndex = 2,
                    explanation = "In reverse bias, the depletion region widens and the diode blocks current flow (ideally zero current)."
                ),
                Question.NumericInput(
                    id = "dr_ni3",
                    questionText = "What resistor value (in \u03A9) is needed to run a red LED (V_LED = 2V, I = 20mA) from a 9V supply?",
                    correctAnswer = 350.0,
                    tolerance = 5.0,
                    unit = "\u03A9",
                    explanation = "R = (V_supply \u2212 V_LED) / I_LED = (9 \u2212 2) / 0.02 = 7/0.02 = 350\u03A9"
                ),
                Question.NumericInput(
                    id = "dr_ni4",
                    questionText = "A half-wave rectifier receives an AC signal with V_peak = 10V. What is the average DC output voltage? Round to one decimal place.",
                    correctAnswer = 3.2,
                    tolerance = 0.1,
                    unit = "V",
                    explanation = "V_dc(avg) = V_peak / \u03C0 = 10 / 3.14 \u2248 3.2V"
                )
            )
        )
    )
}
