package com.circuitqueest.app.data.content

object ElectricMachinesContent {
    val topic = Topic(
        id = "electric_machines",
        title = "Electric Machines",
        subtitle = "Motors, generators, and electromechanical energy conversion",
        icon = "\u2699\uFE0F",
        order = 21,
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
                Question.NumericInput(
                    id = "mach_ni1",
                    questionText = "A motor produces 10 N\u00B7m of torque at 3000 RPM. What is its mechanical power in Watts? (Use \u03C9 = 2\u03C0n/60, round to nearest whole number)",
                    correctAnswer = 3142.0,
                    tolerance = 20.0,
                    unit = "W",
                    explanation = "\u03C9 = 2\u03C0 \u00D7 3000/60 = 314.16 rad/s. P = T\u03C9 = 10 \u00D7 314.16 = 3141.6 \u2248 3142W"
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
                Question.NumericInput(
                    id = "mach_ni2",
                    questionText = "A 4-pole induction motor runs on 60Hz power. What is the synchronous speed in RPM?",
                    correctAnswer = 1800.0,
                    tolerance = 10.0,
                    unit = "RPM",
                    explanation = "n_sync = 120f/P = 120 \u00D7 60 / 4 = 1800 RPM"
                ),
                Question.NumericInput(
                    id = "mach_ni3",
                    questionText = "A stepper motor has 200 steps per revolution. What is the step angle in degrees?",
                    correctAnswer = 1.8,
                    tolerance = 0.05,
                    unit = "\u00B0",
                    explanation = "Step angle = 360\u00B0/200 = 1.8\u00B0 per step"
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
                Question.NumericInput(
                    id = "mach_ni4",
                    questionText = "A motor draws 500W from the supply and delivers 400W of mechanical power. What is its efficiency in percent?",
                    correctAnswer = 80.0,
                    tolerance = 0.5,
                    unit = "%",
                    explanation = "\u03B7 = P_out/P_in \u00D7 100 = 400/500 \u00D7 100 = 80%"
                )
            )
        )
    )
}
