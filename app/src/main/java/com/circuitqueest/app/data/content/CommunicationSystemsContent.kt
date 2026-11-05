package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.log2
import kotlin.math.log10

object CommunicationSystemsContent {
    private val audioBandwidths = listOf(3.0, 4.5, 5.0, 10.0, 15.0)
    private val sampledBandwidths = listOf(3.4, 10.0, 20.0)
    private val signalPowers = listOf(0.5, 1.0, 2.0)
    private val noisePowersMw = listOf(0.1, 1.0, 10.0)
    private val channelBandwidths = listOf(1.0, 5.0, 20.0)
    private val linearSnrs = listOf(3.0, 7.0, 15.0, 31.0, 63.0, 255.0)
    private val constellationSizes = listOf(4, 16, 64, 256, 1024)
    private val symbolRates = listOf(1.0, 5.0, 10.0)
    private val rateConstellations = listOf(4, 16, 64)

    val topic = Topic(
        id = "communication_systems",
        title = "Communication Systems",
        subtitle = "Modulation, bandwidth, and digital communications",
        icon = "\uD83D\uDCF6",
        order = 29,
        lesson = Lesson(
            title = "Communication Systems: Sending Signals",
            sections = listOf(
                LessonSection(
                    heading = "Why Modulation?",
                    content = "To transmit information wirelessly, we need to encode it onto a high-frequency carrier wave. This process is called modulation.\n\nWhy not transmit the signal directly?\n\u2022 Audio frequencies (20Hz\u201320kHz) would need impractically large antennas (\u03BB at 1kHz \u2248 300km)\n\u2022 Multiple stations would interfere without distinct carrier frequencies\n\u2022 Higher frequencies propagate better and allow smaller antennas\n\nModulation maps information onto the amplitude, frequency, or phase of a carrier.",
                    keyPoint = "Modulation shifts signals to higher frequencies for practical transmission"
                ),
                LessonSection(
                    heading = "Amplitude Modulation (AM)",
                    content = "AM varies the carrier's amplitude in proportion to the message signal. The carrier frequency stays constant.\n\nAM is simple to implement but inefficient \u2014 most power is in the carrier, not the sidebands that carry information.\n\nAM bandwidth = 2 \u00D7 highest message frequency. An audio signal up to 5kHz needs 10kHz of bandwidth.\n\nAM is susceptible to noise because noise directly affects amplitude.",
                    formula = "s(t) = [1 + m\u00B7x(t)] \u00D7 cos(2\u03C0f_c t)\nBW = 2f_max",
                    keyPoint = "AM: simple but noise-sensitive; BW = 2 \u00D7 max signal frequency"
                ),
                LessonSection(
                    heading = "Frequency Modulation (FM)",
                    content = "FM varies the carrier's frequency in proportion to the message signal. The amplitude stays constant.\n\nFM is more resistant to noise than AM because information is in the frequency, not the amplitude. Noise primarily affects amplitude, so an FM receiver can clip amplitude variations without losing data.\n\nThe trade-off: FM requires more bandwidth than AM. Commercial FM radio uses 200kHz per station versus 10kHz for AM.",
                    formula = "\u0394f = k_f \u00D7 max|x(t)|\nBW \u2248 2(\u0394f + f_max) (Carson's rule)",
                    keyPoint = "FM: noise-resistant but needs more bandwidth than AM"
                ),
                LessonSection(
                    heading = "Signal-to-Noise Ratio (SNR)",
                    content = "SNR measures signal quality \u2014 how much stronger the desired signal is compared to background noise.\n\nSNR is usually expressed in decibels:\nSNR(dB) = 10 \u00D7 log\u2081\u2080(P_signal / P_noise)\n\n\u2022 SNR > 30dB: excellent quality\n\u2022 SNR 20\u201330dB: good for voice\n\u2022 SNR < 10dB: noisy, errors likely\n\nShannon's theorem gives the maximum data rate for a given SNR and bandwidth.",
                    formula = "SNR(dB) = 10 log\u2081\u2080(P_s / P_n)\nC = BW \u00D7 log\u2082(1 + SNR)",
                    keyPoint = "Shannon's limit: max bit rate = BW \u00D7 log\u2082(1 + SNR)"
                ),
                LessonSection(
                    heading = "Sampling Theorem",
                    content = "To convert analog signals to digital, we must sample them at discrete time intervals. The Nyquist\u2013Shannon sampling theorem states the minimum sampling rate:\n\nf_sample \u2265 2 \u00D7 f_max\n\nIf you sample below this rate, aliasing occurs \u2014 high frequencies fold into lower frequencies, corrupting the signal. This is why CD audio samples at 44.1kHz (just over 2 \u00D7 20kHz).\n\nAnti-aliasing filters remove frequencies above f_max before sampling.",
                    formula = "f_sample \u2265 2 \u00D7 f_max (Nyquist rate)",
                    keyPoint = "Sample at \u2265 2\u00D7 the highest frequency to avoid aliasing"
                ),
                LessonSection(
                    heading = "Digital Modulation",
                    content = "Modern communications use digital modulation to transmit binary data:\n\n\u2022 ASK (Amplitude Shift Keying): different amplitudes for 0 and 1\n\u2022 FSK (Frequency Shift Keying): different frequencies for 0 and 1\n\u2022 PSK (Phase Shift Keying): different phases for 0 and 1\n\u2022 QAM (Quadrature AM): combines amplitude and phase to send multiple bits per symbol\n\n4-QAM sends 2 bits per symbol, 16-QAM sends 4 bits, 256-QAM sends 8 bits. Higher-order schemes are faster but need better SNR.",
                    formula = "Bits per symbol = log\u2082(M)\n(M-QAM: M constellation points)",
                    keyPoint = "Higher-order modulation = more bits/symbol but needs better SNR"
                )
            )
        ),
        quiz = Quiz(
            title = "Communication Systems Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "comm_mc1",
                    questionText = "Which modulation scheme is more resistant to noise?",
                    options = listOf(
                        "AM",
                        "FM",
                        "They are equally resistant",
                        "Neither resists noise"
                    ),
                    correctIndex = 1,
                    explanation = "FM is more noise-resistant because information is encoded in frequency, not amplitude. Noise primarily affects amplitude."
                ),
                Question.MultipleChoice(
                    id = "comm_mc2",
                    questionText = "16-QAM sends how many bits per symbol?",
                    options = listOf(
                        "2",
                        "4",
                        "8",
                        "16"
                    ),
                    correctIndex = 1,
                    explanation = "Bits per symbol = log\u2082(M) = log\u2082(16) = 4 bits per symbol."
                ),
                Question.MultipleChoice(
                    id = "comm_mc3",
                    questionText = "What happens if you sample below the Nyquist rate?",
                    options = listOf(
                        "The signal is amplified",
                        "The signal is compressed",
                        "Aliasing occurs, corrupting the signal",
                        "Nothing, it still works fine"
                    ),
                    correctIndex = 2,
                    explanation = "Sampling below 2\u00D7f_max causes aliasing: high-frequency components fold into lower frequencies, corrupting the reconstructed signal."
                ),
                Question.MultipleChoice(
                    id = "comm_mc4",
                    questionText = "By Carson's rule, the bandwidth of an FM signal is about:",
                    options = listOf(
                        "2 × the audio bandwidth",
                        "2 × (peak deviation + highest audio frequency)",
                        "The peak deviation only",
                        "Half the carrier frequency"
                    ),
                    correctIndex = 1,
                    explanation = "Carson's rule: B ≈ 2(Δf + f_m). Broadcast FM with Δf = 75kHz and f_m = 15kHz needs about 180kHz, which is why FM channels are much wider than AM."
                )
            ),
            generators = listOf(
                generator("comm_ni1") { r ->
                    val fm = audioBandwidths.pick(r)
                    Numeric(
                        text = "An AM transmitter sends audio up to ${fmt(fm)}kHz. What bandwidth does the AM signal occupy, in kHz?",
                        answer = 2 * fm, unit = "kHz",
                        explanation = "AM has upper and lower sidebands: bandwidth = 2 × f_max = 2 × ${fmt(fm)} = ${fmt(2 * fm)}kHz"
                    )
                },
                generator("comm_ni2") { r ->
                    val fm = sampledBandwidths.pick(r)
                    Numeric(
                        text = "What is the minimum sampling frequency, in kHz, needed to digitize a signal with content up to " +
                            "${fmt(fm)}kHz without aliasing?",
                        answer = 2 * fm, unit = "kHz",
                        explanation = "Nyquist: f_s ≥ 2 × f_max = 2 × ${fmt(fm)} = ${fmt(2 * fm)}kHz"
                    )
                },
                generator("comm_ni3") { r ->
                    val ps = signalPowers.pick(r)
                    val pnMw = noisePowersMw.pick(r)
                    val ratio = ps * 1000 / pnMw
                    val db = 10 * log10(ratio)
                    Numeric(
                        text = "A received signal has ${fmt(ps)}W of power and the noise power is ${fmt(pnMw)}mW. What is the SNR in " +
                            "dB? Round to 2 decimal places.",
                        answer = db, unit = "dB",
                        explanation = "SNR = 10·log₁₀(P_s/P_n) = 10·log₁₀(${fmt(ps * 1000)}mW/${fmt(pnMw)}mW) = " +
                            "10·log₁₀(${fmt(ratio)}) = ${fmt(db, 2)}dB"
                    )
                },
                generator("comm_ni4") { r ->
                    val b = channelBandwidths.pick(r)
                    val snr = linearSnrs.pick(r)
                    val c = b * log2(1 + snr)
                    Numeric(
                        text = "A channel has ${fmt(b)}MHz bandwidth and a linear SNR of ${fmt(snr)}. What is its Shannon capacity, " +
                            "in Mbps? Round to 2 decimal places.",
                        answer = c, unit = "Mbps",
                        explanation = "C = B × log₂(1 + SNR) = ${fmt(b)} × log₂(${fmt(1 + snr)}) = ${fmt(c, 2)}Mbps"
                    )
                },
                generator("comm_ni5") { r ->
                    val m = constellationSizes.pick(r)
                    val bits = Int.SIZE_BITS - 1 - Integer.numberOfLeadingZeros(m)
                    Numeric(
                        text = "How many bits does each symbol carry in $m-QAM?",
                        answer = bits.toDouble(), unit = "bits", exact = true,
                        explanation = "Bits per symbol = log₂(M) = log₂($m) = $bits bits"
                    )
                },
                generator("comm_ni6") { r ->
                    val rs = symbolRates.pick(r)
                    val m = rateConstellations.pick(r)
                    val bits = Int.SIZE_BITS - 1 - Integer.numberOfLeadingZeros(m)
                    val rb = rs * bits
                    Numeric(
                        text = "A link sends ${fmt(rs)} million symbols per second using $m-QAM. What is its bit rate, in Mbps?",
                        answer = rb, unit = "Mbps",
                        explanation = "$m-QAM carries log₂($m) = $bits bits per symbol: ${fmt(rs)} × $bits = ${fmt(rb)}Mbps"
                    )
                }
            )
        )
    )
}
