package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI
import kotlin.math.sqrt

object FiltersContent {
    private const val NANO = 1e-9
    private const val DB_PER_DECADE = 20
    private val filterResistors = listOf(1.0, 4.7, 10.0, 22.0, 47.0)
    private val filterCapacitors = listOf(10.0, 47.0, 100.0, 220.0)
    private val filterOrders = listOf(1, 2, 3)
    private val decades = listOf(1, 2)
    private val centreFrequencies = listOf("10kHz" to 10.0, "100kHz" to 100.0, "1MHz" to 1000.0, "10.7MHz" to 10_700.0)
    private val qualityFactors = listOf(10.0, 20.0, 50.0, 100.0)
    private val rollOffOrders = listOf(1, 2, 3, 4, 5)
    private val designResistors = listOf(1.0, 10.0)
    private val designCutoffs = listOf(100.0, 1000.0, 3400.0)
    private val inputAmplitudes = listOf(1.0, 2.0, 5.0, 10.0)

    val topic = Topic(
        id = "filters",
        title = "Filters",
        subtitle = "Shaping signals by frequency",
        icon = "\uD83C\uDFB5",
        order = 11,
        lesson = Lesson(
            title = "Filters: Selecting Frequencies",
            sections = listOf(
                LessonSection(
                    heading = "What is a Filter?",
                    content = "A filter is a circuit that selectively passes certain frequencies while attenuating others. Filters are everywhere in electronics: audio equalizers, radio tuners, anti-aliasing in digital systems, and noise removal.\n\nThe four basic filter types:\n\u2022 Low-pass: passes low frequencies, blocks high\n\u2022 High-pass: passes high frequencies, blocks low\n\u2022 Band-pass: passes a specific range of frequencies\n\u2022 Band-stop (notch): blocks a specific range of frequencies",
                    keyPoint = "Low-pass, high-pass, band-pass, and band-stop are the four basic types"
                ),
                LessonSection(
                    heading = "RC Low-Pass Filter",
                    content = "The simplest low-pass filter is a resistor in series with a capacitor. The output is taken across the capacitor.\n\nAt low frequencies, the capacitor has high reactance and most of the voltage appears across it (output \u2248 input). At high frequencies, the capacitor has low reactance and shorts the output to ground.\n\nThe cutoff frequency f_c is where the output drops to 70.7% (\u22123dB) of the input.",
                    formula = "f_c = 1 / (2\u03C0RC)",
                    keyPoint = "At f_c, output is \u22123dB (70.7%) of input"
                ),
                LessonSection(
                    heading = "RC High-Pass Filter",
                    content = "Swap the resistor and capacitor positions: capacitor in series, output taken across the resistor. Now low frequencies are blocked and high frequencies pass.\n\nAt high frequencies, the capacitor is nearly a short circuit and the full signal reaches the output. At low frequencies, the capacitor blocks and the output drops to zero.\n\nThe cutoff frequency formula is identical to the low-pass filter.",
                    formula = "f_c = 1 / (2\u03C0RC)",
                    keyPoint = "Same f_c formula as low-pass, but the roles of R and C are swapped"
                ),
                LessonSection(
                    heading = "Cutoff Frequency and Roll-Off",
                    content = "The cutoff frequency (\u22123dB point) is where the filter transitions from passband to stopband. For a first-order RC filter, the roll-off rate is \u221220dB per decade.\n\nThis means for every 10\u00D7 increase in frequency past f_c, the output drops by a factor of 10 (20dB). Higher-order filters have steeper roll-offs: second-order = \u221240dB/decade, third-order = \u221260dB/decade.\n\nDecibels: dB = 20 \u00D7 log\u2081\u2080(V_out / V_in)",
                    formula = "Roll-off: \u221220n dB/decade\n(n = filter order)",
                    keyPoint = "First-order: \u221220dB/decade; second-order: \u221240dB/decade"
                ),
                LessonSection(
                    heading = "RLC Band-Pass Filter",
                    content = "Combining a resistor, inductor, and capacitor creates a band-pass filter. The circuit passes frequencies near its resonant frequency and attenuates others.\n\nThe center frequency is the resonant frequency: f\u2080 = 1/(2\u03C0\u221ALC). The bandwidth depends on the Q factor (quality factor): BW = f\u2080/Q.\n\nA high Q means a narrow, selective passband (sharp tuning). A low Q means a wide passband.",
                    formula = "f\u2080 = 1 / (2\u03C0\u221ALC)\nQ = f\u2080 / BW",
                    keyPoint = "High Q = narrow band (selective); Low Q = wide band"
                ),
                LessonSection(
                    heading = "Active Filters",
                    content = "Passive RC/RLC filters can only attenuate signals. Active filters use op-amps to provide gain while filtering, and they avoid loading effects.\n\nA first-order active low-pass filter places an RC network in the op-amp feedback path. Benefits of active filters:\n\u2022 Can provide gain (amplification)\n\u2022 High input impedance, low output impedance\n\u2022 Easy to cascade for higher-order designs\n\u2022 No inductors needed (inductors are bulky and expensive)\n\nThe Sallen-Key and Multiple Feedback topologies are common second-order active filter designs.",
                    keyPoint = "Active filters use op-amps: gain + filtering without inductors"
                )
            )
        ),
        quiz = Quiz(
            title = "Filters Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "flt_mc1",
                    questionText = "A low-pass filter allows which frequencies to pass?",
                    options = listOf(
                        "Only DC (0 Hz)",
                        "Frequencies below the cutoff frequency",
                        "Frequencies above the cutoff frequency",
                        "Only the resonant frequency"
                    ),
                    correctIndex = 1,
                    explanation = "A low-pass filter passes frequencies below its cutoff frequency and attenuates those above it."
                ),
                Question.MultipleChoice(
                    id = "flt_mc2",
                    questionText = "At the cutoff frequency, the output of a filter is attenuated by:",
                    options = listOf(
                        "\u22121dB (89%)",
                        "\u22123dB (70.7%)",
                        "\u22126dB (50%)",
                        "\u221220dB (10%)"
                    ),
                    correctIndex = 1,
                    explanation = "The cutoff frequency is defined as the \u22123dB point, where output is 70.7% of the input (1/\u221A2)."
                ),
                Question.MultipleChoice(
                    id = "flt_mc3",
                    questionText = "What is a key advantage of active filters over passive filters?",
                    options = listOf(
                        "They are simpler to build",
                        "They don't need a power supply",
                        "They can provide gain while filtering",
                        "They work at higher frequencies"
                    ),
                    correctIndex = 2,
                    explanation = "Active filters use op-amps to provide gain (amplification) in addition to filtering, which passive RC filters cannot do."
                ),
                Question.MultipleChoice(
                    id = "flt_mc4",
                    questionText = "In a first-order RC high-pass filter, where does the capacitor go?",
                    options = listOf(
                        "In series with the signal, with the resistor to ground",
                        "From the signal to ground, with the resistor in series",
                        "In parallel with the resistor",
                        "It isn't needed"
                    ),
                    correctIndex = 0,
                    explanation = "The series capacitor blocks low frequencies (its reactance is high there) and passes high ones. Swap the R and C positions and you get a low-pass filter."
                )
            ),
            generators = listOf(
                generator("flt_ni1") { r ->
                    val res = filterResistors.pick(r)
                    val c = filterCapacitors.pick(r)
                    val fc = 1 / (2 * PI * res * 1000 * c * NANO)
                    Numeric(
                        text = "What is the cutoff frequency (in Hz) of an RC low-pass filter with R = ${fmt(res)}kΩ and " +
                            "C = ${fmt(c)}nF? Round to the nearest whole number.",
                        answer = fc, unit = "Hz", decimals = 0,
                        explanation = "f_c = 1/(2πRC) = 1/(2π × ${fmt(res)}×10³ × ${fmt(c)}×10⁻⁹) ≈ ${fmt(fc, 0)}Hz"
                    )
                },
                generator("flt_ni2") { r ->
                    val order = filterOrders.pick(r)
                    val n = decades.pick(r)
                    val db = (DB_PER_DECADE * order * n).toDouble()
                    val ratio = if (n == 1) "10×" else "100×"
                    Numeric(
                        text = "Using the asymptotic roll-off, how much (in dB) does an order-$order low-pass filter attenuate " +
                            "a signal at $ratio its cutoff frequency (relative to the passband)?",
                        answer = db, unit = "dB",
                        explanation = "Roll-off is 20 × order = ${DB_PER_DECADE * order}dB per decade, and $ratio is " +
                            "$n decade(s): ${DB_PER_DECADE * order} × $n = ${fmt(db)}dB"
                    )
                },
                generator("flt_ni3") { r ->
                    val (label, f0Khz) = centreFrequencies.pick(r)
                    val q = qualityFactors.pick(r)
                    val bw = f0Khz / q
                    Numeric(
                        text = "A band-pass filter has a centre frequency of $label and Q = ${fmt(q)}. What is its bandwidth " +
                            "in kHz? Round to 2 decimal places.",
                        answer = bw, unit = "kHz",
                        explanation = "BW = f₀/Q = ${fmt(f0Khz)}kHz/${fmt(q)} = ${fmt(bw, 2)}kHz"
                    )
                },
                generator("flt_ni4") { r ->
                    val order = rollOffOrders.pick(r)
                    Numeric(
                        text = "What is the roll-off rate (in dB/decade) of an order-$order filter?",
                        answer = (DB_PER_DECADE * order).toDouble(), unit = "dB/decade",
                        explanation = "Each order adds 20dB/decade: 20 × $order = ${DB_PER_DECADE * order}dB/decade"
                    )
                },
                generator("flt_ni5") { r ->
                    val res = designResistors.pick(r)
                    val fc = designCutoffs.pick(r)
                    val cNf = 1 / (2 * PI * res * 1000 * fc) / NANO
                    Numeric(
                        text = "You want an RC low-pass filter with f_c = ${fmt(fc)}Hz using R = ${fmt(res)}kΩ. What capacitor " +
                            "value is needed, in nF? Round to 2 decimal places.",
                        answer = cNf, unit = "nF",
                        explanation = "C = 1/(2πRf_c) = 1/(2π × ${fmt(res)}×10³ × ${fmt(fc)}) = ${fmt(cNf, 2)}nF"
                    )
                },
                generator("flt_ni6") { r ->
                    val vin = inputAmplitudes.pick(r)
                    val vout = vin / sqrt(2.0)
                    Numeric(
                        text = "A ${fmt(vin)}V sine wave enters a first-order low-pass filter exactly at its cutoff frequency. " +
                            "What is the output amplitude, in Volts? Round to 2 decimal places.",
                        answer = vout, unit = "V",
                        explanation = "At f_c the output is −3dB, i.e. 1/√2 ≈ 70.7% of the input: ${fmt(vin)} × 0.707 = ${fmt(vout, 2)}V"
                    )
                }
            )
        )
    )
}
