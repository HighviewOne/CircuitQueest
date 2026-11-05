package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

object SignalIntegrityContent {
    private const val DB_AMPLITUDE = 20.0
    private const val CRITICAL_FRACTION = 10.0
    private const val C_CM_PER_NS = 30.0
    private const val BANDWIDTH_FACTOR = 350.0
    private val pinInductances = listOf(1.0, 2.0, 5.0)
    private val currentStepsMa = listOf(100.0, 250.0, 500.0)
    private val edgeTimesNs = listOf(0.5, 1.0, 2.0)
    private val shieldings = listOf(20.0, 30.0, 40.0, 60.0)
    private val riseTimesNs = listOf(0.1, 0.5, 1.0, 2.0)
    private val traceSpeeds = listOf(15.0, 18.0, 20.0)
    private val dielectricConstants = listOf(4.0, 4.3, 4.5)
    private val stepImpedances = listOf(40.0, 60.0, 75.0, 90.0)

    val topic = Topic(
        id = "signal_integrity",
        title = "Signal Integrity & EMC",
        subtitle = "Crosstalk, noise, and electromagnetic compatibility",
        icon = "\uD83D\uDEE1\uFE0F",
        order = 37,
        lesson = Lesson(
            title = "Signal Integrity & EMC: Keeping Signals Clean",
            sections = listOf(
                LessonSection(
                    heading = "Why Signal Integrity Matters",
                    content = "As digital circuits run faster and traces get longer, signals don't just travel cleanly from A to B. They encounter reflections, crosstalk, ringing, and noise that can corrupt data.\n\nAt 100MHz, a signal's rise time is about 1ns. In that time, the signal travels ~15cm in a PCB trace. Any trace longer than ~1.5cm (\u03BB/10) must be treated as a transmission line.\n\nSignal integrity (SI) analysis ensures reliable data transmission at high speeds.",
                    keyPoint = "Faster signals = more SI problems; treat traces as transmission lines"
                ),
                LessonSection(
                    heading = "Reflections and Ringing",
                    content = "When a fast signal reaches an impedance mismatch (connector, via, trace width change, unterminated end), part of the signal reflects back.\n\nReflections cause ringing \u2014 oscillations that can cross logic thresholds and cause false triggering. Solutions:\n\n\u2022 Source termination: series resistor at the driver (R = Z\u2080 \u2212 R_driver)\n\u2022 End termination: parallel resistor at the receiver (R = Z\u2080)\n\u2022 Controlled impedance: maintain consistent Z\u2080 along the entire trace",
                    formula = "\u0393 = (Z_L \u2212 Z\u2080) / (Z_L + Z\u2080)",
                    keyPoint = "Match impedance everywhere to prevent reflections and ringing"
                ),
                LessonSection(
                    heading = "Crosstalk",
                    content = "Crosstalk occurs when a signal on one trace (aggressor) couples noise onto a neighboring trace (victim) through capacitive and inductive coupling.\n\n\u2022 Near-End Crosstalk (NEXT): at the same end as the aggressor driver\n\u2022 Far-End Crosstalk (FEXT): at the opposite end\n\nReducing crosstalk:\n\u2022 Increase spacing between traces (3\u00D7 trace width minimum for sensitive signals)\n\u2022 Use ground planes \u2014 they shield adjacent traces\n\u2022 Route sensitive signals on different layers\n\u2022 Keep parallel run lengths short",
                    keyPoint = "3W rule: space traces at least 3\u00D7 width apart to minimize crosstalk"
                ),
                LessonSection(
                    heading = "Ground Bounce and Power Integrity",
                    content = "When many outputs switch simultaneously, the surge of current through package inductance causes the ground voltage to bounce temporarily. This is ground bounce (SSO \u2014 Simultaneous Switching Output noise).\n\nThe voltage spike is: V_bounce = L \u00D7 dI/dt\n\nMitigation:\n\u2022 Decouple every IC with 100nF + bulk capacitors\n\u2022 Use multiple ground/power pins and vias\n\u2022 Minimize loop area between power and ground\n\u2022 Stagger output switching when possible",
                    formula = "V_bounce = L \u00D7 dI/dt",
                    keyPoint = "Ground bounce = L\u00D7dI/dt; minimize inductance with short, wide paths"
                ),
                LessonSection(
                    heading = "EMC: Emissions and Immunity",
                    content = "Electromagnetic Compatibility (EMC) ensures devices don't emit excessive interference and can tolerate interference from others.\n\n\u2022 Conducted emissions: noise coupled through power cables and I/O lines\n\u2022 Radiated emissions: noise radiated as electromagnetic waves from traces and cables\n\nEvery current loop is an antenna. The radiated power is proportional to loop area and frequency squared. Reducing loop area is the single most effective EMC technique.\n\nAll electronic products must pass EMC testing (FCC in the US, CE in Europe) before sale.",
                    formula = "E \u221D f\u00B2 \u00D7 I \u00D7 A (loop area)",
                    keyPoint = "Minimize current loop area \u2014 the #1 EMC rule"
                ),
                LessonSection(
                    heading = "Shielding and Filtering",
                    content = "When good design practices aren't enough, shielding and filtering provide additional protection:\n\n\u2022 Shielding: metal enclosures block radiated emissions. Effectiveness depends on material, thickness, and seam quality. Even small gaps can leak high-frequency noise.\n\u2022 Ferrite beads: act as frequency-dependent resistors, absorbing high-frequency noise while passing DC and low frequencies.\n\u2022 Common-mode chokes: suppress noise common to both signal lines.\n\u2022 Pi filters (\u03C0): LC filters at power entry and I/O connectors.\n\nShielding effectiveness is measured in dB: 20 dB = 90% reduction, 40 dB = 99%.",
                    keyPoint = "Ferrite beads for HF noise; shielding for radiated emissions"
                )
            )
        ),
        quiz = Quiz(
            title = "Signal Integrity & EMC Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "si_mc1",
                    questionText = "What causes ringing on a digital signal?",
                    options = listOf(
                        "Too much current",
                        "Impedance mismatches causing reflections",
                        "Using too many ground pins",
                        "Clock frequency being too low"
                    ),
                    correctIndex = 1,
                    explanation = "Ringing is caused by signal reflections at impedance mismatches (trace discontinuities, connectors, unterminated ends). The reflected energy bounces back and forth."
                ),
                Question.MultipleChoice(
                    id = "si_mc2",
                    questionText = "The 3W rule for crosstalk reduction states:",
                    options = listOf(
                        "Use 3 ground planes",
                        "Limit traces to 3cm length",
                        "Space traces at least 3\u00D7 the trace width apart",
                        "Use 3 decoupling capacitors per IC"
                    ),
                    correctIndex = 2,
                    explanation = "The 3W rule recommends trace-to-trace spacing of at least 3 times the trace width to reduce capacitive and inductive coupling (crosstalk) between adjacent traces."
                ),
                Question.MultipleChoice(
                    id = "si_mc3",
                    questionText = "The single most effective technique for reducing radiated EMI is:",
                    options = listOf(
                        "Using faster clock edges",
                        "Adding more decoupling capacitors",
                        "Minimizing current loop area",
                        "Increasing trace width"
                    ),
                    correctIndex = 2,
                    explanation = "Radiated emissions are proportional to current loop area. A continuous ground plane directly beneath signal traces minimizes loop area and is the most effective EMC measure."
                ),
                Question.MultipleChoice(
                    id = "si_mc4",
                    questionText = "What does a ferrite bead do?",
                    options = listOf(
                        "Amplifies high-frequency signals",
                        "Acts as a frequency-dependent resistor, absorbing HF noise",
                        "Generates a magnetic field for shielding",
                        "Stores energy like a capacitor"
                    ),
                    correctIndex = 1,
                    explanation = "A ferrite bead presents low impedance at DC/low frequencies (passes signal) and high impedance at high frequencies (absorbs noise as heat)."
                )
            ),
            generators = listOf(
                generator("si_ni1") { r ->
                    val l = pinInductances.pick(r)
                    val di = currentStepsMa.pick(r)
                    val dt = edgeTimesNs.pick(r)
                    val v = l * di / dt / 1000
                    Numeric(
                        text = "A package ground pin has ${fmt(l)}nH of inductance. If ${fmt(di)}mA switches in ${fmt(dt)}ns, what " +
                            "ground bounce does it cause, in Volts? Round to 3 decimal places.",
                        answer = v, unit = "V", decimals = 3,
                        explanation = "V = L × dI/dt = ${fmt(l)}nH × ${fmt(di)}mA/${fmt(dt)}ns = ${fmt(v, 3)}V"
                    )
                },
                generator("si_ni2") { r ->
                    val db = shieldings.pick(r)
                    val pct = (1 - 10.0.pow(-db / DB_AMPLITUDE)) * 100
                    Numeric(
                        text = "A shield provides ${fmt(db)}dB of attenuation. What percentage of the field strength (amplitude) " +
                            "does it block? Round to 2 decimal places.",
                        answer = pct, unit = "%",
                        explanation = "For field amplitude, ${fmt(db)}dB is a factor of 10^(${fmt(db)}/20) = ${fmt(10.0.pow(db / DB_AMPLITUDE))}, " +
                            "so ${fmt(pct, 2)}% is blocked"
                    )
                },
                generator("si_ni3") { r ->
                    val tr = riseTimesNs.pick(r)
                    val v = traceSpeeds.pick(r)
                    val cm = tr * v / CRITICAL_FRACTION
                    Numeric(
                        text = "A signal with a ${fmt(tr)}ns rise time travels at ${fmt(v)}cm/ns on a PCB. Above what trace length " +
                            "must it be treated as a transmission line, in cm? Use a tenth of the rise-time distance and round to " +
                            "2 decimal places.",
                        answer = cm, unit = "cm",
                        explanation = "In ${fmt(tr)}ns the edge travels ${fmt(tr * v)}cm. A tenth of that is ${fmt(cm, 2)}cm"
                    )
                },
                generator("si_ni4") { r ->
                    val er = dielectricConstants.pick(r)
                    val v = C_CM_PER_NS / sqrt(er)
                    Numeric(
                        text = "A signal travels in a dielectric with ε_r = ${fmt(er)}. What is its speed, in cm/ns? " +
                            "Use c = 30cm/ns and round to 2 decimal places.",
                        answer = v, unit = "cm/ns",
                        explanation = "v = c/√ε_r = 30/√${fmt(er)} = ${fmt(v, 2)}cm/ns, about half the speed of light"
                    )
                },
                generator("si_ni5") { r ->
                    val z2 = stepImpedances.pick(r)
                    val g = abs(z2 - 50) / (z2 + 50)
                    Numeric(
                        text = "A 50Ω trace meets a section of ${fmt(z2)}Ω (a connector or a via). What is the magnitude of the " +
                            "reflection coefficient at the step? Round to 3 decimal places.",
                        answer = g, unit = "", decimals = 3,
                        explanation = "|Γ| = |Z₂ − Z₁|/(Z₂ + Z₁) = |${fmt(z2)} − 50|/(${fmt(z2)} + 50) = ${fmt(g, 3)}"
                    )
                },
                generator("si_ni6") { r ->
                    val tr = riseTimesNs.pick(r)
                    val mhz = BANDWIDTH_FACTOR / tr
                    Numeric(
                        text = "A digital edge has a ${fmt(tr)}ns rise time. Using BW ≈ 0.35/t_r, what bandwidth does it contain, in " +
                            "MHz? Round to 1 decimal place.",
                        answer = mhz, unit = "MHz", decimals = 1,
                        explanation = "BW ≈ 0.35/(${fmt(tr)}×10⁻⁹) = ${fmt(mhz, 1)}MHz. Fast edges are high-frequency signals, " +
                            "even at a slow clock rate."
                    )
                }
            )
        )
    )
}
