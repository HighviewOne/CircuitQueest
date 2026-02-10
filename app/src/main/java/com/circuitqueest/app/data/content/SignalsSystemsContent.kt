package com.circuitqueest.app.data.content

object SignalsSystemsContent {
    val topic = Topic(
        id = "signals_systems",
        title = "Signals & Systems",
        subtitle = "Frequency domain, Fourier, and Laplace transforms",
        icon = "\uD83D\uDCC8",
        order = 13,
        lesson = Lesson(
            title = "Signals & Systems: Beyond the Time Domain",
            sections = listOf(
                LessonSection(
                    heading = "Time Domain vs. Frequency Domain",
                    content = "So far we have analyzed circuits in the time domain \u2014 voltage and current as functions of time. But many problems become much simpler in the frequency domain.\n\nThe key insight: any periodic signal can be decomposed into a sum of sinusoids at different frequencies. Analyzing each frequency separately is often easier than working with the original waveform.\n\nThis is the foundation of radio, audio processing, telecommunications, and control systems.",
                    keyPoint = "Frequency domain decomposes signals into individual sinusoidal components"
                ),
                LessonSection(
                    heading = "Fourier Series",
                    content = "The Fourier series represents any periodic signal as a sum of sine and cosine waves at integer multiples of the fundamental frequency.\n\nA square wave, for example, is the sum of odd harmonics:\nf(t) = sin(\u03C9t) + \u2153sin(3\u03C9t) + \u2155sin(5\u03C9t) + ...\n\nThe more harmonics you include, the more closely the sum approximates the original waveform. This explains why square waves contain energy at many frequencies.",
                    formula = "f(t) = a\u2080/2 + \u03A3[a_n cos(n\u03C9t) + b_n sin(n\u03C9t)]",
                    keyPoint = "Any periodic signal = sum of harmonics at integer multiples of f\u2080"
                ),
                LessonSection(
                    heading = "Fourier Transform",
                    content = "While the Fourier series works for periodic signals, the Fourier Transform extends this to non-periodic (aperiodic) signals.\n\nThe Fourier Transform converts a time-domain signal into a continuous spectrum showing how much energy exists at each frequency.\n\nThe result is a complex-valued function: magnitude tells you amplitude at each frequency, phase tells you the timing offset.\n\nKey property: multiplication in frequency domain = convolution in time domain.",
                    formula = "F(\u03C9) = \u222B f(t) e^(-j\u03C9t) dt",
                    keyPoint = "Fourier Transform: time \u2192 frequency for any signal"
                ),
                LessonSection(
                    heading = "Laplace Transform",
                    content = "The Laplace Transform generalizes the Fourier Transform by introducing a complex variable s = \u03C3 + j\u03C9. This handles transient (decaying/growing) signals that the Fourier Transform cannot.\n\nFor circuit analysis, Laplace transforms convert differential equations into algebraic equations:\n\u2022 Resistor: V = IR \u2192 V(s) = I(s)\u00B7R\n\u2022 Capacitor: V = (1/C)\u222Bidt \u2192 V(s) = I(s)/(sC)\n\u2022 Inductor: V = Ldi/dt \u2192 V(s) = I(s)\u00B7sL",
                    formula = "F(s) = \u222B f(t) e^(-st) dt\ns = \u03C3 + j\u03C9",
                    keyPoint = "Laplace Transform turns differential equations into algebra"
                ),
                LessonSection(
                    heading = "Transfer Functions",
                    content = "A transfer function H(s) describes a system's input-output relationship in the Laplace domain:\n\nH(s) = Output(s) / Input(s)\n\nFor example, an RC low-pass filter has:\nH(s) = 1/(1 + sRC)\n\nThe poles and zeros of H(s) determine the system's behavior:\n\u2022 Poles: values of s where H(s) \u2192 \u221E (natural frequencies, decay rates)\n\u2022 Zeros: values of s where H(s) = 0 (frequencies that are blocked)",
                    formula = "H(s) = V_out(s) / V_in(s)",
                    keyPoint = "Poles determine natural response; zeros determine which frequencies are blocked"
                ),
                LessonSection(
                    heading = "Bode Plots",
                    content = "A Bode plot is a graphical representation of a transfer function, showing magnitude (in dB) and phase (in degrees) versus frequency on a logarithmic scale.\n\nBode plots give an intuitive visual summary of how a circuit responds to different frequencies. Key features to identify:\n\u2022 Flat regions (passband)\n\u2022 Roll-off slopes (\u221220dB/decade per pole)\n\u2022 Resonant peaks\n\u2022 Corner frequencies (breakpoints at poles and zeros)\n\nEngineers use Bode plots to design filters, amplifiers, and control systems.",
                    keyPoint = "Bode plots show magnitude (dB) and phase (\u00B0) vs. log frequency"
                )
            )
        ),
        quiz = Quiz(
            title = "Signals & Systems Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ss_mc1",
                    questionText = "What does the Fourier series decompose a periodic signal into?",
                    options = listOf(
                        "DC and AC components only",
                        "Sine and cosine waves at harmonic frequencies",
                        "Exponentially decaying components",
                        "Step functions"
                    ),
                    correctIndex = 1,
                    explanation = "The Fourier series decomposes a periodic signal into a sum of sine and cosine waves at the fundamental frequency and its integer harmonics."
                ),
                Question.MultipleChoice(
                    id = "ss_mc2",
                    questionText = "In the Laplace domain, what does a capacitor's impedance become?",
                    options = listOf(
                        "sC",
                        "1/(sC)",
                        "sL",
                        "R"
                    ),
                    correctIndex = 1,
                    explanation = "A capacitor's impedance in the Laplace domain is Z(s) = 1/(sC). This replaces the integral operation with simple algebra."
                ),
                Question.NumericInput(
                    id = "ss_ni1",
                    questionText = "A square wave has a fundamental frequency of 1kHz. What is the frequency of its 3rd harmonic in kHz?",
                    correctAnswer = 3.0,
                    tolerance = 0.1,
                    unit = "kHz",
                    explanation = "The nth harmonic is n times the fundamental. 3rd harmonic = 3 \u00D7 1kHz = 3kHz."
                ),
                Question.MultipleChoice(
                    id = "ss_mc3",
                    questionText = "What does a pole in a transfer function indicate?",
                    options = listOf(
                        "A frequency that is completely blocked",
                        "A natural frequency or resonance of the system",
                        "The DC gain of the system",
                        "The bandwidth"
                    ),
                    correctIndex = 1,
                    explanation = "Poles are values of s where the transfer function goes to infinity. They correspond to the system's natural frequencies and determine its transient response."
                ),
                Question.NumericInput(
                    id = "ss_ni2",
                    questionText = "An RC low-pass filter has R = 1k\u03A9 and C = 1\u00B5F. What is the pole frequency in Hz? Round to the nearest whole number.",
                    correctAnswer = 159.0,
                    tolerance = 2.0,
                    unit = "Hz",
                    explanation = "The pole is at f = 1/(2\u03C0RC) = 1/(2\u03C0 \u00D7 1000 \u00D7 10\u207B\u2076) = 1/(6.283 \u00D7 10\u207B\u00B3) \u2248 159Hz"
                ),
                Question.NumericInput(
                    id = "ss_ni3",
                    questionText = "A Bode plot shows \u221240dB/decade roll-off. How many poles does this correspond to?",
                    correctAnswer = 2.0,
                    tolerance = 0.1,
                    unit = "poles",
                    explanation = "Each pole contributes \u221220dB/decade of roll-off. \u221240dB/decade means 2 poles."
                ),
                Question.MultipleChoice(
                    id = "ss_mc4",
                    questionText = "The Laplace variable s = \u03C3 + j\u03C9. What does the real part \u03C3 represent?",
                    options = listOf(
                        "Oscillation frequency",
                        "Phase angle",
                        "Exponential growth or decay rate",
                        "Amplitude"
                    ),
                    correctIndex = 2,
                    explanation = "The real part \u03C3 represents exponential growth (\u03C3 > 0) or decay (\u03C3 < 0). The imaginary part j\u03C9 represents oscillation."
                )
            )
        )
    )
}
