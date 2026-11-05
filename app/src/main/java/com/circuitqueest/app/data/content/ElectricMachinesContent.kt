package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.articleCap
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI

object ElectricMachinesContent {
    private const val SYNC_CONSTANT = 120.0
    private const val FULL_TURN = 360.0
    private const val FOUR_POLE = 4.0
    private val torques = listOf(2.0, 5.0, 10.0, 20.0, 50.0)
    private val shaftSpeeds = listOf(1500.0, 3000.0, 6000.0)
    private val mainsFrequencies = listOf(50.0, 60.0)
    private val poleCounts = listOf(2.0, 4.0, 6.0, 8.0)
    private val stepsPerRev = listOf(24.0, 48.0, 100.0, 200.0, 400.0)
    private val inputPowers = listOf(200.0, 500.0, 1000.0, 2000.0)
    private val motorEfficiencies = listOf(75.0, 80.0, 85.0, 90.0, 95.0)
    private val slips = listOf(0.02, 0.03, 0.04, 0.05)
    private val ratedPowersKw = listOf(1.0, 5.0, 15.0)
    private val ratedSpeeds = listOf(1450.0, 2900.0)
    private val dcSupplies = listOf(12.0, 24.0, 48.0)
    private val armatureCurrents = listOf(2.0, 5.0, 10.0)
    private val armatureResistances = listOf(0.2, 0.5, 1.0)

