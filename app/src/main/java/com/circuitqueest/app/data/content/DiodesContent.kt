package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI

object DiodesContent {
    private const val SILICON_DROP = 0.7
    private val diodeSupplies = listOf(5.0, 9.0, 12.0, 15.0, 24.0)
    private val seriesResistors = listOf(220.0, 330.0, 470.0, 680.0, 1000.0)
    private val zenerVoltages = listOf(3.3, 5.1, 6.2, 9.1)
    private val zenerInputs = listOf(12.0, 15.0, 24.0)
    private val zenerResistors = listOf(470.0, 1000.0, 2200.0)
    private val leds = listOf("red" to 2.0, "yellow" to 2.1, "blue" to 3.2, "white" to 3.2)
    private val ledCurrents = listOf(10.0, 15.0, 20.0)
    private val ledSupplies = listOf(5.0, 9.0, 12.0)
    private val rectifierPeaks = listOf(5.0, 10.0, 12.0, 17.0, 24.0, 170.0)
    private val diodeCurrents = listOf(0.1, 0.5, 1.0, 2.0, 3.0)

    val topic = Topic(
        id = "diodes_rectifiers",
        title = "Diodes & Rectifiers",
        subtitle = "One-way gates for current flow",
        icon = "\u25B6\uFE0F",
        order = 6,
        lesson = Lesson(
            title = "Diodes & Rectifiers: The One-Way Street",
            sections = listOf(
                LessonSection(
                    heading = "The PN Junction",
                    content = "A diode is made by joining P-type and N-type semiconductor materials. At the junction, a depletion region forms where electrons and holes recombine, creating a barrier.\n\nThis barrier gives the diode its key property: it allows current to flow easily in one direction (forward bias) and blocks it in the other (reverse bias).",
                    keyPoint = "P-type has excess holes, N-type has excess electrons"
                ),
                LessonSection(
                    heading = "Forward and Reverse Bias",
                    content = "Forward bias: When the anode (+) is more positive than the cathode (\u2212) by at least the threshold voltage, the diode conducts. For silicon diodes, this threshold is approximately 0.7V.\n\nReverse bias: When the cathode is more positive than the anode, the depletion region widens and virtually no current flows.\n\nIn circuit analysis, a conducting silicon diode is often modeled as a 0.7V drop.",
                    formula = "V_forward \u2248 0.7V (silicon)\nV_forward \u2248 0.3V (germanium)",
                    keyPoint = "Silicon diode: 0.7V forward drop; blocks current in reverse"
                ),
                LessonSection(
                    heading = "Zener Diodes",
                    content = "A Zener diode is designed to operate in reverse breakdown at a specific voltage (V_Z). Unlike regular diodes, this breakdown is non-destructive and highly stable.\n\nZener diodes are used as voltage regulators: connect one in reverse bias with a series resistor, and the output voltage stays constant at V_Z regardless of load or input variations (within limits).",
                    formula = "V_out = V_Z (in breakdown)\nI_series = (V_in - V_Z) / R_series",
                    keyPoint = "Zener diodes regulate voltage using controlled reverse breakdown"
                ),
                LessonSection(
                    heading = "Half-Wave Rectifier",
                    content = "A rectifier converts AC to DC. The simplest rectifier uses a single diode.\n\nDuring the positive half-cycle, the diode conducts and the load sees the input voltage (minus 0.7V). During the negative half-cycle, the diode blocks and the load sees 0V.\n\nThe result is pulsating DC \u2014 only the positive halves of the AC waveform pass through. This is inefficient since half the input power is wasted.",
                    formula = "V_dc(avg) = V_peak / \u03C0",
                    keyPoint = "Half-wave: one diode, uses only positive half-cycles"
                ),
                LessonSection(
                    heading = "Full-Wave Bridge Rectifier",
                    content = "A bridge rectifier uses four diodes arranged in a diamond pattern. During each half-cycle, two diodes conduct and two block, routing current through the load in the same direction regardless of input polarity.\n\nThe output is smoother than half-wave and uses the full input waveform. The trade-off is two diode drops (1.4V for silicon) instead of one.\n\nAdding a capacitor across the output smooths the pulsations further.",
                    formula = "V_dc(avg) = 2 \u00D7 V_peak / \u03C0",
                    keyPoint = "Bridge rectifier: 4 diodes, both half-cycles used, smoother DC output"
                ),
                LessonSection(
                    heading = "LEDs and Photodiodes",
                    content = "Light-Emitting Diodes (LEDs) emit light when forward biased. Different semiconductor materials produce different colors. LEDs need a current-limiting resistor to prevent burnout.\n\nPhotodiodes work in reverse: they convert light into current. They are used in optical communications, solar cells, and light sensors.\n\nLED resistor calculation: R = (V_supply \u2212 V_LED) / I_LED",
                    formula = "R_LED = (V_supply \u2212 V_LED) / I_LED",
                    keyPoint = "Always use a resistor with LEDs to limit current"
                )
            )
        ),
        quiz = Quiz(
            title = "Diodes & Rectifiers Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "dr_mc1",
                    questionText = "What is the approximate forward voltage drop of a silicon diode?",
                    options = listOf(
                        "0.3V",
                        "0.7V",
                        "1.2V",
                        "5.0V"
                    ),
                    correctIndex = 1,
                    explanation = "A silicon diode has a forward voltage drop of approximately 0.7V. Germanium diodes have about 0.3V."
                ),
                Question.MultipleChoice(
                    id = "dr_mc2",
                    questionText = "How many diodes does a full-wave bridge rectifier use?",
                    options = listOf(
                        "1",
                        "2",
                        "4",
                        "6"
                    ),
                    correctIndex = 2,
                    explanation = "A bridge rectifier uses 4 diodes arranged in a diamond configuration to rectify both halves of the AC cycle."
                ),
                Question.MultipleChoice(
                    id = "dr_mc3",
                    questionText = "What happens when a regular diode is reverse biased?",
                    options = listOf(
                        "It conducts normally",
                        "It emits light",
                        "It blocks current flow",
                        "It doubles the voltage"
                    ),
                    correctIndex = 2,
                    explanation = "In reverse bias, the depletion region widens and the diode blocks current flow (ideally zero current)."
                ),
                Question.MultipleChoice(
                    id = "dr_mc4",
                    questionText = "Which LED colour needs the highest forward voltage?",
                    options = listOf(
                        "Infrared",
                        "Red",
                        "Yellow",
                        "Blue"
                    ),
                    correctIndex = 3,
                    explanation = "Shorter wavelengths carry more photon energy, so they need a wider band gap and a higher forward voltage: blue is about 3V or more, red about 2V, infrared about 1.2–1.5V."
                ),
                Question.MultipleChoice(
                    id = "dr_mc5",
                    questionText = "What is a Zener diode operated in reverse breakdown mainly used for?",
                    options = listOf(
                        "Rectifying AC to DC",
                        "Holding a nearly constant voltage (regulation)",
                        "Emitting light",
                        "Amplifying signals"
                    ),
                    correctIndex = 1,
                    explanation = "In reverse breakdown a Zener keeps an almost constant voltage V_Z across itself over a wide range of currents, so it's used as a simple voltage regulator or reference."
                )
            ),
            generators = listOf(
                generator("dr_ni1") { r ->
                    val vs = diodeSupplies.pick(r)
                    val res = seriesResistors.pick(r)
                    val ma = (vs - SILICON_DROP) / res * 1000
                    Numeric(
                        text = "${articleCap(vs)} ${fmt(vs)}V source drives a silicon diode in series with " +
                            "${article(res)} ${fmt(res)}Ω resistor. What is the current in milliamps (mA)? " +
                            "Round to 2 decimal places.",
                        answer = ma, unit = "mA",
                        explanation = "I = (V_source − V_diode)/R = (${fmt(vs)} − 0.7)/${fmt(res)} = ${fmt(ma, 2)}mA"
                    )
                },
                generator("dr_ni2") { r ->
                    val vz = zenerVoltages.pick(r)
                    val vin = zenerInputs.pick(r)
                    val res = zenerResistors.pick(r)
                    val ma = (vin - vz) / res * 1000
                    Numeric(
                        text = "A Zener diode has V_Z = ${fmt(vz)}V. With ${article(vin)} ${fmt(vin)}V input and " +
                            "${article(res)} ${fmt(res)}Ω series resistor, what current flows through the resistor, in mA? " +
                            "Round to 2 decimal places.",
                        answer = ma, unit = "mA",
                        explanation = "The resistor drops V_in − V_Z: I = (${fmt(vin)} − ${fmt(vz)})/${fmt(res)} = ${fmt(ma, 2)}mA"
                    )
                },
                generator("dr_ni3") { r ->
                    val (colour, vf) = leds.pick(r)
                    val ma = ledCurrents.pick(r)
                    val vs = ledSupplies.pick(r)
                    val res = (vs - vf) / (ma / 1000)
                    Numeric(
                        text = "What resistor value (in Ω) runs a $colour LED (V_LED = ${fmt(vf)}V) at ${fmt(ma)}mA from " +
                            "${article(vs)} ${fmt(vs)}V supply? Round to the nearest whole number.",
                        answer = res, unit = "Ω", decimals = 0,
                        explanation = "R = (V_supply − V_LED)/I = (${fmt(vs)} − ${fmt(vf)})/${fmt(ma / 1000)} = ${fmt(res, 0)}Ω"
                    )
                },
                generator("dr_ni4") { r ->
                    val vp = rectifierPeaks.pick(r)
                    val avg = vp / PI
                    Numeric(
                        text = "A half-wave rectifier receives an AC signal with V_peak = ${fmt(vp)}V. Ignoring the diode " +
                            "drop, what is the average DC output voltage? Round to 1 decimal place.",
                        answer = avg, unit = "V", decimals = 1,
                        explanation = "Half-wave: V_avg = V_peak/π = ${fmt(vp)}/3.1416 ≈ ${fmt(avg, 1)}V"
                    )
                },
                generator("dr_ni5") { r ->
                    val vp = rectifierPeaks.pick(r)
                    val avg = 2 * vp / PI
                    Numeric(
                        text = "A full-wave rectifier receives an AC signal with V_peak = ${fmt(vp)}V. Ignoring the diode " +
                            "drops, what is the average DC output voltage? Round to 1 decimal place.",
                        answer = avg, unit = "V", decimals = 1,
                        explanation = "Full-wave uses both halves of the cycle: V_avg = 2V_peak/π = 2 × ${fmt(vp)}/3.1416 ≈ " +
                            "${fmt(avg, 1)}V, twice the half-wave value."
                    )
                },
                generator("dr_ni6") { r ->
                    val i = diodeCurrents.pick(r)
                    val p = SILICON_DROP * i
                    Numeric(
                        text = "A silicon diode (0.7V forward drop) carries ${fmt(i)}A. How much power does it dissipate, " +
                            "in Watts? Round to 2 decimal places.",
                        answer = p, unit = "W",
                        explanation = "P = V_F × I = 0.7 × ${fmt(i)} = ${fmt(p, 2)}W"
                    )
                }
            )
        )
    )
}
