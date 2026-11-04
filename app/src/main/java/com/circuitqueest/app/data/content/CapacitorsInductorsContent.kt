package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import com.circuitqueest.app.data.content.QuestionMath.pickThree
import com.circuitqueest.app.data.content.QuestionMath.pickTwo

object CapacitorsInductorsContent {
    private val capacitances = listOf(1.0, 2.2, 4.7, 10.0, 22.0, 47.0, 100.0)
    private val chargeVoltages = listOf(3.3, 5.0, 9.0, 12.0)
    private val rcResistances = listOf(1.0, 2.2, 4.7, 10.0)
    private val rcCapacitances = listOf(1.0, 10.0, 47.0, 100.0)
    private val seriesCapacitances = listOf(1.0, 2.0, 3.0, 4.0, 6.0, 10.0, 12.0)
    private val energyCapacitances = listOf(10.0, 20.0, 47.0, 100.0)
    private val energyVoltages = listOf(5.0, 10.0, 12.0)
    private val inductances = listOf(1.0, 10.0, 47.0, 100.0)
    private val inductorCurrents = listOf(0.5, 1.0, 2.0)

    val topic = Topic(
        id = "capacitors_inductors",
        title = "Capacitors & Inductors",
        subtitle = "Energy storage in electric fields and magnetic fields",
        icon = "\uD83D\uDD0B",
        order = 3,
        lesson = Lesson(
            title = "Capacitors & Inductors: Storing Energy",
            sections = listOf(
                LessonSection(
                    heading = "What is Capacitance?",
                    content = "A capacitor stores energy in an electric field between two conductive plates separated by an insulator (dielectric). Capacitance measures how much charge a capacitor can store per volt of potential difference.\n\nThe unit of capacitance is the Farad (F). In practice, most capacitors are measured in microfarads (\u00B5F), nanofarads (nF), or picofarads (pF).",
                    formula = "C = Q / V",
                    keyPoint = "Capacitance (Farads) = Charge (Coulombs) / Voltage (Volts)"
                ),
                LessonSection(
                    heading = "Capacitors in Series and Parallel",
                    content = "Capacitors combine in the opposite way to resistors:\n\n\u2022 In parallel: capacitances add directly (C_total = C\u2081 + C\u2082 + ...)\n\u2022 In series: reciprocals add (1/C_total = 1/C\u2081 + 1/C\u2082 + ...)\n\nFor two capacitors in series: C_total = (C\u2081 \u00D7 C\u2082) / (C\u2081 + C\u2082)\n\nRemember: series reduces total capacitance, parallel increases it.",
                    formula = "Parallel: C = C\u2081 + C\u2082\nSeries: 1/C = 1/C\u2081 + 1/C\u2082",
                    keyPoint = "Capacitors combine opposite to resistors"
                ),
                LessonSection(
                    heading = "What is Inductance?",
                    content = "An inductor stores energy in a magnetic field created by current flowing through a coil of wire. Inductance measures the inductor's opposition to changes in current.\n\nThe voltage across an inductor is proportional to the rate of change of current. The unit of inductance is the Henry (H).",
                    formula = "V = L \u00D7 (dI/dt)",
                    keyPoint = "Inductors resist changes in current, not steady current"
                ),
                LessonSection(
                    heading = "RC Time Constants",
                    content = "When a capacitor charges or discharges through a resistor, the time constant \u03C4 (tau) determines how quickly the voltage changes.\n\nAfter one time constant, the capacitor reaches about 63.2% of its final value. After 5\u03C4, it's considered fully charged (>99%).\n\n\u2022 Charging: V(t) = V_source \u00D7 (1 - e^(-t/\u03C4))\n\u2022 Discharging: V(t) = V\u2080 \u00D7 e^(-t/\u03C4)",
                    formula = "\u03C4 = R \u00D7 C",
                    keyPoint = "One time constant = 63.2% of the way to final value"
                ),
                LessonSection(
                    heading = "RL Time Constants",
                    content = "Inductors paired with resistors also have a time constant. In an RL circuit, the time constant determines how quickly current rises or falls.\n\nThe behavior mirrors RC circuits: after one \u03C4 the current reaches 63.2% of its final value.\n\n\u2022 Current rise: I(t) = (V/R) \u00D7 (1 - e^(-t/\u03C4))\n\u2022 Current decay: I(t) = I\u2080 \u00D7 e^(-t/\u03C4)",
                    formula = "\u03C4 = L / R",
                    keyPoint = "RC: \u03C4 = RC (seconds) | RL: \u03C4 = L/R (seconds)"
                ),
                LessonSection(
                    heading = "Energy Storage",
                    content = "Both capacitors and inductors store energy, but in different forms:\n\n\u2022 Capacitor: stores energy in an electric field\n\u2022 Inductor: stores energy in a magnetic field\n\nNeither component dissipates energy (ideal) \u2014 they store it and release it. This makes them essential in filters, oscillators, and power supplies.",
                    formula = "E_cap = \u00BDCV\u00B2\nE_ind = \u00BDLI\u00B2",
                    keyPoint = "Capacitors store E in electric fields, inductors in magnetic fields"
                )
            )
        ),
        quiz = Quiz(
            title = "Capacitors & Inductors Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ci_mc1",
                    questionText = "How do capacitors combine in parallel?",
                    options = listOf(
                        "Capacitances add directly",
                        "Reciprocals add",
                        "They cancel out",
                        "Only the largest one matters"
                    ),
                    correctIndex = 0,
                    explanation = "In parallel, capacitances add directly: C_total = C\u2081 + C\u2082 + ... (opposite to resistors)."
                ),
                Question.MultipleChoice(
                    id = "ci_mc2",
                    questionText = "What does an inductor resist?",
                    options = listOf(
                        "Steady-state current",
                        "Changes in voltage",
                        "Changes in current",
                        "Static electric fields"
                    ),
                    correctIndex = 2,
                    explanation = "An inductor opposes changes in current through it (V = L \u00D7 dI/dt). It passes steady-state DC freely."
                ),
                Question.MultipleChoice(
                    id = "ci_mc3",
                    questionText = "What is the time constant of an RL circuit?",
                    options = listOf(
                        "\u03C4 = R \u00D7 L",
                        "\u03C4 = L / R",
                        "\u03C4 = R / L",
                        "\u03C4 = \u221A(LC)"
                    ),
                    correctIndex = 1,
                    explanation = "The RL time constant is \u03C4 = L/R. Unlike RC circuits where \u03C4 = RC, here the inductance is divided by resistance."
                ),
                Question.MultipleChoice(
                    id = "ci_mc4",
                    questionText = "An RC circuit charges from 0V. After one time constant (t = τ), the capacitor voltage is about what fraction of the supply?",
                    options = listOf(
                        "37%",
                        "50%",
                        "63%",
                        "100%"
                    ),
                    correctIndex = 2,
                    explanation = "V = V_s(1 − e^(−t/τ)). At t = τ that's 1 − e⁻¹ ≈ 0.632, about 63%. It's essentially full (over 99%) after 5τ."
                )
            ),
            generators = listOf(
                generator("ci_ni1") { r ->
                    val c = capacitances.pick(r)
                    val v = chargeVoltages.pick(r)
                    val q = c * v
                    Numeric(
                        text = "A ${fmt(c)}µF capacitor is charged to ${fmt(v)}V. How much charge does it store in " +
                            "microcoulombs (µC)? Round to 2 decimal places.",
                        answer = q, unit = "µC",
                        explanation = "Q = C × V = ${fmt(c)}µF × ${fmt(v)}V = ${fmt(q, 2)}µC"
                    )
                },
                generator("ci_ni2") { r ->
                    val res = rcResistances.pick(r)
                    val c = rcCapacitances.pick(r)
                    val tau = res * c
                    Numeric(
                        text = "What is the time constant (in milliseconds) of a ${fmt(res)}kΩ resistor in series with a " +
                            "${fmt(c)}µF capacitor? Round to 2 decimal places.",
                        answer = tau, unit = "ms",
                        explanation = "τ = R × C = ${fmt(res)}kΩ × ${fmt(c)}µF = ${fmt(tau, 2)}ms (kΩ × µF gives ms)"
                    )
                },
                generator("ci_ni3") { r ->
                    val (a, b) = seriesCapacitances.pickTwo(r)
                    val total = a * b / (a + b)
                    Numeric(
                        text = "Two capacitors of ${fmt(a)}µF and ${fmt(b)}µF are connected in series. " +
                            "What is the total capacitance in µF? Round to 2 decimal places.",
                        answer = total, unit = "µF",
                        explanation = "In series, C_total = (C₁ × C₂)/(C₁ + C₂) = (${fmt(a)} × ${fmt(b)})/(${fmt(a)} + ${fmt(b)}) = " +
                            "${fmt(total, 2)}µF"
                    )
                },
                generator("ci_ni4") { r ->
                    val c = energyCapacitances.pick(r)
                    val v = energyVoltages.pick(r)
                    val mj = c * v * v / 2 / 1000
                    Numeric(
                        text = "What is the energy stored in a ${fmt(c)}µF capacitor charged to ${fmt(v)}V? " +
                            "Answer in millijoules (mJ), rounded to 3 decimal places.",
                        answer = mj, unit = "mJ", decimals = 3,
                        explanation = "E = ½CV² = ½ × ${fmt(c)}×10⁻⁶ × ${fmt(v)}² = ${fmt(mj, 3)}mJ"
                    )
                },
                generator("ci_ni5") { r ->
                    val (a, b, c) = capacitances.pickThree(r)
                    Numeric(
                        text = "Three capacitors of ${fmt(a)}µF, ${fmt(b)}µF, and ${fmt(c)}µF are connected in parallel. " +
                            "What is the total capacitance in µF?",
                        answer = a + b + c, unit = "µF",
                        explanation = "In parallel, capacitances add: ${fmt(a)} + ${fmt(b)} + ${fmt(c)} = ${fmt(a + b + c)}µF"
                    )
                },
                generator("ci_ni6") { r ->
                    val l = inductances.pick(r)
                    val i = inductorCurrents.pick(r)
                    val mj = l * i * i / 2
                    Numeric(
                        text = "A ${fmt(l)}mH inductor carries ${fmt(i)}A. How much energy does it store, in millijoules (mJ)? " +
                            "Round to 3 decimal places.",
                        answer = mj, unit = "mJ", decimals = 3,
                        explanation = "E = ½LI² = ½ × ${fmt(l)}mH × ${fmt(i)}² = ${fmt(mj, 3)}mJ"
                    )
                }
            )
        )
    )
}
