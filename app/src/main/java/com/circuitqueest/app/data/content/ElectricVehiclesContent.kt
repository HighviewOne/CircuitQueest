package com.circuitqueest.app.data.content

object ElectricVehiclesContent {
    val topic = Topic(
        id = "electric_vehicles",
        title = "Electric Vehicles",
        subtitle = "EV powertrains, inverters, and charging systems",
        icon = "\uD83D\uDE97",
        order = 36,
        lesson = Lesson(
            title = "Electric Vehicles: Driving the Future",
            sections = listOf(
                LessonSection(
                    heading = "EV Powertrain Architecture",
                    content = "An electric vehicle replaces the internal combustion engine with an electric drivetrain:\n\n\u2022 Battery pack: high-voltage lithium-ion (typically 400V or 800V), stores 40\u2013100+ kWh\n\u2022 Traction inverter: converts battery DC to variable-frequency 3-phase AC\n\u2022 Electric motor: permanent magnet synchronous (PMSM) or induction motor\n\u2022 Gearbox: usually a single-speed reducer (no multi-gear transmission needed)\n\u2022 Battery Management System (BMS): monitors cell voltages, temperature, state of charge\n\nEVs are fundamentally simpler than ICE vehicles: fewer moving parts, no oil changes, and instant torque from zero RPM.",
                    keyPoint = "EV = battery + inverter + motor; simpler than ICE with instant torque"
                ),
                LessonSection(
                    heading = "Traction Inverter",
                    content = "The traction inverter is the power electronics heart of an EV. It converts the battery's DC voltage into variable-frequency, variable-voltage 3-phase AC to control the motor.\n\nA typical 3-phase inverter uses 6 power switches (IGBTs or SiC MOSFETs) arranged in 3 half-bridges. Pulse Width Modulation (PWM) at 5\u201320 kHz controls the output.\n\nSiC (silicon carbide) MOSFETs are replacing IGBTs in modern EVs because they:\n\u2022 Switch faster with lower losses\n\u2022 Enable higher voltage operation (800V+)\n\u2022 Reduce cooling requirements\n\u2022 Increase range by 5\u201310%\n\nPower levels: 100\u2013400 kW for passenger cars, up to 600+ kW for performance vehicles.",
                    formula = "P_motor = \u221A3 \u00D7 V_L \u00D7 I_L \u00D7 cos\u03C6\nPWM frequency: 5\u201320 kHz",
                    keyPoint = "Traction inverter: 6-switch bridge converts DC to variable AC; SiC enables 800V"
                ),
                LessonSection(
                    heading = "EV Motors",
                    content = "Two dominant motor types in EVs:\n\n1. Permanent Magnet Synchronous Motor (PMSM):\n\u2022 Uses rare-earth magnets (NdFeB) in the rotor\n\u2022 High efficiency (95%+) and power density\n\u2022 Used by most manufacturers (Tesla Model 3 rear, BMW, Hyundai)\n\n2. AC Induction Motor (IM):\n\u2022 No magnets \u2014 rotor current is induced\n\u2022 Slightly lower efficiency but lower cost and no rare-earth dependency\n\u2022 More robust at very high speeds\n\u2022 Used in Tesla Model S/X front motor\n\nMotors are rated by peak power (short burst) and continuous power (sustained). Torque is available from 0 RPM, enabling instant acceleration.",
                    formula = "Torque: T = K_t \u00D7 I_q\nPower: P = T \u00D7 \u03C9 = T \u00D7 2\u03C0n/60",
                    keyPoint = "PMSM: highest efficiency; Induction: no rare-earth magnets, lower cost"
                ),
                LessonSection(
                    heading = "Regenerative Braking",
                    content = "When an EV decelerates, the motor runs as a generator, converting kinetic energy back to electrical energy and charging the battery. This is regenerative braking.\n\nBenefits:\n\u2022 Recovers 60\u201370% of braking energy (vs. 0% in friction brakes)\n\u2022 Extends range by 10\u201325% in city driving\n\u2022 Reduces brake pad wear dramatically\n\nThe inverter controls regen by adjusting the motor's field to produce negative torque. The regen rate is limited by:\n\u2022 Battery charge acceptance (limited when cold or near full)\n\u2022 Motor and inverter current limits\n\u2022 Road conditions (traction limits)\n\nOne-pedal driving uses aggressive regen to slow the car without touching the brake pedal.",
                    formula = "E_regen = \u00BD \u00D7 m \u00D7 (v\u00B2_initial \u2212 v\u00B2_final) \u00D7 \u03B7_regen",
                    keyPoint = "Regen braking: motor becomes generator, recovering 60\u201370% of braking energy"
                ),
                LessonSection(
                    heading = "EV Charging",
                    content = "EV charging comes in three levels:\n\n\u2022 Level 1 (120V AC, ~1.4 kW): standard household outlet, adds ~5 miles/hour. Emergency use only.\n\u2022 Level 2 (240V AC, 7\u201319 kW): home/workplace charger, adds 25\u201375 miles/hour. Uses onboard AC-DC charger.\n\u2022 DC Fast Charging (200\u2013920V DC, 50\u2013350 kW): bypasses onboard charger, feeds battery directly. Adds 100\u2013200+ miles in 15\u201330 minutes.\n\nCharging standards:\n\u2022 CCS (Combined Charging System): dominant in North America and Europe\n\u2022 NACS (Tesla connector): increasingly adopted as standard\n\u2022 CHAdeMO: used by some Japanese manufacturers\n\nCharging follows a power curve: fastest at low state of charge, slowing as the battery fills to protect cell life.",
                    keyPoint = "Level 2 for daily home charging; DC fast charge (50\u2013350 kW) for road trips"
                ),
                LessonSection(
                    heading = "Battery Pack Engineering",
                    content = "An EV battery pack is a complex system of cells, modules, and management electronics:\n\n\u2022 Cell formats: cylindrical (2170, 4680), pouch, or prismatic\n\u2022 Series connection for voltage: 96S for 400V pack, 192S for 800V\n\u2022 Parallel connection for capacity: 3P\u20136P typical\n\u2022 Thermal management: liquid cooling maintains 20\u201340\u00B0C for optimal life and performance\n\nThe BMS continuously monitors:\n\u2022 Individual cell voltages (to \u00B11mV accuracy)\n\u2022 Pack current and temperature\n\u2022 State of Charge (SoC) and State of Health (SoH)\n\u2022 Cell balancing (passive or active)\n\nPack energy density targets: >250 Wh/kg at cell level, >180 Wh/kg at pack level for current technology.",
                    formula = "V_pack = V_cell \u00D7 N_series\nE_pack = V_nominal \u00D7 Ah_capacity",
                    keyPoint = "BMS is critical: monitors every cell, manages thermal and balancing"
                )
            )
        ),
        quiz = Quiz(
            title = "Electric Vehicles Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "ev_mc1",
                    questionText = "The traction inverter in an EV converts:",
                    options = listOf(
                        "AC from the grid to DC for the battery",
                        "Battery DC to variable-frequency 3-phase AC for the motor",
                        "Motor AC back to DC for charging",
                        "Low voltage DC to high voltage DC"
                    ),
                    correctIndex = 1,
                    explanation = "The traction inverter converts the battery's DC voltage into variable-frequency, variable-voltage 3-phase AC to control the motor's speed and torque."
                ),
                Question.NumericInput(
                    id = "ev_ni1",
                    questionText = "An 800V battery pack uses cells with 4.2V nominal voltage in series. How many cells are needed in series? Round to nearest whole number.",
                    correctAnswer = 190.0,
                    tolerance = 2.0,
                    unit = "cells",
                    explanation = "N_series = V_pack / V_cell = 800 / 4.2 \u2248 190 cells in series."
                ),
                Question.MultipleChoice(
                    id = "ev_mc2",
                    questionText = "Regenerative braking works by:",
                    options = listOf(
                        "Applying friction brakes harder",
                        "Running the motor as a generator to convert kinetic energy back to electrical energy",
                        "Disconnecting the motor from the wheels",
                        "Increasing motor speed"
                    ),
                    correctIndex = 1,
                    explanation = "During regenerative braking, the motor operates as a generator, converting the vehicle's kinetic energy back into electrical energy that charges the battery."
                ),
                Question.NumericInput(
                    id = "ev_ni2",
                    questionText = "A Level 2 charger delivers 11 kW. How many hours to charge a 77 kWh battery from empty? Round to one decimal.",
                    correctAnswer = 7.0,
                    tolerance = 0.1,
                    unit = "hours",
                    explanation = "Time = Energy / Power = 77 kWh / 11 kW = 7.0 hours"
                ),
                Question.MultipleChoice(
                    id = "ev_mc3",
                    questionText = "SiC MOSFETs are replacing IGBTs in EV inverters because they:",
                    options = listOf(
                        "Are cheaper to manufacture",
                        "Have lower switching losses and enable higher voltage operation",
                        "Operate at lower temperatures",
                        "Are easier to control"
                    ),
                    correctIndex = 1,
                    explanation = "SiC MOSFETs switch faster with lower losses, handle higher voltages (800V+), and reduce cooling requirements, improving overall EV efficiency and range."
                ),
                Question.NumericInput(
                    id = "ev_ni3",
                    questionText = "Regenerative braking recovers 65% of braking energy. A 2000 kg car decelerates from 100 km/h (27.8 m/s) to 0. How much energy is recovered in kJ? Round to nearest whole number.",
                    correctAnswer = 502.0,
                    tolerance = 10.0,
                    unit = "kJ",
                    explanation = "KE = \u00BD\u00D7m\u00D7v\u00B2 = \u00BD\u00D72000\u00D727.8\u00B2 = 772,840 J \u2248 773 kJ. Recovered = 773 \u00D7 0.65 \u2248 502 kJ"
                ),
                Question.MultipleChoice(
                    id = "ev_mc4",
                    questionText = "DC fast charging is faster than Level 2 because it:",
                    options = listOf(
                        "Uses a bigger onboard charger",
                        "Bypasses the onboard charger and feeds DC directly to the battery",
                        "Charges at a higher frequency",
                        "Uses a lower voltage"
                    ),
                    correctIndex = 1,
                    explanation = "DC fast chargers contain their own large AC-DC converter, bypassing the vehicle's smaller onboard charger. This allows power delivery of 50\u2013350 kW directly to the battery."
                )
            )
        )
    )
}
