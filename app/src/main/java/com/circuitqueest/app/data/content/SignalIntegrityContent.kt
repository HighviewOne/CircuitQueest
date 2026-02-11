package com.circuitqueest.app.data.content

object SignalIntegrityContent {
    val topic = Topic(
        id = "signal_integrity",
        title = "Signal Integrity & EMC",
        subtitle = "Crosstalk, noise, and electromagnetic compatibility",
        icon = "\uD83D\uDEE1\uFE0F",
        order = 28,
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
                Question.NumericInput(
                    id = "si_ni1",
                    questionText = "A package pin has 2nH inductance. If 500mA switches in 1ns, what is the ground bounce voltage in Volts?",
                    correctAnswer = 1.0,
                    tolerance = 0.05,
                    unit = "V",
                    explanation = "V = L \u00D7 dI/dt = 2\u00D710\u207B\u2079 \u00D7 (0.5/10\u207B\u2079) = 2\u00D710\u207B\u2079 \u00D7 5\u00D710\u2078 = 1.0V"
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
                Question.NumericInput(
                    id = "si_ni2",
                    questionText = "A shield provides 40dB of attenuation. What percentage of the electromagnetic energy does it block?",
                    correctAnswer = 99.0,
                    tolerance = 0.5,
                    unit = "%",
                    explanation = "40dB = 10^(40/20) = 100\u00D7 voltage reduction. Power reduction = 10^(40/10) = 10,000\u00D7. That's 99.99% power blocked (or 99% field reduction)."
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
                ),
                Question.NumericInput(
                    id = "si_ni3",
                    questionText = "A signal with 1ns rise time travels at 15cm/ns on a PCB. What is the critical trace length (in cm) above which transmission line effects matter? (Use \u03BB/10 rule)",
                    correctAnswer = 1.5,
                    tolerance = 0.1,
                    unit = "cm",
                    explanation = "In 1ns, the signal travels 15cm. This is effectively \u03BB. Critical length = \u03BB/10 = 15/10 = 1.5cm."
                )
            )
        )
    )
}
