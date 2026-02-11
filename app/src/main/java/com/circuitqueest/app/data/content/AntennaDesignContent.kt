package com.circuitqueest.app.data.content

object AntennaDesignContent {
    val topic = Topic(
        id = "antenna_design",
        title = "Antenna Design",
        subtitle = "Radiation patterns, gain, and antenna types",
        icon = "\uD83D\uDCE1",
        order = 26,
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
                Question.NumericInput(
                    id = "ant_ni1",
                    questionText = "What is the wavelength in centimeters of a 5GHz Wi-Fi signal? (Use c = 3\u00D710\u2078 m/s)",
                    correctAnswer = 6.0,
                    tolerance = 0.1,
                    unit = "cm",
                    explanation = "\u03BB = c/f = 3\u00D710\u2078 / 5\u00D710\u2079 = 0.06m = 6cm"
                ),
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
                Question.NumericInput(
                    id = "ant_ni2",
                    questionText = "A half-wave dipole at 300MHz has a length of how many centimeters?",
                    correctAnswer = 50.0,
                    tolerance = 1.0,
                    unit = "cm",
                    explanation = "\u03BB = c/f = 3\u00D710\u2078/300\u00D710\u2076 = 1m. Half-wave = \u03BB/2 = 0.5m = 50cm."
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
                Question.NumericInput(
                    id = "ant_ni3",
                    questionText = "A transmitter outputs 1W (30 dBm) through an antenna with 10 dBi gain. What is the EIRP in dBm?",
                    correctAnswer = 40.0,
                    tolerance = 0.5,
                    unit = "dBm",
                    explanation = "EIRP = P_tx + G_tx = 30 dBm + 10 dBi = 40 dBm"
                ),
                Question.NumericInput(
                    id = "ant_ni4",
                    questionText = "A quarter-wave monopole antenna for 1GHz is how many centimeters long? (Use c = 3\u00D710\u2078 m/s)",
                    correctAnswer = 7.5,
                    tolerance = 0.2,
                    unit = "cm",
                    explanation = "\u03BB = c/f = 3\u00D710\u2078/10\u2079 = 0.3m = 30cm. Quarter-wave = 30/4 = 7.5cm."
                )
            )
        )
    )
}
