package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI

object VlsiDesignContent {
    private const val WAFER_RADIUS_MM = 150.0
    private val criticalPaths = listOf(1.0, 1.25, 2.0, 2.5, 4.0, 5.0)
    private val nominalVoltages = listOf(1.0, 1.2, 1.8)
    private val scaleFactors = listOf(0.7, 0.8, 0.9)
    private val clockToQ = listOf(0.1, 0.2, 0.3)
    private val logicDelays = listOf(0.8, 1.2, 1.5, 2.4)
    private val setupTimes = listOf(0.1, 0.15, 0.2)
    private val startingCounts = listOf(1.0, 2.0, 5.0)
    private val yearSpans = listOf(2, 4, 6, 10)
    private val dieAreas = listOf(50.0, 100.0, 200.0, 400.0)

    val topic = Topic(
        id = "vlsi_design",
        title = "VLSI Design",
        subtitle = "Integrated circuit fabrication and design",
        icon = "\uD83D\uDD2C",
        order = 16,
        lesson = Lesson(
            title = "VLSI Design: Building Chips",
            sections = listOf(
                LessonSection(
                    heading = "What is VLSI?",
                    content = "Very Large Scale Integration (VLSI) refers to the process of creating integrated circuits (ICs) with millions to billions of transistors on a single chip.\n\nMoore's Law observed that transistor count doubles approximately every two years. Modern processors contain over 10 billion transistors on a chip smaller than a fingernail.\n\nVLSI design bridges the gap between a circuit idea and a manufactured silicon chip, involving digital logic design, physical layout, and fabrication.",
                    keyPoint = "VLSI = billions of transistors on one chip; Moore's Law drives density"
                ),
                LessonSection(
                    heading = "CMOS Fabrication Process",
                    content = "Modern chips use CMOS technology. The fabrication process involves:\n\n1. Wafer preparation: pure silicon crystal sliced into thin wafers\n2. Oxidation: grow SiO\u2082 insulating layer\n3. Photolithography: pattern transfer using UV light and photoresist\n4. Etching: remove unwanted material\n5. Ion implantation: dope regions to create N-type and P-type areas\n6. Metal deposition: create interconnect wires (copper, aluminum)\n7. Repeat for multiple layers\n\nProcess nodes (7nm, 5nm, 3nm) refer to the minimum feature size.",
                    keyPoint = "Photolithography patterns each layer; smaller nodes = more transistors"
                ),
                LessonSection(
                    heading = "Design Flow",
                    content = "The VLSI design flow goes from specification to silicon:\n\n1. Specification: define what the chip does\n2. RTL design: describe behavior in Verilog/VHDL\n3. Synthesis: convert RTL to gate-level netlist\n4. Place & Route: assign physical locations and wire connections\n5. Verification: simulate, check timing, verify layout rules (DRC/LVS)\n6. Tape-out: send final design (GDSII file) to foundry\n7. Fabrication: foundry manufactures the chip\n8. Testing: verify manufactured chips work correctly",
                    keyPoint = "RTL \u2192 Synthesis \u2192 Place & Route \u2192 Verification \u2192 Tape-out \u2192 Fab"
                ),
                LessonSection(
                    heading = "Layout Design Rules",
                    content = "Physical layout must follow strict design rules set by the foundry:\n\n\u2022 Minimum width: smallest allowed feature (e.g., transistor gate length)\n\u2022 Minimum spacing: closest two features can be\n\u2022 Minimum overlap/enclosure: how much one layer must extend over another\n\nDRC (Design Rule Check) verifies all rules are met. LVS (Layout vs. Schematic) confirms the layout matches the intended circuit.\n\nViolating design rules causes manufacturing defects: shorts, opens, or parametric failures.",
                    keyPoint = "DRC checks geometry rules; LVS verifies layout matches schematic"
                ),
                LessonSection(
                    heading = "Timing and Clock Design",
                    content = "Digital circuits must meet timing requirements to function correctly:\n\n\u2022 Setup time: data must be stable before the clock edge\n\u2022 Hold time: data must remain stable after the clock edge\n\u2022 Propagation delay: time for a signal to pass through a gate\n\nThe critical path is the longest delay path between flip-flops. It determines the maximum clock frequency:\n\nf_max = 1 / (t_prop + t_setup)\n\nStatic Timing Analysis (STA) checks all paths without simulation.",
                    formula = "f_max = 1 / T_critical\nT_critical = t_cq + t_logic + t_setup",
                    keyPoint = "Critical path limits clock speed; STA verifies all timing constraints"
                ),
                LessonSection(
                    heading = "Power Optimization",
                    content = "Power consumption is a critical concern in VLSI:\n\n\u2022 Dynamic power: consumed during switching. P = \u03B1CV\u00B2f (activity factor \u00D7 capacitance \u00D7 voltage\u00B2 \u00D7 frequency)\n\u2022 Static power: leakage current when transistors are off. Grows worse at smaller nodes.\n\nPower reduction techniques:\n\u2022 Voltage scaling: P \u221D V\u00B2, so reducing V_DD saves significant power\n\u2022 Clock gating: disable clock to unused blocks\n\u2022 Power gating: cut power to dormant blocks entirely\n\u2022 Multi-threshold transistors: use low-leakage devices on non-critical paths",
                    formula = "P_dynamic = \u03B1CV\u00B2f\nP_static = V_DD \u00D7 I_leak",
                    keyPoint = "P \u221D V\u00B2f; voltage scaling is the most effective power reduction"
                )
            )
        ),
        quiz = Quiz(
            title = "VLSI Design Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "vlsi_mc1",
                    questionText = "What does the process node (e.g., 5nm) primarily refer to?",
                    options = listOf(
                        "The chip's physical size",
                        "The minimum feature size / transistor density indicator",
                        "The number of metal layers",
                        "The operating voltage"
                    ),
                    correctIndex = 1,
                    explanation = "The process node historically referred to the minimum gate length, though modern nodes are more of a marketing/density indicator than a literal measurement."
                ),
                Question.MultipleChoice(
                    id = "vlsi_mc2",
                    questionText = "What does DRC check in VLSI layout?",
                    options = listOf(
                        "That timing constraints are met",
                        "That the layout matches the schematic",
                        "That all geometric design rules are satisfied",
                        "That power consumption is within budget"
                    ),
                    correctIndex = 2,
                    explanation = "DRC (Design Rule Check) verifies that the physical layout meets all geometric rules: minimum widths, spacings, overlaps, etc."
                ),
                Question.MultipleChoice(
                    id = "vlsi_mc3",
                    questionText = "The critical path in a digital circuit determines:",
                    options = listOf(
                        "The power consumption",
                        "The maximum operating clock frequency",
                        "The chip area",
                        "The number of I/O pins"
                    ),
                    correctIndex = 1,
                    explanation = "The critical path is the longest delay path. The clock period must be longer than this path, so it sets the maximum frequency: f_max = 1/T_critical."
                ),
                Question.MultipleChoice(
                    id = "vlsi_mc4",
                    questionText = "Which power reduction technique is most effective?",
                    options = listOf(
                        "Clock gating",
                        "Voltage scaling",
                        "Using fewer metal layers",
                        "Increasing transistor size"
                    ),
                    correctIndex = 1,
                    explanation = "Dynamic power is proportional to V\u00B2, so reducing supply voltage gives quadratic power savings \u2014 the most effective technique."
                ),
                Question.MultipleChoice(
                    id = "vlsi_mc5",
                    questionText = "What does LVS (Layout Versus Schematic) verify?",
                    options = listOf(
                        "That the layout meets spacing rules",
                        "That the layout's devices and connections match the schematic",
                        "That timing is met at all corners",
                        "That the chip fits in its package"
                    ),
                    correctIndex = 1,
                    explanation = "LVS extracts the transistors and nets from the layout and checks they match the schematic netlist. DRC checks the geometric rules."
                )
            ),
            generators = listOf(
                generator("vlsi_ni1") { r ->
                    val t = criticalPaths.pick(r)
                    val mhz = 1000 / t
                    Numeric(
                        text = "If the critical path delay is ${fmt(t)}ns, what is the maximum clock frequency in MHz? " +
                            "Round to 2 decimal places.",
                        answer = mhz, unit = "MHz",
                        explanation = "f_max = 1/T = 1/(${fmt(t)}×10⁻⁹) Hz = ${fmt(mhz, 2)}MHz"
                    )
                },
                generator("vlsi_ni2") { r ->
                    val v1 = nominalVoltages.pick(r)
                    val k = scaleFactors.pick(r)
                    val v2 = v1 * k
                    val drop = (1 - k * k) * 100
                    Numeric(
                        text = "Supply voltage is reduced from ${fmt(v1)}V to ${fmt(v2)}V at the same clock. By what percentage " +
                            "does dynamic power decrease? Round to the nearest whole number.",
                        answer = drop, unit = "%", decimals = 0,
                        explanation = "P ∝ V², so P₂/P₁ = (${fmt(v2)}/${fmt(v1)})² = ${fmt(k * k)}. Power falls by " +
                            "${fmt(drop, 0)}%"
                    )
                },
                generator("vlsi_ni3") { r ->
                    val tcq = clockToQ.pick(r)
                    val tl = logicDelays.pick(r)
                    val ts = setupTimes.pick(r)
                    val tmin = tcq + tl + ts
                    Numeric(
                        text = "A flip-flop has t_cq = ${fmt(tcq)}ns, the logic between registers takes ${fmt(tl)}ns, and setup " +
                            "time is ${fmt(ts)}ns. What is the minimum clock period in nanoseconds?",
                        answer = tmin, unit = "ns",
                        explanation = "T_min = t_cq + t_logic + t_setup = ${fmt(tcq)} + ${fmt(tl)} + ${fmt(ts)} = ${fmt(tmin)}ns"
                    )
                },
                generator("vlsi_ni4") { r ->
                    val tcq = clockToQ.pick(r)
                    val tl = logicDelays.pick(r)
                    val ts = setupTimes.pick(r)
                    val tmin = tcq + tl + ts
                    val mhz = 1000 / tmin
                    Numeric(
                        text = "A path has t_cq = ${fmt(tcq)}ns, ${fmt(tl)}ns of logic and a ${fmt(ts)}ns setup time. What is the " +
                            "highest clock frequency it supports, in MHz? Round to 2 decimal places.",
                        answer = mhz, unit = "MHz",
                        explanation = "T_min = ${fmt(tcq)} + ${fmt(tl)} + ${fmt(ts)} = ${fmt(tmin)}ns, so f_max = 1/T_min = " +
                            "${fmt(mhz, 2)}MHz"
                    )
                },
                generator("vlsi_ni5") { r ->
                    val n0 = startingCounts.pick(r)
                    val years = yearSpans.pick(r)
                    val doublings = years / 2
                    val n = n0 * (1 shl doublings)
                    Numeric(
                        text = "A chip has ${fmt(n0)} billion transistors. If transistor counts double every 2 years (Moore's law), " +
                            "how many billion would a chip have $years years later?",
                        answer = n, unit = "billion",
                        explanation = "$years years is $doublings doublings: ${fmt(n0)} × 2^$doublings = ${fmt(n)} billion"
                    )
                },
                generator("vlsi_ni6") { r ->
                    val area = dieAreas.pick(r)
                    val dies = PI * WAFER_RADIUS_MM * WAFER_RADIUS_MM / area
                    Numeric(
                        text = "Roughly how many ${fmt(area)}mm² dies fit on a 300mm wafer? Divide the wafer area by the die area " +
                            "(ignore edge losses) and round to the nearest whole number.",
                        answer = dies, unit = "dies", decimals = 0,
                        explanation = "Wafer area = π × 150² ≈ 70686mm². 70686/${fmt(area)} ≈ ${fmt(dies, 0)} dies " +
                            "(fewer in practice, because partial dies at the edge are lost)"
                    )
                }
            )
        )
    )
}
