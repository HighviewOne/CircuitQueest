package com.circuitqueest.app.data.content

object DigitalLogicContent {
    val topic = Topic(
        id = "digital_logic",
        title = "Digital Logic Gates",
        subtitle = "The foundation of digital electronics",
        icon = "\uD83E\uDDE0",
        order = 9,
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
                Question.NumericInput(
                    id = "dl_ni1",
                    questionText = "How many rows does a truth table have for a 3-input logic gate?",
                    correctAnswer = 8.0,
                    tolerance = 0.1,
                    unit = "rows",
                    explanation = "A truth table with N inputs has 2^N rows. 2^3 = 8 rows."
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
                Question.NumericInput(
                    id = "dl_ni2",
                    questionText = "A NOR gate has inputs A=0 and B=0. What is the output?",
                    correctAnswer = 1.0,
                    tolerance = 0.1,
                    unit = "",
                    explanation = "NOR is NOT-OR. OR(0,0) = 0, then NOT(0) = 1. Output is 1."
                ),
                Question.NumericInput(
                    id = "dl_ni3",
                    questionText = "How many 2-input NAND gates are needed to build a NOT gate?",
                    correctAnswer = 1.0,
                    tolerance = 0.1,
                    unit = "gates",
                    explanation = "Connect both inputs of a NAND gate together: NAND(A,A) = NOT(A). Only 1 gate needed."
                )
            )
        )
    )
}
