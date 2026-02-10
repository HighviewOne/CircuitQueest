package com.circuitqueest.app.data.content

object PowerElectronicsContent {
    val topic = Topic(
        id = "power_electronics",
        title = "Power Electronics",
        subtitle = "Voltage regulation and DC-DC conversion",
        icon = "\uD83D\uDD0B",
        order = 14,
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
                Question.NumericInput(
                    id = "pe_ni1",
                    questionText = "A linear regulator converts 9V to 5V at 500mA. How much power is wasted as heat in Watts?",
                    correctAnswer = 2.0,
                    tolerance = 0.1,
                    unit = "W",
                    explanation = "P_loss = (V_in \u2212 V_out) \u00D7 I = (9 \u2212 5) \u00D7 0.5 = 2W"
                ),
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
                Question.NumericInput(
                    id = "pe_ni2",
                    questionText = "A buck converter has V_in = 24V and duty cycle D = 0.25. What is V_out in Volts?",
                    correctAnswer = 6.0,
                    tolerance = 0.1,
                    unit = "V",
                    explanation = "V_out = D \u00D7 V_in = 0.25 \u00D7 24 = 6V"
                ),
                Question.NumericInput(
                    id = "pe_ni3",
                    questionText = "A boost converter has V_in = 5V and D = 0.5. What is V_out in Volts?",
                    correctAnswer = 10.0,
                    tolerance = 0.1,
                    unit = "V",
                    explanation = "V_out = V_in / (1 \u2212 D) = 5 / (1 \u2212 0.5) = 5 / 0.5 = 10V"
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
                Question.NumericInput(
                    id = "pe_ni4",
                    questionText = "A linear regulator converts 12V to 3.3V. What is its efficiency in percent?",
                    correctAnswer = 27.5,
                    tolerance = 0.5,
                    unit = "%",
                    explanation = "\u03B7 = V_out/V_in \u00D7 100 = 3.3/12 \u00D7 100 = 27.5%. This shows why linear regulators are poor for large voltage drops."
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
                )
            )
        )
    )
}
