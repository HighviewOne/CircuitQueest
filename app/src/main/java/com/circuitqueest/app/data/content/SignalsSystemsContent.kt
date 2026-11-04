package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI
import kotlin.math.log10

object SignalsSystemsContent {
    private const val DB_AMPLITUDE = 20
    private const val DB_PER_POLE = 20
    private const val MICRO = 1e-6
    private val fundamentals = listOf(50.0, 60.0, 100.0, 440.0, 1000.0)
    private val harmonics = listOf(2 to "2nd", 3 to "3rd", 4 to "4th", 5 to "5th", 7 to "7th")
    private val poleResistors = listOf(1.0, 2.2, 4.7, 10.0)
    private val poleCapacitors = listOf(0.1, 1.0, 10.0)
    private val rollOffs = listOf(20, 40, 60, 80)
    private val poleLocations = listOf(10.0, 50.0, 100.0, 200.0, 1000.0)
    private val gainRatios = listOf(2.0, 4.0, 5.0, 10.0, 100.0, 1000.0)
    private val signalFrequencies = listOf(50.0, 60.0, 1000.0)

    val topic = Topic(
        id = "signals_systems",
        title = "Signals & Systems",
        subtitle = "Frequency domain, Fourier, and Laplace transforms",
        icon = "\uD83D\uDCC8",
        order = 18,
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
                ),
                Question.MultipleChoice(
                    id = "ss_mc5",
                    questionText = "What is a zero of a transfer function H(s)?",
                    options = listOf(
                        "A value of s where H(s) becomes infinite",
                        "A value of s where H(s) = 0",
                        "The DC gain",
                        "A pole on the imaginary axis"
                    ),
                    correctIndex = 1,
                    explanation = "Zeros are the roots of the numerator, where H(s) = 0; poles are the roots of the denominator, where H(s) blows up. Zeros shape the response but don't decide stability."
                )
            ),
            generators = listOf(
                generator("ss_ni1") { r ->
                    val f0 = fundamentals.pick(r)
                    val (n, nth) = harmonics.pick(r)
                    Numeric(
                        text = "A periodic signal has a fundamental frequency of ${fmt(f0)}Hz. What is the frequency of its $nth " +
                            "harmonic, in Hz?",
                        answer = n * f0, unit = "Hz",
                        explanation = "The nth harmonic is n times the fundamental: $n × ${fmt(f0)} = ${fmt(n * f0)}Hz"
                    )
                },
                generator("ss_ni2") { r ->
                    val res = poleResistors.pick(r)
                    val c = poleCapacitors.pick(r)
                    val f = 1 / (2 * PI * res * 1000 * c * MICRO)
                    Numeric(
                        text = "An RC low-pass filter has R = ${fmt(res)}kΩ and C = ${fmt(c)}µF. What is its pole frequency in Hz? " +
                            "Round to the nearest whole number.",
                        answer = f, unit = "Hz", decimals = 0,
                        explanation = "The pole is at f = 1/(2πRC) = 1/(2π × ${fmt(res)}×10³ × ${fmt(c)}×10⁻⁶) ≈ ${fmt(f, 0)}Hz"
                    )
                },
                generator("ss_ni3") { r ->
                    val slope = rollOffs.pick(r)
                    val poles = slope / DB_PER_POLE
                    Numeric(
                        text = "A Bode magnitude plot rolls off at −${slope}dB/decade at high frequency. How many poles (net of " +
                            "zeros) does this correspond to?",
                        answer = poles.toDouble(), unit = "poles", exact = true,
                        explanation = "Each pole adds −20dB/decade: $slope/20 = $poles poles"
                    )
                },
                generator("ss_ni4") { r ->
                    val a = poleLocations.pick(r)
                    val ms = 1000 / a
                    Numeric(
                        text = "A first-order system has H(s) = ${fmt(a)}/(s + ${fmt(a)}). What is its time constant, in " +
                            "milliseconds? Round to 2 decimal places.",
                        answer = ms, unit = "ms",
                        explanation = "The pole is at s = −${fmt(a)}, so τ = 1/${fmt(a)} s = ${fmt(ms, 2)}ms"
                    )
                },
                generator("ss_ni5") { r ->
                    val ratio = gainRatios.pick(r)
                    val db = DB_AMPLITUDE * log10(ratio)
                    Numeric(
                        text = "A system amplifies a signal's amplitude by a factor of ${fmt(ratio)}. What is that gain in dB? " +
                            "Round to 2 decimal places.",
                        answer = db, unit = "dB",
                        explanation = "For amplitude (voltage) ratios, gain = 20·log₁₀(${fmt(ratio)}) = ${fmt(db, 2)}dB"
                    )
                },
                generator("ss_ni6") { r ->
                    val f = signalFrequencies.pick(r)
                    val w = 2 * PI * f
                    Numeric(
                        text = "What is the angular frequency ω of a ${fmt(f)}Hz signal, in rad/s? Round to 2 decimal places.",
                        answer = w, unit = "rad/s",
                        explanation = "ω = 2πf = 2π × ${fmt(f)} = ${fmt(w, 2)}rad/s"
                    )
                }
            )
        )
    )
}
