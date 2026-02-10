package com.circuitqueest.app.data.content

object TransformersContent {
    val topic = Topic(
        id = "transformers",
        title = "Transformers",
        subtitle = "Voltage transformation and power transfer",
        icon = "\u2694\uFE0F",
        order = 12,
        lesson = Lesson(
            title = "Transformers: Changing Voltage Levels",
            sections = listOf(
                LessonSection(
                    heading = "How Transformers Work",
                    content = "A transformer transfers electrical energy between two circuits through electromagnetic induction. It consists of two coils (windings) wound around a shared magnetic core.\n\nThe primary winding receives AC input. The changing current creates a changing magnetic field in the core, which induces a voltage in the secondary winding.\n\nTransformers only work with AC \u2014 DC creates a constant magnetic field that cannot induce a voltage.",
                    keyPoint = "Transformers use magnetic coupling \u2014 they only work with AC, never DC"
                ),
                LessonSection(
                    heading = "Turns Ratio",
                    content = "The turns ratio (N_p/N_s) determines how the transformer changes voltage. The ratio of voltages equals the ratio of turns:\n\nV_s/V_p = N_s/N_p\n\n\u2022 Step-up: N_s > N_p \u2192 voltage increases\n\u2022 Step-down: N_s < N_p \u2192 voltage decreases\n\u2022 1:1 isolation transformer: same voltage, provides galvanic isolation\n\nExample: A transformer with 100 primary turns and 500 secondary turns has a 1:5 ratio and steps up voltage by 5\u00D7.",
                    formula = "V_s / V_p = N_s / N_p",
                    keyPoint = "Voltage ratio = turns ratio"
                ),
                LessonSection(
                    heading = "Current Transformation",
                    content = "While a transformer can increase voltage, it cannot create power. An ideal transformer conserves power: P_in = P_out.\n\nThis means if voltage goes up, current must go down proportionally (and vice versa):\n\nI_s/I_p = N_p/N_s\n\nSo a step-up transformer that doubles the voltage will halve the current. This is why high-voltage power lines use less current \u2014 reducing I\u00B2R losses over long distances.",
                    formula = "I_s / I_p = N_p / N_s\nV_p \u00D7 I_p = V_s \u00D7 I_s",
                    keyPoint = "Power is conserved: voltage up = current down"
                ),
                LessonSection(
                    heading = "Impedance Matching",
                    content = "A transformer can transform impedance as well as voltage and current. The impedance seen looking into the primary is related to the load impedance on the secondary by the square of the turns ratio.\n\nThis is crucial in audio systems and RF circuits where maximum power transfer requires matching source and load impedances.\n\nExample: To match a 50\u03A9 source to an 800\u03A9 load, you need a turns ratio of \u221A(800/50) = 4:1.",
                    formula = "Z_primary = (N_p/N_s)\u00B2 \u00D7 Z_load",
                    keyPoint = "Impedance transforms by the square of the turns ratio"
                ),
                LessonSection(
                    heading = "Efficiency and Losses",
                    content = "Real transformers are not 100% efficient. Losses include:\n\n\u2022 Copper losses (I\u00B2R): resistance in the windings dissipates heat\n\u2022 Core losses (hysteresis): energy lost reversing the magnetic domains\n\u2022 Eddy current losses: circulating currents induced in the core (reduced by using laminated cores)\n\u2022 Flux leakage: not all magnetic flux links both windings\n\nLarge power transformers can achieve 95\u201399% efficiency. Small transformers are typically 80\u201395% efficient.",
                    formula = "\u03B7 = P_out / P_in \u00D7 100%",
                    keyPoint = "Laminated cores reduce eddy currents; large transformers reach >98% efficiency"
                ),
                LessonSection(
                    heading = "Applications",
                    content = "Transformers are essential in power systems and electronics:\n\n\u2022 Power grid: step-up to hundreds of kV for transmission, step-down for distribution and use\n\u2022 Phone chargers and adapters: step down mains voltage to 5\u201320V\n\u2022 Audio systems: impedance matching between amplifiers and speakers\n\u2022 Isolation: providing galvanic isolation for safety\n\u2022 Current transformers (CTs): measuring large currents by scaling them down\n\nWithout transformers, efficient long-distance power transmission would be impossible.",
                    keyPoint = "Transformers make the modern power grid possible"
                )
            )
        ),
        quiz = Quiz(
            title = "Transformers Boss Battle",
            questions = listOf(
                Question.NumericInput(
                    id = "xfmr_ni1",
                    questionText = "A transformer has 200 primary turns and 50 secondary turns. If the primary voltage is 120V, what is the secondary voltage in Volts?",
                    correctAnswer = 30.0,
                    tolerance = 0.5,
                    unit = "V",
                    explanation = "V_s = V_p \u00D7 (N_s/N_p) = 120 \u00D7 (50/200) = 120 \u00D7 0.25 = 30V"
                ),
                Question.MultipleChoice(
                    id = "xfmr_mc1",
                    questionText = "A step-up transformer:",
                    options = listOf(
                        "Increases voltage and increases current",
                        "Increases voltage and decreases current",
                        "Decreases voltage and increases current",
                        "Creates extra power"
                    ),
                    correctIndex = 1,
                    explanation = "A step-up transformer increases voltage but decreases current to conserve power (P_in = P_out)."
                ),
                Question.NumericInput(
                    id = "xfmr_ni2",
                    questionText = "A transformer steps 240V down to 12V and delivers 2A to the load. What is the primary current in Amps?",
                    correctAnswer = 0.1,
                    tolerance = 0.01,
                    unit = "A",
                    explanation = "P = V_s \u00D7 I_s = 12 \u00D7 2 = 24W. I_p = P/V_p = 24/240 = 0.1A"
                ),
                Question.MultipleChoice(
                    id = "xfmr_mc2",
                    questionText = "Why do transformers only work with AC?",
                    options = listOf(
                        "DC would overheat the core",
                        "DC creates a constant magnetic field that cannot induce voltage",
                        "DC has too much current",
                        "Transformers are made of special AC-only materials"
                    ),
                    correctIndex = 1,
                    explanation = "Transformers work by electromagnetic induction, which requires a changing magnetic field. DC creates a constant field that induces no voltage."
                ),
                Question.NumericInput(
                    id = "xfmr_ni3",
                    questionText = "What turns ratio is needed to match a 75\u03A9 source to a 300\u03A9 load? Express as N_p:N_s (give N_s if N_p = 1).",
                    correctAnswer = 2.0,
                    tolerance = 0.1,
                    unit = "",
                    explanation = "Z_p/Z_s = (N_p/N_s)\u00B2. 75/300 = (N_p/N_s)\u00B2 = 1/4. N_p/N_s = 1/2, so N_s = 2 (turns ratio 1:2)."
                ),
                Question.NumericInput(
                    id = "xfmr_ni4",
                    questionText = "A transformer delivers 950W to a load from a 1000W input. What is its efficiency in percent?",
                    correctAnswer = 95.0,
                    tolerance = 0.5,
                    unit = "%",
                    explanation = "\u03B7 = P_out/P_in \u00D7 100 = 950/1000 \u00D7 100 = 95%"
                ),
                Question.MultipleChoice(
                    id = "xfmr_mc3",
                    questionText = "Laminated transformer cores are used to reduce:",
                    options = listOf(
                        "Copper losses",
                        "Flux leakage",
                        "Eddy current losses",
                        "The turns ratio"
                    ),
                    correctIndex = 2,
                    explanation = "Laminated cores break up the conductive path, reducing eddy currents (circulating currents induced in the core by the changing magnetic field)."
                )
            )
        )
    )
}
