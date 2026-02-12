package com.circuitqueest.app.data.content

object BiomedicalElectronicsContent {
    val topic = Topic(
        id = "biomedical_electronics",
        title = "Biomedical Electronics",
        subtitle = "ECG, EEG, pacemakers, and patient safety",
        icon = "\uD83E\uDE7A",
        order = 40,
        lesson = Lesson(
            title = "Biomedical Electronics: Where Circuits Save Lives",
            sections = listOf(
                LessonSection(
                    heading = "Biopotentials and Biosignals",
                    content = "The human body generates electrical signals from nerve and muscle activity. These biopotentials are the foundation of medical diagnostics:\n\n\u2022 ECG (Electrocardiogram): heart electrical activity, 0.5\u20134 mV, 0.05\u2013100 Hz\n\u2022 EEG (Electroencephalogram): brain waves, 5\u2013300 \u00B5V, 0.5\u201345 Hz\n\u2022 EMG (Electromyogram): muscle activity, 0.1\u20135 mV, 20\u2013500 Hz\n\u2022 EOG (Electrooculogram): eye movement, 50\u2013500 \u00B5V, DC\u201310 Hz\n\nThese signals are tiny (microvolts to millivolts), embedded in noise, and measured through imperfect electrode-skin interfaces. Extracting them requires specialized instrumentation amplifiers with very high CMRR.",
                    formula = "ECG: 0.5\u20134 mV, 0.05\u2013100 Hz\nEEG: 5\u2013300 \u00B5V, 0.5\u201345 Hz",
                    keyPoint = "Biopotentials are \u00B5V\u2013mV signals requiring high-CMRR instrumentation amps"
                ),
                LessonSection(
                    heading = "The Instrumentation Amplifier",
                    content = "The instrumentation amplifier (INA) is the workhorse of biomedical front-ends. It amplifies the difference between two electrodes while rejecting common-mode interference (power line hum, motion artifacts).\n\nA classic INA uses 3 op-amps:\n\u2022 Two input buffers provide high impedance (>10 G\u03A9) to avoid loading the body\n\u2022 One difference amplifier subtracts common-mode signals\n\u2022 Gain set by a single resistor: G = 1 + 2R/R_G\n\nKey specs for biomedical:\n\u2022 CMRR > 100 dB (to reject 50/60 Hz mains)\n\u2022 Input impedance > 1 G\u03A9\n\u2022 Low noise: <1 \u00B5V_pp in the signal band\n\u2022 Low input bias current: <1 nA (to handle high electrode impedance)",
                    formula = "G = 1 + 2R/R_G\nCMRR > 100 dB typical",
                    keyPoint = "INA: 3 op-amps, single-resistor gain, CMRR >100 dB for mains rejection"
                ),
                LessonSection(
                    heading = "ECG System Design",
                    content = "A complete ECG acquisition system:\n\n1. Electrodes: Ag/AgCl gel electrodes on the skin (standard 12-lead placement)\n2. Lead-off detection: detects if an electrode falls off\n3. Protection: defibrillator protection circuits (withstand >5 kV pulses)\n4. INA front-end: gain of 100\u20131000, CMRR >100 dB\n5. Right-leg drive (RLD): feeds inverted common-mode signal back to the patient to cancel mains interference\n6. Bandpass filter: 0.05\u2013150 Hz (diagnostic) or 0.5\u201340 Hz (monitoring)\n7. ADC: 12\u201324 bit, 250\u20131000 SPS\n8. Digital processing: baseline wander removal, QRS detection, arrhythmia analysis\n\nThe right-leg drive circuit is a key innovation: it actively reduces common-mode interference by 20\u201340 dB.",
                    keyPoint = "Right-leg drive actively cancels mains interference; defibrillator protection is mandatory"
                ),
                LessonSection(
                    heading = "Pacemakers and Implantable Devices",
                    content = "Cardiac pacemakers are implantable electronic devices that deliver electrical pulses to the heart to maintain a regular rhythm.\n\nModern pacemakers:\n\u2022 Sense the heart's natural electrical activity\n\u2022 Pace only when the natural rhythm fails (demand pacing)\n\u2022 Adjust rate based on activity (rate-responsive)\n\u2022 Run for 7\u201315 years on a lithium-iodine battery (~1.5 Ah)\n\nThe pacing pulse is typically 2.5\u20135V, 0.4\u20131.0 ms duration, drawing ~10\u201320 \u00B5A average. Ultra-low-power CMOS circuits and careful duty cycling enable the decade-plus battery life.\n\nICDs (Implantable Cardioverter-Defibrillators) can also deliver high-energy shocks (30\u201340 J) to terminate life-threatening arrhythmias.",
                    formula = "Pacing pulse: 2.5\u20135V, 0.4\u20131.0 ms\nBattery life: 7\u201315 years",
                    keyPoint = "Pacemakers: ultra-low-power sensing + pacing; 7\u201315 years on one battery"
                ),
                LessonSection(
                    heading = "Patient Safety and Electrical Standards",
                    content = "Electrical safety is paramount in medical devices because current can flow through the body to the heart:\n\n\u2022 Macroshock: current through intact skin \u2014 dangerous above ~10 mA (ventricular fibrillation at ~100 mA)\n\u2022 Microshock: current directly to the heart via catheters/pacemaker leads \u2014 dangerous at just 10\u201350 \u00B5A!\n\nSafety measures:\n\u2022 Isolation: galvanic isolation between patient and mains (isolation barrier >4 kV)\n\u2022 Leakage current limits: <10 \u00B5A for patient-connected equipment (IEC 60601)\n\u2022 Applied parts classification: Type B (body), BF (body floating), CF (cardiac floating \u2014 strictest)\n\u2022 Defibrillation protection: all patient-connected circuits must survive defibrillator discharge\n\nIEC 60601 is the international standard governing medical electrical equipment safety.",
                    formula = "Macroshock threshold: ~10 mA\nMicroshock threshold: ~10 \u00B5A\nCF leakage limit: <10 \u00B5A",
                    keyPoint = "Microshock kills at 10 \u00B5A; isolation and IEC 60601 compliance are mandatory"
                ),
                LessonSection(
                    heading = "Medical Imaging Electronics",
                    content = "Medical imaging systems rely heavily on electronics:\n\n\u2022 X-ray/CT: high-voltage generators (50\u2013150 kV), fast detector arrays, image reconstruction DSP\n\u2022 Ultrasound: phased array transducers (2\u201315 MHz piezoelectric elements), beamforming electronics, 128+ channels\n\u2022 MRI: superconducting magnets (1.5\u20137T), RF coils, gradient amplifiers (1000+ A), and extremely sensitive receivers\n\u2022 Pulse oximetry: red and infrared LEDs + photodetector measure blood oxygen via light absorption ratios\n\nPulse oximeters are a brilliant example of simple electronics saving lives: two LEDs, one photodiode, and clever signal processing measure SpO\u2082 non-invasively and continuously.",
                    keyPoint = "From simple pulse oximeters to complex MRI: electronics enable all medical imaging"
                )
            )
        ),
        quiz = Quiz(
            title = "Biomedical Electronics Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "biomed_mc1",
                    questionText = "An ECG signal has an amplitude of approximately:",
                    options = listOf(
                        "5\u2013300 \u00B5V",
                        "0.5\u20134 mV",
                        "1\u201310 V",
                        "50\u2013100 mV"
                    ),
                    correctIndex = 1,
                    explanation = "ECG signals from the heart are typically 0.5\u20134 mV measured at the skin surface, requiring amplification before processing."
                ),
                Question.MultipleChoice(
                    id = "biomed_mc2",
                    questionText = "The right-leg drive circuit in an ECG system:",
                    options = listOf(
                        "Amplifies the ECG signal",
                        "Feeds inverted common-mode signal back to the patient to reduce interference",
                        "Provides defibrillator protection",
                        "Measures leg muscle activity"
                    ),
                    correctIndex = 1,
                    explanation = "The right-leg drive senses the common-mode signal, inverts and amplifies it, and drives it back to the patient through a third electrode. This actively cancels mains interference by 20\u201340 dB."
                ),
                Question.NumericInput(
                    id = "biomed_ni1",
                    questionText = "An instrumentation amplifier has R = 25 k\u03A9 and R_G = 500 \u03A9. What is the gain? (G = 1 + 2R/R_G)",
                    correctAnswer = 101.0,
                    tolerance = 0.5,
                    unit = "",
                    explanation = "G = 1 + 2\u00D725000/500 = 1 + 100 = 101"
                ),
                Question.MultipleChoice(
                    id = "biomed_mc3",
                    questionText = "Microshock (current directly to the heart) is dangerous at:",
                    options = listOf(
                        "100 mA",
                        "10 mA",
                        "10\u201350 \u00B5A",
                        "1 A"
                    ),
                    correctIndex = 2,
                    explanation = "When current flows directly to the heart (via catheters or pacemaker leads), as little as 10\u201350 \u00B5A can cause ventricular fibrillation. This is why CF-rated isolation is critical."
                ),
                Question.NumericInput(
                    id = "biomed_ni2",
                    questionText = "A pacemaker draws 15 \u00B5A average from a 1.5 Ah battery. How many years will it last? (Assume 8760 hours/year, round to one decimal.)",
                    correctAnswer = 11.4,
                    tolerance = 0.3,
                    unit = "years",
                    explanation = "Hours = 1.5 Ah / 0.000015 A = 100,000 hours. Years = 100000 / 8760 \u2248 11.4 years"
                ),
                Question.NumericInput(
                    id = "biomed_ni3",
                    questionText = "An EEG amplifier needs CMRR of 100 dB. If the common-mode 50 Hz interference is 1V, what is the maximum interference at the output in \u00B5V?",
                    correctAnswer = 10.0,
                    tolerance = 0.5,
                    unit = "\u00B5V",
                    explanation = "100 dB = 10^5 attenuation. Output = 1V / 100000 = 10 \u00B5V"
                ),
                Question.MultipleChoice(
                    id = "biomed_mc4",
                    questionText = "A pulse oximeter measures blood oxygen saturation using:",
                    options = listOf(
                        "Ultrasound transducers",
                        "Red and infrared LEDs with a photodetector",
                        "Magnetic resonance",
                        "X-ray absorption"
                    ),
                    correctIndex = 1,
                    explanation = "Pulse oximeters shine red (~660nm) and infrared (~940nm) light through tissue. The ratio of absorption at these wavelengths indicates oxygen saturation because oxygenated and deoxygenated hemoglobin absorb differently."
                )
            )
        )
    )
}
