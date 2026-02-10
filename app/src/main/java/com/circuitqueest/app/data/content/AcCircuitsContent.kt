package com.circuitqueest.app.data.content

object AcCircuitsContent {
    val topic = Topic(
        id = "ac_circuits",
        title = "AC Circuits",
        subtitle = "Sinusoidal signals, reactance, and resonance",
        icon = "\uD83C\uDF0A",
        order = 4,
        lesson = Lesson(
            title = "AC Circuits: Riding the Wave",
            sections = listOf(
                LessonSection(
                    heading = "Sinusoidal Signals",
                    content = "Alternating current (AC) changes direction periodically, following a sinusoidal waveform. AC is the form of electricity delivered by power grids worldwide.\n\nKey parameters:\n\u2022 Amplitude (V_peak): maximum voltage\n\u2022 Frequency (f): cycles per second, in Hertz (Hz)\n\u2022 Period (T): time for one complete cycle, T = 1/f\n\u2022 Angular frequency: \u03C9 = 2\u03C0f",
                    formula = "v(t) = V_peak \u00D7 sin(\u03C9t)",
                    keyPoint = "AC voltage varies sinusoidally with time"
                ),
                LessonSection(
                    heading = "RMS Values",
                    content = "The Root Mean Square (RMS) value is the effective DC-equivalent value of an AC signal. It tells you how much power the AC signal delivers compared to DC.\n\nWhen someone says \"120V AC\" or \"230V AC\", they mean the RMS value. The peak voltage is actually higher by a factor of \u221A2.\n\nFor example, 120V RMS has a peak of about 170V.",
                    formula = "V_rms = V_peak / \u221A2\nI_rms = I_peak / \u221A2",
                    keyPoint = "RMS = Peak / \u221A2 \u2248 Peak \u00D7 0.707"
                ),
                LessonSection(
                    heading = "Capacitive Reactance",
                    content = "Capacitors oppose AC current, and this opposition is called capacitive reactance (X_C). Unlike resistance, reactance depends on frequency.\n\nAt high frequencies, capacitors pass current easily (low reactance). At DC (f=0), reactance is infinite \u2014 the capacitor blocks DC completely.\n\nCapacitive reactance causes current to lead voltage by 90\u00B0.",
                    formula = "X_C = 1 / (2\u03C0fC)",
                    keyPoint = "Higher frequency \u2192 lower capacitive reactance"
                ),
                LessonSection(
                    heading = "Inductive Reactance",
                    content = "Inductors also oppose AC current with inductive reactance (X_L). This is the opposite behavior to capacitors.\n\nAt high frequencies, inductors have high reactance (block current). At DC (f=0), reactance is zero \u2014 an ideal inductor is a short circuit for DC.\n\nInductive reactance causes current to lag voltage by 90\u00B0.",
                    formula = "X_L = 2\u03C0fL",
                    keyPoint = "Higher frequency \u2192 higher inductive reactance"
                ),
                LessonSection(
                    heading = "Impedance",
                    content = "Impedance (Z) is the total opposition to AC current, combining resistance and reactance. Since reactances are 90\u00B0 out of phase with resistance, they combine using the Pythagorean theorem.\n\nFor a series RLC circuit, inductive and capacitive reactances partially cancel each other before combining with resistance.",
                    formula = "Z = \u221A(R\u00B2 + (X_L - X_C)\u00B2)",
                    keyPoint = "Impedance combines resistance and net reactance at right angles"
                ),
                LessonSection(
                    heading = "RLC Resonance",
                    content = "Resonance occurs when inductive reactance equals capacitive reactance (X_L = X_C). At resonance:\n\n\u2022 Impedance is at its minimum (Z = R)\n\u2022 Current is at its maximum\n\u2022 The circuit is purely resistive\n\nResonant circuits are used in radio tuners, filters, and oscillators. By choosing L and C values, you can tune to a specific frequency.",
                    formula = "f\u2080 = 1 / (2\u03C0\u221A(LC))",
                    keyPoint = "At resonance: X_L = X_C, impedance is minimum, current is maximum"
                )
            )
        ),
        quiz = Quiz(
            title = "AC Circuits Boss Battle",
            questions = listOf(
                Question.NumericInput(
                    id = "ac_ni1",
                    questionText = "An AC signal has a peak voltage of 170V. What is its RMS voltage? Round to the nearest whole number.",
                    correctAnswer = 120.0,
                    tolerance = 1.0,
                    unit = "V",
                    explanation = "V_rms = V_peak / \u221A2 = 170 / 1.414 \u2248 120V"
                ),
                Question.MultipleChoice(
                    id = "ac_mc1",
                    questionText = "What happens to capacitive reactance as frequency increases?",
                    options = listOf(
                        "It increases",
                        "It decreases",
                        "It stays the same",
                        "It becomes negative"
                    ),
                    correctIndex = 1,
                    explanation = "X_C = 1/(2\u03C0fC). As frequency increases, the denominator grows, so reactance decreases."
                ),
                Question.NumericInput(
                    id = "ac_ni2",
                    questionText = "What is the inductive reactance of a 10mH inductor at 1kHz? Answer in Ohms. Round to the nearest whole number.",
                    correctAnswer = 63.0,
                    tolerance = 1.0,
                    unit = "\u03A9",
                    explanation = "X_L = 2\u03C0fL = 2\u03C0 \u00D7 1000 \u00D7 0.01 = 62.83 \u2248 63\u03A9"
                ),
                Question.MultipleChoice(
                    id = "ac_mc2",
                    questionText = "At resonance in a series RLC circuit, the impedance equals:",
                    options = listOf(
                        "Zero",
                        "X_L + X_C",
                        "R (resistance only)",
                        "Infinity"
                    ),
                    correctIndex = 2,
                    explanation = "At resonance X_L = X_C, so they cancel. Z = \u221A(R\u00B2 + 0\u00B2) = R."
                ),
                Question.NumericInput(
                    id = "ac_ni3",
                    questionText = "A series circuit has R = 30\u03A9, X_L = 80\u03A9, and X_C = 40\u03A9. What is the impedance in Ohms?",
                    correctAnswer = 50.0,
                    tolerance = 0.5,
                    unit = "\u03A9",
                    explanation = "Z = \u221A(R\u00B2 + (X_L - X_C)\u00B2) = \u221A(30\u00B2 + 40\u00B2) = \u221A(900 + 1600) = \u221A2500 = 50\u03A9"
                ),
                Question.NumericInput(
                    id = "ac_ni4",
                    questionText = "Find the resonant frequency (in Hz) of a circuit with L = 1mH and C = 1\u00B5F. Round to the nearest whole number.",
                    correctAnswer = 5033.0,
                    tolerance = 20.0,
                    unit = "Hz",
                    explanation = "f\u2080 = 1/(2\u03C0\u221A(LC)) = 1/(2\u03C0\u221A(10\u207B\u00B3 \u00D7 10\u207B\u2076)) = 1/(2\u03C0 \u00D7 31.62\u00D710\u207B\u2076) \u2248 5033Hz"
                ),
                Question.MultipleChoice(
                    id = "ac_mc3",
                    questionText = "In an AC circuit, an inductor causes the current to:",
                    options = listOf(
                        "Lead the voltage by 90\u00B0",
                        "Lag the voltage by 90\u00B0",
                        "Be in phase with the voltage",
                        "Lead the voltage by 45\u00B0"
                    ),
                    correctIndex = 1,
                    explanation = "In an inductor, current lags voltage by 90\u00B0. Remember: ELI the ICE man \u2014 in an inductor (L), EMF (E) leads current (I)."
                )
            )
        )
    )
}
