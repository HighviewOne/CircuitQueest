package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.ceil

object IotWirelessContent {
    private const val HOURS_PER_MONTH = 730.0
    private const val HOURS_PER_DAY = 24
    private val activeCurrentsMa = listOf(5.0, 10.0, 20.0)
    private val activeTimesMs = listOf(10.0, 50.0, 100.0)
    private val sleepCurrentsUa = listOf(1.0, 5.0, 10.0)
    private val wakePeriodsS = listOf(10.0, 30.0, 60.0)
    private val batteryMah = listOf(225.0, 1000.0, 2400.0)
    private val averageUa = listOf(5.0, 10.0, 20.0, 50.0)
    private val hopRanges = listOf(10.0, 30.0, 50.0, 100.0)
    private val hopDistances = listOf(100.0, 200.0, 250.0, 450.0)
    private val dutyActiveMs = listOf(5.0, 10.0, 50.0)
    private val dutyPeriodsS = listOf(1.0, 10.0, 60.0)
    private val packetBytes = listOf(12, 50, 200)
    private val packetsPerHour = listOf(1, 4, 60)
    private val radioPowers = listOf(14.0, 20.0)
    private val sensitivities = listOf(-120.0, -130.0, -137.0)

    val topic = Topic(
        id = "iot_wireless",
        title = "IoT & Wireless",
        subtitle = "Wireless protocols, mesh networks, and low-power design",
        icon = "\uD83C\uDF10",
        order = 38,
        lesson = Lesson(
            title = "IoT & Wireless: Connecting Everything",
            sections = listOf(
                LessonSection(
                    heading = "The Internet of Things",
                    content = "The Internet of Things (IoT) connects everyday objects to the internet, enabling them to send and receive data. IoT devices include smart thermostats, wearable sensors, industrial monitors, and agricultural sensors.\n\nA typical IoT node consists of:\n\u2022 Sensor(s) to measure the environment\n\u2022 Microcontroller to process data\n\u2022 Wireless radio to transmit data\n\u2022 Power source (battery, energy harvesting, or mains)\n\nThe key challenge: balancing functionality, wireless range, and battery life.",
                    keyPoint = "IoT = sensors + MCU + wireless radio + power; battery life is the key challenge"
                ),
                LessonSection(
                    heading = "Wi-Fi and Bluetooth/BLE",
                    content = "Short-range wireless standards for IoT:\n\n\u2022 Wi-Fi (802.11): high data rate (Mbps), medium range (~50m indoors), high power consumption. Best for streaming data, cameras, and devices with mains power.\n\n\u2022 Bluetooth Classic: medium data rate (1\u20133 Mbps), short range (~10m), moderate power. Used for audio and continuous data streams.\n\n\u2022 Bluetooth Low Energy (BLE): low data rate (1 Mbps), short range (~30m), very low power. Ideal for sensors, beacons, and wearables. Can run for years on a coin cell.",
                    formula = "BLE: 1 Mbps, ~30m, \u00B5W average\nWi-Fi: 100+ Mbps, ~50m, mW average",
                    keyPoint = "BLE: best for low-power sensors; Wi-Fi: best for high-bandwidth, powered devices"
                ),
                LessonSection(
                    heading = "LoRa and LPWAN",
                    content = "Low-Power Wide-Area Networks (LPWAN) provide long range with minimal power:\n\n\u2022 LoRa/LoRaWAN: 2\u201315 km range, very low data rate (0.3\u201350 kbps), years of battery life. Uses chirp spread spectrum modulation for noise immunity. Ideal for smart agriculture, utility metering, and asset tracking.\n\n\u2022 Sigfox: ultra-narrowband, up to 50 km, only 140 messages/day (12 bytes each). Extremely simple and low cost.\n\n\u2022 NB-IoT: uses existing cellular infrastructure, 1\u201310 km, moderate data rates. Requires a SIM card and cellular subscription.",
                    formula = "LoRa: 2\u201315 km, 0.3\u201350 kbps\nNB-IoT: 1\u201310 km, ~100 kbps",
                    keyPoint = "LoRa: km-range on a battery for years; trade-off is very low data rate"
                ),
                LessonSection(
                    heading = "Zigbee and Mesh Networks",
                    content = "Zigbee (802.15.4) is designed for low-power, low-data-rate mesh networking. Each node can relay messages for other nodes, extending coverage without increasing transmit power.\n\nMesh network advantages:\n\u2022 Self-healing: if one node fails, traffic routes around it\n\u2022 Range extension: each hop adds range\n\u2022 Scalability: supports hundreds of nodes\n\nZigbee operates at 2.4 GHz with 250 kbps data rate and ~10\u201330m per hop. Used in smart home (lights, locks, thermostats) and industrial sensor networks.\n\nThread and Matter are newer mesh protocols gaining adoption in smart homes.",
                    keyPoint = "Mesh networks self-heal and extend range through multi-hop relay"
                ),
                LessonSection(
                    heading = "Low-Power Design Techniques",
                    content = "Battery life is the biggest constraint for IoT devices. Techniques to minimize power:\n\n\u2022 Sleep modes: put the MCU in deep sleep between measurements. Wake on timer or interrupt. Sleep current can be <1\u00B5A vs. mA when active.\n\u2022 Duty cycling: transmit data periodically (e.g., once per minute) rather than continuously.\n\u2022 Data compression: send less data to reduce radio-on time.\n\u2022 Low-power MCUs: choose chips optimized for sleep current (e.g., STM32L, MSP430, nRF52).\n\u2022 Efficient regulators: use DC-DC converters with <1\u00B5A quiescent current.\n\nA sensor that wakes every 15 minutes, reads a sensor, and transmits over BLE can run 5+ years on a CR2032 coin cell (225 mAh).",
                    formula = "Average I = (I_active \u00D7 t_active + I_sleep \u00D7 t_sleep) / T\nBattery life = Capacity / I_avg",
                    keyPoint = "Sleep + duty cycle: \u00B5A average current enables years of battery life"
                ),
                LessonSection(
                    heading = "IoT Security Basics",
                    content = "IoT devices face unique security challenges:\n\n\u2022 Limited resources: constrained MCUs can't run full TLS stacks easily\n\u2022 Physical access: devices deployed in the field can be physically tampered with\n\u2022 Long deployment: devices may run for years without firmware updates\n\nEssential security practices:\n\u2022 Encrypt all wireless communication (AES-128 minimum)\n\u2022 Authenticate devices (mutual authentication, certificates)\n\u2022 Secure boot: verify firmware integrity before execution\n\u2022 OTA updates: ability to patch vulnerabilities remotely\n\u2022 Minimal attack surface: disable unused interfaces and ports",
                    keyPoint = "Encrypt, authenticate, secure boot, and plan for OTA updates"
                )
            )
        ),
        quiz = Quiz(
            title = "IoT & Wireless Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "iot_mc1",
                    questionText = "Which wireless protocol is best for a battery-powered sensor sending small data packets over several kilometers?",
                    options = listOf(
                        "Wi-Fi",
                        "Bluetooth Classic",
                        "LoRa",
                        "Zigbee"
                    ),
                    correctIndex = 2,
                    explanation = "LoRa provides 2\u201315 km range with very low power consumption, ideal for remote sensors sending small amounts of data infrequently."
                ),
                Question.MultipleChoice(
                    id = "iot_mc2",
                    questionText = "What is the main advantage of a mesh network?",
                    options = listOf(
                        "Higher data rates",
                        "Lower cost per node",
                        "Self-healing and range extension through multi-hop relay",
                        "Simpler firmware"
                    ),
                    correctIndex = 2,
                    explanation = "Mesh networks route data through multiple hops, extending range and automatically rerouting if a node fails (self-healing)."
                ),
                Question.MultipleChoice(
                    id = "iot_mc3",
                    questionText = "BLE (Bluetooth Low Energy) is ideal for:",
                    options = listOf(
                        "Streaming high-definition video",
                        "Low-power sensor beacons and wearables",
                        "Long-range agricultural monitoring",
                        "High-speed file transfers"
                    ),
                    correctIndex = 1,
                    explanation = "BLE is optimized for low-power, short-range communication with small data packets \u2014 perfect for wearables, beacons, and proximity sensors."
                ),
                Question.MultipleChoice(
                    id = "iot_mc4",
                    questionText = "Which is NOT an essential IoT security practice?",
                    options = listOf(
                        "Encrypting wireless communication",
                        "Secure boot verification",
                        "Using the highest data rate possible",
                        "Over-the-air firmware updates"
                    ),
                    correctIndex = 2,
                    explanation = "Using the highest data rate has no security benefit. Encryption, secure boot, and OTA updates are all essential security practices for IoT devices."
                )
            ),
            generators = listOf(
                generator("iot_ni1") { r ->
                    val ia = activeCurrentsMa.pick(r)
                    val ta = activeTimesMs.pick(r)
                    val isl = sleepCurrentsUa.pick(r)
                    val period = wakePeriodsS.pick(r)
                    val avgUa = (ia * 1000 * ta / 1000 + isl * (period - ta / 1000)) / period
                    Numeric(
                        text = "A sensor draws ${fmt(ia)}mA for ${fmt(ta)}ms when it wakes, then sleeps at ${fmt(isl)}µA, repeating " +
                            "every ${fmt(period)}s. What is its average current, in µA? Round to 1 decimal place.",
                        answer = avgUa, unit = "µA", decimals = 1,
                        explanation = "Charge per cycle = ${fmt(ia * 1000)}µA × ${fmt(ta / 1000)}s + ${fmt(isl)}µA × " +
                            "${fmt(period - ta / 1000)}s, divided by ${fmt(period)}s = ${fmt(avgUa, 1)}µA"
                    )
                },
                generator("iot_ni2") { r ->
                    val mah = batteryMah.pick(r)
                    val ua = averageUa.pick(r)
                    val hours = mah / (ua / 1000)
                    val months = hours / HOURS_PER_MONTH
                    Numeric(
                        text = "A ${fmt(mah)}mAh battery powers a device averaging ${fmt(ua)}µA. Ideally, how many months does it " +
                            "last? Assume 730 hours per month and round to 1 decimal place.",
                        answer = months, unit = "months", decimals = 1,
                        explanation = "Hours = ${fmt(mah)}mAh/${fmt(ua / 1000)}mA = ${fmt(hours)}h, and ${fmt(hours)}/730 = " +
                            "${fmt(months, 1)} months"
                    )
                },
                generator("iot_ni3") { r ->
                    val range = hopRanges.pick(r)
                    val d = hopDistances.pick(r)
                    val hops = ceil(d / range)
                    Numeric(
                        text = "A mesh node reaches ${fmt(range)}m per hop. What is the fewest hops needed to reach a device " +
                            "${fmt(d)}m away?",
                        answer = hops, unit = "hops", exact = true,
                        explanation = "${fmt(d)}/${fmt(range)} = ${fmt(d / range, 2)}" +
                            "${if (hops == d / range) ", so" else ", rounded up to"} ${fmt(hops)} hops"
                    )
                },
                generator("iot_ni4") { r ->
                    val ta = dutyActiveMs.pick(r)
                    val period = dutyPeriodsS.pick(r)
                    val pct = ta / (period * 1000) * 100
                    Numeric(
                        text = "A radio is on for ${fmt(ta)}ms every ${fmt(period)}s. What is its duty cycle, in percent? " +
                            "Round to 3 decimal places.",
                        answer = pct, unit = "%", decimals = 3,
                        explanation = "Duty = ${fmt(ta)}ms/${fmt(period * 1000)}ms × 100 = ${fmt(pct, 3)}%"
                    )
                },
                generator("iot_ni5") { r ->
                    val b = packetBytes.pick(r)
                    val p = packetsPerHour.pick(r)
                    val perDay = b * p * HOURS_PER_DAY
                    Numeric(
                        text = "A sensor sends a $b-byte packet ${if (p == 1) "once" else "$p times"} an hour. How many bytes does it " +
                            "send per day?",
                        answer = perDay.toDouble(), unit = "bytes", exact = true,
                        explanation = "$b bytes × $p per hour × 24 hours = $perDay bytes"
                    )
                },
                generator("iot_ni6") { r ->
                    val ptx = radioPowers.pick(r)
                    val sens = sensitivities.pick(r)
                    val budget = ptx - sens
                    Numeric(
                        text = "A LoRa radio transmits at ${fmt(ptx)}dBm and the receiver's sensitivity is ${fmt(sens)}dBm. What is " +
                            "the maximum path loss the link can tolerate, in dB (ignoring antenna gains)?",
                        answer = budget, unit = "dB",
                        explanation = "Link budget = P_tx − sensitivity = ${fmt(ptx)} − (${fmt(sens)}) = ${fmt(budget)}dB"
                    )
                }
            )
        )
    )
}
