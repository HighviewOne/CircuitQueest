package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object DigitalSystemsContent {
    private const val BYTES_PER_KB = 1024
    private val counterBits = listOf(3, 4, 6, 8, 10, 12)
    private val stateCounts = listOf(5, 10, 12, 24, 60, 100, 1000)
    private val addressBits = listOf(8, 10, 12, 16)
    private val memoryAddressBits = listOf(10, 12, 16)
    private val dataWidths = listOf(8, 16, 32)
    private val clockFrequencies = listOf(50.0, 100.0, 250.0, 400.0)
    private val dividerInputs = listOf(1.0, 10.0, 16.0)
    private val dividerBits = listOf(4, 8, 10)

    val topic = Topic(
        id = "digital_systems",
        title = "Digital Systems",
        subtitle = "Flip-flops, counters, and state machines",
        icon = "\uD83D\uDD22",
        order = 15,
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
                ),
                Question.MultipleChoice(
                    id = "ds_mc5",
                    questionText = "What is the setup time of a flip-flop?",
                    options = listOf(
                        "How long the output takes to change after the clock edge",
                        "The minimum time the data input must be stable before the clock edge",
                        "How long the clock stays high",
                        "The time to power up the flip-flop"
                    ),
                    correctIndex = 1,
                    explanation = "Data must settle at least t_setup before the active clock edge (and stay for t_hold after). Violating it can leave the flip-flop metastable."
                )
            ),
            generators = listOf(
                generator("ds_ni1") { r ->
                    val n = counterBits.pick(r)
                    val max = (1 shl n) - 1
                    Numeric(
                        text = "${article(n.toDouble()).replaceFirstChar { it.uppercase() }} $n-bit binary counter counts from 0 up " +
                            "to what maximum value?",
                        answer = max.toDouble(), unit = "", exact = true,
                        explanation = "Max count = 2^N − 1 = 2^$n − 1 = $max"
                    )
                },
                generator("ds_ni2") { r ->
                    val m = stateCounts.pick(r)
                    val n = Int.SIZE_BITS - Integer.numberOfLeadingZeros(m - 1)
                    Numeric(
                        text = "A counter must step through $m distinct states. What is the fewest flip-flops it needs?",
                        answer = n.toDouble(), unit = "flip-flops", exact = true,
                        explanation = "n flip-flops give 2^n states. 2^${n - 1} = ${1 shl (n - 1)} is too few and " +
                            "2^$n = ${1 shl n} ≥ $m, so it needs $n flip-flops"
                    )
                },
                generator("ds_ni3") { r ->
                    val n = addressBits.pick(r)
                    val locations = 1 shl n
                    Numeric(
                        text = "How many memory locations can ${article(n.toDouble())} $n-bit address bus access?",
                        answer = locations.toDouble(), unit = "locations", exact = true,
                        explanation = "Address space = 2^N = 2^$n = $locations locations"
                    )
                },
                generator("ds_ni4") { r ->
                    val n = memoryAddressBits.pick(r)
                    val w = dataWidths.pick(r)
                    val kb = (1 shl n) * w / Byte.SIZE_BITS / BYTES_PER_KB
                    Numeric(
                        text = "A memory has ${article(n.toDouble())} $n-bit address and ${article(w.toDouble())} $w-bit data " +
                            "word. What is its capacity in KB (1KB = 1024 bytes)?",
                        answer = kb.toDouble(), unit = "KB", exact = true,
                        explanation = "2^$n = ${1 shl n} words × $w bits = ${(1 shl n) * w / Byte.SIZE_BITS} bytes = $kb KB"
                    )
                },
                generator("ds_ni5") { r ->
                    val f = clockFrequencies.pick(r)
                    val ns = 1000 / f
                    Numeric(
                        text = "What is the clock period of ${article(f)} ${fmt(f)}MHz clock, in nanoseconds? Round to 2 decimal places.",
                        answer = ns, unit = "ns",
                        explanation = "T = 1/f = 1/(${fmt(f)}×10⁶) s = ${fmt(ns, 2)}ns"
                    )
                },
                generator("ds_ni6") { r ->
                    val fMhz = dividerInputs.pick(r)
                    val n = dividerBits.pick(r)
                    val khz = fMhz * 1000 / (1 shl n)
                    Numeric(
                        text = "${article(fMhz).replaceFirstChar { it.uppercase() }} ${fmt(fMhz)}MHz clock drives ${article(n.toDouble())} " +
                            "$n-bit ripple counter. What is the frequency of its most significant bit, in kHz? " +
                            "Round to 3 decimal places.",
                        answer = khz, unit = "kHz", decimals = 3,
                        explanation = "Each stage divides by 2, so the MSB runs at f/2^$n = ${fmt(fMhz * 1000)}kHz/${1 shl n} = " +
                            "${fmt(khz, 3)}kHz"
                    )
                }
            )
        )
    )
}
