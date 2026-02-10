package com.circuitqueest.app.data.content

object MosfetsContent {
    val topic = Topic(
        id = "mosfets",
        title = "MOSFETs",
        subtitle = "Field-effect transistors and CMOS logic",
        icon = "\uD83D\uDD0C",
        order = 10,
        lesson = Lesson(
            title = "MOSFETs: Voltage-Controlled Switches",
            sections = listOf(
                LessonSection(
                    heading = "What is a MOSFET?",
                    content = "A Metal-Oxide-Semiconductor Field-Effect Transistor (MOSFET) is a voltage-controlled device with three terminals: Gate (G), Drain (D), and Source (S).\n\nUnlike BJTs which are current-controlled, MOSFETs are controlled by the voltage at the gate. Virtually no current flows into the gate, making them extremely efficient.\n\nMOSFETs are the building blocks of all modern digital circuits, with billions on a single chip.",
                    keyPoint = "MOSFET = voltage-controlled; BJT = current-controlled"
                ),
                LessonSection(
                    heading = "NMOS and PMOS",
                    content = "There are two types of enhancement-mode MOSFETs:\n\n\u2022 NMOS: turns ON when V_GS > V_th (gate voltage exceeds threshold). Current flows from drain to source. Used to pull outputs LOW.\n\n\u2022 PMOS: turns ON when V_GS < \u2212|V_th| (gate voltage is sufficiently below source). Current flows from source to drain. Used to pull outputs HIGH.\n\nThreshold voltage (V_th) is typically 0.5V\u20132V for modern MOSFETs.",
                    formula = "NMOS ON: V_GS > V_th\nPMOS ON: V_SG > |V_th|",
                    keyPoint = "NMOS: gate HIGH = ON; PMOS: gate LOW = ON"
                ),
                LessonSection(
                    heading = "Operating Regions",
                    content = "A MOSFET operates in three regions:\n\n\u2022 Cutoff: V_GS < V_th. No channel forms, no current flows. The transistor is OFF.\n\n\u2022 Triode (Linear): V_GS > V_th and V_DS < V_GS \u2212 V_th. The MOSFET acts like a variable resistor. Used in analog circuits.\n\n\u2022 Saturation: V_GS > V_th and V_DS \u2265 V_GS \u2212 V_th. Current is nearly constant regardless of V_DS. Used for amplification.",
                    formula = "Saturation: I_D = \u00BDk'(W/L)(V_GS \u2212 V_th)\u00B2",
                    keyPoint = "Cutoff = OFF, Triode = resistor, Saturation = constant current"
                ),
                LessonSection(
                    heading = "MOSFET as a Switch",
                    content = "MOSFETs excel as switches because they are voltage-controlled and have extremely low on-resistance (R_DS(on)).\n\nTo use an NMOS as a switch:\n\u2022 Gate HIGH (> V_th): MOSFET is ON, drain connects to source\n\u2022 Gate LOW (< V_th): MOSFET is OFF, open circuit\n\nPower MOSFETs can switch hundreds of amps with milliohm on-resistance, making them essential in power electronics, motor drivers, and switching power supplies.",
                    formula = "P_dissipated = I_D\u00B2 \u00D7 R_DS(on)",
                    keyPoint = "Low R_DS(on) means very little power wasted as heat when switching"
                ),
                LessonSection(
                    heading = "CMOS Logic",
                    content = "CMOS (Complementary MOS) pairs an NMOS and a PMOS transistor to create logic gates that consume almost zero static power.\n\nCMOS Inverter:\n\u2022 Input HIGH: NMOS ON, PMOS OFF \u2192 output pulled LOW\n\u2022 Input LOW: PMOS ON, NMOS OFF \u2192 output pulled HIGH\n\nIn either state, there is a path from output to V_DD or GND, but never both simultaneously. Power is only consumed during switching transitions.",
                    keyPoint = "CMOS draws almost zero power in steady state \u2014 only during transitions"
                ),
                LessonSection(
                    heading = "MOSFET vs. BJT",
                    content = "Comparing the two transistor families:\n\n\u2022 Input: MOSFET is voltage-controlled (no gate current); BJT is current-controlled (needs base current)\n\u2022 Input impedance: MOSFET is extremely high (>10\u00B9\u03A9); BJT is moderate\n\u2022 Speed: MOSFETs switch faster in digital circuits\n\u2022 Power: CMOS uses less static power\n\u2022 Analog gain: BJTs often have higher transconductance for the same bias current\n\nMOSFETs dominate digital ICs; BJTs are still used where high transconductance matters (RF, precision analog).",
                    keyPoint = "MOSFETs dominate digital; BJTs still used in high-performance analog"
                )
            )
        ),
        quiz = Quiz(
            title = "MOSFETs Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "mos_mc1",
                    questionText = "What are the three terminals of a MOSFET?",
                    options = listOf(
                        "Emitter, Base, Collector",
                        "Anode, Cathode, Gate",
                        "Gate, Drain, Source",
                        "Input, Output, Ground"
                    ),
                    correctIndex = 2,
                    explanation = "A MOSFET has three terminals: Gate (G), Drain (D), and Source (S)."
                ),
                Question.MultipleChoice(
                    id = "mos_mc2",
                    questionText = "An NMOS transistor turns ON when:",
                    options = listOf(
                        "V_GS < V_th",
                        "V_GS > V_th",
                        "V_GS = 0",
                        "V_DS > V_th"
                    ),
                    correctIndex = 1,
                    explanation = "An NMOS turns ON when the gate-to-source voltage exceeds the threshold voltage: V_GS > V_th."
                ),
                Question.MultipleChoice(
                    id = "mos_mc3",
                    questionText = "In a CMOS inverter, when the input is HIGH:",
                    options = listOf(
                        "Both transistors are ON",
                        "Both transistors are OFF",
                        "NMOS is ON, PMOS is OFF, output is LOW",
                        "PMOS is ON, NMOS is OFF, output is HIGH"
                    ),
                    correctIndex = 2,
                    explanation = "Input HIGH turns NMOS ON (pulling output to GND) and PMOS OFF. The output is LOW."
                ),
                Question.NumericInput(
                    id = "mos_ni1",
                    questionText = "A MOSFET has R_DS(on) = 0.05\u03A9 and carries 10A. How much power is dissipated in Watts?",
                    correctAnswer = 5.0,
                    tolerance = 0.1,
                    unit = "W",
                    explanation = "P = I\u00B2 \u00D7 R_DS(on) = 10\u00B2 \u00D7 0.05 = 100 \u00D7 0.05 = 5W"
                ),
                Question.MultipleChoice(
                    id = "mos_mc4",
                    questionText = "Why does CMOS logic consume almost zero static power?",
                    options = listOf(
                        "It uses very small transistors",
                        "In either logic state, only one transistor is ON so there is no path from V_DD to GND",
                        "MOSFETs have infinite resistance",
                        "The power supply voltage is very low"
                    ),
                    correctIndex = 1,
                    explanation = "In CMOS, either the NMOS or PMOS is ON, never both simultaneously, so no DC current flows from supply to ground."
                ),
                Question.NumericInput(
                    id = "mos_ni2",
                    questionText = "A MOSFET has V_th = 1.5V. If V_GS = 3V and V_DS = 1V, what is V_GS \u2212 V_th?",
                    correctAnswer = 1.5,
                    tolerance = 0.1,
                    unit = "V",
                    explanation = "V_GS \u2212 V_th = 3 \u2212 1.5 = 1.5V. Since V_DS (1V) < 1.5V, the MOSFET is in the triode region."
                ),
                Question.NumericInput(
                    id = "mos_ni3",
                    questionText = "A CMOS chip has 2 billion transistors switching at 3GHz. If each transition dissipates 0.5 femtojoules (fJ), what is the dynamic power in Watts?",
                    correctAnswer = 3.0,
                    tolerance = 0.1,
                    unit = "W",
                    explanation = "P = N \u00D7 f \u00D7 E = 2\u00D710\u2079 \u00D7 3\u00D710\u2079 \u00D7 0.5\u00D710\u207B\u00B9\u2075 = 3W (simplified; real chips don't switch all transistors every cycle)."
                )
            )
        )
    )
}
