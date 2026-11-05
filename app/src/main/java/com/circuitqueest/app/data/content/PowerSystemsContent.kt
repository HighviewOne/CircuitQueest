package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.sqrt

object PowerSystemsContent {
    private val sqrt3 = sqrt(3.0)
    private val phaseVoltages = listOf(120.0, 230.0, 277.0, 7200.0)
    private val apparentPowers = listOf(10.0, 20.0, 50.0, 100.0)
    private val powerFactors = listOf(0.7, 0.8, 0.85, 0.9, 0.95)
    private val lowerVoltages = listOf(10.0, 11.0, 33.0)
    private val stepFactors = listOf(2.0, 5.0, 10.0)
    private val lineVoltages = listOf(400.0, 480.0)
    private val lineCurrents = listOf(10.0, 50.0, 100.0)
    private val loadPowerFactors = listOf(0.8, 0.9, 1.0)
    private val powerTriangles = listOf(5.0 to 4.0, 10.0 to 8.0, 13.0 to 12.0, 25.0 to 24.0)
    private val triangleScales = listOf(1.0, 10.0)
    private val feederCurrents = listOf(10.0, 50.0, 100.0)
    private val feederResistances = listOf(0.1, 0.5, 2.0)

    val topic = Topic(
        id = "power_systems",
        title = "Power Systems",
        subtitle = "Generation, transmission, and distribution of electrical power",
        icon = "\uD83C\uDFED",
        order = 23,
        lesson = Lesson(
            title = "Power Systems: Delivering Energy",
            sections = listOf(
                LessonSection(
                    heading = "The Power Grid",
                    content = "The electrical power grid is one of the most complex engineered systems ever built. It connects power generators to billions of loads across vast distances.\n\nThe grid has three stages:\n1. Generation: power plants produce electricity (coal, gas, nuclear, hydro, wind, solar)\n2. Transmission: high-voltage lines carry power long distances (115kV\u2013765kV)\n3. Distribution: substations step voltage down for local delivery (4kV\u201335kV, then 120/240V)\n\nThe grid must balance supply and demand in real time \u2014 storage is limited.",
                    keyPoint = "Generation \u2192 Transmission (high V) \u2192 Distribution (low V) \u2192 Consumer"
                ),
                LessonSection(
                    heading = "Three-Phase Power",
                    content = "Large-scale power systems use three-phase AC, which has three voltage waveforms offset by 120\u00B0. Advantages over single-phase:\n\n\u2022 Delivers constant power (single-phase pulsates)\n\u2022 More efficient: 3 wires carry \u221A3 times the power of 2 single-phase wires\n\u2022 Creates rotating magnetic fields directly (for motors)\n\nThree-phase connections:\n\u2022 Wye (Y/Star): has neutral point, provides two voltages (phase and line)\n\u2022 Delta (\u0394): no neutral, used for motor loads",
                    formula = "V_line = \u221A3 \u00D7 V_phase (Wye)\nP_total = \u221A3 \u00D7 V_line \u00D7 I_line \u00D7 cos\u03C6",
                    keyPoint = "Three-phase: 3 waveforms at 120\u00B0; V_line = \u221A3 \u00D7 V_phase in Wye"
                ),
                LessonSection(
                    heading = "Why High Voltage for Transmission?",
                    content = "Power loss in transmission lines is I\u00B2R. For a given power P = VI, increasing voltage decreases current, dramatically reducing losses.\n\nExample: Transmitting 1MW at 1kV requires 1000A \u2192 I\u00B2R losses are huge.\nAt 500kV, current is only 2A \u2192 I\u00B2R losses are 250,000\u00D7 smaller!\n\nThis is why transformers are essential: step up to high voltage for transmission, step down for safe distribution and use.",
                    formula = "P_loss = I\u00B2R = (P/V)\u00B2 \u00D7 R\nP_loss \u221D 1/V\u00B2",
                    keyPoint = "Doubling transmission voltage reduces losses by 4\u00D7"
                ),
                LessonSection(
                    heading = "Power Factor",
                    content = "Power factor (PF) measures how effectively current is converted to useful work. It is the cosine of the phase angle between voltage and current.\n\n\u2022 PF = 1.0: all power is real (useful work) \u2014 purely resistive load\n\u2022 PF < 1.0: some power bounces back and forth (reactive) \u2014 inductive or capacitive loads\n\nMotors and other inductive loads typically have PF = 0.7\u20130.9. Low power factor wastes grid capacity and increases current. Power factor correction capacitors are added to improve PF toward 1.0.",
                    formula = "PF = cos\u03C6 = P_real / S_apparent\nS = V \u00D7 I (apparent power, in VA)",
                    keyPoint = "PF = 1.0 ideal; inductive loads lower PF; add capacitors to correct"
                ),
                LessonSection(
                    heading = "Protection Systems",
                    content = "Power systems must be protected from faults (short circuits, overloads, lightning) that could damage equipment or endanger lives:\n\n\u2022 Fuses: melt to break the circuit (one-time use)\n\u2022 Circuit breakers: trip to open the circuit (resettable)\n\u2022 Relays: monitor current, voltage, and frequency; command breakers to trip\n\u2022 Ground fault protection: detects current leaking to ground (GFCI)\n\nProtection must be selective: only the closest device should trip, leaving the rest of the grid operational. This is called coordination.",
                    keyPoint = "Protection: detect faults fast, isolate only the affected section"
                ),
                LessonSection(
                    heading = "Renewable Energy Integration",
                    content = "Solar and wind power are intermittent \u2014 they depend on weather. Integrating them into the grid requires:\n\n\u2022 Inverters: convert DC (solar panels) to AC for the grid\n\u2022 Grid-tie requirements: match grid voltage, frequency, and phase\n\u2022 Energy storage: batteries or pumped hydro to smooth variability\n\u2022 Smart grid: real-time monitoring and control to balance supply/demand\n\nSolar panels produce DC at 30\u201350V per panel; inverters convert to 240V AC at 60Hz (or 50Hz) synchronized with the grid.",
                    keyPoint = "Renewables need inverters, storage, and smart grid management"
                )
            )
        ),
        quiz = Quiz(
            title = "Power Systems Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ps_mc1",
                    questionText = "Why is electrical power transmitted at very high voltages?",
                    options = listOf(
                        "High voltage is safer",
                        "High voltage reduces I\u00B2R transmission losses",
                        "Generators can only produce high voltage",
                        "High voltage travels faster through wires"
                    ),
                    correctIndex = 1,
                    explanation = "For a given power P=VI, higher voltage means lower current. Since losses = I\u00B2R, lower current dramatically reduces power wasted as heat in the lines."
                ),
                Question.MultipleChoice(
                    id = "ps_mc2",
                    questionText = "In three-phase power, the voltage waveforms are offset by:",
                    options = listOf(
                        "90\u00B0",
                        "120\u00B0",
                        "180\u00B0",
                        "60\u00B0"
                    ),
                    correctIndex = 1,
                    explanation = "Three-phase power uses three waveforms equally spaced at 360\u00B0/3 = 120\u00B0 apart."
                ),
                Question.MultipleChoice(
                    id = "ps_mc3",
                    questionText = "What device converts DC from solar panels to AC for the grid?",
                    options = listOf(
                        "Transformer",
                        "Rectifier",
                        "Inverter",
                        "Regulator"
                    ),
                    correctIndex = 2,
                    explanation = "An inverter converts DC to AC. Solar panels produce DC, so an inverter is required to feed power into the AC grid."
                ),
                Question.MultipleChoice(
                    id = "ps_mc4",
                    questionText = "Power factor correction is typically achieved by adding:",
                    options = listOf(
                        "Inductors",
                        "Resistors",
                        "Capacitors",
                        "Transformers"
                    ),
                    correctIndex = 2,
                    explanation = "Most loads are inductive (motors, transformers), so adding capacitors provides reactive power compensation to bring the power factor closer to 1.0."
                )
            ),
            generators = listOf(
                generator("ps_ni1") { r ->
                    val vph = phaseVoltages.pick(r)
                    val vl = sqrt3 * vph
                    Numeric(
                        text = "In a Wye (star) system, V_phase = ${fmt(vph)}V. What is V_line in Volts? " +
                            "Round to the nearest whole number.",
                        answer = vl, unit = "V", decimals = 0,
                        explanation = "V_line = √3 × V_phase = 1.732 × ${fmt(vph)} ≈ ${fmt(vl, 0)}V"
                    )
                },
                generator("ps_ni2") { r ->
                    val s = apparentPowers.pick(r)
                    val pf = powerFactors.pick(r)
                    val p = s * pf
                    Numeric(
                        text = "A load draws ${fmt(s)}kVA of apparent power and ${fmt(p)}kW of real power. What is its power factor? " +
                            "Round to 2 decimal places.",
                        answer = pf, unit = "",
                        explanation = "PF = P/S = ${fmt(p)}/${fmt(s)} = ${fmt(pf, 2)}"
                    )
                },
                generator("ps_ni3") { r ->
                    val v1 = lowerVoltages.pick(r)
                    val k = stepFactors.pick(r)
                    val v2 = v1 * k
                    Numeric(
                        text = "The same power is sent over the same line at ${fmt(v1)}kV and then at ${fmt(v2)}kV. By what factor " +
                            "are the I²R losses reduced at the higher voltage?",
                        answer = k * k, unit = "×",
                        explanation = "Current scales as 1/V, so losses scale as 1/V²: (${fmt(v2)}/${fmt(v1)})² = ${fmt(k)}² = ${fmt(k * k)}×"
                    )
                },
                generator("ps_ni4") { r ->
                    val vl = lineVoltages.pick(r)
                    val il = lineCurrents.pick(r)
                    val pf = loadPowerFactors.pick(r)
                    val kw = sqrt3 * vl * il * pf / 1000
                    Numeric(
                        text = "A three-phase load runs at V_line = ${fmt(vl)}V, I_line = ${fmt(il)}A and power factor ${fmt(pf)}. " +
                            "What real power does it draw, in kW? Round to 2 decimal places.",
                        answer = kw, unit = "kW",
                        explanation = "P = √3 × V_L × I_L × PF = 1.732 × ${fmt(vl)} × ${fmt(il)} × ${fmt(pf)} = ${fmt(kw, 2)}kW"
                    )
                },
                generator("ps_ni5") { r ->
                    val (s0, p0) = powerTriangles.pick(r)
                    val k = triangleScales.pick(r)
                    val s = s0 * k
                    val p = p0 * k
                    val q = sqrt(s * s - p * p)
                    Numeric(
                        text = "A load draws ${fmt(s)}kVA of apparent power and ${fmt(p)}kW of real power. What is its reactive " +
                            "power, in kVAR?",
                        answer = q, unit = "kVAR",
                        explanation = "Power triangle: Q = √(S² − P²) = √(${fmt(s)}² − ${fmt(p)}²) = ${fmt(q)}kVAR"
                    )
                },
                generator("ps_ni6") { r ->
                    val i = feederCurrents.pick(r)
                    val res = feederResistances.pick(r)
                    val kw = i * i * res / 1000
                    Numeric(
                        text = "A feeder conductor has ${fmt(res)}Ω resistance and carries ${fmt(i)}A. How much power is lost in it, " +
                            "in kW? Round to 3 decimal places.",
                        answer = kw, unit = "kW", decimals = 3,
                        explanation = "P_loss = I²R = ${fmt(i)}² × ${fmt(res)} = ${fmt(i * i * res)}W = ${fmt(kw, 3)}kW"
                    )
                }
            )
        )
    )
}
