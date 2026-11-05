package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick

object RadarSystemsContent {
    private const val KMH_PER_MS = 3.6
    private const val LIGHT = 3e8
    private const val MICRO = 1e-6
    private const val GIGA = 1e9
    private val echoDelays = listOf(10.0, 20.0, 50.0, 100.0, 200.0)
    private val pulseWidths = listOf(0.1, 0.5, 1.0, 2.0)
    private val carrierGhz = listOf(3.0, 10.0, 24.0, 77.0)
    private val dopplerKhz = listOf(1.0, 2.0, 5.0)
    private val repetitionFrequencies = listOf(1000.0, 2000.0, 5000.0, 10_000.0)
    private val rangeFactors = listOf(2.0, 3.0, 10.0)
    private val targetSpeedsKmh = listOf(50.0, 100.0, 300.0)

    val topic = Topic(
        id = "radar_systems",
        title = "Radar Systems",
        subtitle = "Pulse radar, Doppler, and phased arrays",
        icon = "\uD83D\uDCE1",
        order = 33,
        lesson = Lesson(
            title = "Radar Systems: Seeing with Radio Waves",
            sections = listOf(
                LessonSection(
                    heading = "Radar Fundamentals",
                    content = "RADAR (Radio Detection And Ranging) transmits radio pulses and listens for echoes reflected from targets. By measuring the time delay, the range to the target is determined.\n\nBasic operation:\n1. Transmitter sends a short RF pulse\n2. Pulse travels at the speed of light (c = 3\u00D710\u2078 m/s)\n3. Pulse reflects off the target\n4. Receiver detects the echo after time delay \u0394t\n5. Range = c \u00D7 \u0394t / 2 (divide by 2 for round-trip)\n\nRadar frequencies range from 1 MHz (HF) to 300 GHz (millimeter wave). Common bands: S-band (2\u20134 GHz), X-band (8\u201312 GHz), K-band (18\u201327 GHz), W-band (75\u2013110 GHz).",
                    formula = "R = c \u00D7 \u0394t / 2\nc = 3 \u00D7 10\u2078 m/s",
                    keyPoint = "Radar measures range from the round-trip time of reflected radio pulses"
                ),
                LessonSection(
                    heading = "The Radar Range Equation",
                    content = "The radar range equation relates received power to system parameters:\n\nP_r = (P_t \u00D7 G\u00B2 \u00D7 \u03BB\u00B2 \u00D7 \u03C3) / ((4\u03C0)\u00B3 \u00D7 R\u2074)\n\nwhere:\n\u2022 P_t = transmit power\n\u2022 G = antenna gain\n\u2022 \u03BB = wavelength\n\u2022 \u03C3 = radar cross section (target reflectivity, in m\u00B2)\n\u2022 R = range to target\n\nKey insight: received power drops as R\u2074 (inverse fourth power). Doubling range requires 16\u00D7 more power! This is why radar needs high-power transmitters and sensitive receivers.\n\nMaximum detection range occurs when P_r equals the minimum detectable signal (receiver sensitivity).",
                    formula = "P_r = P_t\u00D7G\u00B2\u00D7\u03BB\u00B2\u00D7\u03C3 / ((4\u03C0)\u00B3\u00D7R\u2074)\nR_max \u221D (P_t)\u00BC",
                    keyPoint = "Power drops as R\u2074; doubling range needs 16\u00D7 power"
                ),
                LessonSection(
                    heading = "Pulse Radar and Resolution",
                    content = "Pulse radar alternates between transmitting and listening. Key parameters:\n\n\u2022 Pulse Repetition Frequency (PRF): how often pulses are sent (typically 100\u201310,000 Hz)\n\u2022 Pulse width (\u03C4): duration of each pulse (0.1\u2013100 \u00B5s)\n\u2022 Duty cycle: \u03C4 \u00D7 PRF (typically 0.1\u20131%)\n\nRange resolution (ability to distinguish two close targets):\n\u0394R = c \u00D7 \u03C4 / 2\n\nShorter pulses give better resolution but less energy. Pulse compression (chirp) solves this: transmit a long, frequency-swept pulse, then compress it in the receiver to get both high energy AND fine resolution.\n\nMaximum unambiguous range: R_max = c / (2 \u00D7 PRF). Higher PRF reduces max range but improves Doppler measurement.",
                    formula = "\u0394R = c\u03C4/2 (range resolution)\nR_unamb = c/(2\u00D7PRF)",
                    keyPoint = "Range resolution = c\u03C4/2; pulse compression gives both energy and resolution"
                ),
                LessonSection(
                    heading = "Doppler Radar",
                    content = "The Doppler effect shifts the frequency of reflected signals based on target velocity:\n\nf_d = 2 \u00D7 v \u00D7 f_0 / c\n\nwhere f_d is the Doppler shift, v is target radial velocity, and f_0 is the transmit frequency.\n\nDoppler radar applications:\n\u2022 Weather radar: measures rainfall velocity and intensity\n\u2022 Police speed guns: measure vehicle speed\n\u2022 Air traffic control: distinguishes moving aircraft from ground clutter\n\u2022 Automotive radar: adaptive cruise control, collision avoidance\n\nContinuous Wave (CW) Doppler radar transmits continuously and measures only velocity (no range). FMCW (Frequency-Modulated CW) radar measures both range AND velocity simultaneously.",
                    formula = "f_d = 2vf_0/c\nv = f_d\u00D7c / (2f_0)",
                    keyPoint = "Doppler shift reveals target velocity; FMCW gives both range and velocity"
                ),
                LessonSection(
                    heading = "Phased Array Antennas",
                    content = "A phased array uses many small antenna elements whose signals are combined with controlled phase shifts. By adjusting the phase of each element, the beam can be electronically steered without physically moving the antenna.\n\nAdvantages:\n\u2022 Beam steering in microseconds (vs. mechanical rotation: seconds)\n\u2022 Multiple simultaneous beams possible\n\u2022 Adaptive nulling: cancel interference from specific directions\n\u2022 Graceful degradation: if some elements fail, the array still works\n\nModern phased arrays use Active Electronically Scanned Arrays (AESA) where each element has its own transmit/receive module. Used in military radar, 5G base stations, satellite communications, and automotive radar.",
                    keyPoint = "Phased arrays steer beams electronically in microseconds; no moving parts"
                ),
                LessonSection(
                    heading = "Automotive Radar",
                    content = "Modern cars use 77 GHz radar for safety and autonomous driving:\n\n\u2022 Long-range radar (LRR): 150\u2013300m range, narrow beam, adaptive cruise control\n\u2022 Medium-range radar (MRR): 70\u2013150m, cross-traffic alert, lane change assist\n\u2022 Short-range radar (SRR): 0.2\u201330m, wide angle, parking assist, blind spot detection\n\nAutomotive radar uses FMCW waveforms to measure range, velocity, and angle simultaneously. A typical sensor has 3\u20134 transmit and 4 receive antennas forming a virtual MIMO array for angular resolution.\n\n77 GHz offers good resolution (wavelength ~4mm) in a compact package. Radar works in rain, fog, and darkness where cameras and lidar struggle.",
                    formula = "77 GHz: \u03BB \u2248 3.9 mm\nRange resolution: c/(2\u00D7BW)",
                    keyPoint = "77 GHz automotive radar: works in all weather; FMCW for range + velocity"
                )
            )
        ),
        quiz = Quiz(
            title = "Radar Systems Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "radar_mc1",
                    questionText = "In the radar range equation, received power drops with range as:",
                    options = listOf(
                        "1/R",
                        "1/R\u00B2",
                        "1/R\u00B3",
                        "1/R\u2074"
                    ),
                    correctIndex = 3,
                    explanation = "The radar range equation shows P_r \u221D 1/R\u2074. The signal spreads as 1/R\u00B2 going out and 1/R\u00B2 coming back, giving 1/R\u2074 total."
                ),
                Question.MultipleChoice(
                    id = "radar_mc2",
                    questionText = "FMCW radar can measure both:",
                    options = listOf(
                        "Temperature and humidity",
                        "Range and velocity simultaneously",
                        "Only velocity",
                        "Only range"
                    ),
                    correctIndex = 1,
                    explanation = "FMCW (Frequency-Modulated Continuous Wave) radar uses a frequency sweep to determine range from beat frequency and velocity from Doppler shift, measuring both simultaneously."
                ),
                Question.MultipleChoice(
                    id = "radar_mc3",
                    questionText = "The main advantage of a phased array antenna is:",
                    options = listOf(
                        "Lower cost than a single antenna",
                        "Electronic beam steering without mechanical movement",
                        "Higher transmit power per element",
                        "Simpler signal processing"
                    ),
                    correctIndex = 1,
                    explanation = "Phased arrays steer the beam electronically by adjusting element phase shifts, enabling microsecond beam repositioning without any mechanical movement."
                ),
                Question.MultipleChoice(
                    id = "radar_mc4",
                    questionText = "Why does a single-antenna pulsed radar have a minimum range (a blind zone)?",
                    options = listOf(
                        "The target is too bright up close",
                        "The receiver is switched off while the pulse is being transmitted",
                        "Close targets have no Doppler shift",
                        "The antenna can't point downward"
                    ),
                    correctIndex = 1,
                    explanation = "While the transmitter fires, the receiver is protected (off), so echoes returning during the pulse are missed: R_min ≈ cτ/2. Shorter pulses shrink the blind zone."
                )
            ),
            generators = listOf(
                generator("radar_ni1") { r ->
                    val us = echoDelays.pick(r)
                    val km = LIGHT * us * MICRO / 2 / 1000
                    Numeric(
                        text = "A radar pulse returns after ${fmt(us)}µs. How far away is the target, in km? Use c = 3×10⁸m/s.",
                        answer = km, unit = "km",
                        explanation = "R = cΔt/2 = 3×10⁸ × ${fmt(us)}×10⁻⁶/2 = ${fmt(km * 1000)}m = ${fmt(km)}km"
                    )
                },
                generator("radar_ni2") { r ->
                    val us = pulseWidths.pick(r)
                    val m = LIGHT * us * MICRO / 2
                    Numeric(
                        text = "A pulse radar has a pulse width of ${fmt(us)}µs. What is its range resolution, in metres?",
                        answer = m, unit = "m",
                        explanation = "ΔR = cτ/2 = 3×10⁸ × ${fmt(us)}×10⁻⁶/2 = ${fmt(m)}m"
                    )
                },
                generator("radar_ni3") { r ->
                    val f0 = carrierGhz.pick(r)
                    val fd = dopplerKhz.pick(r)
                    val v = fd * 1000 * LIGHT / (2 * f0 * GIGA)
                    Numeric(
                        text = "${articleCap(f0)} ${fmt(f0)}GHz radar sees a Doppler shift of ${fmt(fd)}kHz. What is the target's " +
                            "radial velocity, in m/s? Use c = 3×10⁸m/s and round to 2 decimal places.",
                        answer = v, unit = "m/s",
                        explanation = "v = f_d × c/(2f₀) = ${fmt(fd * 1000)} × 3×10⁸/(2 × ${fmt(f0)}×10⁹) = ${fmt(v, 2)}m/s"
                    )
                },
                generator("radar_ni4") { r ->
                    val prf = repetitionFrequencies.pick(r)
                    val km = LIGHT / (2 * prf) / 1000
                    Numeric(
                        text = "A radar has a pulse repetition frequency of ${fmt(prf)}Hz. What is its maximum unambiguous range, " +
                            "in km? Use c = 3×10⁸m/s.",
                        answer = km, unit = "km",
                        explanation = "R_max = c/(2 × PRF) = 3×10⁸/(2 × ${fmt(prf)}) = ${fmt(km * 1000)}m = ${fmt(km)}km"
                    )
                },
                generator("radar_ni5") { r ->
                    val k = rangeFactors.pick(r)
                    val factor = k * k * k * k
                    Numeric(
                        text = "A target moves ${fmt(k)} times farther from a radar. By what factor does the echo power drop?",
                        answer = factor, unit = "×",
                        explanation = "The echo falls as 1/R⁴ (1/R² out and 1/R² back): ${fmt(k)}⁴ = ${fmt(factor)}×"
                    )
                },
                generator("radar_ni6") { r ->
                    val kmh = targetSpeedsKmh.pick(r)
                    val f0 = carrierGhz.pick(r)
                    val v = kmh / KMH_PER_MS
                    val khz = 2 * v * f0 * GIGA / LIGHT / 1000
                    Numeric(
                        text = "A car approaches ${article(f0)} ${fmt(f0)}GHz radar at ${fmt(kmh)}km/h. What Doppler shift does the " +
                            "radar measure, in kHz? Round to 2 decimal places.",
                        answer = khz, unit = "kHz",
                        explanation = "v = ${fmt(kmh)}/3.6 = ${fmt(v, 2)}m/s. f_d = 2vf₀/c = 2 × ${fmt(v, 2)} × ${fmt(f0)}×10⁹/3×10⁸ = " +
                            "${fmt(khz, 2)}kHz"
                    )
                }
            )
        )
    )
}
