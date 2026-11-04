package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object OhmsLawContent {
    private val currents = listOf(0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 4.0, 5.0, 6.0)
    private val resistances = listOf(2.0, 3.0, 4.0, 5.0, 6.0, 8.0, 10.0, 12.0, 15.0, 20.0)
    private val limitResistances = listOf(5.0, 8.0, 10.0, 12.0, 20.0, 24.0, 40.0, 50.0, 100.0)
    private val limitCurrents = listOf(0.25, 0.5, 0.75, 1.0, 1.5, 2.0)
    private val powerCurrents = listOf(0.5, 1.0, 1.5, 2.0, 3.0)
    private val supplies = listOf(12.0, 24.0, 120.0, 230.0)
    private val deviceCurrents = listOf(0.25, 0.5, 1.0, 2.0, 2.5)
    private val sourceVoltages = listOf(5.0, 6.0, 9.0, 12.0, 24.0)
    private val loadResistances = listOf(3.0, 4.0, 6.0, 8.0, 12.0, 18.0)

    val topic = Topic(
        id = "ohms_law",
        title = "Ohm's Law",
        subtitle = "The foundation of circuit analysis",
        icon = "\u26A1",
        order = 0,
        lesson = Lesson(
            title = "Ohm's Law: The First Quest",
            sections = listOf(
                LessonSection(
                    heading = "What is Ohm's Law?",
                    content = "Ohm's Law is the fundamental relationship between voltage, current, and resistance in electrical circuits. Discovered by Georg Simon Ohm in 1827, this law states that the current through a conductor is directly proportional to the voltage across it and inversely proportional to the resistance.",
                    formula = "V = I × R",
                    keyPoint = "Voltage (V) = Current (I) × Resistance (R)"
                ),
                LessonSection(
                    heading = "Understanding the Variables",
                    content = "Voltage (V) is measured in Volts and represents the electrical pressure or potential difference. Current (I) is measured in Amperes (Amps) and represents the flow of electric charge. Resistance (R) is measured in Ohms (\u03A9) and represents the opposition to current flow.",
                    keyPoint = "V = Volts, I = Amps, R = Ohms (\u03A9)"
                ),
                LessonSection(
                    heading = "Rearranging the Formula",
                    content = "Ohm's Law can be rearranged to solve for any variable:\n\n\u2022 To find Voltage: V = I \u00D7 R\n\u2022 To find Current: I = V / R\n\u2022 To find Resistance: R = V / I\n\nThink of it as a triangle: cover the variable you want to find, and the remaining two show you the formula.",
                    formula = "I = V / R\nR = V / I"
                ),
                LessonSection(
                    heading = "Power in Circuits",
                    content = "Electrical power tells us how much energy is consumed or produced per second. Power is measured in Watts (W). Using Ohm's Law, we can derive multiple power formulas:\n\n\u2022 P = V \u00D7 I (Power = Voltage \u00D7 Current)\n\u2022 P = I\u00B2 \u00D7 R (Power = Current\u00B2 \u00D7 Resistance)\n\u2022 P = V\u00B2 / R (Power = Voltage\u00B2 / Resistance)",
                    formula = "P = V \u00D7 I = I\u00B2R = V\u00B2/R",
                    keyPoint = "Power combines Ohm's Law with energy concepts"
                ),
                LessonSection(
                    heading = "Practical Example",
                    content = "Consider a simple circuit with a 9V battery and a 100\u03A9 resistor:\n\n\u2022 Current: I = V/R = 9/100 = 0.09A (90mA)\n\u2022 Power: P = V\u00D7I = 9\u00D70.09 = 0.81W\n\nThis is the foundation of all circuit analysis. Master this, and you've unlocked the first level of your electrical engineering quest!",
                    keyPoint = "Always identify what you know and what you need to find"
                )
            )
        ),
        quiz = Quiz(
            title = "Ohm's Law Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ohm_mc1",
                    questionText = "What does Ohm's Law state?",
                    options = listOf(
                        "V = I \u00D7 R",
                        "V = I / R",
                        "V = I + R",
                        "V = I - R"
                    ),
                    correctIndex = 0,
                    explanation = "Ohm's Law states that Voltage equals Current times Resistance: V = I \u00D7 R"
                ),
                Question.MultipleChoice(
                    id = "ohm_mc2",
                    questionText = "If the resistance in a circuit doubles while voltage stays the same, what happens to the current?",
                    options = listOf(
                        "It doubles",
                        "It halves",
                        "It stays the same",
                        "It quadruples"
                    ),
                    correctIndex = 1,
                    explanation = "From I = V/R, doubling R while V stays constant means I is halved."
                ),
                Question.MultipleChoice(
                    id = "ohm_mc3",
                    questionText = "Which unit is used to measure electrical resistance?",
                    options = listOf(
                        "Watts",
                        "Volts",
                        "Ohms",
                        "Amperes"
                    ),
                    correctIndex = 2,
                    explanation = "Resistance is measured in Ohms (\u03A9), named after Georg Simon Ohm."
                ),
                Question.MultipleChoice(
                    id = "ohm_mc4",
                    questionText = "If both the voltage across a resistor and its resistance double, what happens to the current?",
                    options = listOf(
                        "It doubles",
                        "It halves",
                        "It stays the same",
                        "It quadruples"
                    ),
                    correctIndex = 2,
                    explanation = "I = V/R. Doubling both gives 2V/2R = V/R, so the current is unchanged."
                ),
                Question.MultipleChoice(
                    id = "ohm_mc5",
                    questionText = "The current through a fixed resistor doubles. What happens to the power it dissipates?",
                    options = listOf(
                        "It doubles",
                        "It quadruples",
                        "It halves",
                        "It stays the same"
                    ),
                    correctIndex = 1,
                    explanation = "P = I²R. Doubling I multiplies the power by 2² = 4."
                )
            ),
            generators = listOf(
                generator("ohm_ni1") { r ->
                    val i = currents.pick(r)
                    val res = resistances.pick(r)
                    val v = i * res
                    Numeric(
                        text = "${articleCap(v)} ${fmt(v)}V source is connected to ${article(res)} ${fmt(res)}Ω resistor. " +
                            "What is the current in Amps?",
                        answer = i, unit = "A",
                        explanation = "I = V/R = ${fmt(v)}/${fmt(res)} = ${fmt(i)}A"
                    )
                },
                generator("ohm_ni2") { r ->
                    val res = limitResistances.pick(r)
                    val i = limitCurrents.pick(r)
                    val v = res * i
                    Numeric(
                        text = "What resistance limits the current to ${fmt(i)}A from ${article(v)} ${fmt(v)}V source? Answer in Ohms.",
                        answer = res, unit = "Ω",
                        explanation = "R = V/I = ${fmt(v)}/${fmt(i)} = ${fmt(res)}Ω"
                    )
                },
                generator("ohm_ni3") { r ->
                    val i = powerCurrents.pick(r)
                    val res = resistances.pick(r)
                    val p = i * i * res
                    Numeric(
                        text = "${articleCap(res)} ${fmt(res)}Ω resistor carries ${fmt(i)}A of current. What is the power dissipated in Watts?",
                        answer = p, unit = "W",
                        explanation = "P = I² × R = ${fmt(i)}² × ${fmt(res)} = ${fmt(p)}W"
                    )
                },
                generator("ohm_ni4") { r ->
                    val v = supplies.pick(r)
                    val i = deviceCurrents.pick(r)
                    val p = v * i
                    Numeric(
                        text = "A device rated at ${fmt(p)}W operates on a ${fmt(v)}V supply. What current does it draw in Amps?",
                        answer = i, unit = "A",
                        explanation = "I = P/V = ${fmt(p)}/${fmt(v)} = ${fmt(i)}A"
                    )
                },
                generator("ohm_ni5") { r ->
                    val v = sourceVoltages.pick(r)
                    val res = loadResistances.pick(r)
                    val p = v * v / res
                    Numeric(
                        text = "${articleCap(v)} ${fmt(v)}V source drives ${article(res)} ${fmt(res)}Ω resistor. What power does it dissipate, " +
                            "in Watts? Round to 2 decimal places.",
                        answer = p, unit = "W",
                        explanation = "P = V²/R = ${fmt(v)}²/${fmt(res)} = ${fmt(v * v)}/${fmt(res)} = ${fmt(p, 2)}W"
                    )
                }
            )
        )
    )
}
