package com.circuitqueest.app.data.content

object DspContent {
    val topic = Topic(
        id = "dsp",
        title = "Digital Signal Processing",
        subtitle = "Sampling, FFT, and digital filters",
        icon = "\uD83D\uDCC8",
        order = 34,
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
                Question.NumericInput(
                    id = "dsp_ni1",
                    questionText = "A signal contains frequencies up to 8 kHz. What is the minimum sample rate in kHz to avoid aliasing?",
                    correctAnswer = 16.0,
                    tolerance = 0.1,
                    unit = "kHz",
                    explanation = "Nyquist: f_s \u2265 2 \u00D7 f_max = 2 \u00D7 8 kHz = 16 kHz minimum."
                ),
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
                Question.NumericInput(
                    id = "dsp_ni2",
                    questionText = "What is the SQNR (in dB) of an ideal 12-bit ADC? Round to one decimal.",
                    correctAnswer = 74.0,
                    tolerance = 0.5,
                    unit = "dB",
                    explanation = "SQNR = 6.02\u00D7N + 1.76 = 6.02\u00D712 + 1.76 = 72.24 + 1.76 = 74.0 dB"
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
                Question.NumericInput(
                    id = "dsp_ni3",
                    questionText = "A 1024-point FFT is computed on a signal sampled at 48 kHz. What is the frequency resolution in Hz? Round to one decimal.",
                    correctAnswer = 46.9,
                    tolerance = 0.5,
                    unit = "Hz",
                    explanation = "Frequency resolution = f_s / N = 48000 / 1024 = 46.875 Hz \u2248 46.9 Hz"
                ),
                Question.NumericInput(
                    id = "dsp_ni4",
                    questionText = "How many quantization levels does a 10-bit ADC have?",
                    correctAnswer = 1024.0,
                    tolerance = 1.0,
                    unit = "levels",
                    explanation = "Levels = 2^N = 2^10 = 1024 levels."
                )
            )
        )
    )
}
