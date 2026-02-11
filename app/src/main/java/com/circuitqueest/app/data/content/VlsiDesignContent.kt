package com.circuitqueest.app.data.content

object VlsiDesignContent {
    val topic = Topic(
        id = "vlsi_design",
        title = "VLSI Design",
        subtitle = "Integrated circuit fabrication and design",
        icon = "\uD83D\uDD2C",
        order = 27,
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
                Question.NumericInput(
                    id = "vlsi_ni1",
                    questionText = "If the critical path delay is 2ns, what is the maximum clock frequency in MHz?",
                    correctAnswer = 500.0,
                    tolerance = 5.0,
                    unit = "MHz",
                    explanation = "f_max = 1/T = 1/(2\u00D710\u207B\u2079) = 500\u00D710\u2076 Hz = 500MHz"
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
                Question.NumericInput(
                    id = "vlsi_ni2",
                    questionText = "If supply voltage is reduced from 1.0V to 0.7V, by what percentage does dynamic power decrease? Round to nearest whole number.",
                    correctAnswer = 51.0,
                    tolerance = 1.0,
                    unit = "%",
                    explanation = "P \u221D V\u00B2. Ratio = (0.7/1.0)\u00B2 = 0.49. Power drops to 49%, a decrease of 51%."
                ),
                Question.NumericInput(
                    id = "vlsi_ni3",
                    questionText = "A flip-flop has t_cq = 0.3ns, combinational logic delay = 1.2ns, and setup time = 0.2ns. What is the minimum clock period in nanoseconds?",
                    correctAnswer = 1.7,
                    tolerance = 0.05,
                    unit = "ns",
                    explanation = "T_min = t_cq + t_logic + t_setup = 0.3 + 1.2 + 0.2 = 1.7ns"
                )
            )
        )
    )
}
