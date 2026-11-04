package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.sqrt

object MosfetsContent {
    private const val GIGA = 1e9
    private const val NANO = 1e-9
    private val onResistances = listOf(0.005, 0.01, 0.02, 0.05, 0.1)
    private val drainCurrents = listOf(2.0, 5.0, 10.0, 20.0)
    private val thresholds = listOf(0.7, 1.0, 1.5, 2.0)
    private val gateVoltages = listOf(2.5, 3.3, 5.0, 10.0)
    private val drainVoltages = listOf(0.5, 1.0, 2.0, 5.0, 10.0)
    private val activities = listOf(0.1, 0.2)
    private val switchedCapacitances = listOf(1.0, 2.0, 5.0)
    private val coreVoltages = listOf(0.8, 1.0, 1.2)
    private val clockGigahertz = listOf(1.0, 2.0, 3.0)
    private val transconductances = listOf(2.0, 4.0, 10.0)
    private val powerBudgets = listOf(1.0, 2.0, 5.0)

    val topic = Topic(
        id = "mosfets",
        title = "MOSFETs",
        subtitle = "Field-effect transistors and CMOS logic",
        icon = "\uD83D\uDD0C",
        order = 8,
        lesson = Lesson(
            title = "MOSFETs: Voltage-Controlled Switches",
            sections = listOf(
                LessonSection(
                    heading = "What is a MOSFET?",
                    content = "A Metal-Oxide-Semiconductor Field-Effect Transistor (MOSFET) is a voltage-controlled device with three terminals: Gate (G), Drain (D), and Source (S).\n\nUnlike BJTs which are current-controlled, MOSFETs are controlled by the voltage at the gate. Virtually no current flows into the gate, making them extremely efficient.\n\nMOSFETs are the building blocks of all modern digital circuits, with billions on a single chip.",
                    keyPoint = "MOSFET = voltage-controlled; BJT = current-controlled"
                ),
                LessonSection(
                    heading = "NMOS and PMOS",
                    content = "There are two types of enhancement-mode MOSFETs:\n\n\u2022 NMOS: turns ON when V_GS > V_th (gate voltage exceeds threshold). Current flows from drain to source. Used to pull outputs LOW.\n\n\u2022 PMOS: turns ON when V_GS < \u2212|V_th| (gate voltage is sufficiently below source). Current flows from source to drain. Used to pull outputs HIGH.\n\nThreshold voltage (V_th) is typically 0.5V\u20132V for modern MOSFETs.",
                    formula = "NMOS ON: V_GS > V_th\nPMOS ON: V_SG > |V_th|",
                    keyPoint = "NMOS: gate HIGH = ON; PMOS: gate LOW = ON"
                ),
                LessonSection(
                    heading = "Operating Regions",
                    content = "A MOSFET operates in three regions:\n\n\u2022 Cutoff: V_GS < V_th. No channel forms, no current flows. The transistor is OFF.\n\n\u2022 Triode (Linear): V_GS > V_th and V_DS < V_GS \u2212 V_th. The MOSFET acts like a variable resistor. Used in analog circuits.\n\n\u2022 Saturation: V_GS > V_th and V_DS \u2265 V_GS \u2212 V_th. Current is nearly constant regardless of V_DS. Used for amplification.",
                    formula = "Saturation: I_D = \u00BDk'(W/L)(V_GS \u2212 V_th)\u00B2",
                    keyPoint = "Cutoff = OFF, Triode = resistor, Saturation = constant current"
                ),
                LessonSection(
                    heading = "MOSFET as a Switch",
                    content = "MOSFETs excel as switches because they are voltage-controlled and have extremely low on-resistance (R_DS(on)).\n\nTo use an NMOS as a switch:\n\u2022 Gate HIGH (> V_th): MOSFET is ON, drain connects to source\n\u2022 Gate LOW (< V_th): MOSFET is OFF, open circuit\n\nPower MOSFETs can switch hundreds of amps with milliohm on-resistance, making them essential in power electronics, motor drivers, and switching power supplies.",
                    formula = "P_dissipated = I_D\u00B2 \u00D7 R_DS(on)",
                    keyPoint = "Low R_DS(on) means very little power wasted as heat when switching"
                ),
                LessonSection(
                    heading = "CMOS Logic",
                    content = "CMOS (Complementary MOS) pairs an NMOS and a PMOS transistor to create logic gates that consume almost zero static power.\n\nCMOS Inverter:\n\u2022 Input HIGH: NMOS ON, PMOS OFF \u2192 output pulled LOW\n\u2022 Input LOW: PMOS ON, NMOS OFF \u2192 output pulled HIGH\n\nIn either state, there is a path from output to V_DD or GND, but never both simultaneously. Power is only consumed during switching transitions.",
                    keyPoint = "CMOS draws almost zero power in steady state \u2014 only during transitions"
                ),
                LessonSection(
                    heading = "MOSFET vs. BJT",
                    content = "Comparing the two transistor families:\n\n\u2022 Input: MOSFET is voltage-controlled (no gate current); BJT is current-controlled (needs base current)\n\u2022 Input impedance: MOSFET is extremely high (>10\u00B9\u03A9); BJT is moderate\n\u2022 Speed: MOSFETs switch faster in digital circuits\n\u2022 Power: CMOS uses less static power\n\u2022 Analog gain: BJTs often have higher transconductance for the same bias current\n\nMOSFETs dominate digital ICs; BJTs are still used where high transconductance matters (RF, precision analog).",
                    keyPoint = "MOSFETs dominate digital; BJTs still used in high-performance analog"
                )
            )
        ),
        quiz = Quiz(
            title = "MOSFETs Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "mos_mc1",
                    questionText = "What are the three terminals of a MOSFET?",
                    options = listOf(
                        "Emitter, Base, Collector",
                        "Anode, Cathode, Gate",
                        "Gate, Drain, Source",
                        "Input, Output, Ground"
                    ),
                    correctIndex = 2,
                    explanation = "A MOSFET has three terminals: Gate (G), Drain (D), and Source (S)."
                ),
                Question.MultipleChoice(
                    id = "mos_mc2",
                    questionText = "An NMOS transistor turns ON when:",
                    options = listOf(
                        "V_GS < V_th",
                        "V_GS > V_th",
                        "V_GS = 0",
                        "V_DS > V_th"
                    ),
                    correctIndex = 1,
                    explanation = "An NMOS turns ON when the gate-to-source voltage exceeds the threshold voltage: V_GS > V_th."
                ),
                Question.MultipleChoice(
                    id = "mos_mc3",
                    questionText = "In a CMOS inverter, when the input is HIGH:",
                    options = listOf(
                        "Both transistors are ON",
                        "Both transistors are OFF",
                        "NMOS is ON, PMOS is OFF, output is LOW",
                        "PMOS is ON, NMOS is OFF, output is HIGH"
                    ),
                    correctIndex = 2,
                    explanation = "Input HIGH turns NMOS ON (pulling output to GND) and PMOS OFF. The output is LOW."
                ),
                Question.MultipleChoice(
                    id = "mos_mc4",
                    questionText = "Why does CMOS logic consume almost zero static power?",
                    options = listOf(
                        "It uses very small transistors",
                        "In either logic state, only one transistor is ON so there is no path from V_DD to GND",
                        "MOSFETs have infinite resistance",
                        "The power supply voltage is very low"
                    ),
                    correctIndex = 1,
                    explanation = "In CMOS, either the NMOS or PMOS is ON, never both simultaneously, so no DC current flows from supply to ground."
                ),
                Question.MultipleChoice(
                    id = "mos_mc5",
                    questionText = "In which region does a MOSFET behave like a low-value resistor, as a closed switch?",
                    options = listOf(
                        "Cutoff",
                        "Triode (linear)",
                        "Saturation",
                        "Breakdown"
                    ),
                    correctIndex = 1,
                    explanation = "With a large V_GS and small V_DS the MOSFET is in the triode region and acts like a resistor, R_DS(on). This is how it's used as a power switch."
                ),
                Question.MultipleChoice(
                    id = "mos_mc6",
                    questionText = "Why does a MOSFET's gate draw essentially no DC current?",
                    options = listOf(
                        "The gate is insulated from the channel by a thin oxide layer",
                        "The gate is connected to ground internally",
                        "The drain absorbs the gate current",
                        "Gate current flows only in saturation"
                    ),
                    correctIndex = 0,
                    explanation = "The gate forms a capacitor with the channel across a thin oxide insulator, so no DC current flows. Current flows only briefly to charge or discharge that capacitance when switching."
                )
            ),
            generators = listOf(
                generator("mos_ni1") { r ->
                    val rds = onResistances.pick(r)
                    val i = drainCurrents.pick(r)
                    val p = i * i * rds
                    Numeric(
                        text = "A MOSFET has R_DS(on) = ${fmt(rds)}Ω and carries ${fmt(i)}A. How much power is dissipated, " +
                            "in Watts? Round to 3 decimal places.",
                        answer = p, unit = "W", decimals = 3,
                        explanation = "P = I² × R_DS(on) = ${fmt(i)}² × ${fmt(rds)} = ${fmt(p, 3)}W"
                    )
                },
                generator("mos_ni2") { r ->
                    val vth = thresholds.pick(r)
                    val vgs = gateVoltages.pick(r)
                    val vds = drainVoltages.pick(r)
                    val vov = vgs - vth
                    val region = if (vds < vov) "V_DS < V_GS − V_th, so it's in the triode region"
                    else "V_DS ≥ V_GS − V_th, so it's in saturation"
                    Numeric(
                        text = "A MOSFET has V_th = ${fmt(vth)}V. If V_GS = ${fmt(vgs)}V and V_DS = ${fmt(vds)}V, " +
                            "what is the overdrive voltage V_GS − V_th, in Volts?",
                        answer = vov, unit = "V",
                        explanation = "V_GS − V_th = ${fmt(vgs)} − ${fmt(vth)} = ${fmt(vov)}V. ($region.)"
                    )
                },
                generator("mos_ni3") { r ->
                    val alpha = activities.pick(r)
                    val cNf = switchedCapacitances.pick(r)
                    val v = coreVoltages.pick(r)
                    val fGhz = clockGigahertz.pick(r)
                    val p = alpha * cNf * NANO * v * v * fGhz * GIGA
                    Numeric(
                        text = "A CMOS chip switches ${fmt(cNf)}nF of total capacitance with activity factor α = ${fmt(alpha)}, " +
                            "at V_DD = ${fmt(v)}V and ${fmt(fGhz)}GHz. What is the dynamic power, in Watts? " +
                            "Round to 3 decimal places.",
                        answer = p, unit = "W", decimals = 3,
                        explanation = "P = αCV²f = ${fmt(alpha)} × ${fmt(cNf)}×10⁻⁹ × ${fmt(v)}² × ${fmt(fGhz)}×10⁹ = ${fmt(p, 3)}W. " +
                            "Halving V_DD would cut it to a quarter."
                    )
                },
                generator("mos_ni4") { r ->
                    val k = transconductances.pick(r)
                    val vth = thresholds.pick(r)
                    val vgs = gateVoltages.pick(r)
                    val vov = vgs - vth
                    val id = k * vov * vov / 2
                    Numeric(
                        text = "An NMOS in saturation has k = ${fmt(k)}mA/V² (I_D = ½k(V_GS − V_th)²), V_th = ${fmt(vth)}V and " +
                            "V_GS = ${fmt(vgs)}V. What is the drain current, in mA? Round to 2 decimal places.",
                        answer = id, unit = "mA",
                        explanation = "I_D = ½k(V_GS − V_th)² = ½ × ${fmt(k)} × (${fmt(vgs)} − ${fmt(vth)})² = ${fmt(id, 2)}mA"
                    )
                },
                generator("mos_ni5") { r ->
                    val pMax = powerBudgets.pick(r)
                    val rds = onResistances.pick(r)
                    val i = sqrt(pMax / rds)
                    Numeric(
                        text = "A MOSFET with R_DS(on) = ${fmt(rds)}Ω may dissipate at most ${fmt(pMax)}W. What is the largest " +
                            "current it can carry, in Amps? Round to 2 decimal places.",
                        answer = i, unit = "A",
                        explanation = "From P = I²R: I = √(P/R) = √(${fmt(pMax)}/${fmt(rds)}) = ${fmt(i, 2)}A"
                    )
                }
            )
        )
    )
}
