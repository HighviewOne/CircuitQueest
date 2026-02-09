package com.circuitqueest.app.data.content

object SeriesParallelContent {
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
                Question.NumericInput(
                    id = "sp_ni1",
                    questionText = "Three resistors of 10\u03A9, 20\u03A9, and 30\u03A9 are connected in series. What is the total resistance in Ohms?",
                    correctAnswer = 60.0,
                    tolerance = 0.1,
                    unit = "\u03A9",
                    explanation = "R_total = 10 + 20 + 30 = 60\u03A9"
                ),
                Question.NumericInput(
                    id = "sp_ni2",
                    questionText = "Two resistors of 20\u03A9 and 30\u03A9 are connected in parallel. What is the total resistance in Ohms?",
                    correctAnswer = 12.0,
                    tolerance = 0.1,
                    unit = "\u03A9",
                    explanation = "R_total = (20\u00D730)/(20+30) = 600/50 = 12\u03A9"
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
                Question.NumericInput(
                    id = "sp_ni3",
                    questionText = "A 12V battery powers two resistors in series: 3\u03A9 and 6\u03A9. What is the voltage across the 6\u03A9 resistor?",
                    correctAnswer = 8.0,
                    tolerance = 0.1,
                    unit = "V",
                    explanation = "V = 12 \u00D7 (6/(3+6)) = 12 \u00D7 (6/9) = 12 \u00D7 0.667 = 8V"
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
                Question.NumericInput(
                    id = "sp_ni4",
                    questionText = "Three identical 30\u03A9 resistors are connected in parallel. What is the total resistance in Ohms?",
                    correctAnswer = 10.0,
                    tolerance = 0.1,
                    unit = "\u03A9",
                    explanation = "For N identical resistors in parallel: R_total = R/N = 30/3 = 10\u03A9"
                )
            )
        )
    )
}
