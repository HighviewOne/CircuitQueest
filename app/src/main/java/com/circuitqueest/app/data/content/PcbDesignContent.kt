package com.circuitqueest.app.data.content

object PcbDesignContent {
    val topic = Topic(
        id = "pcb_design",
        title = "PCB Design",
        subtitle = "From schematic to physical circuit board",
        icon = "\uD83D\uDCCB",
        order = 22,
        lesson = Lesson(
            title = "PCB Design: Building Real Circuits",
            sections = listOf(
                LessonSection(
                    heading = "What is a PCB?",
                    content = "A Printed Circuit Board (PCB) is a flat board made of insulating material (typically FR-4 fiberglass) with copper traces that connect electronic components.\n\nPCBs replace hand-wired circuits with reliable, reproducible, compact designs. Every electronic device \u2014 from phones to satellites \u2014 uses PCBs.\n\nThe design flow: Schematic capture \u2192 Component placement \u2192 Trace routing \u2192 Design rule check \u2192 Manufacturing files (Gerber) \u2192 Fabrication \u2192 Assembly.",
                    keyPoint = "PCB design flow: schematic \u2192 layout \u2192 route \u2192 DRC \u2192 fabrication"
                ),
                LessonSection(
                    heading = "PCB Layers and Stackup",
                    content = "PCBs can have multiple copper layers:\n\n\u2022 Single-layer: one copper side, simplest and cheapest\n\u2022 Two-layer: copper on both sides, connected by vias (plated holes)\n\u2022 Four-layer: adds internal ground and power planes for better signal integrity\n\u2022 Six+ layers: used for complex designs (BGA packages, high-speed digital)\n\nA typical 4-layer stackup: Signal \u2192 Ground plane \u2192 Power plane \u2192 Signal. The internal planes provide low-impedance power distribution and signal return paths.",
                    keyPoint = "4-layer (sig-gnd-pwr-sig) is the sweet spot for most designs"
                ),
                LessonSection(
                    heading = "Trace Width and Current Capacity",
                    content = "Copper traces have resistance and limited current capacity. Wider traces carry more current with less voltage drop and heating.\n\nThe IPC-2221 standard provides guidelines. For a 1oz copper outer layer at 10\u00B0C rise:\n\u2022 1A \u2248 10mil (0.25mm) trace\n\u2022 3A \u2248 50mil (1.27mm) trace\n\u2022 5A \u2248 110mil (2.8mm) trace\n\nFor high-current paths, use wider traces or copper fills (polygons). Internal layers can carry less current due to poorer heat dissipation.",
                    formula = "R_trace = \u03C1L / (W \u00D7 t)\n1oz Cu thickness \u2248 35\u00B5m",
                    keyPoint = "Wider traces = more current capacity; use copper pours for power"
                ),
                LessonSection(
                    heading = "Decoupling Capacitors",
                    content = "Every IC needs decoupling (bypass) capacitors placed as close as possible to its power pins. These provide local charge storage to handle sudden current demands.\n\nWithout decoupling, fast-switching ICs create voltage dips on the power rail, causing noise and potential malfunction.\n\nTypical strategy:\n\u2022 100nF ceramic caps at each IC power pin (handles high-frequency noise)\n\u2022 10\u00B5F bulk capacitor per section (handles lower-frequency demands)\n\u2022 Short, wide traces from cap to IC pin and ground",
                    keyPoint = "100nF decoupling cap at every IC power pin, placed as close as possible"
                ),
                LessonSection(
                    heading = "Ground Planes and Return Paths",
                    content = "A continuous ground plane is crucial for good signal integrity. Every signal current needs a return path, and the return current flows through the ground plane directly beneath the trace.\n\nRules for ground planes:\n\u2022 Keep the ground plane continuous \u2014 avoid splits and gaps\n\u2022 Don't route signals across ground plane breaks\n\u2022 Keep high-speed signals on layers adjacent to a ground plane\n\u2022 Separate analog and digital grounds, joining at a single point if needed\n\nA solid ground plane also acts as a shield against electromagnetic interference.",
                    keyPoint = "Unbroken ground plane = clean return paths = low noise"
                ),
                LessonSection(
                    heading = "Design Rules and Manufacturing",
                    content = "PCB manufacturers have minimum specifications:\n\n\u2022 Minimum trace width: typically 6mil (0.15mm)\n\u2022 Minimum spacing: typically 6mil between traces\n\u2022 Minimum drill size: typically 0.3mm\n\u2022 Minimum annular ring: 0.125mm around vias\n\nDesign Rule Check (DRC) verifies your layout meets these constraints before fabrication. Output files (Gerbers, drill files, BOM, pick-and-place) are sent to the manufacturer.\n\nCommon tools: KiCad (free), Altium Designer, Eagle, EasyEDA.",
                    keyPoint = "Always run DRC before sending to fab; know your manufacturer's capabilities"
                )
            )
        ),
        quiz = Quiz(
            title = "PCB Design Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "pcb_mc1",
                    questionText = "What is the typical stackup order for a 4-layer PCB?",
                    options = listOf(
                        "Signal-Signal-Ground-Power",
                        "Signal-Ground-Power-Signal",
                        "Ground-Signal-Signal-Power",
                        "Power-Ground-Signal-Signal"
                    ),
                    correctIndex = 1,
                    explanation = "The standard 4-layer stackup is Signal-Ground-Power-Signal. This ensures both signal layers are adjacent to a reference plane."
                ),
                Question.MultipleChoice(
                    id = "pcb_mc2",
                    questionText = "What is the primary purpose of a decoupling capacitor?",
                    options = listOf(
                        "Filter AC signals from the power rail",
                        "Provide local charge to ICs during fast switching",
                        "Protect against overvoltage",
                        "Increase circuit gain"
                    ),
                    correctIndex = 1,
                    explanation = "Decoupling capacitors provide a local reservoir of charge near ICs, supplying current during rapid switching before the main power supply can respond."
                ),
                Question.NumericInput(
                    id = "pcb_ni1",
                    questionText = "A 100mm long, 0.25mm wide copper trace (1oz, 35\u00B5m thick) has a resistance of about 23m\u03A9. If it carries 1A, what is the voltage drop in millivolts?",
                    correctAnswer = 23.0,
                    tolerance = 1.0,
                    unit = "mV",
                    explanation = "V = IR = 1A \u00D7 0.023\u03A9 = 0.023V = 23mV"
                ),
                Question.MultipleChoice(
                    id = "pcb_mc3",
                    questionText = "Why should you avoid splitting the ground plane?",
                    options = listOf(
                        "It wastes copper",
                        "It disrupts return current paths and increases noise",
                        "It makes the board heavier",
                        "It increases manufacturing cost"
                    ),
                    correctIndex = 1,
                    explanation = "A split ground plane forces return currents to take longer paths, increasing loop area, inductance, and electromagnetic emissions/susceptibility."
                ),
                Question.MultipleChoice(
                    id = "pcb_mc4",
                    questionText = "What file format is used to send PCB designs to manufacturers?",
                    options = listOf(
                        "PDF",
                        "DXF",
                        "Gerber",
                        "SVG"
                    ),
                    correctIndex = 2,
                    explanation = "Gerber files (RS-274X) are the industry standard for PCB manufacturing data, containing layer artwork, drill locations, and solder mask information."
                ),
                Question.NumericInput(
                    id = "pcb_ni2",
                    questionText = "A typical decoupling capacitor value placed at IC power pins is how many nanofarads?",
                    correctAnswer = 100.0,
                    tolerance = 5.0,
                    unit = "nF",
                    explanation = "100nF (0.1\u00B5F) ceramic capacitors are the standard decoupling value for most digital ICs."
                ),
                Question.NumericInput(
                    id = "pcb_ni3",
                    questionText = "Standard 1oz copper on a PCB is approximately how many micrometers thick?",
                    correctAnswer = 35.0,
                    tolerance = 1.0,
                    unit = "\u00B5m",
                    explanation = "1oz copper means 1 ounce of copper per square foot, which corresponds to approximately 35\u00B5m (1.4mil) thickness."
                )
            )
        )
    )
}
