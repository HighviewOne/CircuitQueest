package com.circuitqueest.app.data.content

object TransistorsContent {
    val topic = Topic(
        id = "transistors_bjt",
        title = "Transistors (BJTs)",
        subtitle = "Amplification and switching with bipolar junction transistors",
        icon = "\uD83D\uDD00",
        order = 8,
        lesson = Lesson(
            title = "Transistors: The Building Blocks of Electronics",
            sections = listOf(
                LessonSection(
                    heading = "What is a BJT?",
                    content = "A Bipolar Junction Transistor (BJT) is a three-terminal semiconductor device that can amplify signals or act as a switch. It has three regions: Emitter (E), Base (B), and Collector (C).\n\nThere are two types:\n\u2022 NPN: current flows from collector to emitter, controlled by base current\n\u2022 PNP: current flows from emitter to collector, controlled by base current\n\nNPN transistors are more common and will be our primary focus.",
                    keyPoint = "BJT: a small base current controls a much larger collector current"
                ),
                LessonSection(
                    heading = "Current Gain (\u03B2 / h_FE)",
                    content = "The current gain \u03B2 (beta), also called h_FE, is the ratio of collector current to base current. Typical values range from 50 to 300.\n\nThis means a tiny base current is amplified into a much larger collector current. The emitter current is the sum of both.\n\nFor example, with \u03B2 = 100 and I_B = 0.1mA, the collector current is 10mA.",
                    formula = "\u03B2 = I_C / I_B\nI_E = I_C + I_B",
                    keyPoint = "\u03B2 typically 50\u2013300; I_C = \u03B2 \u00D7 I_B"
                ),
                LessonSection(
                    heading = "Operating Regions",
                    content = "A BJT operates in three regions:\n\n\u2022 Cutoff: Both junctions reverse biased. No current flows. Transistor is OFF (like an open switch).\n\u2022 Active: Base-emitter forward biased, base-collector reverse biased. Current is amplified. Used for analog amplification.\n\u2022 Saturation: Both junctions forward biased. Maximum current flows. Transistor is fully ON (like a closed switch).\n\nV_BE \u2248 0.7V for a silicon BJT in active or saturation mode.",
                    formula = "V_BE \u2248 0.7V (silicon, active region)\nV_CE(sat) \u2248 0.2V",
                    keyPoint = "Cutoff = OFF, Active = amplifier, Saturation = ON"
                ),
                LessonSection(
                    heading = "BJT as a Switch",
                    content = "When used as a switch, the BJT toggles between cutoff (OFF) and saturation (ON).\n\nTo turn the transistor fully ON, you need enough base current to drive it into saturation: I_B \u2265 I_C / \u03B2.\n\nA base resistor limits the current from the control signal:\nR_B = (V_control \u2212 0.7V) / I_B\n\nThis is how microcontrollers drive LEDs, relays, and motors through transistors.",
                    formula = "R_B = (V_control \u2212 V_BE) / I_B",
                    keyPoint = "Switching: ensure I_B is large enough to saturate the transistor"
                ),
                LessonSection(
                    heading = "Common-Emitter Amplifier",
                    content = "The common-emitter (CE) configuration is the most widely used BJT amplifier. The input is applied to the base, and the amplified output is taken from the collector.\n\nThe voltage gain depends on the collector resistor and the transistor's internal emitter resistance:\nA_v \u2248 \u2212R_C / r_e, where r_e \u2248 26mV / I_C\n\nThe negative sign indicates phase inversion (180\u00B0). The CE amplifier provides both voltage and current gain.",
                    formula = "A_v \u2248 \u2212R_C / r_e\nr_e \u2248 26mV / I_C",
                    keyPoint = "Common-emitter: high gain, phase inversion, most popular BJT configuration"
                ),
                LessonSection(
                    heading = "Biasing the BJT",
                    content = "For reliable amplifier operation, the BJT must be biased in the active region with a stable operating point (Q-point).\n\nThe voltage divider bias is the most stable method:\n1. R\u2081 and R\u2082 set the base voltage: V_B = V_CC \u00D7 R\u2082/(R\u2081 + R\u2082)\n2. V_E = V_B \u2212 0.7V\n3. I_C \u2248 I_E = V_E / R_E\n4. V_CE = V_CC \u2212 I_C(R_C + R_E)\n\nThis design maintains a stable Q-point despite \u03B2 variations between transistors.",
                    formula = "V_B = V_CC \u00D7 R\u2082/(R\u2081 + R\u2082)\nI_C \u2248 (V_B \u2212 0.7) / R_E",
                    keyPoint = "Voltage divider bias provides stable operation despite \u03B2 variations"
                )
            )
        ),
        quiz = Quiz(
            title = "Transistors Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "bjt_mc1",
                    questionText = "What are the three terminals of a BJT?",
                    options = listOf(
                        "Anode, Cathode, Gate",
                        "Source, Drain, Gate",
                        "Emitter, Base, Collector",
                        "Input, Output, Ground"
                    ),
                    correctIndex = 2,
                    explanation = "A BJT has three terminals: Emitter (E), Base (B), and Collector (C)."
                ),
                Question.NumericInput(
                    id = "bjt_ni1",
                    questionText = "A BJT has \u03B2 = 150 and I_B = 0.2mA. What is the collector current (I_C) in milliamps?",
                    correctAnswer = 30.0,
                    tolerance = 0.5,
                    unit = "mA",
                    explanation = "I_C = \u03B2 \u00D7 I_B = 150 \u00D7 0.2mA = 30mA"
                ),
                Question.MultipleChoice(
                    id = "bjt_mc2",
                    questionText = "In which operating region does a BJT function as an amplifier?",
                    options = listOf(
                        "Cutoff",
                        "Saturation",
                        "Active",
                        "Breakdown"
                    ),
                    correctIndex = 2,
                    explanation = "In the active region, the BJT amplifies the input signal. Cutoff = OFF, Saturation = fully ON."
                ),
                Question.NumericInput(
                    id = "bjt_ni2",
                    questionText = "An NPN transistor has \u03B2 = 100. To switch a 50mA load, what minimum base current (in mA) is needed?",
                    correctAnswer = 0.5,
                    tolerance = 0.05,
                    unit = "mA",
                    explanation = "I_B(min) = I_C / \u03B2 = 50mA / 100 = 0.5mA"
                ),
                Question.NumericInput(
                    id = "bjt_ni3",
                    questionText = "A 5V control signal drives a base resistor to provide 0.5mA to the base (V_BE = 0.7V). What is the base resistor in k\u03A9?",
                    correctAnswer = 8.6,
                    tolerance = 0.1,
                    unit = "k\u03A9",
                    explanation = "R_B = (V_control \u2212 V_BE) / I_B = (5 \u2212 0.7) / 0.0005 = 4.3/0.0005 = 8600\u03A9 = 8.6k\u03A9"
                ),
                Question.MultipleChoice(
                    id = "bjt_mc3",
                    questionText = "What is the approximate V_BE of a silicon BJT in its active region?",
                    options = listOf(
                        "0V",
                        "0.3V",
                        "0.7V",
                        "5.0V"
                    ),
                    correctIndex = 2,
                    explanation = "A silicon BJT has V_BE \u2248 0.7V when forward biased in the active or saturation region."
                ),
                Question.NumericInput(
                    id = "bjt_ni4",
                    questionText = "A common-emitter amplifier has R_C = 4.7k\u03A9 and I_C = 2mA. What is r_e in Ohms?",
                    correctAnswer = 13.0,
                    tolerance = 0.5,
                    unit = "\u03A9",
                    explanation = "r_e \u2248 26mV / I_C = 26mV / 2mA = 13\u03A9"
                )
            )
        )
    )
}
