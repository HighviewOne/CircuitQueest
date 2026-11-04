package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import com.circuitqueest.app.data.content.QuestionMath.pickTwo

object ElectromagneticsContent {
    private const val COULOMB_K = 8.99e9
    private const val C_M_MHZ = 300.0
    private const val MICRO = 1e-6
    private const val PER_MICRO = 1e6
    private const val WIRE_FIELD = 2e-7
    private val plateVoltages = listOf(10.0, 50.0, 100.0, 500.0, 1000.0)
    private val plateGapsMm = listOf(0.5, 1.0, 2.0, 5.0)
    private val coilTurns = listOf(10.0, 50.0, 100.0, 200.0)
    private val fluxChanges = listOf(0.01, 0.02, 0.05)
    private val changeTimes = listOf(0.01, 0.1, 0.5)
    private val waveFrequenciesMhz = listOf(50.0, 100.0, 150.0, 300.0, 900.0)
    private val wireCurrents = listOf(1.0, 2.0, 5.0, 10.0)
    private val wireLengths = listOf(0.5, 1.0, 2.0)
    private val fluxDensities = listOf(0.1, 0.3, 0.5)
    private val angles = listOf(30.0 to 0.5, 90.0 to 1.0)
    private val conductorCurrents = listOf(1.0, 5.0, 10.0, 20.0)
    private val distancesCm = listOf(1.0, 2.0, 5.0, 10.0)
    private val chargesMicro = listOf(1.0, 2.0, 5.0)
    private val separationsCm = listOf(5.0, 10.0, 20.0)

