package com.circuitqueest.app.data.content

object OhmsLawContent {
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
                Question.NumericInput(
                    id = "ohm_ni1",
                    questionText = "A 12V battery is connected to a 4\u03A9 resistor. What is the current in Amps?",
                    correctAnswer = 3.0,
                    tolerance = 0.1,
                    unit = "A",
                    explanation = "I = V/R = 12/4 = 3A"
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
                Question.NumericInput(
                    id = "ohm_ni2",
                    questionText = "What resistance is needed to limit current to 0.5A from a 10V source? Answer in Ohms.",
                    correctAnswer = 20.0,
                    tolerance = 0.1,
                    unit = "\u03A9",
                    explanation = "R = V/I = 10/0.5 = 20\u03A9"
                ),
                Question.NumericInput(
                    id = "ohm_ni3",
                    questionText = "A 5\u03A9 resistor carries 2A of current. What is the power dissipated in Watts?",
                    correctAnswer = 20.0,
                    tolerance = 0.1,
                    unit = "W",
                    explanation = "P = I\u00B2 \u00D7 R = 2\u00B2 \u00D7 5 = 4 \u00D7 5 = 20W"
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
                Question.NumericInput(
                    id = "ohm_ni4",
                    questionText = "A device rated at 60W operates on a 120V supply. What current does it draw in Amps?",
                    correctAnswer = 0.5,
                    tolerance = 0.05,
                    unit = "A",
                    explanation = "I = P/V = 60/120 = 0.5A"
                )
            )
        )
    )
}
