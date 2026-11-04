package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sqrt

object RfCircuitsContent {
    private const val DB_AMPLITUDE = 20
    private const val MICRO = 1e-6
    private const val PICO = 1e-12
    private const val MEGA = 1e6
    private val rfFrequenciesMhz = listOf(900.0, 1575.42, 2400.0, 5800.0)
    private val intermediateFrequencies = listOf(10.7, 70.0, 100.0)
    private val firstNoiseFigures = listOf(0.5, 1.0, 1.5, 2.0)
    private val firstGains = listOf(10.0, 15.0, 20.0)
    private val secondNoiseFigures = listOf(6.0, 8.0, 10.0)
    private val transmissionMagnitudes = listOf(0.1, 0.316, 0.5, 0.707)
    private val tankInductances = listOf(1.0, 10.0, 100.0)
    private val tankCapacitances = listOf(10.0, 100.0, 470.0)
    private val secondGains = listOf(10.0, 20.0)
    private val filterLosses = listOf(1.0, 2.0, 3.0)

    val topic = Topic(
        id = "rf_circuits",
        title = "RF Circuit Design",
        subtitle = "Oscillators, amplifiers, and S-parameters",
        icon = "\uD83D\uDCFB",
        order = 31,
        lesson = Lesson(
            title = "RF Circuit Design: The High-Frequency Frontier",
            sections = listOf(
                LessonSection(
                    heading = "What Makes RF Different?",
                    content = "Radio Frequency (RF) circuits operate at frequencies from ~3MHz to 300GHz. At these frequencies, normal circuit analysis breaks down:\n\n\u2022 Wires are no longer simple connections \u2014 they are transmission lines\n\u2022 Parasitic capacitances and inductances dominate behavior\n\u2022 Components behave differently (a capacitor can look inductive above its self-resonant frequency)\n\u2022 Electromagnetic radiation becomes significant\n\nRF design requires specialized tools, techniques, and a different way of thinking about circuits.",
                    keyPoint = "At RF, parasitics dominate and every wire is a transmission line"
                ),
                LessonSection(
                    heading = "S-Parameters",
                    content = "At RF, voltage and current are hard to measure directly. Instead, we use scattering parameters (S-parameters) that describe how signals reflect and transmit through a network.\n\nFor a 2-port network:\n\u2022 S11: input reflection (return loss) \u2014 how much bounces back from port 1\n\u2022 S21: forward transmission (gain/loss) \u2014 how much passes from port 1 to port 2\n\u2022 S12: reverse transmission (isolation)\n\u2022 S22: output reflection\n\nS-parameters are complex numbers (magnitude and phase) measured at a specific impedance (usually 50\u03A9).",
                    formula = "S11 = reflected/incident at port 1\nS21 = transmitted/incident (gain)\n|S21|dB = 20 log\u2081\u2080|S21|",
                    keyPoint = "S11 = how well matched; S21 = gain or loss; measured in 50\u03A9 system"
                ),
                LessonSection(
                    heading = "Low-Noise Amplifier (LNA)",
                    content = "The LNA is the first amplifier in a receiver chain. Its job is to amplify the weak received signal while adding as little noise as possible.\n\nThe noise figure (NF) measures how much noise the amplifier adds:\n\u2022 NF = 0 dB: perfect (adds no noise)\n\u2022 NF = 1 dB: excellent (typical LNA)\n\u2022 NF = 3 dB: doubles the noise\n\nFriis formula: the first stage dominates overall noise figure. That's why a good LNA is critical \u2014 noise added later in the chain matters much less.",
                    formula = "NF_total = NF\u2081 + (NF\u2082-1)/G\u2081 + (NF\u2083-1)/(G\u2081G\u2082) + ...",
                    keyPoint = "First-stage NF dominates; a good LNA is the most critical receiver component"
                ),
                LessonSection(
                    heading = "Oscillators",
                    content = "An oscillator generates a periodic signal without an input. It requires:\n\n1. An amplifier with gain\n2. A feedback network that selects the oscillation frequency\n3. Barkhausen criterion: loop gain \u2265 1 and phase shift = 0\u00B0 (or 360\u00B0)\n\nCommon RF oscillator types:\n\u2022 LC oscillator (Colpitts, Hartley): uses inductor-capacitor tank circuit\n\u2022 Crystal oscillator: uses quartz crystal for high stability (ppm accuracy)\n\u2022 VCO (Voltage-Controlled Oscillator): frequency tuned by a control voltage\n\u2022 PLL (Phase-Locked Loop): locks VCO to a reference for precise synthesis",
                    formula = "f_osc = 1 / (2\u03C0\u221ALC)  (LC oscillator)\nBarkhausen: |A\u03B2| \u2265 1, \u2220A\u03B2 = 0\u00B0",
                    keyPoint = "Barkhausen: loop gain \u2265 1 and zero phase shift for sustained oscillation"
                ),
                LessonSection(
                    heading = "Mixers and Frequency Conversion",
                    content = "A mixer multiplies two signals together, producing sum and difference frequencies. This is how radios tune to different stations.\n\nIf f_RF = 900MHz and f_LO = 890MHz:\n\u2022 f_IF = f_RF \u2212 f_LO = 10MHz (difference \u2014 desired)\n\u2022 f_sum = f_RF + f_LO = 1790MHz (filtered out)\n\nDownconversion (RF \u2192 IF) makes the signal easier to filter and digitize. Upconversion (IF \u2192 RF) is used in transmitters.\n\nKey mixer specs: conversion loss, noise figure, linearity (IP3).",
                    formula = "f_IF = |f_RF \u2212 f_LO|\ncos(A) \u00D7 cos(B) = \u00BD[cos(A-B) + cos(A+B)]",
                    keyPoint = "Mixer produces sum and difference frequencies; filter selects the one you want"
                ),
                LessonSection(
                    heading = "RF Matching Networks",
                    content = "Impedance matching is critical at RF to maximize power transfer and minimize reflections. Common matching networks:\n\n\u2022 L-network: two reactive elements (L and C) in an L shape. Simple but limited bandwidth.\n\u2022 Pi-network (\u03C0): three elements, offers more design freedom and can transform wider impedance ratios.\n\u2022 T-network: three elements in a T shape, good for high-Q matching.\n\u2022 Microstrip matching: transmission line sections on a PCB (stubs, quarter-wave transforms).\n\nThe Smith chart is the primary tool for designing matching networks visually.",
                    keyPoint = "L-network is simplest; use Pi/T for wider impedance ratios or bandwidth"
                )
            )
        ),
        quiz = Quiz(
            title = "RF Circuit Design Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "rf_mc1",
                    questionText = "What does the S-parameter S21 represent?",
                    options = listOf(
                        "Input reflection coefficient",
                        "Forward transmission (gain or loss)",
                        "Output reflection coefficient",
                        "Reverse isolation"
                    ),
                    correctIndex = 1,
                    explanation = "S21 is the forward transmission coefficient \u2014 it measures how much signal passes from port 1 to port 2 (gain if >1, loss if <1)."
                ),
                Question.MultipleChoice(
                    id = "rf_mc2",
                    questionText = "Why is the first amplifier stage's noise figure the most critical?",
                    options = listOf(
                        "It draws the most current",
                        "Its noise is amplified by all subsequent stages (Friis formula)",
                        "It operates at the highest frequency",
                        "It has the most gain"
                    ),
                    correctIndex = 1,
                    explanation = "Friis formula shows that noise from the first stage dominates: subsequent stage noise is divided by the gain of preceding stages."
                ),
                Question.MultipleChoice(
                    id = "rf_mc3",
                    questionText = "The Barkhausen criterion for oscillation requires:",
                    options = listOf(
                        "Loop gain < 1 and 90\u00B0 phase shift",
                        "Loop gain \u2265 1 and 0\u00B0 (or 360\u00B0) total phase shift",
                        "Loop gain = 0 and any phase",
                        "Maximum noise figure"
                    ),
                    correctIndex = 1,
                    explanation = "For sustained oscillation, the loop gain must be \u2265 1 and the total phase shift around the loop must be 0\u00B0 (or a multiple of 360\u00B0)."
                ),
                Question.MultipleChoice(
                    id = "rf_mc4",
                    questionText = "A crystal oscillator provides high frequency stability because:",
                    options = listOf(
                        "Crystals have very high gain",
                        "Quartz has a very high Q factor (narrow resonance)",
                        "Crystals operate at microwave frequencies",
                        "Crystals don't need a power supply"
                    ),
                    correctIndex = 1,
                    explanation = "Quartz crystals have extremely high Q factors (10,000\u2013100,000+), creating a very sharp resonance that locks the oscillation frequency with ppm-level stability."
                )
            ),
            generators = listOf(
                generator("rf_ni1") { r ->
                    val rf = rfFrequenciesMhz.pick(r)
                    val ifMhz = intermediateFrequencies.pick(r)
                    val lo = rf - ifMhz
                    Numeric(
                        text = "A mixer has f_RF = ${fmt(rf)}MHz and f_LO = ${fmt(lo)}MHz. What is the IF, in MHz?",
                        answer = ifMhz, unit = "MHz",
                        explanation = "f_IF = |f_RF − f_LO| = |${fmt(rf)} − ${fmt(lo)}| = ${fmt(ifMhz)}MHz"
                    )
                },
                generator("rf_ni2") { r ->
                    val nf1 = firstNoiseFigures.pick(r)
                    val g1 = firstGains.pick(r)
                    val nf2 = secondNoiseFigures.pick(r)
                    val f1 = 10.0.pow(nf1 / 10)
                    val f2 = 10.0.pow(nf2 / 10)
                    val gl = 10.0.pow(g1 / 10)
                    val ftot = f1 + (f2 - 1) / gl
                    val nf = 10 * log10(ftot)
                    Numeric(
                        text = "An LNA has NF = ${fmt(nf1)}dB and gain ${fmt(g1)}dB; the next stage has NF = ${fmt(nf2)}dB. Using " +
                            "Friis' formula, what is the total noise figure in dB? Round to 2 decimal places.",
                        answer = nf, unit = "dB",
                        explanation = "In linear terms F₁ = ${fmt(f1, 3)}, G₁ = ${fmt(gl, 1)}, F₂ = ${fmt(f2, 3)}. F = F₁ + (F₂ − 1)/G₁ = " +
                            "${fmt(ftot, 3)}, so NF = 10·log₁₀(${fmt(ftot, 3)}) = ${fmt(nf, 2)}dB"
                    )
                },
                generator("rf_ni3") { r ->
                    val s21 = transmissionMagnitudes.pick(r)
                    val loss = -DB_AMPLITUDE * log10(s21)
                    Numeric(
                        text = "A filter has |S21| = ${fmt(s21)} (linear) in its stopband. What is its insertion loss there, in dB? " +
                            "Round to 2 decimal places.",
                        answer = loss, unit = "dB",
                        explanation = "S21 = 20·log₁₀(${fmt(s21)}) = −${fmt(loss, 2)}dB, so the insertion loss is ${fmt(loss, 2)}dB"
                    )
                },
                generator("rf_ni4") { r ->
                    val rf = rfFrequenciesMhz.pick(r)
                    val ifMhz = intermediateFrequencies.pick(r)
                    val image = rf - 2 * ifMhz
                    Numeric(
                        text = "A receiver tunes ${fmt(rf)}MHz with the LO below the RF and an IF of ${fmt(ifMhz)}MHz. What is the " +
                            "image frequency, in MHz?",
                        answer = image, unit = "MHz", decimals = 1, exact = true,
                        explanation = "f_LO = ${fmt(rf)} − ${fmt(ifMhz)} = ${fmt(rf - ifMhz)}MHz. The image sits the same IF on the other " +
                            "side of the LO: ${fmt(rf)} − 2 × ${fmt(ifMhz)} = ${fmt(image)}MHz"
                    )
                },
                generator("rf_ni5") { r ->
                    val lMicro = tankInductances.pick(r)
                    val cPico = tankCapacitances.pick(r)
                    val mhz = 1 / (2 * PI * sqrt(lMicro * MICRO * cPico * PICO)) / MEGA
                    Numeric(
                        text = "An LC oscillator uses L = ${fmt(lMicro)}µH and C = ${fmt(cPico)}pF. What is its frequency, in MHz? " +
                            "Round to 3 decimal places.",
                        answer = mhz, unit = "MHz", decimals = 3,
                        explanation = "f = 1/(2π√(LC)) = 1/(2π√(${fmt(lMicro)}×10⁻⁶ × ${fmt(cPico)}×10⁻¹²)) = ${fmt(mhz, 3)}MHz"
                    )
                },
                generator("rf_ni6") { r ->
                    val g1 = firstGains.pick(r)
                    val g2 = secondGains.pick(r)
                    val l = filterLosses.pick(r)
                    val total = g1 + g2 - l
                    Numeric(
                        text = "A receiver chain has ${article(g1)} ${fmt(g1)}dB LNA, a filter with ${fmt(l)}dB loss, and " +
                            "${article(g2)} ${fmt(g2)}dB amplifier. What is the total gain, in dB?",
                        answer = total, unit = "dB",
                        explanation = "In dB, gains add and losses subtract: ${fmt(g1)} − ${fmt(l)} + ${fmt(g2)} = ${fmt(total)}dB"
                    )
                }
            )
        )
    )
}