    val topic = Topic(
        id = "electromagnetics",
        title = "Electromagnetics",
        subtitle = "Fields, forces, and Maxwell's equations",
        icon = "\uD83E\uDDF2",
        order = 27,
        lesson = Lesson(
            title = "Electromagnetics: Fields in Action",
            sections = listOf(
                LessonSection(
                    heading = "Electric Fields",
                    content = "An electric field exists around any charged object. It describes the force per unit charge that a test charge would experience at any point in space.\n\nField lines point away from positive charges and toward negative charges. The strength of the field decreases with distance according to Coulomb's law.\n\nInside a parallel-plate capacitor, the electric field is uniform: E = V/d, where d is the plate separation.",
                    formula = "E = F / q = V / d (parallel plates)\nF = kq\u2081q\u2082 / r\u00B2 (Coulomb's law)",
                    keyPoint = "Electric field = force per unit charge; E = V/d between parallel plates"
                ),
                LessonSection(
                    heading = "Magnetic Fields",
                    content = "A magnetic field is created by moving charges (current). A current-carrying wire produces a circular magnetic field around it (right-hand rule).\n\nA solenoid (coil of wire) creates a strong, uniform field inside it, similar to a bar magnet. This is the principle behind electromagnets, inductors, and transformers.\n\nMagnetic field strength B is measured in Tesla (T).",
                    formula = "B = \u03BC\u2080 \u00D7 n \u00D7 I (solenoid)\n\u03BC\u2080 = 4\u03C0 \u00D7 10\u207B\u2077 T\u00B7m/A",
                    keyPoint = "Current creates magnetic fields; right-hand rule gives direction"
                ),
                LessonSection(
                    heading = "Faraday's Law of Induction",
                    content = "A changing magnetic field through a loop of wire induces a voltage (EMF). This is the principle behind generators, transformers, and inductors.\n\nThe induced EMF is proportional to the rate of change of magnetic flux. More loops and faster changes produce more voltage.\n\nLenz's law states the induced current opposes the change that produced it \u2014 this is nature's way of conserving energy.",
                    formula = "EMF = \u2212N \u00D7 d\u03A6/dt\n\u03A6 = B \u00D7 A \u00D7 cos(\u03B8)",
                    keyPoint = "Changing magnetic flux induces voltage; Lenz's law gives the direction"
                ),
                LessonSection(
                    heading = "Maxwell's Equations",
                    content = "James Clerk Maxwell unified electricity and magnetism into four elegant equations:\n\n1. Gauss's Law (E): Electric flux through a closed surface = enclosed charge/\u03B5\u2080\n2. Gauss's Law (B): Magnetic flux through a closed surface = 0 (no magnetic monopoles)\n3. Faraday's Law: Changing B creates E\n4. Amp\u00E8re-Maxwell Law: Current and changing E create B\n\nThese four equations describe all classical electromagnetic phenomena.",
                    formula = "\u2207\u00B7E = \u03C1/\u03B5\u2080\n\u2207\u00B7B = 0\n\u2207\u00D7E = \u2212\u2202B/\u2202t\n\u2207\u00D7B = \u03BC\u2080J + \u03BC\u2080\u03B5\u2080\u2202E/\u2202t",
                    keyPoint = "Four equations unify all of electricity and magnetism"
                ),
                LessonSection(
                    heading = "Electromagnetic Waves",
                    content = "Maxwell's equations predict that changing electric and magnetic fields propagate through space as waves \u2014 electromagnetic waves.\n\nThese waves travel at the speed of light: c = 1/\u221A(\u03BC\u2080\u03B5\u2080) \u2248 3 \u00D7 10\u2078 m/s.\n\nThe electromagnetic spectrum ranges from radio waves (long wavelength) through microwaves, infrared, visible light, UV, X-rays, to gamma rays (short wavelength). All are the same phenomenon at different frequencies.",
                    formula = "c = f \u00D7 \u03BB = 1/\u221A(\u03BC\u2080\u03B5\u2080)\nc \u2248 3 \u00D7 10\u2078 m/s",
                    keyPoint = "Light is an electromagnetic wave; all EM waves travel at speed c"
                ),
                LessonSection(
                    heading = "Force on a Current-Carrying Conductor",
                    content = "A current-carrying wire in a magnetic field experiences a force. This is the principle behind electric motors.\n\nThe force is perpendicular to both the current direction and the magnetic field (use the right-hand rule or cross product).\n\nThis same principle makes loudspeakers work: a coil carrying audio-frequency current in a magnetic field vibrates to produce sound.",
                    formula = "F = I \u00D7 L \u00D7 B \u00D7 sin(\u03B8)\nF = qv \u00D7 B (on a moving charge)",
                    keyPoint = "F = ILB sin\u03B8; perpendicular force enables motors and speakers"
                )
            )
        ),
        quiz = Quiz(
            title = "Electromagnetics Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "em_mc1",
                    questionText = "What does Faraday's law state?",
                    options = listOf(
                        "Electric charges create electric fields",
                        "Magnetic monopoles don't exist",
                        "A changing magnetic field induces an electric field (EMF)",
                        "Current creates a magnetic field"
                    ),
                    correctIndex = 2,
                    explanation = "Faraday's law states that a time-varying magnetic flux through a loop induces an electromotive force (EMF)."
                ),
                Question.MultipleChoice(
                    id = "em_mc2",
                    questionText = "How many fundamental equations make up Maxwell's equations?",
                    options = listOf(
                        "2",
                        "3",
                        "4",
                        "6"
                    ),
                    correctIndex = 2,
                    explanation = "Maxwell's equations consist of 4 fundamental laws: Gauss's law (E), Gauss's law (B), Faraday's law, and Amp\u00E8re-Maxwell law."
                ),
                Question.MultipleChoice(
                    id = "em_mc3",
                    questionText = "Lenz's law states that the induced current:",
                    options = listOf(
                        "Flows in the same direction as the change",
                        "Opposes the change that produced it",
                        "Is always clockwise",
                        "Is proportional to resistance"
                    ),
                    correctIndex = 1,
                    explanation = "Lenz's law: the induced current flows in a direction that opposes the change in magnetic flux that caused it (conservation of energy)."
                ),
                Question.MultipleChoice(
                    id = "em_mc4",
                    questionText = "What is the shape of the magnetic field around a long, straight current-carrying wire?",
                    options = listOf(
                        "Straight lines parallel to the wire",
                        "Concentric circles around the wire",
                        "Lines radiating out from the wire",
                        "There is no field"
                    ),
                    correctIndex = 1,
                    explanation = "The field forms concentric circles around the wire, with direction given by the right-hand rule (thumb along the current, fingers curl with B), and strength B = µ₀I/(2πr)."
                )
            ),
            generators = listOf(
                generator("em_ni1") { r ->
                    val v = plateVoltages.pick(r)
                    val dMm = plateGapsMm.pick(r)
                    val e = v / (dMm / 1000)
                    Numeric(
                        text = "A parallel-plate capacitor has ${fmt(v)}V across plates ${fmt(dMm)}mm apart. What is the electric " +
                            "field between them, in V/m?",
                        answer = e, unit = "V/m",
                        explanation = "E = V/d = ${fmt(v)}/${fmt(dMm / 1000, 4)} = ${fmt(e)}V/m"
                    )
                },
                generator("em_ni2") { r ->
                    val n = coilTurns.pick(r)
                    val dPhi = fluxChanges.pick(r)
                    val dt = changeTimes.pick(r)
                    val emf = n * dPhi / dt
                    Numeric(
                        text = "A coil of ${fmt(n)} turns sees its flux change by ${fmt(dPhi)}Wb in ${fmt(dt)}s. What is the " +
                            "induced EMF in Volts? Round to 2 decimal places.",
                        answer = emf, unit = "V",
                        explanation = "EMF = N × ΔΦ/Δt = ${fmt(n)} × ${fmt(dPhi)}/${fmt(dt)} = ${fmt(emf, 2)}V"
                    )
                },
                generator("em_ni3") { r ->
                    val f = waveFrequenciesMhz.pick(r)
                    val lambda = C_M_MHZ / f
                    Numeric(
                        text = "What is the wavelength of a ${fmt(f)}MHz electromagnetic wave in free space, in metres? " +
                            "Use c = 3×10⁸m/s and round to 3 decimal places.",
                        answer = lambda, unit = "m", decimals = 3,
                        explanation = "λ = c/f = 3×10⁸/(${fmt(f)}×10⁶) = ${fmt(lambda, 3)}m"
                    )
                },
                generator("em_ni4") { r ->
                    val i = wireCurrents.pick(r)
                    val l = wireLengths.pick(r)
                    val b = fluxDensities.pick(r)
                    val (deg, s) = angles.pick(r)
                    val f = i * l * b * s
                    Numeric(
                        text = "A ${fmt(l)}m wire carrying ${fmt(i)}A crosses a ${fmt(b)}T magnetic field at ${fmt(deg)}°. What is " +
                            "the force on the wire in Newtons? Round to 3 decimal places.",
                        answer = f, unit = "N", decimals = 3,
                        explanation = "F = ILB sin θ = ${fmt(i)} × ${fmt(l)} × ${fmt(b)} × sin ${fmt(deg)}° = ${fmt(f, 3)}N"
                    )
                },
                generator("em_ni5") { r ->
                    val i = conductorCurrents.pick(r)
                    val cm = distancesCm.pick(r)
                    val microTesla = WIRE_FIELD * i / (cm / 100) * PER_MICRO
                    Numeric(
                        text = "A long straight wire carries ${fmt(i)}A. What is the magnetic field ${fmt(cm)}cm from it, in µT? " +
                            "Use B = µ₀I/(2πr) with µ₀ = 4π×10⁻⁷ and round to 2 decimal places.",
                        answer = microTesla, unit = "µT",
                        explanation = "B = µ₀I/(2πr) = 2×10⁻⁷ × ${fmt(i)}/${fmt(cm / 100)} = ${fmt(microTesla, 2)}µT"
                    )
                },
                generator("em_ni6") { r ->
                    val (q1, q2) = chargesMicro.pickTwo(r)
                    val cm = separationsCm.pick(r)
                    val rm = cm / 100
                    val f = COULOMB_K * q1 * MICRO * q2 * MICRO / (rm * rm)
                    Numeric(
                        text = "Two point charges of ${fmt(q1)}µC and ${fmt(q2)}µC are ${fmt(cm)}cm apart. What is the force between " +
                            "them, in Newtons? Use k = 8.99×10⁹ and round to 2 decimal places.",
                        answer = f, unit = "N",
                        explanation = "F = kq₁q₂/r² = 8.99×10⁹ × ${fmt(q1)}×10⁻⁶ × ${fmt(q2)}×10⁻⁶/${fmt(rm)}² = ${fmt(f, 2)}N"
                    )
                }
            )
        )
    )
}