    val topic = Topic(
        id = "electric_machines",
        title = "Electric Machines",
        subtitle = "Motors, generators, and electromechanical energy conversion",
        icon = "\u2699\uFE0F",
        order = 39,
        lesson = Lesson(
            title = "Electric Machines: From Electricity to Motion",
            sections = listOf(
                LessonSection(
                    heading = "Motors and Generators",
                    content = "Electric machines convert between electrical and mechanical energy:\n\n\u2022 Motor: electrical energy \u2192 mechanical energy (rotation)\n\u2022 Generator: mechanical energy \u2192 electrical energy\n\nBoth use the same fundamental principles: a current-carrying conductor in a magnetic field experiences a force (motor action), and a conductor moving through a magnetic field has a voltage induced in it (generator action).\n\nThe same machine can often operate as either a motor or generator.",
                    keyPoint = "Motor = electrical \u2192 mechanical; Generator = mechanical \u2192 electrical"
                ),
                LessonSection(
                    heading = "DC Motors",
                    content = "A DC motor has a stationary magnetic field (stator) and a rotating coil (rotor/armature). Current through the armature creates a torque that spins the rotor.\n\nA commutator reverses the current direction every half-turn to maintain continuous rotation.\n\nKey relationships:\n\u2022 Back-EMF: V_back = k \u00D7 \u03C9 (proportional to speed)\n\u2022 Torque: T = k \u00D7 I_a (proportional to armature current)\n\u2022 V = V_back + I_a \u00D7 R_a",
                    formula = "V = k\u03C9 + I_a R_a\nT = k \u00D7 I_a\nP = T \u00D7 \u03C9",
                    keyPoint = "DC motor: speed \u221D voltage; torque \u221D current"
                ),
                LessonSection(
                    heading = "AC Induction Motors",
                    content = "The induction motor is the workhorse of industry \u2014 simple, rugged, and efficient. The stator creates a rotating magnetic field using three-phase AC power.\n\nThis rotating field induces current in the rotor (like a transformer), which creates a force that makes the rotor follow the field. The rotor always turns slightly slower than the field \u2014 this difference is called slip.\n\nSlip is necessary: if the rotor matched the field speed, there would be no relative motion and no induced current.",
                    formula = "n_sync = 120f / P\nslip = (n_sync \u2212 n_rotor) / n_sync",
                    keyPoint = "Induction motor: rotor follows the rotating field with some slip"
                ),
                LessonSection(
                    heading = "Stepper Motors",
                    content = "Stepper motors rotate in precise, discrete steps rather than continuously. Each electrical pulse moves the shaft by a fixed angle (e.g., 1.8\u00B0 = 200 steps per revolution).\n\nTypes:\n\u2022 Permanent magnet: simple, low cost\n\u2022 Variable reluctance: fast, less torque\n\u2022 Hybrid: combines both for precision and torque\n\nStepper motors are used in 3D printers, CNC machines, and robotics where precise position control is needed without feedback sensors.",
                    formula = "Step angle = 360\u00B0 / (steps per revolution)\nSpeed = steps/sec \u00D7 step angle",
                    keyPoint = "Steppers give precise angular control: 200 steps/rev = 1.8\u00B0/step"
                ),
                LessonSection(
                    heading = "Torque, Speed, and Power",
                    content = "The fundamental relationship between torque, speed, and power:\n\nP = T \u00D7 \u03C9\n\nwhere \u03C9 is angular velocity in rad/s. Converting: \u03C9 = 2\u03C0 \u00D7 RPM / 60.\n\nA motor's torque-speed characteristic shows the trade-off: at higher loads (more torque), speed decreases. At no load, speed is maximum.\n\nEfficiency = P_mechanical / P_electrical. Losses include copper losses (I\u00B2R in windings), iron losses (core hysteresis/eddy currents), and friction.",
                    formula = "P = T \u00D7 \u03C9\n\u03C9 = 2\u03C0n/60 (n in RPM)\n\u03B7 = P_out / P_in",
                    keyPoint = "P = T\u03C9; motor efficiency = mechanical power out / electrical power in"
                ),
                LessonSection(
                    heading = "Motor Control",
                    content = "Modern motor control uses electronics to precisely manage speed and torque:\n\n\u2022 DC motor: PWM controls average voltage and thus speed. H-bridge circuit enables direction reversal.\n\u2022 AC induction motor: Variable Frequency Drive (VFD) adjusts frequency and voltage for speed control.\n\u2022 Stepper: pulse-by-pulse position control; microstepping for smoother motion.\n\u2022 Brushless DC (BLDC): uses electronic commutation instead of mechanical brushes for longer life.\n\nFeedback from encoders or Hall sensors enables closed-loop control for precision.",
                    keyPoint = "PWM for DC, VFD for AC, pulse control for steppers"
                )
            )
        ),
        quiz = Quiz(
            title = "Electric Machines Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "mach_mc1",
                    questionText = "A motor converts:",
                    options = listOf(
                        "Mechanical energy to electrical energy",
                        "Electrical energy to mechanical energy",
                        "Thermal energy to electrical energy",
                        "Chemical energy to electrical energy"
                    ),
                    correctIndex = 1,
                    explanation = "A motor converts electrical energy into mechanical energy (rotation). A generator does the reverse."
                ),
                Question.MultipleChoice(
                    id = "mach_mc2",
                    questionText = "In an induction motor, slip is necessary because:",
                    options = listOf(
                        "The rotor needs to spin faster than the field",
                        "Without relative motion, no current is induced in the rotor",
                        "The commutator requires it",
                        "It reduces power consumption"
                    ),
                    correctIndex = 1,
                    explanation = "Slip (rotor slower than field) creates relative motion between the rotor and the rotating magnetic field, which induces current and produces torque."
                ),
                Question.MultipleChoice(
                    id = "mach_mc3",
                    questionText = "What does an H-bridge circuit do for a DC motor?",
                    options = listOf(
                        "Increases the motor voltage",
                        "Enables bidirectional rotation (forward and reverse)",
                        "Converts AC to DC",
                        "Measures motor speed"
                    ),
                    correctIndex = 1,
                    explanation = "An H-bridge uses 4 switches to reverse the polarity of the voltage applied to the motor, enabling both forward and reverse rotation."
                ),
                Question.MultipleChoice(
                    id = "mach_mc4",
                    questionText = "Why does a DC motor draw a very large current at the instant it starts?",
                    options = listOf(
                        "Its windings are cold",
                        "It isn't spinning yet, so there is no back-EMF to oppose the supply",
                        "The brushes short-circuit",
                        "Friction is highest at rest"
                    ),
                    correctIndex = 1,
                    explanation = "Current = (V − back-EMF)/R. At standstill the back-EMF is zero, so only the small winding resistance limits the current. That's why large motors use soft starters."
                )
            ),
            generators = listOf(
                generator("mach_ni1") { r ->
                    val t = torques.pick(r)
                    val n = shaftSpeeds.pick(r)
                    val w = 2 * PI * n / 60
                    val p = t * w
                    Numeric(
                        text = "A motor produces ${fmt(t)}N·m of torque at ${fmt(n)}RPM. What is its mechanical power in Watts? " +
                            "Use ω = 2πn/60 and round to the nearest whole number.",
                        answer = p, unit = "W", decimals = 0,
                        explanation = "ω = 2π × ${fmt(n)}/60 = ${fmt(w, 2)}rad/s. P = Tω = ${fmt(t)} × ${fmt(w, 2)} = ${fmt(p, 0)}W"
                    )
                },
                generator("mach_ni2") { r ->
                    val f = mainsFrequencies.pick(r)
                    val p = poleCounts.pick(r)
                    val ns = SYNC_CONSTANT * f / p
                    Numeric(
                        text = "${articleCap(p)} ${fmt(p)}-pole induction motor runs on ${fmt(f)}Hz power. What is its synchronous " +
                            "speed, in RPM?",
                        answer = ns, unit = "RPM",
                        explanation = "n_sync = 120f/P = 120 × ${fmt(f)}/${fmt(p)} = ${fmt(ns)}RPM"
                    )
                },
                generator("mach_ni3") { r ->
                    val steps = stepsPerRev.pick(r)
                    val deg = FULL_TURN / steps
                    Numeric(
                        text = "A stepper motor has ${fmt(steps)} steps per revolution. What is its step angle, in degrees?",
                        answer = deg, unit = "°",
                        explanation = "Step angle = 360°/${fmt(steps)} = ${fmt(deg)}°"
                    )
                },
                generator("mach_ni4") { r ->
                    val pin = inputPowers.pick(r)
                    val eff = motorEfficiencies.pick(r)
                    val pout = pin * eff / 100
                    Numeric(
                        text = "A motor draws ${fmt(pin)}W from the supply and delivers ${fmt(pout)}W of mechanical power. What is " +
                            "its efficiency in percent?",
                        answer = eff, unit = "%",
                        explanation = "η = P_out/P_in × 100 = ${fmt(pout)}/${fmt(pin)} × 100 = ${fmt(eff)}%"
                    )
                },
                generator("mach_ni5") { r ->
                    val f = mainsFrequencies.pick(r)
                    val ns = SYNC_CONSTANT * f / FOUR_POLE
                    val s = slips.pick(r)
                    val n = ns * (1 - s)
                    Numeric(
                        text = "A 4-pole induction motor on ${fmt(f)}Hz runs at ${fmt(n)}RPM. What is its slip, in percent?",
                        answer = s * 100, unit = "%",
                        explanation = "n_sync = 120 × ${fmt(f)}/4 = ${fmt(ns)}RPM. Slip = (${fmt(ns)} − ${fmt(n)})/${fmt(ns)} × 100 = " +
                            "${fmt(s * 100)}%"
                    )
                },
                generator("mach_ni6") { r ->
                    val kw = ratedPowersKw.pick(r)
                    val n = ratedSpeeds.pick(r)
                    val w = 2 * PI * n / 60
                    val t = kw * 1000 / w
                    Numeric(
                        text = "${articleCap(kw)} ${fmt(kw)}kW motor runs at ${fmt(n)}RPM. What torque does it deliver, in N·m? " +
                            "Round to 2 decimal places.",
                        answer = t, unit = "N·m",
                        explanation = "ω = 2π × ${fmt(n)}/60 = ${fmt(w, 2)}rad/s, so T = P/ω = ${fmt(kw * 1000)}/${fmt(w, 2)} = " +
                            "${fmt(t, 2)}N·m"
                    )
                },
                generator("mach_ni7") { r ->
                    val v = dcSupplies.pick(r)
                    val i = armatureCurrents.pick(r)
                    val ra = armatureResistances.pick(r)
                    val emf = v - i * ra
                    Numeric(
                        text = "A DC motor on ${fmt(v)}V draws ${fmt(i)}A, and its armature resistance is ${fmt(ra)}Ω. What is its " +
                            "back-EMF, in Volts?",
                        answer = emf, unit = "V",
                        explanation = "E_b = V − I·R_a = ${fmt(v)} − ${fmt(i)} × ${fmt(ra)} = ${fmt(emf)}V"
                    )
                }
            )
        )
    )
}
