package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.sqrt

object TransmissionLinesContent {
    private const val C_M_MHZ = 300.0
    private const val DB_AMPLITUDE = 20
    private val lineImpedances = listOf(50.0, 75.0)
    private val loadImpedances = listOf(10.0, 25.0, 100.0, 150.0, 200.0, 300.0)
    private val reflectionMagnitudes = listOf(0.1, 0.2, 0.333, 0.5)
    private val cableFrequenciesMhz = listOf(100.0, 300.0, 1000.0)
    private val velocityFactors = listOf(0.66, 0.8, 1.0)
    private val matchLoads = listOf(12.5, 100.0, 200.0, 300.0)

    val topic = Topic(
        id = "transmission_lines",
        title = "Transmission Lines",
        subtitle = "Signal propagation, reflections, and impedance matching",
        icon = "\uD83D\uDCE1",
        order = 28,
        lesson = Lesson(
            title = "Transmission Lines: Signals on the Move",
            sections = listOf(
                LessonSection(
                    heading = "When Wires Become Transmission Lines",
                    content = "At low frequencies, wires are just conductors. But when the signal wavelength becomes comparable to the wire length, the wire itself affects the signal. The rule of thumb: if the wire is longer than 1/10 of the wavelength, treat it as a transmission line.\n\nAt 1GHz the wavelength is 30cm, so even PCB traces a few centimeters long matter. This is why high-speed digital and RF design requires transmission line analysis.",
                    formula = "\u03BB = c / f",
                    keyPoint = "Wire length > \u03BB/10 \u2192 must treat as a transmission line"
                ),
                LessonSection(
                    heading = "Characteristic Impedance",
                    content = "Every transmission line has a characteristic impedance Z\u2080 determined by its physical dimensions and materials, not its length.\n\nCommon values:\n\u2022 Coaxial cable (RG-58): 50\u03A9\n\u2022 TV coax (RG-6): 75\u03A9\n\u2022 Twisted pair (Ethernet): 100\u03A9\n\u2022 PCB microstrip: 50\u03A9 (typical)\n\nZ\u2080 depends on the inductance and capacitance per unit length of the line.",
                    formula = "Z\u2080 = \u221A(L/C)  (per unit length)",
                    keyPoint = "Z\u2080 is a property of the line geometry, not its length"
                ),
                LessonSection(
                    heading = "Reflections",
                    content = "When a signal reaches the end of a transmission line, it reflects if the load impedance doesn't match Z\u2080. The reflection coefficient \u0393 (gamma) measures how much reflects:\n\n\u2022 \u0393 = 0: perfect match, no reflection (Z_L = Z\u2080)\n\u2022 \u0393 = +1: open circuit (Z_L = \u221E), full reflection, same polarity\n\u2022 \u0393 = \u22121: short circuit (Z_L = 0), full reflection, inverted\n\nReflections cause signal distortion, ringing, and data errors in digital systems.",
                    formula = "\u0393 = (Z_L \u2212 Z\u2080) / (Z_L + Z\u2080)",
                    keyPoint = "Mismatch causes reflections; match Z_L = Z\u2080 for zero reflection"
                ),
                LessonSection(
                    heading = "VSWR",
                    content = "Voltage Standing Wave Ratio (VSWR) measures the severity of impedance mismatch. When reflections combine with the forward wave, standing wave patterns form on the line.\n\nVSWR ranges from 1.0 (perfect match) to infinity (total reflection).\n\n\u2022 VSWR < 1.5: good match\n\u2022 VSWR < 2.0: acceptable for most systems\n\u2022 VSWR > 3.0: significant mismatch, power loss\n\nVSWR is commonly measured with a network analyzer or SWR meter.",
                    formula = "VSWR = (1 + |\u0393|) / (1 \u2212 |\u0393|)",
                    keyPoint = "VSWR = 1.0 is perfect; higher values mean worse mismatch"
                ),
                LessonSection(
                    heading = "Impedance Matching",
                    content = "Matching eliminates reflections and maximizes power transfer. Common techniques:\n\n\u2022 Quarter-wave transformer: a \u03BB/4 section of line with Z = \u221A(Z\u2080 \u00D7 Z_L)\n\u2022 L-network: an inductor and capacitor in an L configuration\n\u2022 Stub matching: a short piece of transmission line (open or shorted) connected in parallel\n\u2022 Transformer matching: using a transformer's turns ratio\n\nMatching is critical in antenna systems, RF amplifiers, and high-speed data links.",
                    formula = "Z_match = \u221A(Z\u2080 \u00D7 Z_L)  (quarter-wave)",
                    keyPoint = "Matching maximizes power transfer and eliminates reflections"
                ),
                LessonSection(
                    heading = "The Smith Chart",
                    content = "The Smith chart is a graphical tool for transmission line analysis. It maps complex impedances onto a circular plot, making matching calculations visual and intuitive.\n\nKey features:\n\u2022 Center: Z = Z\u2080 (perfect match)\n\u2022 Right edge: open circuit (Z = \u221E)\n\u2022 Left edge: short circuit (Z = 0)\n\u2022 Circles: constant resistance or constant reactance\n\nMoving clockwise around the chart corresponds to moving toward the generator along the line. The Smith chart turns complex math into simple rotations.",
                    keyPoint = "Smith chart: center = matched; clockwise = toward generator"
                )
            )
        ),
        quiz = Quiz(
            title = "Transmission Lines Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "tl_mc1",
                    questionText = "What reflection coefficient indicates a perfect impedance match?",
                    options = listOf(
                        "\u0393 = 1",
                        "\u0393 = \u22121",
                        "\u0393 = 0",
                        "\u0393 = 0.5"
                    ),
                    correctIndex = 2,
                    explanation = "When Z_L = Z\u2080, \u0393 = (Z\u2080 \u2212 Z\u2080)/(Z\u2080 + Z\u2080) = 0. No reflection occurs."
                ),
                Question.MultipleChoice(
                    id = "tl_mc2",
                    questionText = "What is the characteristic impedance of standard coaxial cable (RG-58)?",
                    options = listOf(
                        "25\u03A9",
                        "50\u03A9",
                        "75\u03A9",
                        "100\u03A9"
                    ),
                    correctIndex = 1,
                    explanation = "RG-58 coaxial cable has a characteristic impedance of 50\u03A9, which is the standard for RF and test equipment."
                ),
                Question.MultipleChoice(
                    id = "tl_mc3",
                    questionText = "On a Smith chart, the center point represents:",
                    options = listOf(
                        "An open circuit",
                        "A short circuit",
                        "A perfect match (Z = Z\u2080)",
                        "Pure reactance"
                    ),
                    correctIndex = 2,
                    explanation = "The center of the Smith chart is the normalized impedance z = 1, meaning Z = Z\u2080 (perfect match, \u0393 = 0)."
                ),
                Question.MultipleChoice(
                    id = "tl_mc4",
                    questionText = "What is the reflection coefficient Γ of a line terminated in a short circuit?",
                    options = listOf(
                        "0",
                        "+1",
                        "−1",
                        "∞"
                    ),
                    correctIndex = 2,
                    explanation = "With Z_L = 0, Γ = (0 − Z₀)/(0 + Z₀) = −1: all the power reflects, with the voltage inverted. An open circuit gives Γ = +1."
                )
            ),
            generators = listOf(
                generator("tl_ni1") { r ->
                    val z0 = lineImpedances.pick(r)
                    val zl = loadImpedances.pick(r)
                    val g = abs((zl - z0) / (zl + z0))
                    Numeric(
                        text = "${articleCap(z0)} ${fmt(z0)}Ω line is terminated with ${article(zl)} ${fmt(zl)}Ω load. What is the " +
                            "magnitude of the reflection coefficient |Γ|? Round to 3 decimal places.",
                        answer = g, unit = "", decimals = 3,
                        explanation = "Γ = (Z_L − Z₀)/(Z_L + Z₀) = (${fmt(zl)} − ${fmt(z0)})/(${fmt(zl)} + ${fmt(z0)}), so " +
                            "|Γ| = ${fmt(g, 3)}"
                    )
                },
                generator("tl_ni2") { r ->
                    val g = reflectionMagnitudes.pick(r)
                    val vswr = (1 + g) / (1 - g)
                    Numeric(
                        text = "If |Γ| = ${fmt(g)}, what is the VSWR? Round to 2 decimal places.",
                        answer = vswr, unit = "",
                        explanation = "VSWR = (1 + |Γ|)/(1 − |Γ|) = ${fmt(1 + g)}/${fmt(1 - g)} = ${fmt(vswr, 2)}"
                    )
                },
                generator("tl_ni3") { r ->
                    val f = cableFrequenciesMhz.pick(r)
                    val vf = velocityFactors.pick(r)
                    val lambda = C_M_MHZ * vf / f
                    Numeric(
                        text = "A ${fmt(f)}MHz signal travels on a cable with velocity factor ${fmt(vf)}. What is its wavelength in " +
                            "the cable, in metres? Round to 3 decimal places.",
                        answer = lambda, unit = "m", decimals = 3,
                        explanation = "λ = VF × c/f = ${fmt(vf)} × 3×10⁸/(${fmt(f)}×10⁶) = ${fmt(lambda, 3)}m"
                    )
                },
                generator("tl_ni4") { r ->
                    val z0 = lineImpedances.pick(r)
                    val zl = matchLoads.pick(r)
                    val zq = sqrt(z0 * zl)
                    Numeric(
                        text = "A quarter-wave transformer must match ${article(z0)} ${fmt(z0)}Ω line to ${article(zl)} ${fmt(zl)}Ω " +
                            "load. What impedance should the quarter-wave section have, in Ω? Round to 2 decimal places.",
                        answer = zq, unit = "Ω",
                        explanation = "Z_q = √(Z₀ × Z_L) = √(${fmt(z0)} × ${fmt(zl)}) = ${fmt(zq, 2)}Ω"
                    )
                },
                generator("tl_ni5") { r ->
                    val g = reflectionMagnitudes.pick(r)
                    val rl = -DB_AMPLITUDE * log10(g)
                    Numeric(
                        text = "A load has |Γ| = ${fmt(g)}. What is the return loss, in dB? Round to 2 decimal places.",
                        answer = rl, unit = "dB",
                        explanation = "Return loss = −20·log₁₀|Γ| = −20·log₁₀(${fmt(g)}) = ${fmt(rl, 2)}dB. Higher is a better match."
                    )
                },
                generator("tl_ni6") { r ->
                    val g = reflectionMagnitudes.pick(r)
                    val pct = g * g * 100
                    Numeric(
                        text = "A mismatched load has |Γ| = ${fmt(g)}. What percentage of the incident power is reflected? " +
                            "Round to 2 decimal places.",
                        answer = pct, unit = "%",
                        explanation = "Reflected power fraction = |Γ|² = ${fmt(g)}² = ${fmt(pct, 2)}%"
                    )
                }
            )
        )
    )
}
