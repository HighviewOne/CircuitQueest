package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI

object PcbDesignContent {
    private const val COPPER_RESISTIVITY = 1.72e-8
    private const val OZ_MICRONS = 35.0
    private const val MM_PER_MIL = 0.0254
    private const val MILLI = 1e-3
    private const val MICRO = 1e-6
    private const val NANO = 1e-9
    private const val MEGA = 1e6
    private val traceLengths = listOf(50.0, 100.0, 200.0)
    private val traceWidths = listOf(0.25, 0.5, 1.0)
    private val copperWeights = listOf(1.0, 2.0)
    private val traceCurrents = listOf(0.5, 1.0, 2.0)
    private val decouplingNf = listOf(10.0, 100.0)
    private val noiseFrequenciesMhz = listOf(1.0, 10.0, 100.0)
    private val thicknessWeights = listOf(0.5, 1.0, 2.0, 3.0)
    private val milValues = listOf(5.0, 6.0, 8.0, 10.0, 20.0, 50.0)
    private val bulkNf = listOf(100.0, 1000.0)
    private val allowedDroops = listOf(0.05, 0.1)
    private val burstCurrentsMa = listOf(10.0, 50.0, 100.0)

    private fun traceOhms(lengthMm: Double, widthMm: Double, oz: Double) =
        COPPER_RESISTIVITY * lengthMm * MILLI / (widthMm * MILLI * oz * OZ_MICRONS * MICRO)

    val topic = Topic(
        id = "pcb_design",
        title = "PCB Design",
        subtitle = "From schematic to physical circuit board",
        icon = "\uD83D\uDCCB",
        order = 34,
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
                )
            ),
            generators = listOf(
                generator("pcb_ni1") { r ->
                    val l = traceLengths.pick(r)
                    val w = traceWidths.pick(r)
                    val oz = copperWeights.pick(r)
                    val i = traceCurrents.pick(r)
                    val ohms = traceOhms(l, w, oz)
                    val mv = ohms * i * 1000
                    Numeric(
                        text = "A ${fmt(l)}mm long, ${fmt(w)}mm wide trace of ${fmt(oz)}oz copper (${fmt(oz * OZ_MICRONS)}µm thick, " +
                            "ρ = 1.72×10⁻⁸Ω·m) carries ${fmt(i)}A. What is the voltage drop along it, in mV? Round to 2 decimal places.",
                        answer = mv, unit = "mV",
                        explanation = "R = ρL/(w·t) = 1.72×10⁻⁸ × ${fmt(l / 1000)}/(${fmt(w)}×10⁻³ × ${fmt(oz * OZ_MICRONS)}×10⁻⁶) = " +
                            "${fmt(ohms * 1000, 1)}mΩ, so V = IR = ${fmt(i)} × ${fmt(ohms * 1000, 1)}mΩ = ${fmt(mv, 2)}mV"
                    )
                },
                generator("pcb_ni2") { r ->
                    val cNf = decouplingNf.pick(r)
                    val fMhz = noiseFrequenciesMhz.pick(r)
                    val x = 1 / (2 * PI * fMhz * MEGA * cNf * NANO)
                    Numeric(
                        text = "What is the reactance of ${article(cNf)} ${fmt(cNf)}nF decoupling capacitor at ${fmt(fMhz)}MHz, in Ω? " +
                            "Treat it as ideal and round to 3 decimal places.",
                        answer = x, unit = "Ω", decimals = 3,
                        explanation = "X_C = 1/(2πfC) = 1/(2π × ${fmt(fMhz)}×10⁶ × ${fmt(cNf)}×10⁻⁹) = ${fmt(x, 3)}Ω. Real capacitors stop " +
                            "helping above their self-resonance, which is why several values are often paralleled."
                    )
                },
                generator("pcb_ni3") { r ->
                    val oz = thicknessWeights.pick(r)
                    Numeric(
                        text = "1oz copper is about 35µm thick. How thick is ${fmt(oz)}oz copper, in µm?",
                        answer = oz * OZ_MICRONS, unit = "µm",
                        explanation = "Copper weight is ounces per square foot, so thickness scales with it: ${fmt(oz)} × 35 = " +
                            "${fmt(oz * OZ_MICRONS)}µm"
                    )
                },
                generator("pcb_ni4") { r ->
                    val l = traceLengths.pick(r)
                    val w = traceWidths.pick(r)
                    val oz = copperWeights.pick(r)
                    val i = traceCurrents.pick(r)
                    val ohms = traceOhms(l, w, oz)
                    val mw = i * i * ohms * 1000
                    Numeric(
                        text = "A ${fmt(l)}mm × ${fmt(w)}mm trace of ${fmt(oz)}oz copper (ρ = 1.72×10⁻⁸Ω·m, 1oz = 35µm) carries " +
                            "${fmt(i)}A. How much power does it dissipate, in mW? Round to 2 decimal places.",
                        answer = mw, unit = "mW",
                        explanation = "R = ρL/(w·t) = ${fmt(ohms * 1000, 1)}mΩ, so P = I²R = ${fmt(i)}² × ${fmt(ohms * 1000, 1)}mΩ = " +
                            "${fmt(mw, 2)}mW"
                    )
                },
                generator("pcb_ni5") { r ->
                    val mil = milValues.pick(r)
                    val mm = mil * MM_PER_MIL
                    Numeric(
                        text = "A PCB design rule calls for ${fmt(mil)}mil spacing. What is that in millimetres? Round to 3 decimal places.",
                        answer = mm, unit = "mm", decimals = 3,
                        explanation = "1mil = 0.001 inch = 0.0254mm, so ${fmt(mil)}mil = ${fmt(mil)} × 0.0254 = ${fmt(mm, 3)}mm"
                    )
                },
                generator("pcb_ni6") { r ->
                    val cNf = bulkNf.pick(r)
                    val dv = allowedDroops.pick(r)
                    val ima = burstCurrentsMa.pick(r)
                    val ns = cNf * NANO * dv / (ima * MILLI) / NANO
                    Numeric(
                        text = "${articleCap(cNf)} ${fmt(cNf)}nF capacitor supplies a ${fmt(ima)}mA current burst. How long can it do so " +
                            "before its voltage droops by ${fmt(dv)}V, in ns? Round to 1 decimal place.",
                        answer = ns, unit = "ns", decimals = 1,
                        explanation = "From Q = CV: t = C·ΔV/I = ${fmt(cNf)}×10⁻⁹ × ${fmt(dv)}/${fmt(ima / 1000)} = ${fmt(ns, 1)}ns"
                    )
                }
            )
        )
    )
}
