package com.circuitqueest.app.data.content

object FiltersContent {
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
                Question.NumericInput(
                    id = "flt_ni1",
                    questionText = "What is the cutoff frequency (in Hz) of an RC low-pass filter with R = 10k\u03A9 and C = 100nF? Round to the nearest whole number.",
                    correctAnswer = 159.0,
                    tolerance = 2.0,
                    unit = "Hz",
                    explanation = "f_c = 1/(2\u03C0RC) = 1/(2\u03C0 \u00D7 10000 \u00D7 100\u00D710\u207B\u2079) = 1/(6.283 \u00D7 10\u207B\u00B3) \u2248 159Hz"
                ),
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
                Question.NumericInput(
                    id = "flt_ni2",
                    questionText = "A first-order low-pass filter has f_c = 1kHz. What is the attenuation in dB at 10kHz (one decade above cutoff)?",
                    correctAnswer = 20.0,
                    tolerance = 1.0,
                    unit = "dB",
                    explanation = "First-order roll-off is \u221220dB/decade. One decade above f_c: attenuation = 20dB."
                ),
                Question.NumericInput(
                    id = "flt_ni3",
                    questionText = "A band-pass filter has a center frequency of 1MHz and Q = 50. What is the bandwidth in kHz?",
                    correctAnswer = 20.0,
                    tolerance = 0.5,
                    unit = "kHz",
                    explanation = "BW = f\u2080/Q = 1,000,000/50 = 20,000Hz = 20kHz"
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
                Question.NumericInput(
                    id = "flt_ni4",
                    questionText = "What is the roll-off rate (in dB/decade) of a third-order filter?",
                    correctAnswer = 60.0,
                    tolerance = 1.0,
                    unit = "dB/decade",
                    explanation = "Roll-off = \u221220n dB/decade, where n is the filter order. Third-order: 20 \u00D7 3 = 60dB/decade."
                )
            )
        )
    )
}
