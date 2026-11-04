package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object DigitalLogicContent {
    private const val HEX_BASE = 16
    private val inputCounts = listOf(2, 3, 4, 5, 6)
    private val baseRules = mapOf(
        "AND" to "only if both inputs are 1", "OR" to "if either input is 1", "XOR" to "if the inputs differ"
    )
    private val gateNames = listOf("AND", "OR", "XOR", "NAND", "NOR", "XNOR")
    private val nandBuilds = listOf(
        Triple("NOT", 1, "Tie both inputs of one NAND together: NAND(A, A) = NOT A. 1 gate"),
        Triple("AND", 2, "A NAND followed by a NAND wired as an inverter: 2 gates"),
        Triple("OR", 3, "Invert each input (2 NANDs), then NAND them: NAND(NOT A, NOT B) = A OR B by De Morgan. 3 gates"),
        Triple("NOR", 4, "Build OR from 3 NANDs, then invert it with one more: 4 gates"),
        Triple("XOR", 4, "The classic circuit: N = NAND(A, B), then NAND(A, N) and NAND(B, N) feed a final NAND. 4 gates"),
        Triple("XNOR", 5, "Build XOR from 4 NANDs, then invert it with one more: 5 gates")
    )
    private val functionInputs = listOf(1, 2, 3)
    private val byteValues = (1..255).toList()

    /** "an AND", "an OR", "an XOR", "an XNOR", but "a NAND", "a NOR". */
    private fun articleFor(gate: String) = if (gate.first() in "AOX") "an" else "a"

    private fun evaluate(gate: String, a: Int, b: Int): Int = when (gate.removePrefix("N").ifEmpty { gate }) {
        "AND" -> a and b
        "OR" -> a or b
        else -> a xor b
    }.let { if (gate.startsWith("N") || gate == "XNOR") 1 - it else it }

    val topic = Topic(
        id = "digital_logic",
        title = "Digital Logic Gates",
        subtitle = "The foundation of digital electronics",
        icon = "\uD83E\uDDE0",
        order = 14,
        lesson = Lesson(
            title = "Digital Logic: Zeros and Ones",
            sections = listOf(
                LessonSection(
                    heading = "Analog vs. Digital",
                    content = "Analog signals vary continuously (like a dimmer switch), while digital signals have only two states: HIGH (1) and LOW (0), like an on/off switch.\n\nDigital circuits represent these states with voltage levels:\n\u2022 HIGH (logic 1): typically 3.3V or 5V\n\u2022 LOW (logic 0): typically 0V (ground)\n\nAll modern computers, phones, and digital devices are built from simple logic gates.",
                    keyPoint = "Digital = two states only: HIGH (1) and LOW (0)"
                ),
                LessonSection(
                    heading = "AND, OR, and NOT Gates",
                    content = "The three fundamental logic gates:\n\n\u2022 AND: Output is 1 only when ALL inputs are 1.\n  A=1, B=1 \u2192 Output=1; otherwise Output=0\n\n\u2022 OR: Output is 1 when ANY input is 1.\n  A=0, B=0 \u2192 Output=0; otherwise Output=1\n\n\u2022 NOT (Inverter): Output is the opposite of the input.\n  A=1 \u2192 Output=0; A=0 \u2192 Output=1\n\nEvery digital circuit can be built from these three gates.",
                    formula = "AND: Y = A \u00B7 B\nOR: Y = A + B\nNOT: Y = A\u0304",
                    keyPoint = "AND = all inputs HIGH; OR = any input HIGH; NOT = invert"
                ),
                LessonSection(
                    heading = "NAND and NOR Gates",
                    content = "NAND and NOR are the inverses of AND and OR:\n\n\u2022 NAND: Output is 0 only when ALL inputs are 1 (NOT-AND).\n\u2022 NOR: Output is 0 when ANY input is 1 (NOT-OR).\n\nNAND is called a universal gate because you can build any other gate using only NAND gates. The same is true for NOR. This makes chip manufacturing simpler and more efficient.",
                    formula = "NAND: Y = (A \u00B7 B)\u0304\nNOR: Y = (A + B)\u0304",
                    keyPoint = "NAND and NOR are universal gates \u2014 any logic function can be built from either one alone"
                ),
                LessonSection(
                    heading = "XOR Gate",
                    content = "XOR (Exclusive OR) outputs 1 when the inputs are different:\n\n\u2022 A=0, B=0 \u2192 0\n\u2022 A=0, B=1 \u2192 1\n\u2022 A=1, B=0 \u2192 1\n\u2022 A=1, B=1 \u2192 0\n\nXOR is essential in arithmetic circuits (adders), error detection (parity checks), and encryption. It can be thought of as asking: \"Are the inputs different?\"",
                    formula = "XOR: Y = A \u2295 B",
                    keyPoint = "XOR = 1 when inputs differ; used in adders and checksums"
                ),
                LessonSection(
                    heading = "Truth Tables",
                    content = "A truth table lists every possible input combination and the resulting output. For N inputs, there are 2^N rows.\n\n2-input AND truth table:\n  0,0 \u2192 0 | 0,1 \u2192 0 | 1,0 \u2192 0 | 1,1 \u2192 1\n\nTruth tables are the fundamental way to define and verify logic gate behavior. Any Boolean function can be completely described by its truth table.",
                    keyPoint = "N inputs = 2^N rows in the truth table"
                ),
                LessonSection(
                    heading = "Boolean Algebra Basics",
                    content = "Boolean algebra provides rules for simplifying logic expressions:\n\n\u2022 Identity: A \u00B7 1 = A, A + 0 = A\n\u2022 Null: A \u00B7 0 = 0, A + 1 = 1\n\u2022 Complement: A \u00B7 A\u0304 = 0, A + A\u0304 = 1\n\u2022 De Morgan's Laws:\n  (A \u00B7 B)\u0304 = A\u0304 + B\u0304\n  (A + B)\u0304 = A\u0304 \u00B7 B\u0304\n\nDe Morgan's laws are especially important \u2014 they let you convert between AND/OR expressions and are used extensively in digital circuit design.",
                    formula = "(A \u00B7 B)\u0304 = A\u0304 + B\u0304\n(A + B)\u0304 = A\u0304 \u00B7 B\u0304",
                    keyPoint = "De Morgan's: break the bar, change the sign"
                )
            )
        ),
        quiz = Quiz(
            title = "Digital Logic Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "dl_mc1",
                    questionText = "What is the output of an AND gate when A=1 and B=0?",
                    options = listOf(
                        "1",
                        "0",
                        "Undefined",
                        "Depends on the supply voltage"
                    ),
                    correctIndex = 1,
                    explanation = "An AND gate outputs 1 only when ALL inputs are 1. Since B=0, the output is 0."
                ),
                Question.MultipleChoice(
                    id = "dl_mc2",
                    questionText = "Which gate is considered a universal gate?",
                    options = listOf(
                        "AND",
                        "OR",
                        "XOR",
                        "NAND"
                    ),
                    correctIndex = 3,
                    explanation = "NAND (and NOR) are universal gates \u2014 any logic function can be implemented using only NAND gates."
                ),
                Question.MultipleChoice(
                    id = "dl_mc3",
                    questionText = "What is the output of XOR when both inputs are 1?",
                    options = listOf(
                        "1",
                        "0",
                        "Undefined",
                        "HIGH impedance"
                    ),
                    correctIndex = 1,
                    explanation = "XOR outputs 1 only when inputs are different. When both are 1, they're the same, so output is 0."
                ),
                Question.MultipleChoice(
                    id = "dl_mc4",
                    questionText = "By De Morgan's law, NOT(A AND B) is equivalent to:",
                    options = listOf(
                        "NOT(A) AND NOT(B)",
                        "NOT(A) OR NOT(B)",
                        "A OR B",
                        "A AND NOT(B)"
                    ),
                    correctIndex = 1,
                    explanation = "De Morgan's law: (A \u00B7 B)\u0304 = A\u0304 + B\u0304. Break the bar, change the operator (AND \u2192 OR)."
                ),
                Question.MultipleChoice(
                    id = "dl_mc5",
                    questionText = "In a half adder, which gate produces the SUM bit?",
                    options = listOf(
                        "AND",
                        "OR",
                        "XOR",
                        "NAND"
                    ),
                    correctIndex = 2,
                    explanation = "Sum = A XOR B (1 when exactly one input is 1), and Carry = A AND B. Together they add two 1-bit numbers."
                )
            ),
            generators = listOf(
                generator("dl_ni1") { r ->
                    val n = inputCounts.pick(r)
                    val rows = 1 shl n
                    Numeric(
                        text = "How many rows does a truth table have for a $n-input logic function?",
                        answer = rows.toDouble(), unit = "rows", exact = true,
                        explanation = "Each input doubles the combinations: 2^N rows, so 2^$n = $rows rows"
                    )
                },
                generator("dl_ni2") { r ->
                    val gate = gateNames.pick(r)
                    val a = r.nextInt(2)
                    val b = r.nextInt(2)
                    val out = evaluate(gate, a, b)
                    val base = if (gate == "XNOR") "XOR" else gate.removePrefix("N").ifEmpty { gate }
                    val inverted = base != gate
                    val baseOut = if (inverted) 1 - out else out
                    Numeric(
                        text = "${articleFor(gate).replaceFirstChar { it.uppercase() }} $gate gate has inputs A = $a and " +
                            "B = $b. What is its output (0 or 1)?",
                        answer = out.toDouble(), unit = "", exact = true,
                        explanation = if (inverted) {
                            "$gate is the inverse of $base, which outputs 1 ${baseRules.getValue(base)}. " +
                                "$base gives $baseOut, so $gate gives $out"
                        } else {
                            "$gate outputs 1 ${baseRules.getValue(base)}. With A = $a and B = $b the output is $out"
                        }
                    )
                },
                generator("dl_ni3") { r ->
                    val (target, count, how) = nandBuilds.pick(r)
                    Numeric(
                        text = "What is the fewest 2-input NAND gates needed to build ${articleFor(target)} $target gate?",
                        answer = count.toDouble(), unit = "", exact = true,
                        explanation = how
                    )
                },
                generator("dl_ni4") { r ->
                    val v = byteValues.pick(r)
                    val bits = Integer.toBinaryString(v).padStart(Byte.SIZE_BITS, '0')
                    val parts = bits.mapIndexedNotNull { i, c -> if (c == '1') 1 shl (bits.length - 1 - i) else null }
                    Numeric(
                        text = "What is the binary number $bits in decimal?",
                        answer = v.toDouble(), unit = "", exact = true,
                        explanation = "Add the place values of the 1 bits: ${parts.joinToString(" + ")} = $v"
                    )
                },
                generator("dl_ni5") { r ->
                    val n = functionInputs.pick(r)
                    val rows = 1 shl n
                    val count = 1 shl rows
                    Numeric(
                        text = "How many different Boolean functions of $n input${if (n > 1) "s" else ""} are there?",
                        answer = count.toDouble(), unit = "functions", exact = true,
                        explanation = "The truth table has 2^$n = $rows rows, and each row's output can be 0 or 1: " +
                            "2^$rows = $count functions"
                    )
                },
                generator("dl_ni6") { r ->
                    val v = byteValues.pick(r)
                    val hex = "%02X".format(v)
                    val hi = v / HEX_BASE
                    val lo = v % HEX_BASE
                    Numeric(
                        text = "What is the hexadecimal number 0x$hex in decimal?",
                        answer = v.toDouble(), unit = "", exact = true,
                        explanation = "0x$hex = ${hex[0]} × 16 + ${hex[1]}, that is $hi × 16 + $lo = $v"
                    )
                }
            )
        )
    )
}
