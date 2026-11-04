package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import com.circuitqueest.app.data.content.QuestionMath.pickThree
import com.circuitqueest.app.data.content.QuestionMath.pickTwo

object KirchhoffsContent {
    private val nodeInCurrents = listOf(4.0, 5.0, 6.0, 8.0, 10.0, 12.0)
    private val nodeOutCurrents = listOf(1.0, 2.0, 3.0)
    private val loopCurrents = listOf(0.5, 1.0, 1.5, 2.0, 3.0)
    private val loopResistors = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 8.0, 10.0)
    private val bigBatteries = listOf(9.0, 12.0, 15.0, 24.0)
    private val smallBatteries = listOf(3.0, 5.0, 6.0)
    private val opposingResistors = listOf(2.0, 3.0, 5.0, 6.0, 10.0)
    private val branchCurrents = listOf(2.0, 3.0, 4.0, 5.0, 6.0)
    private val kvlSupplies = listOf(12.0, 24.0, 48.0)
    private val firstDrops = listOf(2.0, 3.0, 4.0, 5.0)
    private val secondDrops = listOf(1.5, 2.5, 3.5, 4.5)

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
                Question.MultipleChoice(
                    id = "kl_mc4",
                    questionText = "Kirchhoff's Voltage Law (KVL) follows from the conservation of which quantity?",
                    options = listOf(
                        "Charge",
                        "Energy",
                        "Current",
                        "Resistance"
                    ),
                    correctIndex = 1,
                    explanation = "KVL follows from conservation of energy: a charge that goes around a closed loop returns to the same potential, so the voltage rises and drops cancel."
                ),
                Question.MultipleChoice(
                    id = "kl_mc5",
                    questionText = "Going around a loop in the same direction as the current, the voltage across a resistor is a:",
                    options = listOf(
                        "Rise of I×R",
                        "Drop of I×R",
                        "Drop of I/R",
                        "Zero"
                    ),
                    correctIndex = 1,
                    explanation = "Current flows from higher to lower potential through a resistor, so following the current you see a drop of I×R (written −IR in the KVL sum)."
                )
            ),
            generators = listOf(
                generator("kl_ni1") { r ->
                    val iIn = nodeInCurrents.pick(r)
                    val iOut = nodeOutCurrents.pick(r)
                    Numeric(
                        text = "At a node, three wires meet. Wire 1 carries ${fmt(iIn)}A into the node, Wire 2 carries " +
                            "${fmt(iOut)}A out. How much current flows through Wire 3 (in Amps, out of the node)?",
                        answer = iIn - iOut, unit = "A",
                        explanation = "By KCL: I_in = I_out. ${fmt(iIn)} = ${fmt(iOut)} + I₃, so I₃ = ${fmt(iIn - iOut)}A (out)."
                    )
                },
                generator("kl_ni2") { r ->
                    val i = loopCurrents.pick(r)
                    val (a, b, c) = loopResistors.pickThree(r)
                    val v = i * (a + b + c)
                    Numeric(
                        text = "A loop contains ${article(v)} ${fmt(v)}V battery and three resistors of ${fmt(a)}Ω, ${fmt(b)}Ω, and " +
                            "${fmt(c)}Ω in series. What is the current in Amps?",
                        answer = i, unit = "A",
                        explanation = "By KVL: ${fmt(v)} − I(${fmt(a)} + ${fmt(b)} + ${fmt(c)}) = 0 → I = ${fmt(v)}/${fmt(a + b + c)} = ${fmt(i)}A"
                    )
                },
                generator("kl_ni3") { r ->
                    val v1 = bigBatteries.pick(r)
                    val v2 = smallBatteries.pick(r)
                    val res = opposingResistors.pick(r)
                    val i = (v1 - v2) / res
                    Numeric(
                        text = "In a loop, ${article(v1)} ${fmt(v1)}V battery and ${article(v2)} ${fmt(v2)}V battery (opposing) are " +
                            "connected with ${article(res)} ${fmt(res)}Ω resistor. What current flows, in Amps? Round to 2 decimal places.",
                        answer = i, unit = "A",
                        explanation = "By KVL: ${fmt(v1)} − ${fmt(v2)} − ${fmt(res)}I = 0 → I = ${fmt(v1 - v2)}/${fmt(res)} = ${fmt(i, 2)}A"
                    )
                },
                generator("kl_ni4") { r ->
                    val (a, b) = branchCurrents.pickTwo(r)
                    val out = nodeOutCurrents.pick(r)
                    val rest = a + b - out
                    Numeric(
                        text = "A node has currents of ${fmt(a)}A and ${fmt(b)}A entering, and ${fmt(out)}A leaving through " +
                            "one branch. How much current (in Amps) leaves through the remaining branch?",
                        answer = rest, unit = "A",
                        explanation = "By KCL: ${fmt(a)} + ${fmt(b)} = ${fmt(out)} + I₄ → I₄ = ${fmt(rest)}A"
                    )
                },
                generator("kl_ni5") { r ->
                    val vs = kvlSupplies.pick(r)
                    val va = firstDrops.pick(r)
                    val vb = secondDrops.pick(r)
                    val vc = vs - va - vb
                    Numeric(
                        text = "A ${fmt(vs)}V source drives three resistors in series. The first drops ${fmt(va)}V and the " +
                            "second drops ${fmt(vb)}V. What is the voltage across the third resistor, in Volts?",
                        answer = vc, unit = "V",
                        explanation = "By KVL the drops add up to the source: V₃ = ${fmt(vs)} − ${fmt(va)} − ${fmt(vb)} = ${fmt(vc)}V"
                    )
                }
            )
        )
    )
}
