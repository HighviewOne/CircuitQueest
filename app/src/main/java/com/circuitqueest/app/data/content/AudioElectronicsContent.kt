package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.log10

object AudioElectronicsContent {
    private const val DB_PER_BIT = 6.02
    private const val DB_OFFSET = 1.76
    private val bitDepths = listOf(8, 12, 16, 20, 24)
    private val headphoneImpedances = listOf(16.0, 32.0, 64.0, 300.0)
    private val headphoneVoltages = listOf(0.1, 0.5, 1.0, 2.0)
    private val amplifierClasses = listOf(
        "A" to listOf(20.0, 25.0), "AB" to listOf(50.0, 60.0, 70.0), "D" to listOf(85.0, 90.0)
    )
    private val supplyPowers = listOf(20.0, 50.0, 100.0)
    private val powerRatios = listOf(2.0, 4.0, 10.0, 100.0)
    private val speakerPeaks = listOf(5.0, 10.0, 20.0, 28.0)
    private val speakerImpedances = listOf(4.0, 8.0)
    private val sampleRates = listOf(44.1, 48.0, 96.0, 192.0)

    val topic = Topic(
        id = "audio_electronics",
        title = "Audio Electronics",
        subtitle = "Amplifier classes, DACs, and speaker design",
        icon = "\uD83C\uDFA7",
        order = 13,
        lesson = Lesson(
            title = "Audio Electronics: The Sound of Circuits",
            sections = listOf(
                LessonSection(
                    heading = "Audio Signal Chain",
                    content = "The audio signal chain converts sound waves to electrical signals and back:\n\n1. Microphone: converts sound pressure to electrical signal (mV level)\n2. Preamplifier: boosts the weak mic signal to line level (~1V RMS)\n3. ADC: converts analog to digital for processing/recording\n4. DSP/Processing: equalization, mixing, effects\n5. DAC: converts digital back to analog\n6. Power amplifier: drives speakers or headphones\n7. Speaker/headphone: converts electrical signal back to sound\n\nAudio bandwidth: 20 Hz to 20 kHz (human hearing range). High-fidelity (hi-fi) systems aim to reproduce this range with minimal distortion.",
                    keyPoint = "Audio chain: mic \u2192 preamp \u2192 ADC \u2192 DSP \u2192 DAC \u2192 power amp \u2192 speaker"
                ),
                LessonSection(
                    heading = "Amplifier Classes",
                    content = "Power amplifiers are classified by how their output transistors conduct:\n\n\u2022 Class A: output transistors conduct 100% of the cycle. Highest linearity, lowest distortion, but only ~25% efficient (wastes 75% as heat).\n\n\u2022 Class B: each transistor conducts 50% of the cycle (push-pull). Up to 78.5% efficient but has crossover distortion at the zero crossing.\n\n\u2022 Class AB: small bias keeps both transistors on near zero crossing, eliminating crossover distortion. 50\u201370% efficient. The most common hi-fi amplifier class.\n\n\u2022 Class D: uses PWM switching (not 'digital'). Transistors are fully on or off, achieving 90\u201395% efficiency. Dominant in portable devices, subwoofers, and modern receivers.",
                    formula = "Class A max efficiency: 25%\nClass B max efficiency: 78.5%\nClass D typical efficiency: 90\u201395%",
                    keyPoint = "Class AB: best sound quality for hi-fi; Class D: best efficiency for portable/high-power"
                ),
                LessonSection(
                    heading = "Digital-to-Analog Converters (DACs)",
                    content = "The DAC is crucial for audio quality. It converts digital samples back to a continuous analog waveform.\n\nDAC architectures:\n\u2022 R-2R ladder: simple resistor network, good linearity\n\u2022 Delta-sigma (\u0394\u03A3): oversamples and noise-shapes for very high resolution. Used in most modern audio DACs.\n\nKey specifications:\n\u2022 Resolution: 16-bit (CD), 24-bit (hi-res audio), 32-bit (studio)\n\u2022 Sample rate: 44.1/48 kHz (standard), 96/192 kHz (hi-res)\n\u2022 THD+N: Total Harmonic Distortion + Noise, lower is better (<0.001% for good DACs)\n\u2022 DNR: Dynamic Range, higher is better (>110 dB for good DACs)\n\nA reconstruction filter (low-pass) smooths the staircase output into a clean analog waveform.",
                    formula = "DNR \u2248 6.02\u00D7N + 1.76 dB\n16-bit: ~96 dB, 24-bit: ~144 dB",
                    keyPoint = "Delta-sigma DACs dominate audio; 24-bit/96kHz is hi-res standard"
                ),
                LessonSection(
                    heading = "Speaker Design",
                    content = "A speaker converts electrical energy into sound using electromagnetic force:\n\n\u2022 Voice coil: carries the audio signal current\n\u2022 Permanent magnet: creates a static magnetic field\n\u2022 Cone/diaphragm: moves air to create sound waves\n\nA single driver can't cover the full audio range well, so multi-way systems use:\n\u2022 Woofer: low frequencies (20\u2013500 Hz), large cone\n\u2022 Midrange: mid frequencies (500 Hz\u20135 kHz)\n\u2022 Tweeter: high frequencies (5\u201320 kHz), small dome\n\nA crossover network (passive LC filters or active DSP) splits the signal and sends the right frequencies to each driver.\n\nSpeaker impedance is nominally 4\u03A9 or 8\u03A9 but varies with frequency.",
                    keyPoint = "Multi-way speakers + crossover network for full-range reproduction"
                ),
                LessonSection(
                    heading = "Headphone Amplifiers",
                    content = "Headphones present unique amplification challenges:\n\n\u2022 Low impedance headphones (16\u201332\u03A9): need high current capability, easy to drive from phones\n\u2022 High impedance headphones (250\u2013600\u03A9): need higher voltage swing, benefit from dedicated headphone amps\n\nPower needed: P = V\u00B2/R. A 32\u03A9 headphone at 1V RMS draws ~31 mW. A 300\u03A9 headphone needs the same voltage for only ~3.3 mW but requires more voltage for loud volumes.\n\nA headphone amp needs:\n\u2022 Low output impedance (<1\u03A9) for damping factor and flat response\n\u2022 Low noise floor (especially for sensitive IEMs)\n\u2022 Sufficient voltage swing for high-impedance headphones\n\nThe damping factor (speaker impedance / amp output impedance) should be >8 for tight bass control.",
                    formula = "P = V\u00B2_RMS / Z\nDamping factor = Z_load / Z_output",
                    keyPoint = "Low output impedance + enough voltage swing = proper headphone driving"
                ),
                LessonSection(
                    heading = "Audio Measurements and Distortion",
                    content = "Key audio quality metrics:\n\n\u2022 THD (Total Harmonic Distortion): unwanted harmonics as % of fundamental. <0.1% is good, <0.01% is excellent.\n\u2022 IMD (Intermodulation Distortion): unwanted sum/difference frequencies from two tones.\n\u2022 SNR (Signal-to-Noise Ratio): signal power vs. noise floor. >100 dB is excellent.\n\u2022 Frequency Response: deviation from flat across 20 Hz\u201320 kHz. \u00B10.5 dB is very good.\n\u2022 Crosstalk: signal leaking between left and right channels. <\u221260 dB is good.\n\nModern audio electronics routinely achieve THD < 0.001% and SNR > 110 dB, far exceeding human perception limits. The speaker and room acoustics are usually the weakest links.",
                    formula = "THD = \u221A(V\u00B2_2 + V\u00B2_3 + ...) / V_1 \u00D7 100%\nSNR(dB) = 20 log(V_signal / V_noise)",
                    keyPoint = "THD, SNR, and frequency response define audio quality; speakers are usually the bottleneck"
                )
            )
        ),
        quiz = Quiz(
            title = "Audio Electronics Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ae_mc1",
                    questionText = "Which amplifier class is most efficient?",
                    options = listOf(
                        "Class A",
                        "Class AB",
                        "Class B",
                        "Class D"
                    ),
                    correctIndex = 3,
                    explanation = "Class D amplifiers use PWM switching, keeping transistors fully on or off. This achieves 90\u201395% efficiency, far higher than Class A (25%) or Class AB (50\u201370%)."
                ),
                Question.MultipleChoice(
                    id = "ae_mc2",
                    questionText = "Crossover distortion is a problem in:",
                    options = listOf(
                        "Class A amplifiers",
                        "Class B amplifiers",
                        "Class D amplifiers",
                        "DACs"
                    ),
                    correctIndex = 1,
                    explanation = "Class B amplifiers have a dead zone at the zero crossing where neither transistor conducts, creating crossover distortion. Class AB adds bias to eliminate this."
                ),
                Question.MultipleChoice(
                    id = "ae_mc3",
                    questionText = "In a multi-way speaker system, the crossover network:",
                    options = listOf(
                        "Amplifies the signal for each driver",
                        "Splits frequencies so each driver handles its optimal range",
                        "Converts digital to analog",
                        "Adds bass boost"
                    ),
                    correctIndex = 1,
                    explanation = "The crossover network uses filters to split the audio signal, sending low frequencies to the woofer, mids to the midrange, and highs to the tweeter."
                ),
                Question.MultipleChoice(
                    id = "ae_mc4",
                    questionText = "Delta-sigma (\u0394\u03A3) DACs achieve high resolution by:",
                    options = listOf(
                        "Using very precise resistors",
                        "Oversampling and noise shaping",
                        "Increasing the supply voltage",
                        "Using multiple parallel outputs"
                    ),
                    correctIndex = 1,
                    explanation = "Delta-sigma DACs oversample the signal at many times the Nyquist rate and use noise shaping to push quantization noise out of the audio band, achieving very high effective resolution."
                ),
                Question.MultipleChoice(
                    id = "ae_mc5",
                    questionText = "Why does an audio DAC need a reconstruction (anti-imaging) filter on its output?",
                    options = listOf(
                        "To boost the bass",
                        "To remove copies (images) of the signal around multiples of the sample rate",
                        "To convert the signal to digital",
                        "To add dither"
                    ),
                    correctIndex = 1,
                    explanation = "A DAC outputs a stepped signal containing images of the audio around fs, 2fs, … A low-pass reconstruction filter removes them, leaving the smooth audio below fs/2."
                )
            ),
            generators = listOf(
                generator("ae_ni1") { r ->
                    val n = bitDepths.pick(r)
                    val dnr = DB_PER_BIT * n + DB_OFFSET
                    Numeric(
                        text = "What is the theoretical dynamic range of ${article(n.toDouble())} $n-bit DAC, in dB? " +
                            "Round to 1 decimal place.",
                        answer = dnr, unit = "dB", decimals = 1,
                        explanation = "DNR = 6.02 × N + 1.76 = 6.02 × $n + 1.76 = ${fmt(dnr, 1)}dB"
                    )
                },
                generator("ae_ni2") { r ->
                    val z = headphoneImpedances.pick(r)
                    val v = headphoneVoltages.pick(r)
                    val mw = v * v / z * 1000
                    Numeric(
                        text = "${articleCap(z)} ${fmt(z)}Ω headphone is driven at ${fmt(v)}V RMS. What power does it receive, " +
                            "in mW? Round to 2 decimal places.",
                        answer = mw, unit = "mW",
                        explanation = "P = V²/R = ${fmt(v)}²/${fmt(z)} = ${fmt(mw, 2)}mW"
                    )
                },
                generator("ae_ni3") { r ->
                    val (cls, efficiencies) = amplifierClasses.pick(r)
                    val eff = efficiencies.pick(r)
                    val psup = supplyPowers.pick(r)
                    val pout = psup * eff / 100
                    Numeric(
                        text = "A Class $cls amplifier draws ${fmt(psup)}W from the " +
                            "supply and delivers ${fmt(pout)}W to the speaker. What is its efficiency in percent?",
                        answer = eff, unit = "%",
                        explanation = "η = P_out/P_supply × 100 = ${fmt(pout)}/${fmt(psup)} × 100 = ${fmt(eff)}%"
                    )
                },
                generator("ae_ni4") { r ->
                    val ratio = powerRatios.pick(r)
                    val db = 10 * log10(ratio)
                    Numeric(
                        text = "An amplifier's output power is increased ${fmt(ratio)}-fold. How many dB is that? " +
                            "Round to 2 decimal places.",
                        answer = db, unit = "dB",
                        explanation = "Doubling the power adds only about 3dB. Here: 10·log₁₀(${fmt(ratio)}) = ${fmt(db, 2)}dB"
                    )
                },
                generator("ae_ni5") { r ->
                    val vp = speakerPeaks.pick(r)
                    val z = speakerImpedances.pick(r)
                    val p = vp * vp / (2 * z)
                    Numeric(
                        text = "An amplifier drives a ${fmt(vp)}V-peak sine wave into ${article(z)} ${fmt(z)}Ω speaker. " +
                            "What is the average (RMS) power, in Watts? Round to 2 decimal places.",
                        answer = p, unit = "W",
                        explanation = "V_rms = V_p/√2, so P = V_p²/(2R) = ${fmt(vp)}²/(2 × ${fmt(z)}) = ${fmt(p, 2)}W"
                    )
                },
                generator("ae_ni6") { r ->
                    val fs = sampleRates.pick(r)
                    Numeric(
                        text = "Audio is sampled at ${fmt(fs)}kHz. What is the highest frequency it can represent, in kHz? " +
                            "Round to 2 decimal places.",
                        answer = fs / 2, unit = "kHz",
                        explanation = "The Nyquist limit is half the sample rate: ${fmt(fs)}/2 = ${fmt(fs / 2, 2)}kHz"
                    )
                }
            )
        )
    )
}
