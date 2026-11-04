package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.sqrt

object TransformersContent {
    private val primaryTurns = listOf(100.0, 200.0, 400.0, 500.0, 1000.0)
    private val secondaryTurns = listOf(25.0, 50.0, 150.0, 2000.0)
    private val mainsVoltages = listOf(120.0, 230.0, 240.0)
    private val lowVoltages = listOf(6.0, 12.0, 24.0)
    private val loadCurrents = listOf(0.5, 1.0, 2.0, 5.0)
    private val impedancePairs = listOf(300.0 to 75.0, 200.0 to 50.0, 450.0 to 50.0, 800.0 to 8.0, 1250.0 to 50.0)
    private val inputPowers = listOf(500.0, 1000.0, 2000.0, 5000.0)
    private val efficiencies = listOf(90.0, 95.0, 97.0, 98.0)
    private val designPrimaryTurns = listOf(400.0, 500.0, 1000.0, 1200.0)
    private val designPrimaryVoltages = listOf(120.0, 240.0)
    private val windingCurrents = listOf(2.0, 5.0, 10.0)
    private val windingResistances = listOf(0.1, 0.2, 0.5)

    val topic = Topic(
        id = "transformers",
        title = "Transformers",
        subtitle = "Voltage transformation and power transfer",
        icon = "\u2694\uFE0F",
        order = 21,
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
                ),
                Question.MultipleChoice(
                    id = "xfmr_mc4",
                    questionText = "A transformer's rating is given in kVA rather than kW because:",
                    options = listOf(
                        "kVA is the same as kW",
                        "Its heating depends on voltage and current, whatever the load's power factor",
                        "Transformers only supply resistive loads",
                        "kW is not an SI unit"
                    ),
                    correctIndex = 1,
                    explanation = "Winding losses depend on current and core losses on voltage, so the limit is apparent power V × I (kVA), whatever the power factor of the load."
                )
            ),
            generators = listOf(
                generator("xfmr_ni1") { r ->
                    val np = primaryTurns.pick(r)
                    val ns = secondaryTurns.pick(r)
                    val vp = mainsVoltages.pick(r)
                    val vs = vp * ns / np
                    Numeric(
                        text = "A transformer has ${fmt(np)} primary turns and ${fmt(ns)} secondary turns. If the primary voltage is " +
                            "${fmt(vp)}V, what is the secondary voltage in Volts? Round to 2 decimal places.",
                        answer = vs, unit = "V",
                        explanation = "V_s = V_p × (N_s/N_p) = ${fmt(vp)} × (${fmt(ns)}/${fmt(np)}) = ${fmt(vs, 2)}V"
                    )
                },
                generator("xfmr_ni2") { r ->
                    val vp = mainsVoltages.pick(r)
                    val vs = lowVoltages.pick(r)
                    val isec = loadCurrents.pick(r)
                    val ip = vs * isec / vp
                    Numeric(
                        text = "An ideal transformer steps ${fmt(vp)}V down to ${fmt(vs)}V and delivers ${fmt(isec)}A to the load. " +
                            "What is the primary current in Amps? Round to 3 decimal places.",
                        answer = ip, unit = "A", decimals = 3,
                        explanation = "P = V_s × I_s = ${fmt(vs)} × ${fmt(isec)} = ${fmt(vs * isec)}W, and P is the same on both " +
                            "sides: I_p = ${fmt(vs * isec)}/${fmt(vp)} = ${fmt(ip, 3)}A"
                    )
                },
                generator("xfmr_ni3") { r ->
                    val (zp, zs) = impedancePairs.pick(r)
                    val n = sqrt(zp / zs)
                    Numeric(
                        text = "A transformer must make a ${fmt(zs)}Ω load look like ${fmt(zp)}Ω at its primary. What turns ratio " +
                            "N_p/N_s is needed?",
                        answer = n, unit = "",
                        explanation = "Impedance transforms with the square of the turns ratio: Z_p/Z_s = (N_p/N_s)², so " +
                            "N_p/N_s = √(${fmt(zp)}/${fmt(zs)}) = ${fmt(n, 2)}"
                    )
                },
                generator("xfmr_ni4") { r ->
                    val pin = inputPowers.pick(r)
                    val eff = efficiencies.pick(r)
                    val pout = pin * eff / 100
                    Numeric(
                        text = "A transformer delivers ${fmt(pout)}W to a load from a ${fmt(pin)}W input. What is its efficiency " +
                            "in percent?",
                        answer = eff, unit = "%",
                        explanation = "η = P_out/P_in × 100 = ${fmt(pout)}/${fmt(pin)} × 100 = ${fmt(eff)}%"
                    )
                },
                generator("xfmr_ni5") { r ->
                    val np = designPrimaryTurns.pick(r)
                    val vp = designPrimaryVoltages.pick(r)
                    val vs = lowVoltages.pick(r)
                    val ns = np * vs / vp
                    Numeric(
                        text = "A transformer's primary has ${fmt(np)} turns on ${fmt(vp)}V. How many secondary turns give " +
                            "${fmt(vs)}V? Round to the nearest whole number.",
                        answer = ns, unit = "turns", decimals = 0,
                        explanation = "N_s = N_p × V_s/V_p = ${fmt(np)} × ${fmt(vs)}/${fmt(vp)} = ${fmt(ns, 1)} turns"
                    )
                },
                generator("xfmr_ni6") { r ->
                    val i = windingCurrents.pick(r)
                    val res = windingResistances.pick(r)
                    val p = i * i * res
                    Numeric(
                        text = "A transformer winding has ${fmt(res)}Ω resistance and carries ${fmt(i)}A. What is its copper (I²R) " +
                            "loss, in Watts? Round to 2 decimal places.",
                        answer = p, unit = "W",
                        explanation = "P_cu = I²R = ${fmt(i)}² × ${fmt(res)} = ${fmt(p, 2)}W"
                    )
                }
            )
        )
    )
}
