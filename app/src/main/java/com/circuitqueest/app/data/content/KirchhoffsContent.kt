package com.circuitqueest.app.data.content

object KirchhoffsContent {
    val topic = Topic(
        id = "kirchhoffs",
        title = "Kirchhoff's Laws",
        subtitle = "Master node and loop analysis",
        icon = "\uD83C\uDF10",
        order = 2,
        lesson = Lesson(
            title = "Kirchhoff's Laws: The Grand Circuit",
            sections = listOf(
                LessonSection(
                    heading = "Gustav Kirchhoff's Legacy",
                    content = "In 1845, Gustav Kirchhoff formulated two laws that are essential for analyzing complex circuits that can't be simplified using series/parallel rules alone. These laws are based on conservation of charge and conservation of energy.",
                    keyPoint = "KCL = conservation of charge, KVL = conservation of energy"
                ),
                LessonSection(
                    heading = "Kirchhoff's Current Law (KCL)",
                    content = "KCL states that the total current entering a node (junction) equals the total current leaving that node. In other words, current is conserved - charge doesn't accumulate at a junction.\n\nExample: If 5A enters a node and splits into two branches, and one branch carries 3A, the other must carry 2A.",
                    formula = "\u03A3 I_in = \u03A3 I_out",
                    keyPoint = "Sum of currents at any node = 0"
                ),
                LessonSection(
                    heading = "KCL in Practice",
                    content = "To apply KCL:\n1. Identify all nodes (connection points) in the circuit\n2. Pick a node to analyze\n3. Assign current directions (if unknown, assume a direction)\n4. Write the equation: currents in = currents out\n5. If your assumed direction was wrong, the answer will be negative\n\nConvention: Currents entering a node are positive, leaving are negative.",
                    keyPoint = "If a calculated current is negative, the actual direction is opposite to your assumption"
                ),
                LessonSection(
                    heading = "Kirchhoff's Voltage Law (KVL)",
                    content = "KVL states that the sum of all voltage changes around any closed loop in a circuit equals zero. This means the energy supplied by sources equals the energy consumed by resistors.\n\nThink of it like hiking a mountain trail that forms a loop - your net elevation change when you return to the start is always zero.",
                    formula = "\u03A3 V_loop = 0",
                    keyPoint = "Sum of voltages around any closed loop = 0"
                ),
                LessonSection(
                    heading = "KVL in Practice",
                    content = "To apply KVL:\n1. Choose a closed loop in the circuit\n2. Pick a direction to traverse (clockwise or counterclockwise)\n3. For voltage sources: +V if traversing from - to +, -V if from + to -\n4. For resistors: -IR if traversing in the direction of current\n5. Set the sum equal to zero and solve\n\nExample: A loop with a 12V battery and two resistors (3\u03A9 and 6\u03A9) with current I:\n12 - 3I - 6I = 0 \u2192 I = 12/9 = 1.33A",
                    formula = "V_source - IR\u2081 - IR\u2082 = 0",
                    keyPoint = "Voltage rises = Voltage drops in any loop"
                ),
                LessonSection(
                    heading = "Combining KCL and KVL",
                    content = "Complex circuits often require both laws:\n\u2022 Use KCL at nodes to relate branch currents\n\u2022 Use KVL around loops to create equations\n\u2022 Solve the system of equations simultaneously\n\nThis method works for ANY circuit, no matter how complex. It's the ultimate tool in your circuit analysis arsenal!",
                    keyPoint = "KCL + KVL together can solve any linear circuit"
                )
            )
        ),
        quiz = Quiz(
            title = "Kirchhoff's Laws Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "kl_mc1",
                    questionText = "What does Kirchhoff's Current Law (KCL) state?",
                    options = listOf(
                        "Voltage around a loop sums to zero",
                        "Current entering a node equals current leaving",
                        "Power is conserved in a circuit",
                        "Resistance adds in series"
                    ),
                    correctIndex = 1,
                    explanation = "KCL states that the sum of currents entering a node equals the sum of currents leaving that node (conservation of charge)."
                ),
                Question.NumericInput(
                    id = "kl_ni1",
                    questionText = "At a node, three wires meet. Wire 1 carries 5A into the node, Wire 2 carries 3A out. How much current flows through Wire 3 (in Amps, out of the node)?",
                    correctAnswer = 2.0,
                    tolerance = 0.1,
                    unit = "A",
                    explanation = "By KCL: I_in = I_out. 5A = 3A + I\u2083, so I\u2083 = 2A (out)."
                ),
                Question.MultipleChoice(
                    id = "kl_mc2",
                    questionText = "What does Kirchhoff's Voltage Law (KVL) state?",
                    options = listOf(
                        "Current entering equals current leaving a node",
                        "Power is always conserved",
                        "The sum of voltages around any closed loop is zero",
                        "Voltage divides equally among resistors"
                    ),
                    correctIndex = 2,
                    explanation = "KVL states that the algebraic sum of all voltages around any closed loop in a circuit equals zero (conservation of energy)."
                ),
                Question.NumericInput(
                    id = "kl_ni2",
                    questionText = "A loop contains a 24V battery and three resistors of 2\u03A9, 4\u03A9, and 6\u03A9 in series. What is the current in Amps?",
                    correctAnswer = 2.0,
                    tolerance = 0.1,
                    unit = "A",
                    explanation = "By KVL: 24 - 2I - 4I - 6I = 0 \u2192 24 = 12I \u2192 I = 2A"
                ),
                Question.NumericInput(
                    id = "kl_ni3",
                    questionText = "In a loop, a 10V battery and a 5V battery (opposing) are connected with a 5\u03A9 resistor. What current flows in Amps?",
                    correctAnswer = 1.0,
                    tolerance = 0.1,
                    unit = "A",
                    explanation = "By KVL: 10 - 5 - 5I = 0 \u2192 5 = 5I \u2192 I = 1A"
                ),
                Question.MultipleChoice(
                    id = "kl_mc3",
                    questionText = "KCL is based on the conservation of which quantity?",
                    options = listOf(
                        "Energy",
                        "Voltage",
                        "Charge",
                        "Power"
                    ),
                    correctIndex = 2,
                    explanation = "KCL is based on conservation of charge - charge cannot accumulate at a node."
                ),
                Question.NumericInput(
                    id = "kl_ni4",
                    questionText = "A node has currents of 4A and 6A entering, and 3A leaving through one branch. How much current (in Amps) leaves through the remaining branch?",
                    correctAnswer = 7.0,
                    tolerance = 0.1,
                    unit = "A",
                    explanation = "By KCL: 4 + 6 = 3 + I\u2084 \u2192 I\u2084 = 7A"
                )
            )
        )
    )
}
