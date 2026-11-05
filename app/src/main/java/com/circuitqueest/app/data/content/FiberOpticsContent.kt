package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.sqrt

object FiberOpticsContent {
    private val attenuations = listOf(0.2, 0.25, 0.35, 0.5)
    private val linkLengths = listOf(10.0, 20.0, 50.0, 80.0)
    private val channelCounts = listOf(40.0, 80.0, 96.0)
    private val channelRates = listOf(10.0, 100.0, 400.0)
    private val responsivities = listOf(0.5, 0.8, 0.9, 1.0)
    private val opticalPowers = listOf(0.01, 0.1, 1.0)
    private val launchPowers = listOf(0.0, 3.0, 5.0)
    private val budgetAttenuations = listOf(0.2, 0.25)
    private val budgetLengths = listOf(20.0, 40.0, 80.0)
    private val sensitivities = listOf(-28.0, -24.0, -20.0)
    private val coreIndices = listOf(1.46, 1.47, 1.48)
    private val indexSteps = listOf(0.005, 0.01, 0.02)
    private val lossBudgets = listOf(10.0, 20.0, 30.0)

    val topic = Topic(
        id = "fiber_optics",
        title = "Fiber Optics & Photonics",
        subtitle = "Optical fiber, lasers, and photonic communication",
        icon = "\uD83D\uDD2E",
        order = 32,
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
            ),
            generators = listOf(
                generator("fo_ni1") { r ->
                    val a = attenuations.pick(r)
                    val l = linkLengths.pick(r)
                    Numeric(
                        text = "A fiber link is ${fmt(l)}km long with attenuation ${fmt(a)}dB/km. What is the total fiber loss, in dB?",
                        answer = a * l, unit = "dB",
                        explanation = "Loss = α × L = ${fmt(a)} × ${fmt(l)} = ${fmt(a * l)}dB"
                    )
                },
                generator("fo_ni2") { r ->
                    val n = channelCounts.pick(r)
                    val g = channelRates.pick(r)
                    val tbps = n * g / 1000
                    Numeric(
                        text = "A DWDM system carries ${fmt(n)} wavelengths at ${fmt(g)}Gbps each. What is the total capacity, in " +
                            "Tbps? Round to 2 decimal places.",
                        answer = tbps, unit = "Tbps",
                        explanation = "Total = ${fmt(n)} × ${fmt(g)}Gbps = ${fmt(n * g)}Gbps = ${fmt(tbps, 2)}Tbps"
                    )
                },
                generator("fo_ni3") { r ->
                    val resp = responsivities.pick(r)
                    val p = opticalPowers.pick(r)
                    Numeric(
                        text = "A PIN photodiode with responsivity ${fmt(resp)}A/W receives ${fmt(p)}mW. What photocurrent does it " +
                            "produce, in mA? Round to 3 decimal places.",
                        answer = resp * p, unit = "mA", decimals = 3,
                        explanation = "I = R × P = ${fmt(resp)}A/W × ${fmt(p)}mW = ${fmt(resp * p, 3)}mA"
                    )
                },
                generator("fo_ni4") { r ->
                    val ptx = launchPowers.pick(r)
                    val a = budgetAttenuations.pick(r)
                    val l = budgetLengths.pick(r)
                    val sens = sensitivities.pick(r)
                    val prx = ptx - a * l
                    val margin = prx - sens
                    Numeric(
                        text = "A link launches ${fmt(ptx)}dBm into ${fmt(l)}km of fiber at ${fmt(a)}dB/km, and the receiver needs " +
                            "${fmt(sens)}dBm. What is the link margin, in dB?",
                        answer = margin, unit = "dB",
                        explanation = "Received = ${fmt(ptx)} − ${fmt(a)} × ${fmt(l)} = ${fmt(prx)}dBm. Margin = ${fmt(prx)} − " +
                            "(${fmt(sens)}) = ${fmt(margin)}dB"
                    )
                },
                generator("fo_ni5") { r ->
                    val n1 = coreIndices.pick(r)
                    val n2 = n1 - indexSteps.pick(r)
                    val na = sqrt(n1 * n1 - n2 * n2)
                    Numeric(
                        text = "A fiber's core index is ${fmt(n1)} and its cladding index is ${fmt(n2)}. What is its numerical " +
                            "aperture? Round to 3 decimal places.",
                        answer = na, unit = "", decimals = 3,
                        explanation = "NA = √(n₁² − n₂²) = √(${fmt(n1)}² − ${fmt(n2)}²) = ${fmt(na, 3)}"
                    )
                },
                generator("fo_ni6") { r ->
                    val budget = lossBudgets.pick(r)
                    val a = attenuations.pick(r)
                    val km = budget / a
                    Numeric(
                        text = "A link has a ${fmt(budget)}dB loss budget for the fiber, which has ${fmt(a)}dB/km attenuation. What " +
                            "is the longest span, in km? Round to 1 decimal place.",
                        answer = km, unit = "km", decimals = 1,
                        explanation = "Length = budget/attenuation = ${fmt(budget)}/${fmt(a)} = ${fmt(km, 1)}km"
                    )
                }
            )
        )
    )
}
