package com.circuitqueest.app.data.content

object EmbeddedSystemsContent {
    val topic = Topic(
        id = "embedded_systems",
        title = "Embedded Systems",
        subtitle = "Microcontrollers, peripherals, and communication",
        icon = "\uD83D\uDCDF",
        order = 23,
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
                Question.NumericInput(
                    id = "emb_ni1",
                    questionText = "A 10-bit ADC has a reference voltage of 5V. What is its resolution in millivolts per step? Round to one decimal.",
                    correctAnswer = 4.9,
                    tolerance = 0.1,
                    unit = "mV",
                    explanation = "Resolution = V_ref / 2^N = 5 / 1024 = 0.00488V \u2248 4.9mV per step."
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
                Question.NumericInput(
                    id = "emb_ni2",
                    questionText = "A 12-bit ADC can represent how many distinct voltage levels?",
                    correctAnswer = 4096.0,
                    tolerance = 1.0,
                    unit = "levels",
                    explanation = "2^12 = 4096 distinct levels (values 0 to 4095)."
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
                Question.NumericInput(
                    id = "emb_ni3",
                    questionText = "A 16-bit timer running at 8MHz overflows every how many milliseconds? Round to two decimal places.",
                    correctAnswer = 8.19,
                    tolerance = 0.02,
                    unit = "ms",
                    explanation = "Overflow period = 2^16 / 8,000,000 = 65536 / 8000000 = 0.008192s = 8.19ms."
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
                )
            )
        )
    )
}
