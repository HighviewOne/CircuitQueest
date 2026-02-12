package com.circuitqueest.app.data.content

object FiberOpticsContent {
    val topic = Topic(
        id = "fiber_optics",
        title = "Fiber Optics & Photonics",
        subtitle = "Optical fiber, lasers, and photonic communication",
        icon = "\uD83D\uDD2E",
        order = 35,
        lesson = Lesson(
            title = "Fiber Optics & Photonics: Light as Information",
            sections = listOf(
                LessonSection(
                    heading = "Why Light?",
                    content = "Optical fiber carries data as pulses of light instead of electrical signals. Advantages over copper:\n\n\u2022 Enormous bandwidth: terabits per second over a single fiber\n\u2022 Low loss: ~0.2 dB/km at 1550nm (vs. copper: ~20 dB/km at GHz)\n\u2022 Immune to electromagnetic interference (EMI)\n\u2022 Lightweight and small diameter\n\u2022 Secure: very difficult to tap without detection\n\nFiber optics form the backbone of the internet, carrying >99% of intercontinental data traffic through undersea cables.",
                    keyPoint = "Fiber: massive bandwidth, very low loss, immune to EMI"
                ),
                LessonSection(
                    heading = "Optical Fiber Structure",
                    content = "An optical fiber has three layers:\n\n\u2022 Core: glass (silica) center where light travels (~8\u00B5m for single-mode, ~50\u00B5m for multimode)\n\u2022 Cladding: glass layer with slightly lower refractive index, keeping light confined by total internal reflection\n\u2022 Coating: protective polymer jacket\n\nTwo main fiber types:\n\u2022 Single-mode (SMF): tiny core (~8\u00B5m), only one light path. Used for long distances (km\u2013thousands of km). Lower dispersion.\n\u2022 Multimode (MMF): larger core (~50\u00B5m), multiple light paths. Used for short distances (<500m). Cheaper transceivers.",
                    formula = "Total internal reflection: n_core > n_cladding\nNA = \u221A(n\u00B2_core \u2212 n\u00B2_clad)",
                    keyPoint = "Single-mode: long distance, small core; Multimode: short distance, large core"
                ),
                LessonSection(
                    heading = "Light Sources: LEDs and Lasers",
                    content = "Fiber optic transmitters convert electrical signals to light:\n\n\u2022 LED: broad spectrum, low cost, used with multimode fiber for short links. Bandwidth limited to ~hundreds of MHz.\n\n\u2022 Laser diode: narrow spectrum, coherent light, used with single-mode fiber. Types include:\n  - Fabry-Perot (FP): simpler, wider linewidth\n  - Distributed Feedback (DFB): single wavelength, used in telecom\n  - VCSEL: low cost, used in short-range multimode (data centers)\n\nLasers enable higher data rates and longer distances due to narrow spectral width (less dispersion).",
                    keyPoint = "Lasers for long-distance/high-speed; LEDs/VCSELs for short-range/low-cost"
                ),
                LessonSection(
                    heading = "Photodetectors",
                    content = "At the receive end, photodetectors convert light back to electrical signals:\n\n\u2022 PIN photodiode: simple, fast, moderate sensitivity. Used in most receivers.\n\u2022 Avalanche photodiode (APD): internal gain through avalanche multiplication. More sensitive but noisier and needs higher bias voltage.\n\nKey parameters:\n\u2022 Responsivity (A/W): current produced per watt of light (~0.5\u20131.0 A/W for PIN at 1550nm)\n\u2022 Bandwidth: how fast it can respond (GHz range)\n\u2022 Dark current: leakage current with no light (should be minimal)\n\nThe receiver also includes a transimpedance amplifier (TIA) to convert the tiny photocurrent to a usable voltage.",
                    formula = "R = I_photo / P_optical (A/W)\nAPD gain: M = 10\u2013100 typical",
                    keyPoint = "PIN: simple and fast; APD: more sensitive with internal gain"
                ),
                LessonSection(
                    heading = "Fiber Loss and Dispersion",
                    content = "Two factors limit fiber transmission distance:\n\n1. Attenuation (loss): light intensity decreases along the fiber.\n\u2022 0.35 dB/km at 1310nm\n\u2022 0.20 dB/km at 1550nm (the telecom sweet spot)\n\u2022 Causes: Rayleigh scattering, absorption, bend loss\n\n2. Dispersion: light pulses spread out over distance, causing inter-symbol interference.\n\u2022 Chromatic dispersion: different wavelengths travel at different speeds\n\u2022 Modal dispersion: different paths in multimode fiber (not present in single-mode)\n\u2022 Polarization mode dispersion (PMD): slight birefringence in the fiber\n\nOptical amplifiers (EDFA) compensate for loss; dispersion-compensating fiber corrects for dispersion.",
                    formula = "P_out = P_in \u00D7 10^(\u2212\u03B1L/10)\n\u03B1 = 0.2 dB/km at 1550nm",
                    keyPoint = "Loss (~0.2 dB/km at 1550nm) and dispersion limit distance; EDFA amplifies"
                ),
                LessonSection(
                    heading = "Wavelength Division Multiplexing (WDM)",
                    content = "WDM sends multiple wavelengths (colors) of light through a single fiber simultaneously, each carrying independent data:\n\n\u2022 Coarse WDM (CWDM): ~18 wavelengths, 20nm spacing. Lower cost.\n\u2022 Dense WDM (DWDM): 40\u201396+ wavelengths, 0.8nm spacing. Used in long-haul telecom.\n\nA single fiber pair using DWDM can carry tens of terabits per second!\n\nComponents: multiplexers combine wavelengths at the transmit end; demultiplexers separate them at the receive end. Optical add-drop multiplexers (OADMs) allow inserting/extracting individual wavelengths at intermediate nodes.",
                    keyPoint = "DWDM: 96+ wavelengths on one fiber = tens of Tbps capacity"
                )
            )
        ),
        quiz = Quiz(
            title = "Fiber Optics & Photonics Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "fo_mc1",
                    questionText = "Single-mode fiber is preferred for long-distance communication because:",
                    options = listOf(
                        "It has a larger core for more light",
                        "It eliminates modal dispersion and has lower loss",
                        "It uses LEDs instead of lasers",
                        "It is cheaper than multimode"
                    ),
                    correctIndex = 1,
                    explanation = "Single-mode fiber's tiny core (~8\u00B5m) supports only one propagation mode, eliminating modal dispersion. Combined with low attenuation at 1550nm, it enables transmission over thousands of km."
                ),
                Question.NumericInput(
                    id = "fo_ni1",
                    questionText = "A fiber link is 50 km long with attenuation of 0.2 dB/km. What is the total loss in dB?",
                    correctAnswer = 10.0,
                    tolerance = 0.1,
                    unit = "dB",
                    explanation = "Total loss = \u03B1 \u00D7 L = 0.2 dB/km \u00D7 50 km = 10 dB"
                ),
                Question.MultipleChoice(
                    id = "fo_mc2",
                    questionText = "Which photodetector has internal gain through avalanche multiplication?",
                    options = listOf(
                        "PIN photodiode",
                        "Avalanche photodiode (APD)",
                        "Phototransistor",
                        "Solar cell"
                    ),
                    correctIndex = 1,
                    explanation = "APDs use high reverse bias to create avalanche multiplication, providing internal current gain of 10\u2013100\u00D7, making them more sensitive than PIN photodiodes."
                ),
                Question.NumericInput(
                    id = "fo_ni2",
                    questionText = "A DWDM system uses 80 wavelengths, each carrying 100 Gbps. What is the total capacity in Tbps?",
                    correctAnswer = 8.0,
                    tolerance = 0.1,
                    unit = "Tbps",
                    explanation = "Total = 80 wavelengths \u00D7 100 Gbps = 8000 Gbps = 8 Tbps"
                ),
                Question.MultipleChoice(
                    id = "fo_mc3",
                    questionText = "Light is confined in the fiber core by:",
                    options = listOf(
                        "Magnetic fields",
                        "Total internal reflection at the core-cladding boundary",
                        "Absorption in the cladding",
                        "The protective coating"
                    ),
                    correctIndex = 1,
                    explanation = "The core has a higher refractive index than the cladding, causing total internal reflection at the boundary, which keeps light confined to the core."
                ),
                Question.NumericInput(
                    id = "fo_ni3",
                    questionText = "A PIN photodiode has a responsivity of 0.8 A/W. If 1 mW of optical power hits it, what photocurrent is generated in mA?",
                    correctAnswer = 0.8,
                    tolerance = 0.05,
                    unit = "mA",
                    explanation = "I = R \u00D7 P = 0.8 A/W \u00D7 0.001 W = 0.0008 A = 0.8 mA"
                ),
                Question.MultipleChoice(
                    id = "fo_mc4",
                    questionText = "The lowest-loss wavelength window for silica fiber is:",
                    options = listOf(
                        "850 nm",
                        "1310 nm",
                        "1550 nm",
                        "1625 nm"
                    ),
                    correctIndex = 2,
                    explanation = "Silica fiber has minimum attenuation (~0.2 dB/km) at 1550 nm, making it the preferred wavelength for long-haul telecommunications."
                )
            )
        )
    )
}
