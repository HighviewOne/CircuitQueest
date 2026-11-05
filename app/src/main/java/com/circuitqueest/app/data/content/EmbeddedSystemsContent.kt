package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object EmbeddedSystemsContent {
    private const val UART_BITS_PER_BYTE = 10
    private val adcReferences = listOf(3.3, 5.0)
    private val adcResolutions = listOf(8, 10, 12)
    private val levelBits = listOf(8, 10, 12, 14, 16)
    private val timerBits = listOf(8, 16)
    private val timerClocksMhz = listOf(1.0, 8.0, 16.0, 48.0)
    private val prescalers = listOf(1, 8, 64)
    private val adcReadings = listOf(10 to 256, 10 to 512, 10 to 1000, 12 to 1024, 12 to 2048, 12 to 3000)
    private val baudRates = listOf(9600.0, 115_200.0, 1_000_000.0)
    private val messageBytes = listOf(64.0, 100.0, 1024.0)
    private val pwmSupplies = listOf(3.3, 5.0, 12.0)
    private val pwmDuties = listOf(10.0, 25.0, 50.0, 75.0)

    val topic = Topic(
        id = "embedded_systems",
        title = "Embedded Systems",
        subtitle = "Microcontrollers, peripherals, and communication",
        icon = "\uD83D\uDCDF",
        order = 35,
        lesson = Lesson(
            title = "Embedded Systems: Computers Inside Everything",
            sections = listOf(
                LessonSection(
                    heading = "What is an Embedded System?",
                    content = "An embedded system is a computer designed for a specific task, built into a larger device. Unlike a general-purpose PC, it runs dedicated software (firmware) to control hardware.\n\nExamples are everywhere:\n\u2022 Washing machine controller\n\u2022 Car engine management\n\u2022 Fitness tracker\n\u2022 Smart thermostat\n\nThe heart of most embedded systems is a microcontroller (MCU) \u2014 a single chip containing a CPU, memory, and peripherals.",
                    keyPoint = "Embedded system = microcontroller + firmware for a dedicated task"
                ),
                LessonSection(
                    heading = "Microcontroller Architecture",
                    content = "A microcontroller integrates on a single chip:\n\n\u2022 CPU: executes instructions (8-bit, 16-bit, or 32-bit)\n\u2022 Flash memory: stores the program (non-volatile, typically 16KB\u20132MB)\n\u2022 SRAM: stores variables at runtime (volatile, typically 2KB\u2013512KB)\n\u2022 Peripherals: GPIO, timers, ADC, UART, SPI, I2C, PWM\n\u2022 Clock: internal oscillator or external crystal (typically 1MHz\u2013200MHz)\n\nPopular families: Arduino (ATmega), STM32, ESP32, PIC, MSP430.",
                    keyPoint = "MCU = CPU + Flash + SRAM + peripherals, all on one chip"
                ),
                LessonSection(
                    heading = "GPIO: General Purpose Input/Output",
                    content = "GPIO pins are the MCU's connection to the outside world. Each pin can be configured as:\n\n\u2022 Digital output: drive HIGH (V_DD) or LOW (GND) to control LEDs, relays, etc.\n\u2022 Digital input: read HIGH or LOW from buttons, sensors, etc.\n\u2022 Alternate function: UART, SPI, PWM, ADC (depends on the pin)\n\nInput pins can have internal pull-up or pull-down resistors enabled in software, eliminating external resistors for buttons and switches.",
                    keyPoint = "GPIO pins are configurable: input, output, or alternate peripheral function"
                ),
                LessonSection(
                    heading = "ADC and DAC",
                    content = "Analog-to-Digital Converter (ADC): converts analog voltages to digital numbers the MCU can process. A 10-bit ADC with 3.3V reference has:\n\nResolution = 3.3V / 2\u00B9\u2070 = 3.3V / 1024 \u2248 3.22mV per step\n\nDigital-to-Analog Converter (DAC): outputs analog voltage from a digital value. Not all MCUs have DACs \u2014 PWM with filtering is often used as a simpler alternative.\n\nSampling rate matters: the ADC must sample at least 2\u00D7 the signal frequency (Nyquist).",
                    formula = "Resolution = V_ref / 2^N\nDigital value = V_in / Resolution",
                    keyPoint = "N-bit ADC: 2^N levels; resolution = V_ref / 2^N volts per step"
                ),
                LessonSection(
                    heading = "Communication Protocols",
                    content = "MCUs communicate with sensors, displays, and other devices using serial protocols:\n\n\u2022 UART: asynchronous, 2 wires (TX, RX), point-to-point. Used for debug consoles and GPS modules. Common: 9600 or 115200 baud.\n\u2022 SPI: synchronous, 4 wires (MOSI, MISO, SCK, CS), fast (1\u201350MHz). Used for displays, SD cards, fast sensors.\n\u2022 I\u00B2C: synchronous, 2 wires (SDA, SCL), multi-device bus with addresses. Used for sensors, EEPROMs. Typical: 100kHz\u2013400kHz.\n\nSPI is fastest but uses the most pins; I\u00B2C uses fewest pins but is slower.",
                    keyPoint = "UART: simple point-to-point; SPI: fast, 4-wire; I\u00B2C: multi-device, 2-wire"
                ),
                LessonSection(
                    heading = "Interrupts and Timers",
                    content = "Interrupts allow the MCU to respond immediately to events without constantly checking (polling). When an interrupt fires, the CPU pauses its current task, runs the Interrupt Service Routine (ISR), then resumes.\n\nCommon interrupt sources: GPIO pin change, timer overflow, ADC conversion complete, data received on UART.\n\nTimers are hardware counters that count clock cycles. They generate precise time delays, PWM signals, and periodic interrupts. A 16-bit timer at 16MHz overflows every 2\u00B9\u2076/16MHz = 4.096ms.",
                    formula = "Timer overflow = 2^N / f_clock\nPWM frequency = f_clock / (prescaler \u00D7 2^N)",
                    keyPoint = "Interrupts = instant response; Timers = precise timing without CPU load"
                )
            )
        ),
        quiz = Quiz(
            title = "Embedded Systems Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "emb_mc1",
                    questionText = "What does a microcontroller integrate on a single chip?",
                    options = listOf(
                        "Only a CPU",
                        "CPU, memory, and peripherals",
                        "Only memory and peripherals",
                        "A GPU and display controller"
                    ),
                    correctIndex = 1,
                    explanation = "A microcontroller integrates a CPU, Flash memory (program), SRAM (data), and peripherals (GPIO, ADC, timers, etc.) on a single chip."
                ),
                Question.MultipleChoice(
                    id = "emb_mc2",
                    questionText = "Which communication protocol uses only 2 wires and supports multiple devices on the same bus?",
                    options = listOf(
                        "UART",
                        "SPI",
                        "I\u00B2C",
                        "USB"
                    ),
                    correctIndex = 2,
                    explanation = "I\u00B2C uses just 2 wires (SDA and SCL) and supports multiple devices via unique addresses on the same bus."
                ),
                Question.MultipleChoice(
                    id = "emb_mc3",
                    questionText = "What is the main advantage of interrupts over polling?",
                    options = listOf(
                        "Interrupts use less memory",
                        "Interrupts allow instant response without wasting CPU cycles checking",
                        "Interrupts are simpler to program",
                        "Interrupts work without a clock"
                    ),
                    correctIndex = 1,
                    explanation = "Interrupts free the CPU to do other work and respond instantly when an event occurs, unlike polling which wastes cycles continuously checking."
                ),
                Question.MultipleChoice(
                    id = "emb_mc4",
                    questionText = "Which serial protocol is typically the fastest?",
                    options = listOf(
                        "UART",
                        "I\u00B2C",
                        "SPI",
                        "They are all the same speed"
                    ),
                    correctIndex = 2,
                    explanation = "SPI is typically the fastest (1\u201350MHz clock rates) because it uses a dedicated clock line and separate data lines for each direction."
                ),
                Question.MultipleChoice(
                    id = "emb_mc5",
                    questionText = "Why do I²C lines need pull-up resistors?",
                    options = listOf(
                        "To limit the clock speed",
                        "Because devices only pull the lines low (open-drain), so something must pull them high",
                        "To protect against reverse voltage",
                        "To set each device's address"
                    ),
                    correctIndex = 1,
                    explanation = "I²C devices are open-drain: they can only pull SDA and SCL low. Pull-ups (typically 2.2–10kΩ) return the lines high, which lets any device hold the bus low without a conflict."
                )
            ),
            generators = listOf(
                generator("emb_ni1") { r ->
                    val vref = adcReferences.pick(r)
                    val n = adcResolutions.pick(r)
                    val mv = vref / (1 shl n) * 1000
                    Numeric(
                        text = "${articleCap(n.toDouble())} $n-bit ADC has a ${fmt(vref)}V reference. What is its resolution in mV per " +
                            "step? Round to 2 decimal places.",
                        answer = mv, unit = "mV",
                        explanation = "Resolution = V_ref/2^N = ${fmt(vref)}/${1 shl n} = ${fmt(mv, 2)}mV per step"
                    )
                },
                generator("emb_ni2") { r ->
                    val n = levelBits.pick(r)
                    val levels = 1 shl n
                    Numeric(
                        text = "${articleCap(n.toDouble())} $n-bit ADC can represent how many distinct levels?",
                        answer = levels.toDouble(), unit = "levels", exact = true,
                        explanation = "2^$n = $levels levels (codes 0 to ${levels - 1})"
                    )
                },
                generator("emb_ni3") { r ->
                    val bits = timerBits.pick(r)
                    val f = timerClocksMhz.pick(r)
                    val pre = prescalers.pick(r)
                    val ms = (1 shl bits) * pre / f / 1000
                    Numeric(
                        text = "${articleCap(bits.toDouble())} $bits-bit timer counts at ${fmt(f)}MHz with a prescaler of $pre. How often " +
                            "does it overflow, in milliseconds? Round to 3 decimal places.",
                        answer = ms, unit = "ms", decimals = 3,
                        explanation = "Period = 2^$bits × $pre/(${fmt(f)}×10⁶) = ${(1 shl bits) * pre}/${fmt(f)}µs = ${fmt(ms, 3)}ms"
                    )
                },
                generator("emb_ni4") { r ->
                    val (n, code) = adcReadings.pick(r)
                    val vref = adcReferences.pick(r)
                    val v = code.toDouble() / (1 shl n) * vref
                    Numeric(
                        text = "${articleCap(n.toDouble())} $n-bit ADC with a ${fmt(vref)}V reference reads $code. What input voltage " +
                            "is that, in Volts? Use V = code × V_ref/2^N and round to 3 decimal places.",
                        answer = v, unit = "V", decimals = 3,
                        explanation = "V = $code × ${fmt(vref)}/${1 shl n} = ${fmt(v, 3)}V"
                    )
                },
                generator("emb_ni5") { r ->
                    val baud = baudRates.pick(r)
                    val bytes = messageBytes.pick(r)
                    val ms = bytes * UART_BITS_PER_BYTE / baud * 1000
                    Numeric(
                        text = "How long does it take to send ${fmt(bytes)} bytes over a UART at ${fmt(baud)} baud with 8N1 framing " +
                            "(10 bits per byte), in milliseconds? Round to 3 decimal places.",
                        answer = ms, unit = "ms", decimals = 3,
                        explanation = "${fmt(bytes)} × 10 bits = ${fmt(bytes * UART_BITS_PER_BYTE)} bits; at ${fmt(baud)} bits/s that " +
                            "takes ${fmt(ms, 3)}ms"
                    )
                },
                generator("emb_ni6") { r ->
                    val vcc = pwmSupplies.pick(r)
                    val duty = pwmDuties.pick(r)
                    val avg = vcc * duty / 100
                    Numeric(
                        text = "A PWM output switches between 0V and ${fmt(vcc)}V at ${fmt(duty)}% duty cycle. What is its average " +
                            "voltage, in Volts? Round to 3 decimal places.",
                        answer = avg, unit = "V", decimals = 3,
                        explanation = "V_avg = duty × V_high = ${fmt(duty / 100)} × ${fmt(vcc)} = ${fmt(avg, 3)}V"
                    )
                }
            )
        )
    )
}
