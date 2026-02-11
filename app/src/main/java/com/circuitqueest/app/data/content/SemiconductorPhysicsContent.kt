package com.circuitqueest.app.data.content

object SemiconductorPhysicsContent {
    val topic = Topic(
        id = "semiconductor_physics",
        title = "Semiconductor Physics",
        subtitle = "Band theory, doping, and carrier transport",
        icon = "\u2699\uFE0F",
        order = 19,
        lesson = Lesson(
            title = "Semiconductor Physics: Inside the Silicon",
            sections = listOf(
                LessonSection(
                    heading = "Conductors, Insulators, and Semiconductors",
                    content = "Materials are classified by how easily electrons flow through them:\n\n\u2022 Conductors (metals): electrons flow freely. Resistivity ~ 10\u207B\u2078 \u03A9\u00B7m\n\u2022 Insulators (glass, rubber): electrons are tightly bound. Resistivity ~ 10\u00B9\u2076 \u03A9\u00B7m\n\u2022 Semiconductors (silicon, germanium): in between, and their conductivity can be precisely controlled\n\nThis controllability is what makes semiconductors the foundation of all modern electronics.",
                    keyPoint = "Semiconductors have controllable conductivity \u2014 the basis of all electronic devices"
                ),
                LessonSection(
                    heading = "Band Theory",
                    content = "In a crystal, electron energy levels merge into continuous bands:\n\n\u2022 Valence band: the highest band filled with electrons at absolute zero\n\u2022 Conduction band: the next higher band where electrons can move freely\n\u2022 Band gap (E_g): the energy gap between them\n\nFor silicon, E_g = 1.12 eV. Electrons need at least this much energy to jump from valence to conduction band. Conductors have overlapping bands (no gap). Insulators have very large gaps (>5 eV).",
                    formula = "Silicon: E_g = 1.12 eV\nGermanium: E_g = 0.67 eV\nGaAs: E_g = 1.42 eV",
                    keyPoint = "Band gap determines whether a material is conductor, semiconductor, or insulator"
                ),
                LessonSection(
                    heading = "Intrinsic Semiconductors",
                    content = "Pure (intrinsic) silicon has 4 valence electrons and forms covalent bonds with its neighbors in a crystal lattice.\n\nAt room temperature, thermal energy frees some electrons from their bonds, creating two charge carriers:\n\u2022 Free electrons in the conduction band (negative charge)\n\u2022 Holes left behind in the valence band (positive charge)\n\nIn intrinsic silicon, the number of electrons equals the number of holes: n = p = n_i \u2248 1.5 \u00D7 10\u00B9\u2070 per cm\u00B3 at 300K.",
                    formula = "n \u00D7 p = n_i\u00B2\nn_i \u2248 1.5 \u00D7 10\u00B9\u2070 /cm\u00B3 (Si, 300K)",
                    keyPoint = "Intrinsic: equal electrons and holes; n = p = n_i"
                ),
                LessonSection(
                    heading = "Doping: N-type and P-type",
                    content = "Doping adds impurity atoms to dramatically increase conductivity:\n\n\u2022 N-type: Add atoms with 5 valence electrons (phosphorus, arsenic). Extra electron per atom \u2192 electrons are majority carriers.\n\u2022 P-type: Add atoms with 3 valence electrons (boron, gallium). Missing electron per atom creates a hole \u2192 holes are majority carriers.\n\nDoping concentrations typically range from 10\u00B9\u2075 to 10\u00B2\u2070 per cm\u00B3, vastly outnumbering the 10\u00B9\u2070 intrinsic carriers.",
                    formula = "N-type: n \u2248 N_D (donor concentration)\nP-type: p \u2248 N_A (acceptor concentration)",
                    keyPoint = "N-type: extra electrons (donor); P-type: extra holes (acceptor)"
                ),
                LessonSection(
                    heading = "Carrier Transport",
                    content = "Charge carriers move through semiconductors by two mechanisms:\n\n\u2022 Drift: movement due to an applied electric field. Drift velocity = \u03BC \u00D7 E, where \u03BC is mobility. This creates drift current: J = \u03C3E.\n\u2022 Diffusion: movement from high to low concentration (like perfume spreading). Creates current even without an electric field.\n\nTotal current is the sum of drift and diffusion components for both electrons and holes.",
                    formula = "J_drift = q(n\u03BC_n + p\u03BC_p)E\nJ_diff = qD_n(dn/dx) \u2212 qD_p(dp/dx)",
                    keyPoint = "Drift (from E field) + diffusion (from concentration gradient) = total current"
                ),
                LessonSection(
                    heading = "The PN Junction Revisited",
                    content = "When P-type meets N-type semiconductor, electrons diffuse into the P-side and holes into the N-side. This creates a depletion region with a built-in potential V_bi.\n\nThe built-in voltage for silicon is typically 0.6\u20130.7V. This is why diodes have a forward voltage drop.\n\nThe depletion width depends on doping: W \u221D \u221A(V_bi/N), so heavily doped junctions have narrow depletion regions. This physics underpins every diode, transistor, and solar cell.",
                    formula = "V_bi = (kT/q) \u00D7 ln(N_A \u00D7 N_D / n_i\u00B2)\nkT/q \u2248 26mV at 300K",
                    keyPoint = "PN junction built-in voltage creates the 0.7V diode drop in silicon"
                )
            )
        ),
        quiz = Quiz(
            title = "Semiconductor Physics Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "sp_mc1",
                    questionText = "What is the band gap of silicon at room temperature?",
                    options = listOf(
                        "0.67 eV",
                        "1.12 eV",
                        "1.42 eV",
                        "3.4 eV"
                    ),
                    correctIndex = 1,
                    explanation = "Silicon has a band gap of 1.12 eV. Germanium is 0.67 eV and GaAs is 1.42 eV."
                ),
                Question.MultipleChoice(
                    id = "sp_mc2",
                    questionText = "N-type silicon is created by doping with atoms that have:",
                    options = listOf(
                        "3 valence electrons",
                        "4 valence electrons",
                        "5 valence electrons",
                        "6 valence electrons"
                    ),
                    correctIndex = 2,
                    explanation = "N-type doping uses donor atoms with 5 valence electrons (e.g., phosphorus). The extra electron becomes a free carrier."
                ),
                Question.MultipleChoice(
                    id = "sp_mc3",
                    questionText = "In P-type silicon, the majority charge carriers are:",
                    options = listOf(
                        "Electrons",
                        "Holes",
                        "Protons",
                        "Neutrons"
                    ),
                    correctIndex = 1,
                    explanation = "P-type silicon has acceptor impurities that create holes. Holes are the majority carriers; electrons are minority carriers."
                ),
                Question.NumericInput(
                    id = "sp_ni1",
                    questionText = "If an N-type semiconductor has n = 10\u00B9\u2077/cm\u00B3 and n_i = 10\u00B9\u2070/cm\u00B3, what is the hole concentration p (in /cm\u00B3)? Express as a power of 10.",
                    correctAnswer = 1000.0,
                    tolerance = 50.0,
                    unit = "/cm\u00B3",
                    explanation = "n \u00D7 p = n_i\u00B2. p = n_i\u00B2/n = (10\u00B9\u2070)\u00B2/10\u00B9\u2077 = 10\u00B2\u2070/10\u00B9\u2077 = 10\u00B3 = 1000/cm\u00B3"
                ),
                Question.MultipleChoice(
                    id = "sp_mc4",
                    questionText = "What are the two mechanisms of carrier transport in semiconductors?",
                    options = listOf(
                        "Convection and radiation",
                        "Drift and diffusion",
                        "Conduction and induction",
                        "Tunneling and hopping"
                    ),
                    correctIndex = 1,
                    explanation = "Carriers move by drift (due to electric field) and diffusion (due to concentration gradients)."
                ),
                Question.NumericInput(
                    id = "sp_ni2",
                    questionText = "The thermal voltage kT/q at room temperature (300K) is approximately how many millivolts?",
                    correctAnswer = 26.0,
                    tolerance = 0.5,
                    unit = "mV",
                    explanation = "kT/q = (1.38\u00D710\u207B\u00B2\u00B3 \u00D7 300) / 1.6\u00D710\u207B\u00B9\u2079 \u2248 0.026V = 26mV"
                ),
                Question.NumericInput(
                    id = "sp_ni3",
                    questionText = "An intrinsic semiconductor has n_i = 1.5\u00D710\u00B9\u2070/cm\u00B3. What is n_i\u00B2 expressed as 10^x? Give x.",
                    correctAnswer = 20.0,
                    tolerance = 0.2,
                    unit = "",
                    explanation = "n_i\u00B2 = (1.5\u00D710\u00B9\u2070)\u00B2 = 2.25\u00D710\u00B2\u2070 \u2248 10\u00B2\u2070. So x \u2248 20."
                )
            )
        )
    )
}
