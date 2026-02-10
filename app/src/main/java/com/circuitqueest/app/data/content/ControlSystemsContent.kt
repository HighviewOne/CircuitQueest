package com.circuitqueest.app.data.content

object ControlSystemsContent {
    val topic = Topic(
        id = "control_systems",
        title = "Control Systems",
        subtitle = "Feedback, stability, and PID controllers",
        icon = "\uD83C\uDFAF",
        order = 15,
        lesson = Lesson(
            title = "Control Systems: Mastering Feedback",
            sections = listOf(
                LessonSection(
                    heading = "What is a Control System?",
                    content = "A control system manages the behavior of a dynamic system to achieve a desired output. Examples are everywhere:\n\n\u2022 Thermostat maintaining room temperature\n\u2022 Cruise control holding car speed\n\u2022 Voltage regulator stabilizing output\n\u2022 Robot arm tracking a path\n\nThe key idea: measure the output, compare it to the desired value (setpoint), and adjust the input to reduce the error.",
                    keyPoint = "Control = measure, compare to setpoint, correct the error"
                ),
                LessonSection(
                    heading = "Open-Loop vs. Closed-Loop",
                    content = "Open-loop control applies a fixed input without checking the output. A toaster timer is open-loop \u2014 it runs for a set time regardless of how toasted the bread is.\n\nClosed-loop (feedback) control continuously measures the output and adjusts. The error signal drives the controller:\n\nerror = setpoint \u2212 measured output\n\nClosed-loop systems are more accurate and robust to disturbances, but can become unstable if poorly designed.",
                    formula = "error(t) = setpoint \u2212 output(t)",
                    keyPoint = "Closed-loop uses feedback to self-correct; open-loop does not"
                ),
                LessonSection(
                    heading = "Block Diagrams and Transfer Functions",
                    content = "Control systems are represented using block diagrams. Each block has a transfer function G(s) relating its output to input in the Laplace domain.\n\nFor a standard feedback loop:\n\u2022 Forward path: G(s) (controller + plant)\n\u2022 Feedback path: H(s) (sensor)\n\u2022 Closed-loop transfer function: T(s) = G(s) / (1 + G(s)H(s))\n\nThe denominator 1 + G(s)H(s) determines stability \u2014 its roots are the closed-loop poles.",
                    formula = "T(s) = G(s) / (1 + G(s)H(s))",
                    keyPoint = "Closed-loop poles determine stability and transient response"
                ),
                LessonSection(
                    heading = "PID Controller",
                    content = "The PID controller is the most widely used controller in industry. It has three terms:\n\n\u2022 Proportional (P): output proportional to current error. Reduces error but can't eliminate it completely (steady-state error).\n\u2022 Integral (I): output proportional to accumulated error over time. Eliminates steady-state error but can cause overshoot.\n\u2022 Derivative (D): output proportional to rate of change of error. Predicts future error and dampens oscillations.\n\nTuning K_p, K_i, and K_d balances speed, accuracy, and stability.",
                    formula = "u(t) = K_p\u00B7e(t) + K_i\u222Be(t)dt + K_d\u00B7de/dt",
                    keyPoint = "P = react to present error, I = correct past error, D = predict future error"
                ),
                LessonSection(
                    heading = "Stability",
                    content = "A system is stable if its output settles to a finite value after a disturbance. An unstable system's output grows without bound (oscillations, runaway).\n\nStability is determined by the location of closed-loop poles:\n\u2022 All poles in the left half of the s-plane: STABLE\n\u2022 Any pole in the right half: UNSTABLE\n\u2022 Poles on the imaginary axis: marginally stable (sustained oscillations)\n\nGain margin and phase margin are practical measures of how close a system is to instability.",
                    keyPoint = "Stable = all closed-loop poles have negative real parts"
                ),
                LessonSection(
                    heading = "System Response Characteristics",
                    content = "When a control system responds to a step input, key characteristics include:\n\n\u2022 Rise time: time to go from 10% to 90% of final value (speed)\n\u2022 Overshoot: how much the output exceeds the setpoint (accuracy)\n\u2022 Settling time: time to stay within \u00B12% of final value (stability)\n\u2022 Steady-state error: permanent offset from setpoint\n\nFaster response often means more overshoot \u2014 control design is about finding the right balance for each application.",
                    keyPoint = "Design trade-off: faster response vs. less overshoot vs. zero steady-state error"
                )
            )
        ),
        quiz = Quiz(
            title = "Control Systems Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "cs_mc1",
                    questionText = "What is the main advantage of closed-loop over open-loop control?",
                    options = listOf(
                        "It is simpler to implement",
                        "It uses less power",
                        "It self-corrects using feedback",
                        "It always has zero overshoot"
                    ),
                    correctIndex = 2,
                    explanation = "Closed-loop control uses feedback to measure the output and correct errors automatically, making it robust to disturbances."
                ),
                Question.MultipleChoice(
                    id = "cs_mc2",
                    questionText = "Which PID term eliminates steady-state error?",
                    options = listOf(
                        "Proportional (P)",
                        "Integral (I)",
                        "Derivative (D)",
                        "None of them"
                    ),
                    correctIndex = 1,
                    explanation = "The Integral term accumulates error over time. As long as any error exists, the integral grows and increases the control effort until the error reaches zero."
                ),
                Question.MultipleChoice(
                    id = "cs_mc3",
                    questionText = "A system is stable when all closed-loop poles are:",
                    options = listOf(
                        "On the imaginary axis",
                        "In the right half of the s-plane",
                        "In the left half of the s-plane",
                        "At the origin"
                    ),
                    correctIndex = 2,
                    explanation = "Poles in the left half-plane (negative real part) correspond to decaying exponentials, meaning the system returns to equilibrium."
                ),
                Question.NumericInput(
                    id = "cs_ni1",
                    questionText = "A system has a setpoint of 100\u00B0C and the measured temperature is 95\u00B0C. What is the error signal?",
                    correctAnswer = 5.0,
                    tolerance = 0.1,
                    unit = "\u00B0C",
                    explanation = "error = setpoint \u2212 output = 100 \u2212 95 = 5\u00B0C"
                ),
                Question.NumericInput(
                    id = "cs_ni2",
                    questionText = "A step response has a final value of 10V and peaks at 12V. What is the percent overshoot?",
                    correctAnswer = 20.0,
                    tolerance = 0.5,
                    unit = "%",
                    explanation = "Overshoot = (peak \u2212 final)/final \u00D7 100 = (12 \u2212 10)/10 \u00D7 100 = 20%"
                ),
                Question.MultipleChoice(
                    id = "cs_mc4",
                    questionText = "The derivative (D) term in a PID controller helps to:",
                    options = listOf(
                        "Eliminate steady-state error",
                        "Increase the system gain",
                        "Dampen oscillations and reduce overshoot",
                        "Speed up the rise time"
                    ),
                    correctIndex = 2,
                    explanation = "The D term responds to the rate of change of error, providing a damping effect that reduces overshoot and oscillations."
                ),
                Question.NumericInput(
                    id = "cs_ni3",
                    questionText = "A forward-path gain is G = 20 and feedback gain H = 1. What is the closed-loop gain T = G/(1+GH)?",
                    correctAnswer = 0.95,
                    tolerance = 0.01,
                    unit = "",
                    explanation = "T = G/(1+GH) = 20/(1 + 20\u00D71) = 20/21 \u2248 0.952"
                )
            )
        )
    )
}
