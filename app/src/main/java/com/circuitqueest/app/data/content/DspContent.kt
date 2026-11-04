package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object DspContent {
    private const val DB_PER_BIT = 6.02
    private const val DB_OFFSET = 1.76
    private val maxFrequencies = listOf(4.0, 8.0, 20.0, 22.05)
    private val adcBits = listOf(8, 10, 12, 14, 16)
    private val sampleRates = listOf(8000.0, 44_100.0, 48_000.0, 96_000.0)
    private val fftSizes = listOf(256, 512, 1024, 4096)
    private val levelBits = listOf(6, 8, 10, 12, 16)
    private val aliasRates = listOf(1000.0, 8000.0, 10_000.0)
    private val aliasFractions = listOf(0.6, 0.7, 0.8, 0.9)
    private val referenceVoltages = listOf(3.3, 5.0)
    private val lsbBits = listOf(8, 10, 12)

    val topic = Topic(
        id = "dsp",
        title = "Digital Signal Processing",
        subtitle = "Sampling, FFT, and digital filters",
        icon = "\uD83D\uDCC8",
        order = 20,
        lesson = Lesson(
            title = "Digital Signal Processing: Crunching Signals with Math",
            sections = listOf(
                LessonSection(
                    heading = "From Analog to Digital",
                    content = "Digital Signal Processing (DSP) manipulates signals after they've been converted from analog to digital form. An ADC samples the continuous signal at regular intervals and quantizes each sample to a finite number of bits.\n\nThe process:\n\u2022 Sampling: capture the signal value at discrete time intervals (sample rate f_s)\n\u2022 Quantization: round each sample to the nearest digital level (N-bit resolution gives 2^N levels)\n\u2022 Processing: apply mathematical operations (filtering, transforms, compression)\n\u2022 Reconstruction: convert back to analog via DAC if needed\n\nDSP advantages over analog: perfect reproducibility, easy reprogramming, no component drift.",
                    keyPoint = "DSP = sample the analog world, process with math, reconstruct if needed"
                ),
                LessonSection(
                    heading = "Sampling Theorem (Nyquist-Shannon)",
                    content = "The Nyquist-Shannon sampling theorem states: to perfectly reconstruct a signal, the sample rate must be at least twice the highest frequency component.\n\nf_s \u2265 2 \u00D7 f_max\n\nThe frequency f_s/2 is called the Nyquist frequency. If you sample below this rate, higher frequencies fold back as lower frequencies \u2014 this is aliasing.\n\nExample: CD audio samples at 44.1 kHz, capturing frequencies up to 22.05 kHz (beyond human hearing at ~20 kHz). An anti-aliasing filter removes frequencies above f_s/2 before sampling.\n\nIn practice, oversampling (sampling faster than 2\u00D7) eases filter requirements and improves quality.",
                    formula = "f_s \u2265 2 \u00D7 f_max (Nyquist criterion)\nf_Nyquist = f_s / 2",
                    keyPoint = "Sample at \u22652\u00D7 the max frequency or aliasing corrupts the signal"
                ),
                LessonSection(
                    heading = "Quantization and Resolution",
                    content = "Quantization maps continuous amplitude values to discrete digital levels. An N-bit ADC has 2^N levels.\n\n\u2022 8-bit: 256 levels (low-fi audio, simple sensors)\n\u2022 12-bit: 4,096 levels (typical MCU ADC)\n\u2022 16-bit: 65,536 levels (CD audio quality)\n\u2022 24-bit: 16.7 million levels (professional audio)\n\nQuantization introduces quantization noise \u2014 the difference between the true value and the rounded value. The Signal-to-Quantization-Noise Ratio (SQNR) improves by ~6 dB per additional bit.\n\nDynamic range = 6.02\u00D7N + 1.76 dB for an ideal N-bit converter.",
                    formula = "Levels = 2^N\nSQNR \u2248 6.02N + 1.76 dB\nLSB = V_ref / 2^N",
                    keyPoint = "Each additional bit adds ~6 dB of dynamic range; 16-bit gives ~96 dB"
                ),
                LessonSection(
                    heading = "The Discrete Fourier Transform (DFT/FFT)",
                    content = "The DFT converts a time-domain signal into its frequency components. It answers: 'which frequencies are present and how strong are they?'\n\nFor N samples, the DFT produces N frequency bins from 0 to f_s. The FFT (Fast Fourier Transform) is an efficient algorithm that computes the DFT in O(N log N) operations instead of O(N\u00B2).\n\nFrequency resolution = f_s / N. More samples give finer frequency resolution.\n\nApplications:\n\u2022 Spectrum analysis (identify signal frequencies)\n\u2022 Audio equalization and compression (MP3, AAC)\n\u2022 Vibration analysis in machinery\n\u2022 Radar and sonar signal processing",
                    formula = "Frequency resolution = f_s / N\nFFT complexity: O(N log N)\nDFT: X[k] = \u03A3 x[n] \u00D7 e^(-j2\u03C0kn/N)",
                    keyPoint = "FFT efficiently finds frequency content; resolution = f_s/N"
                ),
                LessonSection(
                    heading = "FIR Filters",
                    content = "A Finite Impulse Response (FIR) filter computes each output sample as a weighted sum of current and past input samples:\n\ny[n] = b\u2080x[n] + b\u2081x[n-1] + b\u2082x[n-2] + ... + b_M x[n-M]\n\nThe coefficients b\u2080...b_M define the filter's frequency response. M is the filter order.\n\nFIR advantages:\n\u2022 Always stable (no feedback)\n\u2022 Can achieve exactly linear phase (no signal distortion)\n\u2022 Easy to design with windowing or Parks-McClellan algorithm\n\nFIR disadvantage: may require many taps (high order) for sharp cutoffs, increasing computation.",
                    formula = "y[n] = \u03A3 b_k \u00D7 x[n-k], k=0 to M\nLinear phase: symmetric coefficients",
                    keyPoint = "FIR = weighted sum of inputs; always stable, can be linear phase"
                ),
                LessonSection(
                    heading = "IIR Filters",
                    content = "An Infinite Impulse Response (IIR) filter uses feedback \u2014 the output depends on both input samples AND previous output samples:\n\ny[n] = b\u2080x[n] + b\u2081x[n-1] + ... \u2212 a\u2081y[n-1] \u2212 a\u2082y[n-2] \u2212 ...\n\nIIR advantages:\n\u2022 Much sharper cutoffs with fewer coefficients than FIR\n\u2022 Can mimic classic analog filters (Butterworth, Chebyshev, Elliptic)\n\nIIR disadvantages:\n\u2022 Can be unstable if poles are outside the unit circle\n\u2022 Non-linear phase (causes group delay distortion)\n\u2022 Sensitive to coefficient quantization in fixed-point arithmetic\n\nChoice: use FIR when linear phase matters (audio, communications); use IIR when efficiency matters (real-time embedded).",
                    formula = "y[n] = \u03A3 b_k\u00D7x[n-k] \u2212 \u03A3 a_k\u00D7y[n-k]\nStable if all poles |z| < 1",
                    keyPoint = "IIR = feedback filter; sharper with fewer taps but can be unstable"
                )
            )
        ),
        quiz = Quiz(
            title = "Digital Signal Processing Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "dsp_mc1",
                    questionText = "What happens if you sample a signal below the Nyquist rate?",
                    options = listOf(
                        "The signal is amplified",
                        "Aliasing occurs \u2014 high frequencies appear as false low frequencies",
                        "The signal is perfectly reconstructed",
                        "Quantization noise increases"
                    ),
                    correctIndex = 1,
                    explanation = "Sampling below 2\u00D7f_max causes aliasing: frequencies above f_s/2 fold back into the baseband as false lower frequencies that cannot be removed."
                ),
                Question.MultipleChoice(
                    id = "dsp_mc2",
                    questionText = "The FFT computes the DFT with complexity:",
                    options = listOf(
                        "O(N)",
                        "O(N log N)",
                        "O(N\u00B2)",
                        "O(2^N)"
                    ),
                    correctIndex = 1,
                    explanation = "The FFT reduces the DFT computation from O(N\u00B2) to O(N log N) by exploiting symmetry and periodicity of the complex exponentials."
                ),
                Question.MultipleChoice(
                    id = "dsp_mc3",
                    questionText = "Which filter type is always stable and can achieve linear phase?",
                    options = listOf(
                        "IIR filter",
                        "FIR filter",
                        "Butterworth filter",
                        "Chebyshev filter"
                    ),
                    correctIndex = 1,
                    explanation = "FIR filters have no feedback (no poles), so they're always stable. With symmetric coefficients, they achieve exactly linear phase."
                ),
                Question.MultipleChoice(
                    id = "dsp_mc4",
                    questionText = "Why is a window function (e.g. Hann) applied to a signal before taking its FFT?",
                    options = listOf(
                        "To increase the sample rate",
                        "To reduce spectral leakage from cutting the signal off abruptly",
                        "To remove DC offset",
                        "To make the FFT faster"
                    ),
                    correctIndex = 1,
                    explanation = "The FFT assumes the block repeats forever. If the block doesn't contain whole cycles, the ends jump and energy 'leaks' into neighbouring bins. Tapering the ends with a window reduces that leakage."
                )
            ),
            generators = listOf(
                generator("dsp_ni1") { r ->
                    val fmax = maxFrequencies.pick(r)
                    Numeric(
                        text = "A signal contains frequencies up to ${fmt(fmax)}kHz. What is the minimum sample rate to avoid " +
                            "aliasing, in kHz? Round to 2 decimal places.",
                        answer = 2 * fmax, unit = "kHz",
                        explanation = "Nyquist: f_s ≥ 2 × f_max = 2 × ${fmt(fmax)} = ${fmt(2 * fmax, 2)}kHz"
                    )
                },
                generator("dsp_ni2") { r ->
                    val n = adcBits.pick(r)
                    val sqnr = DB_PER_BIT * n + DB_OFFSET
                    Numeric(
                        text = "What is the SQNR of an ideal $n-bit ADC, in dB? Round to 1 decimal place.",
                        answer = sqnr, unit = "dB", decimals = 1,
                        explanation = "SQNR = 6.02 × N + 1.76 = 6.02 × $n + 1.76 = ${fmt(sqnr, 1)}dB"
                    )
                },
                generator("dsp_ni3") { r ->
                    val fs = sampleRates.pick(r)
                    val n = fftSizes.pick(r)
                    val res = fs / n
                    Numeric(
                        text = "A $n-point FFT is computed on a signal sampled at ${fmt(fs)}Hz. What is the frequency resolution, " +
                            "in Hz? Round to 2 decimal places.",
                        answer = res, unit = "Hz",
                        explanation = "Bin spacing = f_s/N = ${fmt(fs)}/$n = ${fmt(res, 2)}Hz"
                    )
                },
                generator("dsp_ni4") { r ->
                    val n = levelBits.pick(r)
                    val levels = 1 shl n
                    Numeric(
                        text = "How many quantization levels does ${article(n.toDouble())} $n-bit ADC have?",
                        answer = levels.toDouble(), unit = "levels", exact = true,
                        explanation = "Levels = 2^N = 2^$n = $levels levels"
                    )
                },
                generator("dsp_ni5") { r ->
                    val fs = aliasRates.pick(r)
                    val f = fs * aliasFractions.pick(r)
                    val alias = fs - f
                    Numeric(
                        text = "${articleCap(f)} ${fmt(f)}Hz tone is sampled at ${fmt(fs)}Hz with no anti-aliasing filter. At what frequency " +
                            "does it appear in the sampled signal, in Hz?",
                        answer = alias, unit = "Hz",
                        explanation = "${fmt(f)}Hz is above f_s/2 = ${fmt(fs / 2)}Hz, so it folds back to f_s − f = " +
                            "${fmt(fs)} − ${fmt(f)} = ${fmt(alias)}Hz"
                    )
                },
                generator("dsp_ni6") { r ->
                    val vref = referenceVoltages.pick(r)
                    val n = lsbBits.pick(r)
                    val mv = vref / (1 shl n) * 1000
                    Numeric(
                        text = "${articleCap(n.toDouble())} $n-bit ADC has a ${fmt(vref)}V reference. What voltage does one LSB " +
                            "step represent, in mV? Round to 3 decimal places.",
                        answer = mv, unit = "mV", decimals = 3,
                        explanation = "LSB = V_ref/2^N = ${fmt(vref)}/${1 shl n} = ${fmt(mv, 3)}mV"
                    )
                }
            )
        )
    )
}
