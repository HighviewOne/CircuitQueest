package com.circuitqueest.app.data.content

object BatteryStorageContent {
    val topic = Topic(
        id = "battery_storage",
        title = "Battery & Energy Storage",
        subtitle = "Chemistry, charging, and battery management",
        icon = "\uD83D\uDD0B",
        order = 29,
        lesson = Lesson(
            title = "Battery & Energy Storage: Portable Power",
            sections = listOf(
                LessonSection(
                    heading = "Battery Fundamentals",
                    content = "A battery converts chemical energy to electrical energy through electrochemical reactions. It has three key components:\n\n\u2022 Anode (negative terminal): releases electrons during discharge\n\u2022 Cathode (positive terminal): accepts electrons during discharge\n\u2022 Electrolyte: allows ion transport between electrodes\n\nPrimary batteries (alkaline, lithium) are single-use. Secondary batteries (Li-ion, NiMH, lead-acid) are rechargeable \u2014 the chemical reaction is reversible.",
                    keyPoint = "Primary = single-use; Secondary = rechargeable; chemical \u2192 electrical energy"
                ),
                LessonSection(
                    heading = "Lithium-Ion Batteries",
                    content = "Li-ion batteries dominate modern electronics due to their high energy density and no memory effect.\n\nKey specifications:\n\u2022 Nominal voltage: 3.7V per cell (range: 3.0V empty to 4.2V full)\n\u2022 Energy density: 150\u2013260 Wh/kg (best of rechargeable chemistries)\n\u2022 Cycle life: 300\u20131000 full charge/discharge cycles\n\u2022 Self-discharge: ~2\u20133% per month\n\nVariants: LiCoO\u2082 (phones), LiFePO\u2084 (safer, longer life), NMC (EVs), LiPo (flexible form factor).",
                    formula = "Nominal: 3.7V/cell\nFull: 4.2V | Empty: 3.0V",
                    keyPoint = "Li-ion: 3.7V nominal, 4.2V max, high energy density, no memory effect"
                ),
                LessonSection(
                    heading = "Capacity and C-Rate",
                    content = "Battery capacity is measured in ampere-hours (Ah) or milliampere-hours (mAh). A 3000mAh battery can supply 3000mA for 1 hour, or 1500mA for 2 hours (ideally).\n\nThe C-rate describes charge/discharge speed relative to capacity:\n\u2022 1C: full charge or discharge in 1 hour\n\u2022 0.5C: takes 2 hours\n\u2022 2C: takes 30 minutes (stresses the battery)\n\nEnergy (Wh) = Capacity (Ah) \u00D7 Voltage (V). A 3.7V, 3000mAh battery stores 11.1 Wh.",
                    formula = "C-rate: I = C \u00D7 Capacity\nEnergy(Wh) = V \u00D7 Ah",
                    keyPoint = "1C = discharge in 1 hour; Energy = Voltage \u00D7 Capacity"
                ),
                LessonSection(
                    heading = "Charging Profiles",
                    content = "Li-ion batteries use a CC-CV (Constant Current \u2013 Constant Voltage) charging profile:\n\n1. CC phase: charge at constant current (typically 0.5C\u20131C) until voltage reaches 4.2V\n2. CV phase: hold voltage at 4.2V while current gradually decreases\n3. Termination: stop when current drops below ~C/10 (e.g., 300mA for a 3000mAh cell)\n\nNever exceed 4.2V \u2014 overcharging causes lithium plating, gas generation, and fire risk. Never discharge below 2.5\u20133.0V \u2014 deep discharge damages the cell permanently.",
                    formula = "CC: I = constant until V = 4.2V\nCV: V = 4.2V until I < C/10",
                    keyPoint = "CC-CV charging; never exceed 4.2V or discharge below 3.0V"
                ),
                LessonSection(
                    heading = "Battery Management System (BMS)",
                    content = "A BMS protects and manages battery packs, especially multi-cell configurations:\n\n\u2022 Overvoltage protection: prevents charging above 4.2V per cell\n\u2022 Undervoltage protection: disconnects load below 3.0V\n\u2022 Overcurrent protection: limits discharge current\n\u2022 Temperature monitoring: disables charging below 0\u00B0C or above 45\u00B0C\n\u2022 Cell balancing: equalizes voltage across series cells (passive or active balancing)\n\nFor series cells, the weakest cell limits the entire pack. Balancing ensures all cells are used equally.",
                    keyPoint = "BMS protects against over/under-voltage, overcurrent, and temperature"
                ),
                LessonSection(
                    heading = "Supercapacitors",
                    content = "Supercapacitors (ultracapacitors) bridge the gap between batteries and regular capacitors:\n\n\u2022 Energy density: 5\u201310 Wh/kg (lower than batteries)\n\u2022 Power density: 10,000+ W/kg (much higher than batteries)\n\u2022 Cycle life: 500,000+ cycles (far exceeds batteries)\n\u2022 Charge time: seconds to minutes\n\nThey excel at capturing regenerative braking energy, stabilizing power buses, and providing burst power. They are often paired with batteries: the battery provides sustained energy, and the supercapacitor handles peak demands.",
                    formula = "E = \u00BDCV\u00B2\nP = V\u00B2/(4\u00D7ESR)",
                    keyPoint = "Supercaps: low energy but extreme power density and cycle life"
                )
            )
        ),
        quiz = Quiz(
            title = "Battery & Energy Storage Boss Battle",
            questions = listOf(
                Question.NumericInput(
                    id = "bat_ni1",
                    questionText = "A 3.7V, 5000mAh Li-ion battery stores how many watt-hours of energy?",
                    correctAnswer = 18.5,
                    tolerance = 0.1,
                    unit = "Wh",
                    explanation = "Energy = V \u00D7 Ah = 3.7 \u00D7 5.0 = 18.5 Wh"
                ),
                Question.MultipleChoice(
                    id = "bat_mc1",
                    questionText = "What is the maximum safe charge voltage for a single Li-ion cell?",
                    options = listOf(
                        "3.7V",
                        "4.0V",
                        "4.2V",
                        "5.0V"
                    ),
                    correctIndex = 2,
                    explanation = "Li-ion cells must never be charged above 4.2V. Exceeding this causes dangerous lithium plating and potential thermal runaway."
                ),
                Question.NumericInput(
                    id = "bat_ni2",
                    questionText = "A 2000mAh battery is discharged at 2C. What is the discharge current in milliamps?",
                    correctAnswer = 4000.0,
                    tolerance = 50.0,
                    unit = "mA",
                    explanation = "I = C-rate \u00D7 Capacity = 2 \u00D7 2000mAh = 4000mA. The battery will be depleted in about 30 minutes."
                ),
                Question.MultipleChoice(
                    id = "bat_mc2",
                    questionText = "Li-ion batteries are charged using which profile?",
                    options = listOf(
                        "Constant voltage only",
                        "Constant current only",
                        "CC-CV (Constant Current then Constant Voltage)",
                        "Pulsed charging"
                    ),
                    correctIndex = 2,
                    explanation = "Li-ion charging uses CC-CV: constant current until 4.2V is reached, then constant voltage until current tapers off."
                ),
                Question.MultipleChoice(
                    id = "bat_mc3",
                    questionText = "What is a key advantage of supercapacitors over batteries?",
                    options = listOf(
                        "Higher energy density",
                        "Much higher power density and cycle life",
                        "Higher voltage per cell",
                        "Lower cost per kWh"
                    ),
                    correctIndex = 1,
                    explanation = "Supercapacitors deliver much higher power (fast charge/discharge) and survive 500,000+ cycles, far exceeding battery cycle life."
                ),
                Question.NumericInput(
                    id = "bat_ni3",
                    questionText = "A battery pack has 3 Li-ion cells in series. What is the fully charged pack voltage in Volts?",
                    correctAnswer = 12.6,
                    tolerance = 0.1,
                    unit = "V",
                    explanation = "Each cell is 4.2V when full. 3 \u00D7 4.2V = 12.6V."
                ),
                Question.NumericInput(
                    id = "bat_ni4",
                    questionText = "A 10000mAh battery powers a device drawing 500mA. How many hours will it last (ideal)?",
                    correctAnswer = 20.0,
                    tolerance = 0.5,
                    unit = "hours",
                    explanation = "Time = Capacity / Current = 10000mAh / 500mA = 20 hours."
                )
            )
        )
    )
}
