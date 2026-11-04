package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI
import kotlin.math.log10

object AnalogCircuitsContent {
    private const val THERMAL_MV = 26.0
    private const val MEGA = 1e6
    private const val DB_AMPLITUDE = 20
    private val openLoopGains = listOf(10_000.0, 100_000.0, 1_000_000.0)
    private val feedbackFactors = listOf(0.01, 0.02, 0.05, 0.1)
    private val gainBandwidths = listOf(1.0, 3.0, 10.0, 100.0)
    private val closedLoopGains = listOf(10.0, 20.0, 50.0, 100.0)
    private val referenceRatios = listOf(5.0, 10.0, 20.0)
    private val outputRatios = listOf(10.0, 20.0, 30.0, 40.0, 60.0)
    private val referenceCurrents = listOf(10.0, 50.0, 100.0, 200.0)
    private val differentialGains = listOf(100.0, 1000.0, 10_000.0)
    private val commonModeGains = listOf(0.01, 0.1, 1.0)
    private val tailCurrents = listOf(0.1, 0.5, 1.0, 2.0)
    private val slewRates = listOf(0.5, 1.0, 10.0, 20.0)
    private val peakSwings = listOf(1.0, 5.0, 10.0)

    val topic = Topic(
        id = "analog_circuits",
        title = "Analog Circuit Design",
        subtitle = "Current mirrors, diff pairs, and references",
        icon = "\u2747\uFE0F",
        order = 12,
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
                Question.MultipleChoice(
                    id = "ac_mc5",
                    questionText = "Adding negative feedback to an amplifier lowers its gain. What does it gain in return?",
                    options = listOf(
                        "Higher open-loop gain",
                        "Wider bandwidth, lower distortion and a predictable gain",
                        "Higher power consumption",
                        "Lower input impedance only"
                    ),
                    correctIndex = 1,
                    explanation = "Feedback trades raw gain for accuracy: the closed-loop gain becomes ≈ 1/β (set by resistors, not transistors), distortion drops and bandwidth widens, since gain × bandwidth stays roughly constant."
                )
            ),
            generators = listOf(
                generator("ac_ni1") { r ->
                    val a = openLoopGains.pick(r)
                    val beta = feedbackFactors.pick(r)
                    val acl = a / (1 + a * beta)
                    Numeric(
                        text = "An op-amp has open-loop gain A = ${fmt(a)} and feedback factor β = ${fmt(beta)}. What is the " +
                            "closed-loop gain? Round to 2 decimal places.",
                        answer = acl, unit = "",
                        explanation = "With a large loop gain the answer is close to 1/β = ${fmt(1 / beta)}. Exactly: A/(1 + Aβ) = " +
                            "${fmt(a)}/(1 + ${fmt(a * beta)}) = ${fmt(acl, 2)}"
                    )
                },
                generator("ac_ni2") { r ->
                    val gbw = gainBandwidths.pick(r)
                    val g = closedLoopGains.pick(r)
                    val khz = gbw * 1000 / g
                    Numeric(
                        text = "An op-amp has GBW = ${fmt(gbw)}MHz. With a closed-loop gain of ${fmt(g)}, what is the bandwidth " +
                            "in kHz? Round to 2 decimal places.",
                        answer = khz, unit = "kHz",
                        explanation = "Gain × bandwidth is constant: BW = GBW/Gain = ${fmt(gbw)}MHz/${fmt(g)} = ${fmt(khz, 2)}kHz"
                    )
                },
                generator("ac_ni3") { r ->
                    val wl1 = referenceRatios.pick(r)
                    val wl2 = outputRatios.pick(r)
                    val iref = referenceCurrents.pick(r)
                    val iout = iref * wl2 / wl1
                    Numeric(
                        text = "A MOSFET current mirror has (W/L)₁ = ${fmt(wl1)} on the reference side and (W/L)₂ = ${fmt(wl2)} " +
                            "on the output. If I_ref = ${fmt(iref)}µA, what is I_out in µA? Round to 2 decimal places.",
                        answer = iout, unit = "µA",
                        explanation = "I_out = I_ref × (W/L)₂/(W/L)₁ = ${fmt(iref)} × ${fmt(wl2)}/${fmt(wl1)} = ${fmt(iout, 2)}µA"
                    )
                },
                generator("ac_ni4") { r ->
                    val ad = differentialGains.pick(r)
                    val acm = commonModeGains.pick(r)
                    val cmrr = DB_AMPLITUDE * log10(ad / acm)
                    Numeric(
                        text = "A differential amplifier has a differential gain of ${fmt(ad)} and a common-mode gain of " +
                            "${fmt(acm)}. What is its CMRR in dB? Round to 1 decimal place.",
                        answer = cmrr, unit = "dB", decimals = 1,
                        explanation = "CMRR = 20·log₁₀(A_d/A_cm) = 20·log₁₀(${fmt(ad / acm)}) = ${fmt(cmrr, 1)}dB"
                    )
                },
                generator("ac_ni5") { r ->
                    val ic = tailCurrents.pick(r)
                    val gm = ic / THERMAL_MV * 1000
                    Numeric(
                        text = "A BJT is biased at I_C = ${fmt(ic)}mA. What is its transconductance g_m = I_C/V_T, in mA/V? " +
                            "Use V_T = 26mV and round to 2 decimal places.",
                        answer = gm, unit = "mA/V",
                        explanation = "g_m = I_C/V_T = ${fmt(ic)}mA/26mV = ${fmt(gm, 2)}mA/V"
                    )
                },
                generator("ac_ni6") { r ->
                    val sr = slewRates.pick(r)
                    val vp = peakSwings.pick(r)
                    val khz = sr * MEGA / (2 * PI * vp) / 1000
                    Numeric(
                        text = "An op-amp has a slew rate of ${fmt(sr)}V/µs. What is the highest frequency at which it can output " +
                            "a ${fmt(vp)}V-peak sine wave without slew distortion, in kHz? Round to 2 decimal places.",
                        answer = khz, unit = "kHz",
                        explanation = "Full-power bandwidth f = SR/(2πV_p) = ${fmt(sr)}×10⁶/(2π × ${fmt(vp)}) = ${fmt(khz, 2)}kHz"
                    )
                }
            )
        )
    )
}
