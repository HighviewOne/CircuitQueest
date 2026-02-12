package com.circuitqueest.app.data.content

object AnalogCircuitsContent {
    val topic = Topic(
        id = "analog_circuits",
        title = "Analog Circuit Design",
        subtitle = "Current mirrors, diff pairs, and references",
        icon = "\u2747\uFE0F",
        order = 31,
        lesson = Lesson(
            title = "Analog Circuit Design: Precision in the Continuous Domain",
            sections = listOf(
                LessonSection(
                    heading = "Why Analog Still Matters",
                    content = "Despite the digital revolution, every electronic system needs analog circuits at its boundaries. The real world is analog \u2014 temperature, sound, light, and motion are all continuous.\n\nAnalog circuits handle:\n\u2022 Sensor signal conditioning (amplification, filtering)\n\u2022 Data conversion (ADC/DAC)\n\u2022 Power management (regulators, references)\n\u2022 RF and wireless communication\n\nThe fundamental analog building blocks \u2014 current mirrors, differential pairs, and voltage references \u2014 appear inside nearly every IC.",
                    keyPoint = "Analog interfaces the continuous real world with digital processing"
                ),
                LessonSection(
                    heading = "Current Mirrors",
                    content = "A current mirror copies (mirrors) a reference current to one or more outputs. It is the most fundamental analog building block in IC design.\n\nBasic BJT current mirror: two matched transistors share the same V_BE. One is diode-connected (collector tied to base) to set the reference current. The other copies it.\n\nI_out = I_ref \u00D7 (W/L)\u2082 / (W/L)\u2081 (for MOSFETs)\n\nCurrent mirrors provide bias currents throughout a chip, replacing resistors which are expensive in silicon area.",
                    formula = "I_out \u2248 I_ref (matched devices)\nMOSFET: I_out = I_ref \u00D7 (W/L)\u2082/(W/L)\u2081",
                    keyPoint = "Current mirrors copy a reference current; essential for IC biasing"
                ),
                LessonSection(
                    heading = "Differential Pair",
                    content = "The differential pair (diff pair) is the input stage of nearly every op-amp and comparator. Two matched transistors share a common tail current source.\n\nIt amplifies the difference between two inputs while rejecting signals common to both (common-mode rejection).\n\n\u2022 Differential gain: A_d = g_m \u00D7 R_D\n\u2022 Common-mode gain: ideally zero (limited by tail current source impedance)\n\u2022 CMRR = A_d / A_cm (higher is better)\n\nThe diff pair converts a voltage difference into a current difference, which is then converted back to voltage by load resistors or a current mirror.",
                    formula = "A_d = g_m \u00D7 R_D\nCMRR = A_d / A_cm",
                    keyPoint = "Diff pair: amplifies difference, rejects common-mode; heart of every op-amp"
                ),
                LessonSection(
                    heading = "Feedback Amplifiers",
                    content = "Negative feedback is the cornerstone of precision analog design. By feeding a fraction of the output back to the input, we trade gain for stability and accuracy.\n\nFeedback benefits:\n\u2022 Gain becomes predictable: A_closed = 1/\u03B2 (depends on feedback network, not amplifier)\n\u2022 Bandwidth increases: BW_closed = BW_open \u00D7 (1 + A\u03B2)\n\u2022 Distortion decreases by factor (1 + A\u03B2)\n\u2022 Input/output impedance improves\n\nThe gain-bandwidth product (GBW) is constant: increasing closed-loop gain reduces bandwidth proportionally.",
                    formula = "A_closed = A / (1 + A\u03B2) \u2248 1/\u03B2\nGBW = A_open \u00D7 BW_open = constant",
                    keyPoint = "Negative feedback: trade gain for stability, accuracy, and bandwidth"
                ),
                LessonSection(
                    heading = "Bandgap Voltage Reference",
                    content = "A bandgap reference generates a stable voltage (~1.25V for silicon) that is nearly independent of temperature and supply voltage.\n\nIt works by summing two opposing temperature coefficients:\n\u2022 V_BE of a BJT: decreases ~\u22122mV/\u00B0C (CTAT)\n\u2022 \u0394V_BE of two BJTs at different current densities: increases with temperature (PTAT)\n\nWeighting them correctly cancels the temperature dependence at ~1.25V \u2014 close to silicon's bandgap energy.\n\nBandgap references are inside virtually every ADC, DAC, regulator, and precision analog IC.",
                    formula = "V_ref = V_BE + K \u00D7 \u0394V_BE \u2248 1.25V\n\u0394V_BE = (kT/q) \u00D7 ln(N)",
                    keyPoint = "Bandgap: ~1.25V reference, temperature-stable, in nearly every IC"
                ),
                LessonSection(
                    heading = "Cascode and Gain Boosting",
                    content = "A single transistor amplifier has limited gain due to finite output resistance. The cascode configuration stacks a second transistor on top to dramatically increase output resistance and voltage gain.\n\n\u2022 Simple common-source: A_v = g_m \u00D7 r_o\n\u2022 Cascode: A_v = g_m \u00D7 (g_m2 \u00D7 r_o1 \u00D7 r_o2) (much higher)\n\nThe cascode also improves bandwidth by reducing the Miller effect (input capacitance multiplication).\n\nFolded cascode and regulated cascode are variants used in high-performance op-amp design to maximize gain within limited voltage headroom.",
                    formula = "Cascode gain \u2248 g_m \u00D7 (g_m \u00D7 r_o\u00B2)\nSimple gain = g_m \u00D7 r_o",
                    keyPoint = "Cascode: stacked transistors for much higher gain and bandwidth"
                )
            )
        ),
        quiz = Quiz(
            title = "Analog Circuit Design Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ac_mc1",
                    questionText = "What does a current mirror do?",
                    options = listOf(
                        "Reflects voltage signals",
                        "Copies a reference current to one or more outputs",
                        "Converts AC to DC",
                        "Amplifies current by a factor of beta"
                    ),
                    correctIndex = 1,
                    explanation = "A current mirror uses matched transistors to copy (mirror) a reference current. The output current equals the reference current for matched devices."
                ),
                Question.MultipleChoice(
                    id = "ac_mc2",
                    questionText = "The differential pair amplifies:",
                    options = listOf(
                        "The sum of two inputs",
                        "The difference between two inputs",
                        "Only the larger input",
                        "The common-mode signal"
                    ),
                    correctIndex = 1,
                    explanation = "A differential pair amplifies the voltage difference between its two inputs while rejecting signals common to both (common-mode rejection)."
                ),
                Question.NumericInput(
                    id = "ac_ni1",
                    questionText = "An op-amp has open-loop gain A = 100,000 and feedback factor \u03B2 = 0.01. What is the closed-loop gain?",
                    correctAnswer = 100.0,
                    tolerance = 0.5,
                    unit = "",
                    explanation = "A_closed = A/(1+A\u03B2) = 100000/(1+100000\u00D70.01) = 100000/1001 \u2248 100. Or simply 1/\u03B2 = 1/0.01 = 100."
                ),
                Question.MultipleChoice(
                    id = "ac_mc3",
                    questionText = "A bandgap voltage reference produces approximately:",
                    options = listOf(
                        "0.7V",
                        "1.25V",
                        "3.3V",
                        "5.0V"
                    ),
                    correctIndex = 1,
                    explanation = "A silicon bandgap reference produces ~1.25V, close to the bandgap energy of silicon (1.12eV), by canceling opposing temperature coefficients."
                ),
                Question.NumericInput(
                    id = "ac_ni2",
                    questionText = "An amplifier has GBW = 10MHz. If the closed-loop gain is 100, what is the bandwidth in kHz?",
                    correctAnswer = 100.0,
                    tolerance = 1.0,
                    unit = "kHz",
                    explanation = "BW = GBW / Gain = 10MHz / 100 = 100kHz. Gain-bandwidth product is constant."
                ),
                Question.MultipleChoice(
                    id = "ac_mc4",
                    questionText = "The cascode configuration improves:",
                    options = listOf(
                        "Input impedance only",
                        "Output resistance and voltage gain",
                        "Power consumption",
                        "Noise figure only"
                    ),
                    correctIndex = 1,
                    explanation = "The cascode stacks a second transistor to dramatically increase output resistance (and thus voltage gain) while also improving bandwidth by reducing Miller effect."
                ),
                Question.NumericInput(
                    id = "ac_ni3",
                    questionText = "A MOSFET current mirror has (W/L)\u2081 = 10 and (W/L)\u2082 = 30. If I_ref = 100\u00B5A, what is I_out in \u00B5A?",
                    correctAnswer = 300.0,
                    tolerance = 5.0,
                    unit = "\u00B5A",
                    explanation = "I_out = I_ref \u00D7 (W/L)\u2082/(W/L)\u2081 = 100 \u00D7 30/10 = 300\u00B5A"
                )
            )
        )
    )
}
