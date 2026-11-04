package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object BatteryStorageContent {
    private const val FULL_CELL = 4.2
    private const val NOMINAL_CELL = 3.7
    private val packVoltages = listOf(3.7, 7.4, 11.1, 12.0)
    private val capacitiesMah = listOf(2000.0, 3000.0, 5000.0, 10_000.0)
    private val cRateCapacities = listOf(1000.0, 2000.0, 2500.0, 5000.0)
    private val cRates = listOf(0.5, 1.0, 2.0, 5.0)
    private val seriesCells = listOf(2.0, 3.0, 4.0, 6.0, 12.0)
    private val runtimeCapacities = listOf(1000.0, 2000.0, 5000.0, 10_000.0)
    private val loadCurrents = listOf(100.0, 250.0, 500.0, 1000.0)
    private val dischargeRates = listOf(0.5, 1.0, 2.0, 4.0, 5.0)
    private val supercapFarads = listOf(1.0, 10.0, 100.0)
    private val supercapVoltages = listOf(2.7, 5.0)

    val topic = Topic(
        id = "battery_storage",
        title = "Battery & Energy Storage",
        subtitle = "Chemistry, charging, and battery management",
        icon = "\uD83D\uDD0B",
        order = 26,
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
                Question.MultipleChoice(
                    id = "bat_mc4",
                    questionText = "Which lithium chemistry is prized for long cycle life and thermal stability, at the cost of lower energy density?",
                    options = listOf(
                        "LiCoO₂ (LCO)",
                        "LiFePO₄ (LFP)",
                        "NMC",
                        "Lead-acid"
                    ),
                    correctIndex = 1,
                    explanation = "LFP (lithium iron phosphate) cells run at about 3.2V nominal, last thousands of cycles and resist thermal runaway, so they're common in stationary storage and many EVs, though they store less energy per kg than NMC."
                )
            ),
            generators = listOf(
                generator("bat_ni1") { r ->
                    val v = packVoltages.pick(r)
                    val mah = capacitiesMah.pick(r)
                    val wh = v * mah / 1000
                    Numeric(
                        text = "${articleCap(v)} ${fmt(v)}V, ${fmt(mah)}mAh battery stores how many watt-hours? Round to 2 decimal places.",
                        answer = wh, unit = "Wh",
                        explanation = "Energy = V × Ah = ${fmt(v)} × ${fmt(mah / 1000)} = ${fmt(wh, 2)}Wh"
                    )
                },
                generator("bat_ni2") { r ->
                    val cap = cRateCapacities.pick(r)
                    val c = cRates.pick(r)
                    Numeric(
                        text = "${articleCap(cap)} ${fmt(cap)}mAh battery is discharged at ${fmt(c)}C. What is the discharge current, " +
                            "in mA?",
                        answer = c * cap, unit = "mA",
                        explanation = "I = C-rate × capacity = ${fmt(c)} × ${fmt(cap)} = ${fmt(c * cap)}mA"
                    )
                },
                generator("bat_ni3") { r ->
                    val n = seriesCells.pick(r)
                    val full = r.nextBoolean()
                    val vcell = if (full) FULL_CELL else NOMINAL_CELL
                    val state = if (full) "fully charged" else "nominal"
                    Numeric(
                        text = "A pack has ${fmt(n)} Li-ion cells in series. What is its $state voltage, " +
                            "in Volts? Round to 1 decimal place.",
                        answer = n * vcell, unit = "V", decimals = 1,
                        explanation = "A Li-ion cell is ${fmt(NOMINAL_CELL)}V nominal and ${fmt(FULL_CELL)}V when full: " +
                            "${fmt(n)} × ${fmt(vcell)} = ${fmt(n * vcell, 1)}V"
                    )
                },
                generator("bat_ni4") { r ->
                    val cap = runtimeCapacities.pick(r)
                    val i = loadCurrents.pick(r)
                    val h = cap / i
                    Numeric(
                        text = "${articleCap(cap)} ${fmt(cap)}mAh battery powers a device drawing ${fmt(i)}mA. Ideally, how many hours " +
                            "will it last? Round to 2 decimal places.",
                        answer = h, unit = "hours",
                        explanation = "Time = capacity/current = ${fmt(cap)}/${fmt(i)} = ${fmt(h, 2)} hours"
                    )
                },
                generator("bat_ni5") { r ->
                    val c = dischargeRates.pick(r)
                    val min = 60 / c
                    Numeric(
                        text = "A battery is discharged at a constant ${fmt(c)}C. Ideally, how many minutes does a full discharge " +
                            "take? Round to 1 decimal place.",
                        answer = min, unit = "minutes", decimals = 1,
                        explanation = "1C empties the battery in 1 hour, so ${fmt(c)}C takes 60/${fmt(c)} = ${fmt(min, 1)} minutes"
                    )
                },
                generator("bat_ni6") { r ->
                    val f = supercapFarads.pick(r)
                    val v = supercapVoltages.pick(r)
                    val j = f * v * v / 2
                    Numeric(
                        text = "${articleCap(f)} ${fmt(f)}F supercapacitor is charged to ${fmt(v)}V. How much energy does it store, " +
                            "in joules? Round to 2 decimal places.",
                        answer = j, unit = "J",
                        explanation = "E = ½CV² = ½ × ${fmt(f)} × ${fmt(v)}² = ${fmt(j, 2)}J"
                    )
                }
            )
        )
    )
}
