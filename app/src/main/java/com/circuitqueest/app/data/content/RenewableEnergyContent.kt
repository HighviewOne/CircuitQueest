package com.circuitqueest.app.data.content

object RenewableEnergyContent {
    val topic = Topic(
        id = "renewable_energy",
        title = "Renewable Energy",
        subtitle = "Solar cells, wind turbines, and energy harvesting",
        icon = "\u2600\uFE0F",
        order = 32,
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
                Question.NumericInput(
                    id = "re_ni1",
                    questionText = "A solar panel produces 300W under STC (1000 W/m\u00B2) and has an area of 1.6 m\u00B2. What is its efficiency in percent? Round to one decimal.",
                    correctAnswer = 18.8,
                    tolerance = 0.2,
                    unit = "%",
                    explanation = "\u03B7 = P/(G\u00D7A) \u00D7 100 = 300/(1000\u00D71.6) \u00D7 100 = 300/1600 \u00D7 100 = 18.75%"
                ),
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
                Question.NumericInput(
                    id = "re_ni2",
                    questionText = "The Betz limit states the maximum fraction of wind energy a turbine can extract. What is this limit in percent? Round to one decimal.",
                    correctAnswer = 59.3,
                    tolerance = 0.5,
                    unit = "%",
                    explanation = "The Betz limit is 16/27 \u2248 0.593 or 59.3%. No wind turbine can exceed this theoretical maximum."
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
                Question.NumericInput(
                    id = "re_ni3",
                    questionText = "A 60-cell solar panel has cells in series, each producing 0.5V. What is the panel's open-circuit voltage in Volts?",
                    correctAnswer = 30.0,
                    tolerance = 0.5,
                    unit = "V",
                    explanation = "V_oc = 60 cells \u00D7 0.5V/cell = 30V"
                ),
                Question.NumericInput(
                    id = "re_ni4",
                    questionText = "If wind speed doubles from 5 m/s to 10 m/s, by what factor does the available wind power increase?",
                    correctAnswer = 8.0,
                    tolerance = 0.1,
                    unit = "\u00D7",
                    explanation = "P \u221D v\u00B3. Factor = (10/5)\u00B3 = 2\u00B3 = 8\u00D7"
                )
            )
        )
    )
}
