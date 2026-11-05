package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI

object RenewableEnergyContent {
    private const val STC_IRRADIANCE = 1000.0
    private const val AIR_DENSITY = 1.225
    private const val BETZ_LIMIT = 16.0 / 27.0
    private const val QUARTER = 0.25
    private val panelPowers = listOf(250.0, 300.0, 350.0, 400.0)
    private val panelAreas = listOf(1.6, 1.7, 1.9, 2.0)
    private val availablePowers = listOf(10.0, 50.0, 100.0, 500.0)
    private val cellCounts = listOf(36.0, 60.0, 72.0, 96.0)
    private val cellVoltages = listOf(0.5, 0.6, 0.65)
    private val windSpeeds = listOf(4.0, 5.0, 6.0)
    private val speedFactors = listOf(1.5, 2.0, 3.0)
    private val rotorDiameters = listOf(2.0, 10.0, 40.0, 80.0)
    private val ratedSpeeds = listOf(5.0, 8.0, 10.0, 12.0)
    private val sunHours = listOf(3.0, 4.0, 5.0, 6.0)
    private val panelCounts = listOf(10.0, 20.0)

    val topic = Topic(
        id = "renewable_energy",
        title = "Renewable Energy",
        subtitle = "Solar cells, wind turbines, and energy harvesting",
        icon = "\u2600\uFE0F",
        order = 24,
        lesson = Lesson(
            title = "Renewable Energy: Powering the Future",
            sections = listOf(
                LessonSection(
                    heading = "Solar Cell Physics",
                    content = "A solar cell is a large-area PN junction diode that converts sunlight directly into electricity through the photovoltaic effect.\n\nWhen photons with energy greater than the band gap (1.12eV for silicon) strike the cell, they create electron-hole pairs. The junction's built-in electric field separates them, producing current.\n\nA single silicon solar cell produces ~0.5\u20130.6V open-circuit. Cells are connected in series for higher voltage and in parallel for higher current. A typical 60-cell panel produces ~30V.",
                    formula = "V_oc \u2248 0.5\u20130.6V per cell\nP = V_mp \u00D7 I_mp",
                    keyPoint = "Solar cell = PN junction; photons create electron-hole pairs that produce current"
                ),
                LessonSection(
                    heading = "Solar Panel Characteristics",
                    content = "A solar panel's I-V curve shows how current varies with voltage:\n\n\u2022 Short-circuit current (I_sc): maximum current (voltage = 0)\n\u2022 Open-circuit voltage (V_oc): maximum voltage (current = 0)\n\u2022 Maximum Power Point (MPP): the V\u00D7I point that delivers the most power\n\nEfficiency = P_out / (Irradiance \u00D7 Area). Typical silicon panels are 18\u201322% efficient. Standard test conditions: 1000 W/m\u00B2 irradiance, 25\u00B0C.\n\nHigher temperature reduces V_oc (~\u22120.3%/\u00B0C), lowering power output.",
                    formula = "\u03B7 = P_max / (G \u00D7 A) \u00D7 100%\nG = 1000 W/m\u00B2 (STC)",
                    keyPoint = "MPP is where V\u00D7I is maximized; efficiency 18\u201322% for silicon"
                ),
                LessonSection(
                    heading = "Maximum Power Point Tracking (MPPT)",
                    content = "The MPP shifts with changing sunlight and temperature. An MPPT controller continuously adjusts the operating point to extract maximum power.\n\nCommon algorithms:\n\u2022 Perturb & Observe (P&O): slightly changes voltage, checks if power increased or decreased, adjusts accordingly\n\u2022 Incremental Conductance: compares dI/dV to \u2212I/V to find MPP mathematically\n\nMPPT controllers use DC-DC converters (buck, boost, or buck-boost) to transform the panel voltage to the required battery or bus voltage while tracking MPP. They achieve 95\u201399% efficiency.",
                    formula = "At MPP: dP/dV = 0\nIncremental: dI/dV = \u2212I/V",
                    keyPoint = "MPPT continuously finds the voltage where power is maximized"
                ),
                LessonSection(
                    heading = "Wind Turbines",
                    content = "Wind turbines convert kinetic energy of wind into electricity. The power available in wind is:\n\nP_wind = \u00BD \u00D7 \u03C1 \u00D7 A \u00D7 v\u00B3\n\nwhere \u03C1 is air density, A is swept area, and v is wind speed. Power scales with the cube of wind speed \u2014 doubling wind speed gives 8\u00D7 the power!\n\nThe Betz limit states that no turbine can capture more than 59.3% of the wind's kinetic energy. Practical turbines achieve 35\u201345%.\n\nMost large turbines use permanent magnet synchronous generators with variable-speed drives.",
                    formula = "P_wind = \u00BD\u03C1Av\u00B3\nBetz limit: \u03B7_max = 59.3%",
                    keyPoint = "Wind power \u221D v\u00B3; Betz limit caps extraction at 59.3%"
                ),
                LessonSection(
                    heading = "Grid-Tie Inverters",
                    content = "Grid-tie inverters convert DC from solar panels or wind turbines into AC synchronized with the utility grid.\n\nRequirements:\n\u2022 Match grid voltage (e.g., 240V RMS) and frequency (50/60Hz)\n\u2022 Synchronize phase with the grid\n\u2022 Meet power quality standards (low harmonic distortion, THD < 5%)\n\u2022 Anti-islanding: detect grid outage and disconnect within 2 seconds for safety\n\nModern inverters use high-frequency PWM with IGBT or MOSFET switches, achieving 95\u201398% efficiency.",
                    keyPoint = "Grid-tie: must sync voltage, frequency, and phase; anti-islanding is mandatory"
                ),
                LessonSection(
                    heading = "Energy Harvesting",
                    content = "Energy harvesting captures tiny amounts of ambient energy to power low-power electronics (sensors, IoT devices):\n\n\u2022 Photovoltaic: indoor light (10\u2013100 \u00B5W/cm\u00B2)\n\u2022 Thermoelectric: temperature gradients (10\u201350 \u00B5W/cm\u00B2)\n\u2022 Piezoelectric: vibration and motion (1\u2013200 \u00B5W/cm\u00B3)\n\u2022 RF harvesting: ambient radio waves (0.1\u201310 \u00B5W/cm\u00B2)\n\nHarvesting circuits need ultra-low-power DC-DC converters that can start from millivolt inputs. Supercapacitors or small Li-ion cells store the harvested energy for burst operation.",
                    keyPoint = "Harvest ambient energy (\u00B5W to mW) for battery-free sensor nodes"
                )
            )
        ),
        quiz = Quiz(
            title = "Renewable Energy Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "re_mc1",
                    questionText = "Wind power is proportional to wind speed:",
                    options = listOf(
                        "Linearly (v)",
                        "Squared (v\u00B2)",
                        "Cubed (v\u00B3)",
                        "To the fourth power (v\u2074)"
                    ),
                    correctIndex = 2,
                    explanation = "P = \u00BD\u03C1Av\u00B3. Wind power scales with the cube of wind speed, so doubling wind speed gives 8\u00D7 the power."
                ),
                Question.MultipleChoice(
                    id = "re_mc2",
                    questionText = "What does an MPPT controller do?",
                    options = listOf(
                        "Maximizes the solar panel voltage",
                        "Finds the voltage-current operating point that maximizes power",
                        "Converts AC to DC",
                        "Stores energy in batteries"
                    ),
                    correctIndex = 1,
                    explanation = "MPPT continuously adjusts the operating point on the panel's I-V curve to extract maximum power as conditions change."
                ),
                Question.MultipleChoice(
                    id = "re_mc3",
                    questionText = "Anti-islanding protection in grid-tie inverters ensures:",
                    options = listOf(
                        "Maximum power output at all times",
                        "The inverter disconnects when the grid goes down",
                        "The inverter operates at night",
                        "Power flows only from grid to home"
                    ),
                    correctIndex = 1,
                    explanation = "Anti-islanding detects grid outage and disconnects the inverter within 2 seconds to prevent energizing downed power lines, which would endanger utility workers."
                ),
                Question.MultipleChoice(
                    id = "re_mc4",
                    questionText = "Why does a solar panel produce less power on a very hot day, even in full sun?",
                    options = listOf(
                        "The sunlight is weaker",
                        "Cell voltage drops as temperature rises",
                        "The inverter switches off",
                        "Heat increases the cell's band gap"
                    ),
                    correctIndex = 1,
                    explanation = "Silicon cell voltage falls by about 0.3–0.5% per °C above 25°C, so a hot panel makes noticeably less power. That's why panels are rated at 25°C and good airflow behind them helps."
                )
            ),
            generators = listOf(
                generator("re_ni1") { r ->
                    val p = panelPowers.pick(r)
                    val area = panelAreas.pick(r)
                    val eff = p / (STC_IRRADIANCE * area) * 100
                    Numeric(
                        text = "A solar panel produces ${fmt(p)}W under standard test conditions (1000W/m²) and has an area of " +
                            "${fmt(area)}m². What is its efficiency in percent? Round to 1 decimal place.",
                        answer = eff, unit = "%", decimals = 1,
                        explanation = "η = P/(G × A) × 100 = ${fmt(p)}/(1000 × ${fmt(area)}) × 100 = ${fmt(eff, 1)}%"
                    )
                },
                generator("re_ni2") { r ->
                    val avail = availablePowers.pick(r)
                    val max = avail * BETZ_LIMIT
                    Numeric(
                        text = "The wind passing through a turbine's rotor carries ${fmt(avail)}kW. By the Betz limit, what is the " +
                            "most power any turbine could extract, in kW? Round to 1 decimal place.",
                        answer = max, unit = "kW", decimals = 1,
                        explanation = "The Betz limit is 16/27 ≈ 59.3% of the wind's power: 0.593 × ${fmt(avail)} = ${fmt(max, 1)}kW"
                    )
                },
                generator("re_ni3") { r ->
                    val n = cellCounts.pick(r)
                    val vc = cellVoltages.pick(r)
                    Numeric(
                        text = "A ${fmt(n)}-cell solar panel has its cells in series, each giving ${fmt(vc)}V. What is the panel's " +
                            "open-circuit voltage in Volts? Round to 2 decimal places.",
                        answer = n * vc, unit = "V",
                        explanation = "Series cells add: V_oc = ${fmt(n)} × ${fmt(vc)}V = ${fmt(n * vc, 2)}V"
                    )
                },
                generator("re_ni4") { r ->
                    val v1 = windSpeeds.pick(r)
                    val k = speedFactors.pick(r)
                    val v2 = v1 * k
                    val factor = k * k * k
                    Numeric(
                        text = "Wind speed rises from ${fmt(v1)}m/s to ${fmt(v2)}m/s. By what factor does the available wind " +
                            "power increase? Round to 3 decimal places.",
                        answer = factor, unit = "×", decimals = 3,
                        explanation = "P ∝ v³, so the factor is (${fmt(v2)}/${fmt(v1)})³ = ${fmt(k)}³ = ${fmt(factor, 3)}×"
                    )
                },
                generator("re_ni5") { r ->
                    val d = rotorDiameters.pick(r)
                    val v = ratedSpeeds.pick(r)
                    val area = PI * d * d * QUARTER
                    val kw = AIR_DENSITY * area * v * v * v / 2 / 1000
                    Numeric(
                        text = "A wind turbine has a ${fmt(d)}m rotor diameter. How much power does a ${fmt(v)}m/s wind carry through " +
                            "it, in kW? Use P = ½ρAv³ with ρ = 1.225kg/m³ and round to 1 decimal place.",
                        answer = kw, unit = "kW", decimals = 1,
                        explanation = "A = π × ${fmt(d)}²/4 = ${fmt(area, 1)}m². P = ½ × 1.225 × ${fmt(area, 1)} × ${fmt(v)}³ = " +
                            "${fmt(kw, 1)}kW"
                    )
                },
                generator("re_ni6") { r ->
                    val n = panelCounts.pick(r)
                    val p = panelPowers.pick(r)
                    val h = sunHours.pick(r)
                    val kwh = n * p * h / 1000
                    Numeric(
                        text = "A roof has ${fmt(n)} panels of ${fmt(p)}W each, and the site gets ${fmt(h)} peak sun hours a day. " +
                            "Roughly how much energy does it produce per day, in kWh (ignore losses)?",
                        answer = kwh, unit = "kWh",
                        explanation = "Energy = ${fmt(n)} × ${fmt(p)}W × ${fmt(h)}h = ${fmt(n * p * h)}Wh = ${fmt(kwh)}kWh"
                    )
                }
            )
        )
    )
}
