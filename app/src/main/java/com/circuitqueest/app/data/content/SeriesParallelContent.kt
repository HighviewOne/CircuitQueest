package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import com.circuitqueest.app.data.content.QuestionMath.pickThree
import com.circuitqueest.app.data.content.QuestionMath.pickTwo

object SeriesParallelContent {
    private val seriesValues = listOf(10.0, 22.0, 33.0, 47.0, 68.0, 100.0, 150.0, 220.0)
    private val parallelValues = listOf(10.0, 12.0, 15.0, 20.0, 30.0, 40.0, 60.0, 100.0)
    private val dividerSupplies = listOf(5.0, 9.0, 12.0, 15.0, 24.0)
    private val dividerValues = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 10.0)
    private val identicalValues = listOf(10.0, 20.0, 30.0, 60.0, 100.0, 120.0, 150.0, 300.0)
    private val identicalCounts = listOf("Two" to 2, "Three" to 3, "Four" to 4, "Five" to 5, "Six" to 6)
    private val totalCurrents = listOf(1.0, 2.0, 3.0, 6.0)

    val topic = Topic(
        id = "series_parallel",
        title = "Series & Parallel",
        subtitle = "Circuit combinations mastery",
        icon = "\uD83D\uDD0C",
        order = 1,
        lesson = Lesson(
            title = "Series & Parallel Circuits: Branching Paths",
            sections = listOf(
                LessonSection(
                    heading = "Series Circuits",
                    content = "In a series circuit, components are connected end-to-end, forming a single path for current flow. The same current flows through every component. If one component fails (opens), the entire circuit stops working - like old Christmas lights!",
                    keyPoint = "Series: One path, same current everywhere"
                ),
                LessonSection(
                    heading = "Series Resistance",
                    content = "In series, resistances simply add up. The total resistance is always greater than the largest individual resistance.\n\nFor example, three resistors of 10\u03A9, 20\u03A9, and 30\u03A9 in series:\nR_total = 10 + 20 + 30 = 60\u03A9",
                    formula = "R_total = R\u2081 + R\u2082 + R\u2083 + ...",
                    keyPoint = "Series resistances ADD together"
                ),
                LessonSection(
                    heading = "Voltage Division in Series",
                    content = "In a series circuit, the source voltage divides across components proportionally to their resistance. A larger resistance gets a larger share of voltage.\n\nVoltage across R\u2081 = V_source \u00D7 (R\u2081 / R_total)\n\nThe sum of all voltage drops equals the source voltage.",
                    formula = "V_n = V_source \u00D7 (R_n / R_total)",
                    keyPoint = "Voltage DIVIDES in series, proportional to resistance"
                ),
                LessonSection(
                    heading = "Parallel Circuits",
                    content = "In a parallel circuit, components are connected across the same two nodes, providing multiple paths for current. Each branch has the same voltage across it. If one branch fails, the others continue to operate - like modern home wiring!",
                    keyPoint = "Parallel: Multiple paths, same voltage everywhere"
                ),
                LessonSection(
                    heading = "Parallel Resistance",
                    content = "In parallel, the reciprocals of resistances add up. The total resistance is always less than the smallest individual resistance.\n\nFor two resistors in parallel, there's a shortcut:\nR_total = (R\u2081 \u00D7 R\u2082) / (R\u2081 + R\u2082)\n\nFor example, 20\u03A9 and 30\u03A9 in parallel:\nR_total = (20\u00D730)/(20+30) = 600/50 = 12\u03A9",
                    formula = "1/R_total = 1/R\u2081 + 1/R\u2082 + 1/R\u2083 + ...",
                    keyPoint = "Parallel resistance is LESS than the smallest resistor"
                ),
                LessonSection(
                    heading = "Current Division in Parallel",
                    content = "In a parallel circuit, current divides among branches inversely proportional to their resistance. A smaller resistance carries more current.\n\nFor two resistors:\nI\u2081 = I_total \u00D7 R\u2082 / (R\u2081 + R\u2082)\n\nThe sum of branch currents equals the total current.",
                    formula = "I_n = V / R_n",
                    keyPoint = "Current DIVIDES in parallel, inversely proportional to resistance"
                )
            )
        ),
        quiz = Quiz(
            title = "Series & Parallel Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "sp_mc1",
                    questionText = "In a series circuit, which quantity is the same through all components?",
                    options = listOf(
                        "Voltage",
                        "Current",
                        "Resistance",
                        "Power"
                    ),
                    correctIndex = 1,
                    explanation = "In a series circuit, the same current flows through all components since there is only one path."
                ),
                Question.MultipleChoice(
                    id = "sp_mc2",
                    questionText = "In a parallel circuit, which quantity is the same across all branches?",
                    options = listOf(
                        "Current",
                        "Resistance",
                        "Voltage",
                        "Power"
                    ),
                    correctIndex = 2,
                    explanation = "In a parallel circuit, all branches share the same voltage (potential difference)."
                ),
                Question.MultipleChoice(
                    id = "sp_mc3",
                    questionText = "What happens to total resistance when you add more resistors in parallel?",
                    options = listOf(
                        "It increases",
                        "It decreases",
                        "It stays the same",
                        "It depends on the values"
                    ),
                    correctIndex = 1,
                    explanation = "Adding more parallel paths always decreases total resistance, since there are more paths for current to flow."
                ),
                Question.MultipleChoice(
                    id = "sp_mc4",
                    questionText = "Two bulbs are wired in parallel across a battery. One burns out (becomes an open circuit). What happens to the other bulb?",
                    options = listOf(
                        "It stays lit at the same brightness",
                        "It goes out",
                        "It gets brighter",
                        "It gets dimmer"
                    ),
                    correctIndex = 0,
                    explanation = "Each parallel branch has the full battery voltage across it on its own path, so the other bulb is unaffected (with an ideal battery). In series, both would go out."
                )
            ),
            generators = listOf(
                generator("sp_ni1") { r ->
                    val (a, b, c) = seriesValues.pickThree(r)
                    Numeric(
                        text = "Three resistors of ${fmt(a)}Ω, ${fmt(b)}Ω, and ${fmt(c)}Ω are connected in series. " +
                            "What is the total resistance in Ohms?",
                        answer = a + b + c, unit = "Ω",
                        explanation = "In series, resistances add: R_total = ${fmt(a)} + ${fmt(b)} + ${fmt(c)} = ${fmt(a + b + c)}Ω"
                    )
                },
                generator("sp_ni2") { r ->
                    val (a, b) = parallelValues.pickTwo(r)
                    val total = a * b / (a + b)
                    Numeric(
                        text = "Two resistors of ${fmt(a)}Ω and ${fmt(b)}Ω are connected in parallel. " +
                            "What is the total resistance in Ohms? Round to 2 decimal places.",
                        answer = total, unit = "Ω",
                        explanation = "R_total = (R₁×R₂)/(R₁+R₂) = (${fmt(a)}×${fmt(b)})/(${fmt(a)}+${fmt(b)}) = " +
                            "${fmt(a * b)}/${fmt(a + b)} = ${fmt(total, 2)}Ω"
                    )
                },
                generator("sp_ni3") { r ->
                    val vs = dividerSupplies.pick(r)
                    val (r1, r2) = dividerValues.pickTwo(r)
                    val v2 = vs * r2 / (r1 + r2)
                    Numeric(
                        text = "A ${fmt(vs)}V supply powers two resistors in series: ${fmt(r1)}kΩ and ${fmt(r2)}kΩ. " +
                            "What is the voltage across the ${fmt(r2)}kΩ resistor? Round to 2 decimal places.",
                        answer = v2, unit = "V",
                        explanation = "Voltage divider: V = V_s × R₂/(R₁+R₂) = ${fmt(vs)} × ${fmt(r2)}/${fmt(r1 + r2)} = ${fmt(v2, 2)}V"
                    )
                },
                generator("sp_ni4") { r ->
                    val res = identicalValues.pick(r)
                    val (word, n) = identicalCounts.pick(r)
                    val total = res / n
                    Numeric(
                        text = "$word identical ${fmt(res)}Ω resistors are connected in parallel. " +
                            "What is the total resistance in Ohms? Round to 2 decimal places.",
                        answer = total, unit = "Ω",
                        explanation = "For N identical resistors in parallel: R_total = R/N = ${fmt(res)}/$n = ${fmt(total, 2)}Ω"
                    )
                },
                generator("sp_ni5") { r ->
                    val total = totalCurrents.pick(r)
                    val (r1, r2) = parallelValues.pickTwo(r)
                    val i1 = total * r2 / (r1 + r2)
                    Numeric(
                        text = "A total current of ${fmt(total)}A splits between a ${fmt(r1)}Ω and a ${fmt(r2)}Ω resistor " +
                            "in parallel. How much current flows through the ${fmt(r1)}Ω resistor, in Amps? " +
                            "Round to 2 decimal places.",
                        answer = i1, unit = "A",
                        explanation = "Current divider: I₁ = I × R₂/(R₁+R₂) = ${fmt(total)} × ${fmt(r2)}/${fmt(r1 + r2)} = " +
                            "${fmt(i1, 2)}A. The smaller resistor takes the larger share."
                    )
                },
                generator("sp_ni6") { r ->
                    val r1 = seriesValues.pick(r)
                    val (r2, r3) = parallelValues.pickTwo(r)
                    val par = r2 * r3 / (r2 + r3)
                    val total = r1 + par
                    Numeric(
                        text = "A ${fmt(r1)}Ω resistor is in series with a parallel pair of ${fmt(r2)}Ω and ${fmt(r3)}Ω. " +
                            "What is the total resistance in Ohms? Round to 2 decimal places.",
                        answer = total, unit = "Ω",
                        explanation = "Parallel pair first: (${fmt(r2)}×${fmt(r3)})/(${fmt(r2)}+${fmt(r3)}) = ${fmt(par, 2)}Ω. " +
                            "Then add the series resistor: ${fmt(r1)} + ${fmt(par, 2)} = ${fmt(total, 2)}Ω"
                    )
                }
            )
        )
    )
}
