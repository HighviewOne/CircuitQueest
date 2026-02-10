package com.circuitqueest.app.data.content

object CommunicationSystemsContent {
    val topic = Topic(
        id = "communication_systems",
        title = "Communication Systems",
        subtitle = "Modulation, bandwidth, and digital communications",
        icon = "\uD83D\uDCF6",
        order = 17,
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
                Question.NumericInput(
                    id = "comm_ni1",
                    questionText = "An AM radio station transmits audio up to 5kHz. What bandwidth (in kHz) does the AM signal occupy?",
                    correctAnswer = 10.0,
                    tolerance = 0.5,
                    unit = "kHz",
                    explanation = "AM bandwidth = 2 \u00D7 f_max = 2 \u00D7 5kHz = 10kHz."
                ),
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
                Question.NumericInput(
                    id = "comm_ni2",
                    questionText = "What is the minimum sampling frequency (in kHz) needed to digitize a 10kHz audio signal without aliasing?",
                    correctAnswer = 20.0,
                    tolerance = 0.5,
                    unit = "kHz",
                    explanation = "Nyquist rate: f_sample \u2265 2 \u00D7 f_max = 2 \u00D7 10kHz = 20kHz."
                ),
                Question.NumericInput(
                    id = "comm_ni3",
                    questionText = "A signal has power 1W and noise power is 0.001W. What is the SNR in dB?",
                    correctAnswer = 30.0,
                    tolerance = 0.5,
                    unit = "dB",
                    explanation = "SNR = 10 log\u2081\u2080(1/0.001) = 10 log\u2081\u2080(1000) = 10 \u00D7 3 = 30dB."
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
                Question.NumericInput(
                    id = "comm_ni4",
                    questionText = "A channel has bandwidth 1MHz and SNR = 31 (linear, not dB). What is the Shannon capacity in Mbps?",
                    correctAnswer = 5.0,
                    tolerance = 0.1,
                    unit = "Mbps",
                    explanation = "C = BW \u00D7 log\u2082(1 + SNR) = 10\u2076 \u00D7 log\u2082(32) = 10\u2076 \u00D7 5 = 5Mbps."
                )
            )
        )
    )
}
