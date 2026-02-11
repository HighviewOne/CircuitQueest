package com.circuitqueest.app.data.content

object SensorsMeasurementContent {
    val topic = Topic(
        id = "sensors_measurement",
        title = "Sensors & Measurement",
        subtitle = "Transducers, signal conditioning, and instrumentation",
        icon = "\uD83C\uDF21\uFE0F",
        order = 25,
        lesson = Lesson(
            title = "Sensors & Measurement: Reading the Physical World",
            sections = listOf(
                LessonSection(
                    heading = "Sensors and Transducers",
                    content = "A sensor detects a physical quantity (temperature, pressure, light, etc.) and converts it to an electrical signal that a circuit can process.\n\nA transducer converts one form of energy to another. All sensors are transducers, but not all transducers are sensors (e.g., a speaker converts electrical to sound).\n\nThe ideal sensor is linear, accurate, repeatable, and has fast response time. Real sensors have non-linearity, offset, drift, and noise that must be handled by signal conditioning.",
                    keyPoint = "Sensor = physical quantity \u2192 electrical signal"
                ),
                LessonSection(
                    heading = "Temperature Sensors",
                    content = "Common temperature sensing methods:\n\n\u2022 Thermistor: resistance changes with temperature. NTC (decreases with heat) is most common. Non-linear but sensitive.\n\u2022 RTD (Resistance Temperature Detector): platinum wire, very linear and accurate. Pt100 = 100\u03A9 at 0\u00B0C.\n\u2022 Thermocouple: two dissimilar metals generate a voltage (Seebeck effect). Wide range (\u2212200\u00B0C to +2000\u00B0C) but small signal (~40\u00B5V/\u00B0C).\n\u2022 IC sensors: LM35, TMP36 output calibrated voltage (e.g., 10mV/\u00B0C). Easiest to use.",
                    formula = "LM35: V_out = 10mV \u00D7 T(\u00B0C)\nPt100: R = 100(1 + 0.00385T)",
                    keyPoint = "Thermistors: sensitive but non-linear; RTDs: accurate and linear"
                ),
                LessonSection(
                    heading = "The Wheatstone Bridge",
                    content = "The Wheatstone bridge is a classic circuit for measuring small resistance changes with high precision. Four resistors form a diamond, with the sensor replacing one.\n\nWhen balanced (R1/R2 = R3/R4), the voltage difference across the bridge is zero. A small change in the sensor resistance unbalances the bridge, producing a proportional voltage.\n\nThis principle is used in strain gauges, pressure sensors, and precision temperature measurement.",
                    formula = "V_out = V_s \u00D7 (R3/(R3+R4) \u2212 R2/(R1+R2))\nBalanced: R1\u00D7R4 = R2\u00D7R3",
                    keyPoint = "Wheatstone bridge detects tiny resistance changes with high sensitivity"
                ),
                LessonSection(
                    heading = "Signal Conditioning",
                    content = "Raw sensor signals are often too small, noisy, or non-linear for direct use. Signal conditioning processes them:\n\n\u2022 Amplification: boost small signals (mV range) to usable levels (0\u20135V). Op-amp circuits handle this.\n\u2022 Filtering: remove noise and interference (low-pass filter for slow-changing signals).\n\u2022 Linearization: correct for sensor non-linearity (lookup tables or analog circuits).\n\u2022 Level shifting: adjust signal range to match ADC input (e.g., \u00B110mV \u2192 0\u20133.3V).",
                    keyPoint = "Amplify, filter, linearize, and level-shift before the ADC"
                ),
                LessonSection(
                    heading = "Instrumentation Amplifier",
                    content = "The instrumentation amplifier (INA) is the gold standard for sensor signal conditioning. It is a specialized differential amplifier with:\n\n\u2022 Very high input impedance (won't load the sensor)\n\u2022 High common-mode rejection (ignores noise present on both inputs)\n\u2022 Gain set by a single resistor: G = 1 + 2R_f/R_gain\n\nInternally, it uses three op-amps. Common ICs: INA128, AD620. Used in medical instruments, strain gauge amplifiers, and precision measurements.",
                    formula = "G = 1 + (2R_f / R_gain)\nV_out = G \u00D7 (V+ \u2212 V\u2212)",
                    keyPoint = "Instrumentation amp: high CMRR, high Z_in, gain set by one resistor"
                ),
                LessonSection(
                    heading = "Measurement Accuracy",
                    content = "Key metrics for measurement quality:\n\n\u2022 Accuracy: how close to the true value (affected by calibration)\n\u2022 Precision (repeatability): how consistent repeated measurements are\n\u2022 Resolution: smallest change detectable (set by ADC bits and signal range)\n\u2022 Sensitivity: output change per unit input change (e.g., mV/\u00B0C)\n\u2022 Noise: random fluctuations that obscure the signal (SNR matters)\n\nA measurement can be precise but inaccurate (consistently wrong), or accurate but imprecise (scattered around the true value).",
                    keyPoint = "Accuracy \u2260 precision; both matter; resolution is set by ADC bits"
                )
            )
        ),
        quiz = Quiz(
            title = "Sensors & Measurement Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "sm_mc1",
                    questionText = "Which temperature sensor offers the widest measurement range?",
                    options = listOf(
                        "Thermistor",
                        "RTD (Pt100)",
                        "Thermocouple",
                        "LM35 IC sensor"
                    ),
                    correctIndex = 2,
                    explanation = "Thermocouples can measure from about \u2212200\u00B0C to over +2000\u00B0C, far exceeding the range of other sensor types."
                ),
                Question.NumericInput(
                    id = "sm_ni1",
                    questionText = "An LM35 temperature sensor outputs 10mV/\u00B0C. At 25\u00B0C, what is the output voltage in millivolts?",
                    correctAnswer = 250.0,
                    tolerance = 1.0,
                    unit = "mV",
                    explanation = "V_out = 10mV/\u00B0C \u00D7 25\u00B0C = 250mV"
                ),
                Question.MultipleChoice(
                    id = "sm_mc2",
                    questionText = "A Wheatstone bridge is used to measure:",
                    options = listOf(
                        "Large voltages",
                        "Small resistance changes with high precision",
                        "High-frequency signals",
                        "Power consumption"
                    ),
                    correctIndex = 1,
                    explanation = "The Wheatstone bridge excels at detecting small resistance changes (e.g., from strain gauges or RTDs) by converting them to a measurable voltage."
                ),
                Question.NumericInput(
                    id = "sm_ni2",
                    questionText = "An instrumentation amplifier has R_f = 25k\u03A9 and R_gain = 1k\u03A9. What is the gain? (G = 1 + 2R_f/R_gain)",
                    correctAnswer = 51.0,
                    tolerance = 0.5,
                    unit = "",
                    explanation = "G = 1 + 2 \u00D7 25k/1k = 1 + 50 = 51"
                ),
                Question.MultipleChoice(
                    id = "sm_mc3",
                    questionText = "A measurement that is precise but not accurate means:",
                    options = listOf(
                        "Readings are scattered randomly",
                        "Readings are consistent but offset from the true value",
                        "The sensor has poor resolution",
                        "The ADC has too few bits"
                    ),
                    correctIndex = 1,
                    explanation = "Precise but inaccurate means readings cluster tightly together (repeatable) but are consistently offset from the true value. Calibration can fix this."
                ),
                Question.NumericInput(
                    id = "sm_ni3",
                    questionText = "A Pt100 RTD has R = 100\u03A9 at 0\u00B0C and a coefficient of 0.385\u03A9/\u00B0C. What is its resistance at 50\u00B0C in Ohms?",
                    correctAnswer = 119.25,
                    tolerance = 0.25,
                    unit = "\u03A9",
                    explanation = "R = 100 + 0.385 \u00D7 50 = 100 + 19.25 = 119.25\u03A9"
                ),
                Question.MultipleChoice(
                    id = "sm_mc4",
                    questionText = "What is the main advantage of an instrumentation amplifier over a basic op-amp?",
                    options = listOf(
                        "It is cheaper",
                        "It has high common-mode rejection and high input impedance",
                        "It can operate at higher frequencies",
                        "It doesn't need a power supply"
                    ),
                    correctIndex = 1,
                    explanation = "Instrumentation amplifiers have very high CMRR (rejecting noise common to both inputs) and high input impedance (won't load the sensor), making them ideal for precision measurements."
                )
            )
        )
    )
}
