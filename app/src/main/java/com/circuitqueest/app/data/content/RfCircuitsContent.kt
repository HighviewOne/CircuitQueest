package com.circuitqueest.app.data.content

object RfCircuitsContent {
    val topic = Topic(
        id = "rf_circuits",
        title = "RF Circuit Design",
        subtitle = "Oscillators, amplifiers, and S-parameters",
        icon = "\uD83D\uDCFB",
        order = 30,
        lesson = Lesson(
            title = "RF Circuit Design: The High-Frequency Frontier",
            sections = listOf(
                LessonSection(
                    heading = "What Makes RF Different?",
                    content = "Radio Frequency (RF) circuits operate at frequencies from ~3MHz to 300GHz. At these frequencies, normal circuit analysis breaks down:\n\n\u2022 Wires are no longer simple connections \u2014 they are transmission lines\n\u2022 Parasitic capacitances and inductances dominate behavior\n\u2022 Components behave differently (a capacitor can look inductive above its self-resonant frequency)\n\u2022 Electromagnetic radiation becomes significant\n\nRF design requires specialized tools, techniques, and a different way of thinking about circuits.",
                    keyPoint = "At RF, parasitics dominate and every wire is a transmission line"
                ),
                LessonSection(
                    heading = "S-Parameters",
                    content = "At RF, voltage and current are hard to measure directly. Instead, we use scattering parameters (S-parameters) that describe how signals reflect and transmit through a network.\n\nFor a 2-port network:\n\u2022 S11: input reflection (return loss) \u2014 how much bounces back from port 1\n\u2022 S21: forward transmission (gain/loss) \u2014 how much passes from port 1 to port 2\n\u2022 S12: reverse transmission (isolation)\n\u2022 S22: output reflection\n\nS-parameters are complex numbers (magnitude and phase) measured at a specific impedance (usually 50\u03A9).",
                    formula = "S11 = reflected/incident at port 1\nS21 = transmitted/incident (gain)\n|S21|dB = 20 log\u2081\u2080|S21|",
                    keyPoint = "S11 = how well matched; S21 = gain or loss; measured in 50\u03A9 system"
                ),
                LessonSection(
                    heading = "Low-Noise Amplifier (LNA)",
                    content = "The LNA is the first amplifier in a receiver chain. Its job is to amplify the weak received signal while adding as little noise as possible.\n\nThe noise figure (NF) measures how much noise the amplifier adds:\n\u2022 NF = 0 dB: perfect (adds no noise)\n\u2022 NF = 1 dB: excellent (typical LNA)\n\u2022 NF = 3 dB: doubles the noise\n\nFriis formula: the first stage dominates overall noise figure. That's why a good LNA is critical \u2014 noise added later in the chain matters much less.",
                    formula = "NF_total = NF\u2081 + (NF\u2082-1)/G\u2081 + (NF\u2083-1)/(G\u2081G\u2082) + ...",
                    keyPoint = "First-stage NF dominates; a good LNA is the most critical receiver component"
                ),
                LessonSection(
                    heading = "Oscillators",
                    content = "An oscillator generates a periodic signal without an input. It requires:\n\n1. An amplifier with gain\n2. A feedback network that selects the oscillation frequency\n3. Barkhausen criterion: loop gain \u2265 1 and phase shift = 0\u00B0 (or 360\u00B0)\n\nCommon RF oscillator types:\n\u2022 LC oscillator (Colpitts, Hartley): uses inductor-capacitor tank circuit\n\u2022 Crystal oscillator: uses quartz crystal for high stability (ppm accuracy)\n\u2022 VCO (Voltage-Controlled Oscillator): frequency tuned by a control voltage\n\u2022 PLL (Phase-Locked Loop): locks VCO to a reference for precise synthesis",
                    formula = "f_osc = 1 / (2\u03C0\u221ALC)  (LC oscillator)\nBarkhausen: |A\u03B2| \u2265 1, \u2220A\u03B2 = 0\u00B0",
                    keyPoint = "Barkhausen: loop gain \u2265 1 and zero phase shift for sustained oscillation"
                ),
                LessonSection(
                    heading = "Mixers and Frequency Conversion",
                    content = "A mixer multiplies two signals together, producing sum and difference frequencies. This is how radios tune to different stations.\n\nIf f_RF = 900MHz and f_LO = 890MHz:\n\u2022 f_IF = f_RF \u2212 f_LO = 10MHz (difference \u2014 desired)\n\u2022 f_sum = f_RF + f_LO = 1790MHz (filtered out)\n\nDownconversion (RF \u2192 IF) makes the signal easier to filter and digitize. Upconversion (IF \u2192 RF) is used in transmitters.\n\nKey mixer specs: conversion loss, noise figure, linearity (IP3).",
                    formula = "f_IF = |f_RF \u2212 f_LO|\ncos(A) \u00D7 cos(B) = \u00BD[cos(A-B) + cos(A+B)]",
                    keyPoint = "Mixer produces sum and difference frequencies; filter selects the one you want"
                ),
                LessonSection(
                    heading = "RF Matching Networks",
                    content = "Impedance matching is critical at RF to maximize power transfer and minimize reflections. Common matching networks:\n\n\u2022 L-network: two reactive elements (L and C) in an L shape. Simple but limited bandwidth.\n\u2022 Pi-network (\u03C0): three elements, offers more design freedom and can transform wider impedance ratios.\n\u2022 T-network: three elements in a T shape, good for high-Q matching.\n\u2022 Microstrip matching: transmission line sections on a PCB (stubs, quarter-wave transforms).\n\nThe Smith chart is the primary tool for designing matching networks visually.",
                    keyPoint = "L-network is simplest; use Pi/T for wider impedance ratios or bandwidth"
                )
            )
        ),
        quiz = Quiz(
            title = "RF Circuit Design Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "rf_mc1",
                    questionText = "What does the S-parameter S21 represent?",
                    options = listOf(
                        "Input reflection coefficient",
                        "Forward transmission (gain or loss)",
                        "Output reflection coefficient",
                        "Reverse isolation"
                    ),
                    correctIndex = 1,
                    explanation = "S21 is the forward transmission coefficient \u2014 it measures how much signal passes from port 1 to port 2 (gain if >1, loss if <1)."
                ),
                Question.MultipleChoice(
                    id = "rf_mc2",
                    questionText = "Why is the first amplifier stage's noise figure the most critical?",
                    options = listOf(
                        "It draws the most current",
                        "Its noise is amplified by all subsequent stages (Friis formula)",
                        "It operates at the highest frequency",
                        "It has the most gain"
                    ),
                    correctIndex = 1,
                    explanation = "Friis formula shows that noise from the first stage dominates: subsequent stage noise is divided by the gain of preceding stages."
                ),
                Question.NumericInput(
                    id = "rf_ni1",
                    questionText = "A mixer has f_RF = 2.4GHz and f_LO = 2.3GHz. What is the IF frequency in MHz?",
                    correctAnswer = 100.0,
                    tolerance = 1.0,
                    unit = "MHz",
                    explanation = "f_IF = |f_RF \u2212 f_LO| = |2400 \u2212 2300| = 100MHz"
                ),
                Question.MultipleChoice(
                    id = "rf_mc3",
                    questionText = "The Barkhausen criterion for oscillation requires:",
                    options = listOf(
                        "Loop gain < 1 and 90\u00B0 phase shift",
                        "Loop gain \u2265 1 and 0\u00B0 (or 360\u00B0) total phase shift",
                        "Loop gain = 0 and any phase",
                        "Maximum noise figure"
                    ),
                    correctIndex = 1,
                    explanation = "For sustained oscillation, the loop gain must be \u2265 1 and the total phase shift around the loop must be 0\u00B0 (or a multiple of 360\u00B0)."
                ),
                Question.NumericInput(
                    id = "rf_ni2",
                    questionText = "An LNA has NF = 1.5dB and gain = 20dB. The second stage has NF = 10dB. What is the approximate total NF in dB? (Hint: convert to linear first)",
                    correctAnswer = 1.6,
                    tolerance = 0.1,
                    unit = "dB",
                    explanation = "NF1=1.41(linear), G1=100(linear), NF2=10(linear). NF_total = 1.41 + (10-1)/100 = 1.41 + 0.09 = 1.50 linear \u2248 1.6dB. The high-gain LNA suppresses the second stage noise."
                ),
                Question.NumericInput(
                    id = "rf_ni3",
                    questionText = "An amplifier has |S21| = 0.1 (linear). What is S21 in dB?",
                    correctAnswer = -20.0,
                    tolerance = 0.5,
                    unit = "dB",
                    explanation = "S21(dB) = 20 log\u2081\u2080(0.1) = 20 \u00D7 (\u22121) = \u221220dB. This represents a loss, not gain."
                ),
                Question.MultipleChoice(
                    id = "rf_mc4",
                    questionText = "A crystal oscillator provides high frequency stability because:",
                    options = listOf(
                        "Crystals have very high gain",
                        "Quartz has a very high Q factor (narrow resonance)",
                        "Crystals operate at microwave frequencies",
                        "Crystals don't need a power supply"
                    ),
                    correctIndex = 1,
                    explanation = "Quartz crystals have extremely high Q factors (10,000\u2013100,000+), creating a very sharp resonance that locks the oscillation frequency with ppm-level stability."
                )
            )
        )
    )
}
