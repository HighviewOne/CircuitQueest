package com.circuitqueest.app.data.content

object ElectromagneticsContent {
    val topic = Topic(
        id = "electromagnetics",
        title = "Electromagnetics",
        subtitle = "Fields, forces, and Maxwell's equations",
        icon = "\uD83E\uDDF2",
        order = 18,
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
                Question.NumericInput(
                    id = "em_ni1",
                    questionText = "A parallel-plate capacitor has 100V across plates separated by 2mm. What is the electric field in V/m?",
                    correctAnswer = 50000.0,
                    tolerance = 500.0,
                    unit = "V/m",
                    explanation = "E = V/d = 100/0.002 = 50,000 V/m"
                ),
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
                Question.NumericInput(
                    id = "em_ni2",
                    questionText = "A coil of 50 turns experiences a flux change of 0.02 Wb in 0.1 seconds. What is the induced EMF in Volts?",
                    correctAnswer = 10.0,
                    tolerance = 0.1,
                    unit = "V",
                    explanation = "EMF = N \u00D7 \u0394\u03A6/\u0394t = 50 \u00D7 0.02/0.1 = 50 \u00D7 0.2 = 10V"
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
                Question.NumericInput(
                    id = "em_ni3",
                    questionText = "What is the wavelength (in meters) of a 100MHz electromagnetic wave? (Use c = 3\u00D710\u2078 m/s)",
                    correctAnswer = 3.0,
                    tolerance = 0.1,
                    unit = "m",
                    explanation = "\u03BB = c/f = 3\u00D710\u2078 / 100\u00D710\u2076 = 3m"
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
                Question.NumericInput(
                    id = "em_ni4",
                    questionText = "A 2m wire carrying 5A is in a 0.3T magnetic field at 90\u00B0. What is the force on the wire in Newtons?",
                    correctAnswer = 3.0,
                    tolerance = 0.1,
                    unit = "N",
                    explanation = "F = ILB sin(\u03B8) = 5 \u00D7 2 \u00D7 0.3 \u00D7 sin(90\u00B0) = 5 \u00D7 2 \u00D7 0.3 \u00D7 1 = 3N"
                )
            )
        )
    )
}
