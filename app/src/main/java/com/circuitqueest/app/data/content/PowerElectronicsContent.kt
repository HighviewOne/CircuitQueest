package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object PowerElectronicsContent {
    private val linearInputs = listOf(9.0, 12.0, 15.0, 24.0)
    private val linearOutputs = listOf(3.3, 5.0)
    private val linearCurrents = listOf(0.1, 0.25, 0.5, 1.0)
    private val buckInputs = listOf(12.0, 24.0, 48.0)
    private val buckDuties = listOf(0.1, 0.25, 0.4, 0.5, 0.75)
    private val boostInputs = listOf(3.3, 5.0, 12.0)
    private val boostDuties = listOf(0.2, 0.25, 0.4, 0.5, 0.6, 0.75)
    private val efficiencyInputs = listOf(5.0, 9.0, 12.0, 24.0)
    private val efficiencyOutputs = listOf(1.8, 3.3)
    private val buckTargets = listOf(1.2, 3.3, 5.0)
    private val outputCurrents = listOf(0.5, 1.0, 2.0)
    private val converterEfficiencies = listOf(85.0, 90.0, 95.0)

    val topic = Topic(
        id = "power_electronics",
        title = "Power Electronics",
        subtitle = "Voltage regulation and DC-DC conversion",
        icon = "\uD83D\uDD0B",
        order = 22,
        lesson = Lesson(
            title = "Power Electronics: Taming the Voltage",
            sections = listOf(
                LessonSection(
                    heading = "Why Regulate Voltage?",
                    content = "Electronic circuits require stable, precise supply voltages to operate correctly. Batteries discharge over time, wall adapters fluctuate, and different parts of a system need different voltages.\n\nVoltage regulators convert an unregulated input voltage into a stable output. There are two main approaches:\n\u2022 Linear regulators: simple, low noise, but inefficient\n\u2022 Switching regulators: efficient, but more complex and noisy",
                    keyPoint = "Linear = simple & clean; Switching = efficient & versatile"
                ),
                LessonSection(
                    heading = "Linear Regulators",
                    content = "A linear regulator acts like a variable resistor that adjusts to maintain a constant output voltage. The classic LM7805 provides a fixed 5V output.\n\nThe excess voltage is dissipated as heat:\nP_dissipated = (V_in \u2212 V_out) \u00D7 I_load\n\nDropout voltage is the minimum difference between V_in and V_out for proper regulation. LDO (Low Dropout) regulators can work with as little as 0.1\u20130.3V difference.",
                    formula = "P_loss = (V_in \u2212 V_out) \u00D7 I_load\n\u03B7 = V_out / V_in \u00D7 100%",
                    keyPoint = "Linear regulators waste excess voltage as heat: \u03B7 = V_out/V_in"
                ),
                LessonSection(
                    heading = "Buck Converter (Step-Down)",
                    content = "A buck converter efficiently steps voltage down using rapid switching and an LC filter. A MOSFET switch chops the input voltage into pulses, and an inductor-capacitor pair smooths them into a steady DC output.\n\nThe duty cycle D (fraction of time the switch is ON) sets the output voltage:\nV_out = D \u00D7 V_in\n\nExample: 12V input, D = 0.42 \u2192 V_out = 5V. Efficiencies of 90\u201395% are typical.",
                    formula = "V_out = D \u00D7 V_in\nD = t_on / T",
                    keyPoint = "Buck: V_out < V_in, controlled by duty cycle"
                ),
                LessonSection(
                    heading = "Boost Converter (Step-Up)",
                    content = "A boost converter steps voltage up. It stores energy in an inductor during the switch ON time and releases it at a higher voltage during the switch OFF time.\n\nV_out = V_in / (1 \u2212 D)\n\nExample: 3.3V input, D = 0.34 \u2192 V_out \u2248 5V. This is how a phone can charge at 5V from a 3.7V lithium battery.\n\nThe trade-off: higher output voltage means lower output current (power is conserved).",
                    formula = "V_out = V_in / (1 \u2212 D)",
                    keyPoint = "Boost: V_out > V_in; higher voltage = lower current"
                ),
                LessonSection(
                    heading = "PWM Control",
                    content = "Pulse Width Modulation (PWM) is the control method for switching regulators. A controller compares the output voltage to a reference and adjusts the duty cycle:\n\n\u2022 Output too low \u2192 increase duty cycle (switch ON longer)\n\u2022 Output too high \u2192 decrease duty cycle (switch ON less)\n\nTypical switching frequencies range from 100kHz to several MHz. Higher frequencies allow smaller inductors and capacitors but increase switching losses.",
                    formula = "D = t_on / (t_on + t_off)",
                    keyPoint = "PWM feedback loop: adjusts duty cycle to maintain regulated output"
                ),
                LessonSection(
                    heading = "Efficiency and Thermal Design",
                    content = "Efficiency matters in power electronics because wasted power becomes heat:\n\n\u03B7 = P_out / P_in \u00D7 100%\nP_loss = P_in \u2212 P_out\n\nA 90% efficient 10W supply wastes 1.1W as heat. A 50% efficient one wastes 10W.\n\nSwitching losses (during transitions), conduction losses (R_DS(on)), and inductor core losses all contribute. Proper thermal design with heatsinks or thermal pads is essential for reliable operation.",
                    formula = "\u03B7 = P_out / P_in \u00D7 100%\nP_loss = P_in(1 \u2212 \u03B7)",
                    keyPoint = "Every percent of efficiency matters at high power levels"
                )
            )
        ),
        quiz = Quiz(
            title = "Power Electronics Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "pe_mc1",
                    questionText = "Which type of regulator is more efficient?",
                    options = listOf(
                        "Linear regulator",
                        "Switching regulator",
                        "They are equally efficient",
                        "It depends on the load current only"
                    ),
                    correctIndex = 1,
                    explanation = "Switching regulators are much more efficient (typically 85\u201395%) because they transfer energy through inductors rather than dissipating excess voltage as heat."
                ),
                Question.MultipleChoice(
                    id = "pe_mc2",
                    questionText = "What does LDO stand for in voltage regulators?",
                    options = listOf(
                        "Low Digital Output",
                        "Linear Direct Output",
                        "Low Dropout",
                        "Load Dependent Operation"
                    ),
                    correctIndex = 2,
                    explanation = "LDO stands for Low Dropout, meaning the regulator can operate with a very small difference between input and output voltage (as low as 0.1\u20130.3V)."
                ),
                Question.MultipleChoice(
                    id = "pe_mc3",
                    questionText = "In a buck converter, increasing the duty cycle:",
                    options = listOf(
                        "Decreases the output voltage",
                        "Increases the output voltage",
                        "Increases the switching frequency",
                        "Has no effect on output voltage"
                    ),
                    correctIndex = 1,
                    explanation = "V_out = D \u00D7 V_in, so increasing D increases V_out (the switch is ON for a larger fraction of each cycle)."
                ),
                Question.MultipleChoice(
                    id = "pe_mc4",
                    questionText = "In a buck converter, what is the inductor's job?",
                    options = listOf(
                        "To raise the output voltage",
                        "To store energy while the switch is on and keep current flowing while it's off",
                        "To filter the input AC",
                        "To limit the switching frequency"
                    ),
                    correctIndex = 1,
                    explanation = "The switch chops the input. The inductor stores energy during the ON time and keeps current flowing (through the diode or low-side switch) during the OFF time, so the output gets a smooth average D × V_in."
                )
            ),
            generators = listOf(
                generator("pe_ni1") { r ->
                    val vin = linearInputs.pick(r)
                    val vout = linearOutputs.pick(r)
                    val i = linearCurrents.pick(r)
                    val p = (vin - vout) * i
                    Numeric(
                        text = "A linear regulator converts ${fmt(vin)}V to ${fmt(vout)}V at " +
                            "${if (i < 1) "${fmt(i * 1000)}mA" else "${fmt(i)}A"}. How much power is " +
                            "wasted as heat, in Watts? Round to 2 decimal places.",
                        answer = p, unit = "W",
                        explanation = "P_loss = (V_in − V_out) × I = (${fmt(vin)} − ${fmt(vout)}) × ${fmt(i)} = ${fmt(p, 2)}W"
                    )
                },
                generator("pe_ni2") { r ->
                    val vin = buckInputs.pick(r)
                    val d = buckDuties.pick(r)
                    Numeric(
                        text = "A buck converter has V_in = ${fmt(vin)}V and duty cycle D = ${fmt(d)}. What is V_out in Volts?",
                        answer = d * vin, unit = "V",
                        explanation = "V_out = D × V_in = ${fmt(d)} × ${fmt(vin)} = ${fmt(d * vin)}V"
                    )
                },
                generator("pe_ni3") { r ->
                    val vin = boostInputs.pick(r)
                    val d = boostDuties.pick(r)
                    val vout = vin / (1 - d)
                    Numeric(
                        text = "A boost converter has V_in = ${fmt(vin)}V and duty cycle D = ${fmt(d)}. What is V_out in Volts? " +
                            "Round to 2 decimal places.",
                        answer = vout, unit = "V",
                        explanation = "V_out = V_in/(1 − D) = ${fmt(vin)}/(1 − ${fmt(d)}) = ${fmt(vout, 2)}V"
                    )
                },
                generator("pe_ni4") { r ->
                    val vin = efficiencyInputs.pick(r)
                    val vout = efficiencyOutputs.pick(r)
                    val eff = vout / vin * 100
                    Numeric(
                        text = "A linear regulator converts ${fmt(vin)}V to ${fmt(vout)}V. What is its efficiency in percent? " +
                            "Round to 1 decimal place.",
                        answer = eff, unit = "%", decimals = 1,
                        explanation = "The same current flows in and out, so η = V_out/V_in × 100 = ${fmt(vout)}/${fmt(vin)} × 100 = " +
                            "${fmt(eff, 1)}%"
                    )
                },
                generator("pe_ni5") { r ->
                    val vin = buckInputs.pick(r)
                    val vout = buckTargets.pick(r)
                    val d = vout / vin
                    Numeric(
                        text = "A buck converter must produce ${fmt(vout)}V from ${fmt(vin)}V. What duty cycle is needed? " +
                            "Round to 3 decimal places.",
                        answer = d, unit = "", decimals = 3,
                        explanation = "D = V_out/V_in = ${fmt(vout)}/${fmt(vin)} = ${fmt(d, 3)}"
                    )
                },
                generator("pe_ni6") { r ->
                    val vin = buckInputs.pick(r)
                    val vout = buckTargets.pick(r)
                    val iout = outputCurrents.pick(r)
                    val eff = converterEfficiencies.pick(r)
                    val iin = vout * iout / (eff / 100 * vin)
                    Numeric(
                        text = "A switching converter delivers ${fmt(vout)}V at ${fmt(iout)}A from a ${fmt(vin)}V input with " +
                            "${fmt(eff)}% efficiency. What is the input current, in Amps? Round to 3 decimal places.",
                        answer = iin, unit = "A", decimals = 3,
                        explanation = "P_out = ${fmt(vout)} × ${fmt(iout)} = ${fmt(vout * iout)}W, so P_in = P_out/η = " +
                            "${fmt(vout * iout / (eff / 100), 3)}W and I_in = P_in/V_in = ${fmt(iin, 3)}A"
                    )
                }
            )
        )
    )
}
