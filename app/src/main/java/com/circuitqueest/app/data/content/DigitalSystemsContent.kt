package com.circuitqueest.app.data.content

object DigitalSystemsContent {
    val topic = Topic(
        id = "digital_systems",
        title = "Digital Systems",
        subtitle = "Flip-flops, counters, and state machines",
        icon = "\uD83D\uDD22",
        order = 20,
        lesson = Lesson(
            title = "Digital Systems: Sequential Logic",
            sections = listOf(
                LessonSection(
                    heading = "Combinational vs. Sequential Logic",
                    content = "So far in digital logic, we covered combinational circuits \u2014 outputs depend only on current inputs. Sequential circuits add memory: outputs depend on current inputs AND past history.\n\nSequential logic is what makes computers possible. It enables:\n\u2022 Storing data (registers, memory)\n\u2022 Counting events\n\u2022 Following sequences of operations (state machines)\n\nThe fundamental building block is the flip-flop.",
                    keyPoint = "Combinational = no memory; Sequential = has memory (flip-flops)"
                ),
                LessonSection(
                    heading = "Latches and Flip-Flops",
                    content = "A latch is a simple memory element that stores one bit. The SR (Set-Reset) latch uses two cross-coupled NAND or NOR gates.\n\nA flip-flop is a clock-controlled latch \u2014 it only changes state on the clock edge:\n\u2022 D flip-flop: stores the input D on the rising clock edge. The simplest and most common.\n\u2022 JK flip-flop: like SR but toggles when both inputs are 1.\n\u2022 T flip-flop: toggles output on each clock edge when T=1.",
                    formula = "D flip-flop: Q(next) = D\nT flip-flop: Q(next) = Q \u2295 T\nJK: Q(next) = J\u00B7Q\u0304 + K\u0304\u00B7Q",
                    keyPoint = "D flip-flop: captures input on clock edge; one bit of memory"
                ),
                LessonSection(
                    heading = "Registers",
                    content = "A register is a group of flip-flops that stores a multi-bit value. An 8-bit register uses 8 D flip-flops sharing the same clock.\n\nTypes of registers:\n\u2022 Parallel register: all bits loaded simultaneously\n\u2022 Shift register: bits shift one position per clock cycle (used in serial communication)\n\u2022 PISO: Parallel-In, Serial-Out\n\u2022 SIPO: Serial-In, Parallel-Out\n\nRegisters are the basic storage elements inside CPUs and digital systems.",
                    keyPoint = "Register = group of flip-flops; stores N-bit values"
                ),
                LessonSection(
                    heading = "Counters",
                    content = "A counter is a register that cycles through a sequence of states. The most common is a binary counter that counts 0, 1, 2, 3, ... and wraps around.\n\nAn N-bit binary counter counts from 0 to 2^N \u2212 1:\n\u2022 4-bit counter: counts 0 to 15\n\u2022 8-bit counter: counts 0 to 255\n\nCounters can count up, down, or be loaded with a specific value. They are used for timers, frequency dividers, address generators, and event counting.",
                    formula = "N-bit counter: 2^N states\nMax count = 2^N \u2212 1",
                    keyPoint = "N-bit counter counts from 0 to 2^N \u2212 1 then wraps"
                ),
                LessonSection(
                    heading = "Finite State Machines",
                    content = "A Finite State Machine (FSM) is a system with a fixed number of states, transitions between states based on inputs, and outputs based on current state.\n\nTwo types:\n\u2022 Moore machine: outputs depend only on the current state\n\u2022 Mealy machine: outputs depend on current state AND inputs\n\nFSMs are used to design traffic light controllers, vending machines, communication protocols, and CPU control units. They are described using state diagrams or state tables.",
                    keyPoint = "Moore = outputs from state only; Mealy = outputs from state + inputs"
                ),
                LessonSection(
                    heading = "Memory",
                    content = "Digital memory stores large amounts of data:\n\n\u2022 SRAM (Static RAM): uses flip-flops, fast but expensive. Used for CPU caches.\n\u2022 DRAM (Dynamic RAM): uses capacitors, dense but needs refreshing. Used for main memory.\n\u2022 ROM/Flash: non-volatile (retains data without power). Used for firmware and storage.\n\nMemory is organized in rows and columns, addressed by binary addresses. An N-bit address can access 2^N locations. A 32-bit address space covers 4GB.",
                    formula = "Address space = 2^N locations\n32-bit: 2\u00B3\u00B2 = 4,294,967,296 = 4GB",
                    keyPoint = "SRAM = fast cache; DRAM = main memory; Flash = persistent storage"
                )
            )
        ),
        quiz = Quiz(
            title = "Digital Systems Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ds_mc1",
                    questionText = "What is the key difference between combinational and sequential logic?",
                    options = listOf(
                        "Sequential logic uses analog signals",
                        "Sequential logic has memory (depends on past inputs)",
                        "Combinational logic is faster",
                        "Combinational logic uses more transistors"
                    ),
                    correctIndex = 1,
                    explanation = "Sequential logic includes memory elements (flip-flops) so its output depends on both current inputs and past state."
                ),
                Question.MultipleChoice(
                    id = "ds_mc2",
                    questionText = "A D flip-flop captures its input on the:",
                    options = listOf(
                        "High level of the clock",
                        "Low level of the clock",
                        "Rising edge of the clock",
                        "Both edges of the clock"
                    ),
                    correctIndex = 2,
                    explanation = "A standard D flip-flop captures the D input and transfers it to Q on the rising (positive) edge of the clock signal."
                ),
                Question.NumericInput(
                    id = "ds_ni1",
                    questionText = "A 4-bit binary counter can count from 0 to what maximum value?",
                    correctAnswer = 15.0,
                    tolerance = 0.1,
                    unit = "",
                    explanation = "Max count = 2^N \u2212 1 = 2\u2074 \u2212 1 = 16 \u2212 1 = 15"
                ),
                Question.NumericInput(
                    id = "ds_ni2",
                    questionText = "How many D flip-flops are needed to build a 16-bit register?",
                    correctAnswer = 16.0,
                    tolerance = 0.1,
                    unit = "flip-flops",
                    explanation = "One D flip-flop stores one bit. A 16-bit register needs 16 flip-flops."
                ),
                Question.MultipleChoice(
                    id = "ds_mc3",
                    questionText = "In a Moore state machine, the outputs depend on:",
                    options = listOf(
                        "Current state only",
                        "Current inputs only",
                        "Current state and current inputs",
                        "Previous outputs only"
                    ),
                    correctIndex = 0,
                    explanation = "In a Moore machine, outputs depend only on the current state. In a Mealy machine, outputs depend on both state and inputs."
                ),
                Question.NumericInput(
                    id = "ds_ni3",
                    questionText = "How many memory locations can a 10-bit address bus access?",
                    correctAnswer = 1024.0,
                    tolerance = 1.0,
                    unit = "locations",
                    explanation = "Address space = 2^N = 2\u00B9\u2070 = 1024 locations (1K)."
                ),
                Question.MultipleChoice(
                    id = "ds_mc4",
                    questionText = "Which type of RAM uses capacitors and needs periodic refreshing?",
                    options = listOf(
                        "SRAM",
                        "DRAM",
                        "Flash",
                        "ROM"
                    ),
                    correctIndex = 1,
                    explanation = "DRAM stores bits as charge on tiny capacitors. Since charge leaks, it must be refreshed thousands of times per second."
                )
            )
        )
    )
}
