package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object TransistorsContent {
    private const val V_BE = 0.7
    private const val V_CE_SAT = 0.2
    private const val THERMAL_MV = 26.0
    private val betas = listOf(50.0, 100.0, 150.0, 200.0, 300.0)
    private val baseCurrents = listOf(0.05, 0.1, 0.2, 0.5)
    private val loadCurrents = listOf(20.0, 50.0, 100.0, 200.0)
    private val controlVoltages = listOf(3.3, 5.0, 12.0)
    private val driveCurrents = listOf(0.1, 0.2, 0.5, 1.0)
    private val biasCurrents = listOf(0.5, 1.0, 2.0, 5.0, 10.0)
    private val supplyVoltages = listOf(5.0, 9.0, 12.0)
    private val collectorResistors = listOf(1.0, 2.2, 4.7)

    val topic = Topic(
        id = "transistors_bjt",
        title = "Transistors (BJTs)",
        subtitle = "Amplification and switching with bipolar junction transistors",
        icon = "\uD83D\uDD00",
        order = 7,
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
                Question.MultipleChoice(
                    id = "bjt_mc4",
                    questionText = "A BJT switches a relay coil. Which region should it be driven into when it is ON?",
                    options = listOf(
                        "Cutoff",
                        "Active",
                        "Saturation",
                        "Breakdown"
                    ),
                    correctIndex = 2,
                    explanation = "A switch should be fully ON: in saturation V_CE is only about 0.2V, so the transistor wastes little power. In the active region it would drop more voltage and heat up."
                ),
                Question.MultipleChoice(
                    id = "bjt_mc5",
                    questionText = "On an NPN transistor symbol, which way does the emitter arrow point?",
                    options = listOf(
                        "Into the transistor",
                        "Out of the transistor",
                        "Toward the base",
                        "There is no arrow"
                    ),
                    correctIndex = 1,
                    explanation = "The arrow shows conventional current at the emitter. For NPN it points out ('Not Pointing iN'); for PNP it points in."
                )
            ),
            generators = listOf(
                generator("bjt_ni1") { r ->
                    val beta = betas.pick(r)
                    val ib = baseCurrents.pick(r)
                    Numeric(
                        text = "A BJT has β = ${fmt(beta)} and I_B = ${fmt(ib)}mA. What is the collector current (I_C) in milliamps?",
                        answer = beta * ib, unit = "mA",
                        explanation = "I_C = β × I_B = ${fmt(beta)} × ${fmt(ib)}mA = ${fmt(beta * ib)}mA"
                    )
                },
                generator("bjt_ni2") { r ->
                    val beta = betas.pick(r)
                    val ic = loadCurrents.pick(r)
                    val ib = ic / beta
                    Numeric(
                        text = "An NPN transistor has β = ${fmt(beta)}. To switch ${article(ic)} ${fmt(ic)}mA load, what minimum " +
                            "base current (in mA) is needed? Round to 3 decimal places.",
                        answer = ib, unit = "mA", decimals = 3,
                        explanation = "I_B(min) = I_C/β = ${fmt(ic)}mA/${fmt(beta)} = ${fmt(ib, 3)}mA. In practice you drive " +
                            "a few times more to be sure of saturation."
                    )
                },
                generator("bjt_ni3") { r ->
                    val vc = controlVoltages.pick(r)
                    val ib = driveCurrents.pick(r)
                    val rb = (vc - V_BE) / ib
                    Numeric(
                        text = "${articleCap(vc)} ${fmt(vc)}V control signal drives a base resistor to provide ${fmt(ib)}mA to " +
                            "the base (V_BE = 0.7V). What is the base resistor in kΩ? Round to 2 decimal places.",
                        answer = rb, unit = "kΩ",
                        explanation = "R_B = (V_control − V_BE)/I_B = (${fmt(vc)} − 0.7)/${fmt(ib)}mA = ${fmt(rb, 2)}kΩ"
                    )
                },
                generator("bjt_ni4") { r ->
                    val ic = biasCurrents.pick(r)
                    val re = THERMAL_MV / ic
                    Numeric(
                        text = "A BJT is biased at I_C = ${fmt(ic)}mA. What is its small-signal emitter resistance r_e, in Ohms? " +
                            "Round to 2 decimal places.",
                        answer = re, unit = "Ω",
                        explanation = "r_e ≈ V_T/I_C = 26mV/${fmt(ic)}mA = ${fmt(re, 2)}Ω"
                    )
                },
                generator("bjt_ni5") { r ->
                    val beta = betas.pick(r)
                    val ib = baseCurrents.pick(r)
                    val ie = ib * (beta + 1)
                    Numeric(
                        text = "A BJT in its active region has β = ${fmt(beta)} and I_B = ${fmt(ib)}mA. What is the emitter " +
                            "current I_E, in mA? Round to 2 decimal places.",
                        answer = ie, unit = "mA",
                        explanation = "I_E = I_C + I_B = (β + 1) × I_B = ${fmt(beta + 1)} × ${fmt(ib)} = ${fmt(ie, 2)}mA"
                    )
                },
                generator("bjt_ni6") { r ->
                    val vcc = supplyVoltages.pick(r)
                    val rc = collectorResistors.pick(r)
                    val ic = (vcc - V_CE_SAT) / rc
                    Numeric(
                        text = "A BJT switch has V_CC = ${fmt(vcc)}V and R_C = ${fmt(rc)}kΩ. Assuming V_CE(sat) = 0.2V, " +
                            "what is the saturated collector current in mA? Round to 2 decimal places.",
                        answer = ic, unit = "mA",
                        explanation = "I_C(sat) = (V_CC − V_CE(sat))/R_C = (${fmt(vcc)} − 0.2)/${fmt(rc)}kΩ = ${fmt(ic, 2)}mA"
                    )
                }
            )
        )
    )
}
