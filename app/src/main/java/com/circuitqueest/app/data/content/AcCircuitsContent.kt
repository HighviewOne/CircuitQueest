package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI
import kotlin.math.sqrt

object AcCircuitsContent {
    private val peakVoltages = listOf(10.0, 24.0, 100.0, 170.0, 325.0, 340.0)
    private val reactanceFrequencies = listOf(50.0, 60.0, 1000.0, 10000.0)
    private val reactanceInductances = listOf(1.0, 10.0, 47.0, 100.0)
    private val rightTriangles = listOf(3.0 to 4.0, 6.0 to 8.0, 5.0 to 12.0, 8.0 to 15.0)
    private val triangleScales = listOf(1.0, 2.0, 5.0, 10.0)
    private val capacitiveReactances = listOf(10.0, 20.0, 40.0)
    private val resonantInductances = listOf(1.0, 10.0, 100.0)
    private val resonantCapacitances = listOf(0.1, 1.0, 10.0)
    private val capacitorFrequencies = listOf(50.0, 60.0, 1000.0)
    private val capacitorValues = listOf(1.0, 10.0, 100.0)
    private val periodFrequencies = listOf(50.0, 60.0, 100.0, 400.0, 1000.0)
    private const val MICRO = 1e-6

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
                ),
                Question.MultipleChoice(
                    id = "ac_mc4",
                    questionText = "In a capacitor, how does the current relate to the voltage?",
                    options = listOf(
                        "It lags the voltage by 90°",
                        "It leads the voltage by 90°",
                        "It is in phase with the voltage",
                        "It leads the voltage by 180°"
                    ),
                    correctIndex = 1,
                    explanation = "In a capacitor (C), current (I) leads voltage (E) by 90°: the 'ICE' in 'ELI the ICE man'."
                )
            ),
            generators = listOf(
                generator("ac_ni1") { r ->
                    val vp = peakVoltages.pick(r)
                    val vrms = vp / sqrt(2.0)
                    Numeric(
                        text = "An AC signal has a peak voltage of ${fmt(vp)}V. What is its RMS voltage? Round to 1 decimal place.",
                        answer = vrms, unit = "V", decimals = 1,
                        explanation = "V_rms = V_peak / √2 = ${fmt(vp)} / 1.414 ≈ ${fmt(vrms, 1)}V"
                    )
                },
                generator("ac_ni2") { r ->
                    val f = reactanceFrequencies.pick(r)
                    val l = reactanceInductances.pick(r)
                    val xl = 2 * PI * f * l / 1000
                    Numeric(
                        text = "What is the inductive reactance of a ${fmt(l)}mH inductor at ${fmt(f)}Hz? " +
                            "Answer in Ohms, rounded to 2 decimal places.",
                        answer = xl, unit = "Ω",
                        explanation = "X_L = 2πfL = 2π × ${fmt(f)} × ${fmt(l / 1000, 4)} = ${fmt(xl, 2)}Ω"
                    )
                },
                generator("ac_ni3") { r ->
                    val (a, b) = rightTriangles.pick(r)
                    val k = triangleScales.pick(r)
                    val res = a * k
                    val xc = capacitiveReactances.pick(r)
                    val xl = xc + b * k
                    val z = sqrt(res * res + (xl - xc) * (xl - xc))
                    Numeric(
                        text = "A series circuit has R = ${fmt(res)}Ω, X_L = ${fmt(xl)}Ω, and X_C = ${fmt(xc)}Ω. " +
                            "What is the impedance in Ohms?",
                        answer = z, unit = "Ω",
                        explanation = "Z = √(R² + (X_L − X_C)²) = √(${fmt(res)}² + ${fmt(xl - xc)}²) = " +
                            "√${fmt(res * res + (xl - xc) * (xl - xc))} = ${fmt(z)}Ω"
                    )
                },
                generator("ac_ni4") { r ->
                    val l = resonantInductances.pick(r)
                    val c = resonantCapacitances.pick(r)
                    val f0 = 1 / (2 * PI * sqrt(l / 1000 * c * MICRO))
                    Numeric(
                        text = "Find the resonant frequency (in Hz) of a circuit with L = ${fmt(l)}mH and C = ${fmt(c)}µF. " +
                            "Round to the nearest whole number.",
                        answer = f0, unit = "Hz", decimals = 0,
                        explanation = "f₀ = 1/(2π√(LC)) = 1/(2π√(${fmt(l)}×10⁻³ × ${fmt(c)}×10⁻⁶)) ≈ ${fmt(f0, 0)}Hz"
                    )
                },
                generator("ac_ni5") { r ->
                    val f = capacitorFrequencies.pick(r)
                    val c = capacitorValues.pick(r)
                    val xc = 1 / (2 * PI * f * c * MICRO)
                    Numeric(
                        text = "What is the capacitive reactance of a ${fmt(c)}µF capacitor at ${fmt(f)}Hz? " +
                            "Answer in Ohms, rounded to 2 decimal places.",
                        answer = xc, unit = "Ω",
                        explanation = "X_C = 1/(2πfC) = 1/(2π × ${fmt(f)} × ${fmt(c)}×10⁻⁶) = ${fmt(xc, 2)}Ω"
                    )
                },
                generator("ac_ni6") { r ->
                    val f = periodFrequencies.pick(r)
                    val ms = 1000 / f
                    Numeric(
                        text = "What is the period of a ${fmt(f)}Hz AC signal, in milliseconds? Round to 2 decimal places.",
                        answer = ms, unit = "ms",
                        explanation = "T = 1/f = 1/${fmt(f)} s = ${fmt(ms, 2)}ms"
                    )
                }
            )
        )
    )
}
