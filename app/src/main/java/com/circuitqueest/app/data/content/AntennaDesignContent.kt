package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.log10

object AntennaDesignContent {
    private const val FSPL_CONSTANT = 32.44
    private const val DB_AMPLITUDE = 20
    private const val C_CM_GHZ = 30.0
    private const val C_M_MHZ = 300.0
    private const val HALF_WAVE_CM_MHZ = 15_000.0
    private const val QUARTER_WAVE_CM_GHZ = 7.5
    private val wifiFrequencies = listOf(0.9, 2.4, 5.0, 5.8, 10.0)
    private val dipoleFrequencies = listOf(100.0, 146.0, 300.0, 433.0, 900.0)
    private val transmitPowers = listOf(20.0, 27.0, 30.0, 37.0)
    private val antennaGains = listOf(2.15, 6.0, 10.0, 15.0)
    private val monopoleFrequencies = listOf(0.433, 0.9, 1.0, 2.4)
    private val powersWatts = listOf(0.001, 0.01, 0.1, 1.0, 10.0)
    private val linkDistances = listOf(1.0, 10.0, 100.0)
    private val linkFrequencies = listOf(900.0, 2400.0, 5800.0)

    val topic = Topic(
        id = "antenna_design",
        title = "Antenna Design",
        subtitle = "Radiation patterns, gain, and antenna types",
        icon = "\uD83D\uDCE1",
        order = 30,
        lesson = Lesson(
            title = "Antenna Design: Reaching the Airwaves",
            sections = listOf(
                LessonSection(
                    heading = "What is an Antenna?",
                    content = "An antenna is a transducer that converts electrical signals into electromagnetic waves (transmitting) or electromagnetic waves into electrical signals (receiving).\n\nAn antenna's properties are the same whether transmitting or receiving (reciprocity). A good transmitting antenna is equally good for receiving at the same frequency.\n\nThe simplest antenna is a wire whose length is related to the signal wavelength. At 2.4GHz (Wi-Fi), the wavelength is about 12.5cm, so antennas can be very compact.",
                    formula = "\u03BB = c / f\n\u03BB(2.4GHz) \u2248 12.5cm",
                    keyPoint = "Antenna size is related to wavelength; reciprocity means TX = RX behavior"
                ),
                LessonSection(
                    heading = "Radiation Pattern",
                    content = "The radiation pattern shows how an antenna radiates energy in different directions. It is plotted as a 3D shape or 2D cross-sections.\n\n\u2022 Isotropic: radiates equally in all directions (theoretical reference)\n\u2022 Omnidirectional: uniform in one plane, like a donut (e.g., vertical dipole)\n\u2022 Directional: focuses energy in a specific direction (e.g., dish, Yagi)\n\nThe main lobe is the direction of maximum radiation. Side lobes and back lobes are unwanted radiation in other directions.",
                    keyPoint = "Omnidirectional = coverage everywhere; Directional = focused beam"
                ),
                LessonSection(
                    heading = "Gain and Directivity",
                    content = "Directivity measures how focused an antenna's radiation pattern is compared to an isotropic antenna. Gain includes directivity plus efficiency losses.\n\nGain is measured in dBi (decibels relative to isotropic):\n\u2022 Dipole: 2.15 dBi\n\u2022 Typical patch antenna: 6\u20139 dBi\n\u2022 Dish antenna: 20\u201340 dBi\n\nHigher gain means the antenna concentrates energy in a narrower beam. Gain does not create extra power \u2014 it redirects it.",
                    formula = "G(dBi) = 10 log\u2081\u2080(G_linear)\nEIRP = P_tx \u00D7 G_tx",
                    keyPoint = "Higher gain = narrower beam; gain redirects power, doesn't create it"
                ),
                LessonSection(
                    heading = "Common Antenna Types",
                    content = "Different applications require different antennas:\n\n\u2022 Dipole (\u03BB/2): simple, omnidirectional, 2.15 dBi. Used for FM radio, base stations.\n\u2022 Monopole (\u03BB/4): half a dipole over a ground plane. Used for car antennas, walkie-talkies.\n\u2022 Patch/Microstrip: flat, low-profile, directional. Used in phones, GPS, Wi-Fi.\n\u2022 Yagi-Uda: directional array of dipole elements. Used for TV reception, amateur radio.\n\u2022 Parabolic dish: very high gain, pencil beam. Used for satellite links, radio astronomy.",
                    keyPoint = "Dipole for coverage, patch for compact devices, dish for long-range links"
                ),
                LessonSection(
                    heading = "Antenna Impedance and Matching",
                    content = "An antenna has a feed impedance that must match the transmission line (typically 50\u03A9 or 75\u03A9) for maximum power transfer.\n\nA half-wave dipole has approximately 73\u03A9 impedance. A quarter-wave monopole over a perfect ground plane has about 36.5\u03A9.\n\nMismatch causes reflected power, reducing efficiency. The return loss (S11) measures matching quality:\n\u2022 S11 < \u221210 dB: good match (less than 10% power reflected)\n\u2022 S11 < \u221220 dB: excellent match (less than 1% reflected)",
                    formula = "S11(dB) = 20 log\u2081\u2080|\u0393|\nReturn loss = \u2212S11",
                    keyPoint = "S11 < \u221210 dB means good matching; < \u221220 dB is excellent"
                ),
                LessonSection(
                    heading = "Link Budget",
                    content = "A link budget calculates whether a wireless link will work by adding gains and subtracting losses from transmitter to receiver:\n\nP_rx = P_tx + G_tx \u2212 L_path + G_rx \u2212 L_other\n\nFree-space path loss increases with distance and frequency:\nL_path = 20log(d) + 20log(f) + 32.44 (d in km, f in MHz, L in dB)\n\nThe received power must exceed the receiver sensitivity for reliable communication. The margin above sensitivity determines link reliability.",
                    formula = "P_rx = P_tx + G_tx + G_rx \u2212 L_path\nL_path = 20log(d) + 20log(f) + 32.44",
                    keyPoint = "Link budget: sum all gains, subtract all losses; P_rx must exceed sensitivity"
                )
            )
        ),
        quiz = Quiz(
            title = "Antenna Design Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ant_mc1",
                    questionText = "A half-wave dipole antenna has a gain of approximately:",
                    options = listOf(
                        "0 dBi",
                        "2.15 dBi",
                        "6 dBi",
                        "10 dBi"
                    ),
                    correctIndex = 1,
                    explanation = "A half-wave dipole has a gain of 2.15 dBi, which is the standard reference for many antenna measurements."
                ),
                Question.MultipleChoice(
                    id = "ant_mc2",
                    questionText = "Which antenna type is best suited for satellite communication?",
                    options = listOf(
                        "Dipole",
                        "Monopole",
                        "Parabolic dish",
                        "Loop antenna"
                    ),
                    correctIndex = 2,
                    explanation = "Parabolic dish antennas provide very high gain (20\u201340 dBi) with narrow beams, ideal for the long distances in satellite communication."
                ),
                Question.MultipleChoice(
                    id = "ant_mc3",
                    questionText = "What does S11 < \u221210 dB indicate?",
                    options = listOf(
                        "The antenna has very high gain",
                        "Less than 10% of power is reflected (good match)",
                        "The antenna is broken",
                        "The antenna has a narrow bandwidth"
                    ),
                    correctIndex = 1,
                    explanation = "S11 < \u221210 dB means the reflection coefficient is small enough that less than 10% of power is reflected back, indicating a good impedance match."
                ),
                Question.MultipleChoice(
                    id = "ant_mc4",
                    questionText = "An antenna gain quoted in dBi is measured relative to:",
                    options = listOf(
                        "A half-wave dipole",
                        "An isotropic radiator that radiates equally in all directions",
                        "1 milliwatt",
                        "The transmitter's power"
                    ),
                    correctIndex = 1,
                    explanation = "dBi compares to an ideal isotropic antenna. dBd compares to a half-wave dipole, which itself has 2.15dBi, so dBi = dBd + 2.15."
                )
            ),
            generators = listOf(
                generator("ant_ni1") { r ->
                    val f = wifiFrequencies.pick(r)
                    val cm = C_CM_GHZ / f
                    Numeric(
                        text = "What is the free-space wavelength of a ${fmt(f)}GHz signal, in centimetres? Use c = 3×10⁸m/s and " +
                            "round to 2 decimal places.",
                        answer = cm, unit = "cm",
                        explanation = "λ = c/f = 3×10⁸/(${fmt(f)}×10⁹) m = ${fmt(cm, 2)}cm"
                    )
                },
                generator("ant_ni2") { r ->
                    val f = dipoleFrequencies.pick(r)
                    val cm = HALF_WAVE_CM_MHZ / f
                    Numeric(
                        text = "How long is a half-wave dipole for ${fmt(f)}MHz, in centimetres? Use c = 3×10⁸m/s, ignore end " +
                            "effects and round to 2 decimal places.",
                        answer = cm, unit = "cm",
                        explanation = "λ = 3×10⁸/(${fmt(f)}×10⁶) = ${fmt(C_M_MHZ / f, 3)}m. Half of that is ${fmt(cm, 2)}cm"
                    )
                },
                generator("ant_ni3") { r ->
                    val p = transmitPowers.pick(r)
                    val g = antennaGains.pick(r)
                    Numeric(
                        text = "A transmitter outputs ${fmt(p)}dBm into an antenna with ${fmt(g)}dBi gain. What is the EIRP in dBm?",
                        answer = p + g, unit = "dBm",
                        explanation = "In dB, gains add: EIRP = ${fmt(p)}dBm + ${fmt(g)}dBi = ${fmt(p + g)}dBm"
                    )
                },
                generator("ant_ni4") { r ->
                    val f = monopoleFrequencies.pick(r)
                    val cm = QUARTER_WAVE_CM_GHZ / f
                    Numeric(
                        text = "How long is a quarter-wave monopole for ${fmt(f)}GHz, in centimetres? Use c = 3×10⁸m/s and " +
                            "round to 2 decimal places.",
                        answer = cm, unit = "cm",
                        explanation = "λ = 30/${fmt(f)} = ${fmt(C_CM_GHZ / f, 2)}cm, and a quarter of that is ${fmt(cm, 2)}cm"
                    )
                },
                generator("ant_ni5") { r ->
                    val w = powersWatts.pick(r)
                    val dbm = 10 * log10(w * 1000)
                    Numeric(
                        text = "Express ${fmt(w)}W in dBm.",
                        answer = dbm, unit = "dBm",
                        explanation = "dBm = 10·log₁₀(P/1mW) = 10·log₁₀(${fmt(w * 1000)}) = ${fmt(dbm)}dBm"
                    )
                },
                generator("ant_ni6") { r ->
                    val d = linkDistances.pick(r)
                    val f = linkFrequencies.pick(r)
                    val fspl = DB_AMPLITUDE * log10(d) + DB_AMPLITUDE * log10(f) + FSPL_CONSTANT
                    Numeric(
                        text = "What is the free-space path loss over ${fmt(d)}km at ${fmt(f)}MHz, in dB? Use FSPL = 20·log₁₀(d_km) + " +
                            "20·log₁₀(f_MHz) + 32.44 and round to 1 decimal place.",
                        answer = fspl, unit = "dB", decimals = 1,
                        explanation = "FSPL = 20·log₁₀(${fmt(d)}) + 20·log₁₀(${fmt(f)}) + 32.44 = ${fmt(fspl, 1)}dB"
                    )
                }
            )
        )
    )
}
