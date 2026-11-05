package com.circuitqueest.app.data.content

import com.circuitqueest.app.data.content.QuestionMath.Numeric
import com.circuitqueest.app.data.content.QuestionMath.article
import com.circuitqueest.app.data.content.QuestionMath.fmt
import com.circuitqueest.app.data.content.QuestionMath.generator
import com.circuitqueest.app.data.content.QuestionMath.pick
import kotlin.math.PI
import kotlin.math.sqrt

object MemsContent {
    private const val AIR_DENSITY = 1.225
    private const val GRAVITY = 9.81
    private const val GYRO_LEVELS = 65_536.0
    private const val NANO = 1e-9
    private const val HALF_TURN_DEG = 180.0
    private val sensitivities = listOf(1.0, 2.0, 5.0, 10.0)
    private val accelerations = listOf(0.5, 2.0, 5.0, 16.0)
    private val altitudeChangesCm = listOf(10.0, 50.0, 100.0, 300.0)
    private val gyroRanges = listOf(125.0, 250.0, 500.0, 2000.0)
    private val accelRanges = listOf(2.0, 4.0, 8.0, 16.0)
    private val accelBits = listOf(12, 16)
    private val springConstants = listOf(0.5, 1.0, 5.0)
    private val proofMassesUg = listOf(1.0, 5.0, 10.0)
    private val driveVelocitiesMm = listOf(10.0, 100.0)
    private val rotationRates = listOf(100.0, 500.0)

    val topic = Topic(
        id = "mems",
        title = "MEMS",
        subtitle = "Accelerometers, gyroscopes, and microfabrication",
        icon = "\uD83D\uDD2C",
        order = 40,
        lesson = Lesson(
            title = "MEMS: Tiny Machines, Big Impact",
            sections = listOf(
                LessonSection(
                    heading = "What Are MEMS?",
                    content = "Microelectromechanical Systems (MEMS) are tiny mechanical devices built on silicon chips using semiconductor fabrication techniques. They range from 1\u00B5m to 1mm in size.\n\nMEMS combine:\n\u2022 Mechanical elements: beams, membranes, cantilevers, gears\n\u2022 Electrical elements: capacitors, resistors, piezoelectric films\n\u2022 Electronics: signal conditioning, ADC, digital interface\n\nMEMS are everywhere: every smartphone contains at least 5\u20137 MEMS devices (accelerometer, gyroscope, microphone, pressure sensor, etc.). They're also in cars (airbag sensors), medical devices, and industrial systems.",
                    keyPoint = "MEMS = microscale mechanical + electrical devices on silicon; in every smartphone"
                ),
                LessonSection(
                    heading = "MEMS Accelerometers",
                    content = "A MEMS accelerometer measures acceleration (including gravity) using a tiny proof mass suspended by spring-like flexures.\n\nWhen the device accelerates, the proof mass deflects relative to the frame. This deflection is measured capacitively: the gap between movable and fixed electrodes changes, changing capacitance.\n\n\u0394C/C \u221D displacement \u221D acceleration\n\nTypical specs:\n\u2022 Range: \u00B12g to \u00B116g (smartphones), up to \u00B1200g (crash sensors)\n\u2022 Sensitivity: 0.5\u20134 mV/g or digital LSB/g\n\u2022 Noise density: 100\u2013400 \u00B5g/\u221AHz\n\u2022 Bandwidth: DC to 1\u20136 kHz\n\nApplications: screen rotation, step counting, vibration monitoring, vehicle crash detection.",
                    formula = "F = m \u00D7 a (proof mass force)\n\u0394C = \u03B5A/(d\u2212x) \u2212 \u03B5A/(d+x)",
                    keyPoint = "Capacitive sensing: proof mass deflects, changing gap capacitance proportional to acceleration"
                ),
                LessonSection(
                    heading = "MEMS Gyroscopes",
                    content = "A MEMS gyroscope measures angular velocity (rotation rate) using the Coriolis effect.\n\nA vibrating proof mass is driven into oscillation in one axis (drive axis). When the device rotates, the Coriolis force deflects the mass in a perpendicular axis (sense axis):\n\nF_Coriolis = 2 \u00D7 m \u00D7 v \u00D7 \u03A9\n\nwhere m is mass, v is drive velocity, and \u03A9 is angular rate.\n\nTypical specs:\n\u2022 Range: \u00B1125 to \u00B12000 \u00B0/s\n\u2022 Noise: 0.003\u20130.01 \u00B0/s/\u221AHz\n\u2022 Bias stability: 1\u201310 \u00B0/hr (consumer), <0.1 \u00B0/hr (tactical grade)\n\nCombined with accelerometers, gyroscopes enable inertial measurement units (IMUs) for navigation, drone stabilization, and motion tracking.",
                    formula = "F_Coriolis = 2m \u00D7 v_drive \u00D7 \u03A9\nSense displacement \u221D \u03A9",
                    keyPoint = "Gyroscope: vibrating mass + Coriolis force = angular rate measurement"
                ),
                LessonSection(
                    heading = "MEMS Pressure Sensors",
                    content = "MEMS pressure sensors use a thin silicon membrane (diaphragm) that deflects under pressure.\n\nTwo main sensing methods:\n\u2022 Piezoresistive: strain gauges on the membrane change resistance with deflection. Simple, robust, but temperature-sensitive.\n\u2022 Capacitive: membrane forms one plate of a capacitor. More sensitive and stable, but needs more complex readout.\n\nTypes by reference:\n\u2022 Absolute: measures vs. vacuum (barometric pressure)\n\u2022 Gauge: measures vs. atmosphere\n\u2022 Differential: measures pressure difference between two ports\n\nApplications: altimeters (smartphones), tire pressure (TPMS), blood pressure monitors, industrial process control. Smartphone barometers resolve ~10 cm altitude change!",
                    formula = "Deflection \u221D P \u00D7 a\u2074 / (E \u00D7 t\u00B3)\n(a = membrane radius, t = thickness)",
                    keyPoint = "Thin membrane deflects with pressure; piezoresistive or capacitive readout"
                ),
                LessonSection(
                    heading = "MEMS Fabrication",
                    content = "MEMS are built using modified semiconductor processes:\n\n\u2022 Surface micromachining: build structures layer by layer on the wafer surface. Deposit structural layers (polysilicon) over sacrificial layers (oxide), then etch away the sacrificial layer to release movable parts.\n\n\u2022 Bulk micromachining: etch deep into the silicon wafer itself using wet etching (KOH, TMAH) or deep reactive ion etching (DRIE/Bosch process) to create thick, robust structures.\n\n\u2022 Wafer bonding: bond two or more processed wafers together to create sealed cavities, hermetic packages, or complex 3D structures.\n\nDRIE can etch vertical sidewalls >100\u00B5m deep with aspect ratios >20:1, enabling high-performance inertial sensors.",
                    keyPoint = "Surface micromachining: thin layers; Bulk: deep etch; DRIE for high-aspect structures"
                ),
                LessonSection(
                    heading = "MEMS Microphones and Beyond",
                    content = "MEMS microphones have replaced electret condensers in most consumer electronics. A thin membrane vibrates with sound pressure, changing capacitance relative to a fixed backplate.\n\nAdvantages: tiny size (3\u00D74mm), SMD soldering compatible, consistent performance, and integrates easily with digital ASICs.\n\nOther MEMS devices:\n\u2022 MEMS oscillators: replacing quartz crystals in some applications\n\u2022 Micromirrors (DMD): Texas Instruments DLP projectors use millions of tilting micromirrors\n\u2022 RF MEMS switches: low-loss signal switching for 5G\n\u2022 Lab-on-chip: microfluidic channels for medical diagnostics\n\u2022 Energy harvesters: piezoelectric MEMS for vibration energy harvesting",
                    keyPoint = "MEMS microphones dominate consumer electronics; micromirrors enable DLP projection"
                )
            )
        ),
        quiz = Quiz(
            title = "MEMS Boss Battle",
            questions = listOf(
                Question.MultipleChoice(
                    id = "mems_mc1",
                    questionText = "MEMS accelerometers most commonly sense deflection using:",
                    options = listOf(
                        "Optical interferometry",
                        "Capacitance change between movable and fixed electrodes",
                        "Magnetic field variation",
                        "Temperature change"
                    ),
                    correctIndex = 1,
                    explanation = "Most MEMS accelerometers use capacitive sensing: the proof mass deflection changes the gap between electrodes, producing a measurable capacitance change proportional to acceleration."
                ),
                Question.MultipleChoice(
                    id = "mems_mc2",
                    questionText = "A MEMS gyroscope measures angular velocity using:",
                    options = listOf(
                        "Centripetal force",
                        "Gravitational pull",
                        "The Coriolis effect on a vibrating mass",
                        "Magnetic precession"
                    ),
                    correctIndex = 2,
                    explanation = "MEMS gyroscopes drive a proof mass into vibration. When the device rotates, the Coriolis force (F = 2m\u00D7v\u00D7\u03A9) deflects the mass perpendicular to both the drive and rotation axes."
                ),
                Question.MultipleChoice(
                    id = "mems_mc3",
                    questionText = "Deep Reactive Ion Etching (DRIE) is used in MEMS to:",
                    options = listOf(
                        "Deposit thin metal films",
                        "Etch deep, high-aspect-ratio vertical structures in silicon",
                        "Bond two wafers together",
                        "Grow silicon dioxide"
                    ),
                    correctIndex = 1,
                    explanation = "DRIE (Bosch process) alternates etching and passivation to create deep vertical trenches (>100\u00B5m) with high aspect ratios, essential for robust inertial sensors."
                ),
                Question.MultipleChoice(
                    id = "mems_mc4",
                    questionText = "In surface micromachining, movable structures are released by:",
                    options = listOf(
                        "Cutting with a laser",
                        "Etching away a sacrificial layer beneath the structural layer",
                        "Mechanical polishing",
                        "Heating until the structure detaches"
                    ),
                    correctIndex = 1,
                    explanation = "Surface micromachining deposits structural layers over sacrificial layers, then chemically etches away the sacrificial material to free the movable structures."
                )
            ),
            generators = listOf(
                generator("mems_ni1") { r ->
                    val s = sensitivities.pick(r)
                    val g = accelerations.pick(r)
                    Numeric(
                        text = "A MEMS accelerometer's sensitivity is ${fmt(s)}mV/g. At ${fmt(g)}g, what is its output, in mV?",
                        answer = s * g, unit = "mV",
                        explanation = "V_out = sensitivity × acceleration = ${fmt(s)} × ${fmt(g)} = ${fmt(s * g)}mV"
                    )
                },
                generator("mems_ni2") { r ->
                    val cm = altitudeChangesCm.pick(r)
                    val pa = AIR_DENSITY * GRAVITY * cm / 100
                    Numeric(
                        text = "A phone's MEMS barometer must detect a ${fmt(cm)}cm change in altitude near sea level. Using " +
                            "ΔP = ρgΔh with ρ = 1.225kg/m³ and g = 9.81m/s², what pressure change is that, in Pa? Round to " +
                            "2 decimal places.",
                        answer = pa, unit = "Pa",
                        explanation = "Near sea level air pressure falls by about 12Pa per metre. Here: ΔP = 1.225 × 9.81 × " +
                            "${fmt(cm / 100)} = ${fmt(pa, 2)}Pa"
                    )
                },
                generator("mems_ni3") { r ->
                    val range = gyroRanges.pick(r)
                    val res = 2 * range / GYRO_LEVELS
                    Numeric(
                        text = "A MEMS gyroscope has a ±${fmt(range)}°/s range and 16-bit output. What is its resolution in °/s per " +
                            "LSB? Round to 4 decimal places.",
                        answer = res, unit = "°/s", decimals = 4,
                        explanation = "Full span = ${fmt(2 * range)}°/s over 2¹⁶ = 65536 codes: ${fmt(2 * range)}/65536 = " +
                            "${fmt(res, 4)}°/s"
                    )
                },
                generator("mems_ni4") { r ->
                    val range = accelRanges.pick(r)
                    val bits = accelBits.pick(r)
                    val mg = 2 * range * 1000 / (1 shl bits)
                    Numeric(
                        text = "An accelerometer set to ±${fmt(range)}g has ${article(bits.toDouble())} $bits-bit output. What is its " +
                            "resolution, in mg per LSB? Round to 3 decimal places.",
                        answer = mg, unit = "mg", decimals = 3,
                        explanation = "Span = ${fmt(2 * range * 1000)}mg over ${1 shl bits} codes = ${fmt(mg, 3)}mg"
                    )
                },
                generator("mems_ni5") { r ->
                    val k = springConstants.pick(r)
                    val ug = proofMassesUg.pick(r)
                    val khz = sqrt(k / (ug * NANO)) / (2 * PI) / 1000
                    Numeric(
                        text = "A MEMS proof mass of ${fmt(ug)}µg hangs on springs with k = ${fmt(k)}N/m. What is its resonant " +
                            "frequency, in kHz? Round to 3 decimal places.",
                        answer = khz, unit = "kHz", decimals = 3,
                        explanation = "f = (1/2π)√(k/m) = (1/2π)√(${fmt(k)}/(${fmt(ug)}×10⁻⁹)) = ${fmt(khz, 3)}kHz"
                    )
                },
                generator("mems_ni6") { r ->
                    val ug = proofMassesUg.pick(r)
                    val vmm = driveVelocitiesMm.pick(r)
                    val deg = rotationRates.pick(r)
                    val omega = deg * PI / HALF_TURN_DEG
                    val nn = 2 * ug * NANO * vmm / 1000 * omega / NANO
                    Numeric(
                        text = "A gyroscope's ${fmt(ug)}µg proof mass is driven at ${fmt(vmm)}mm/s while the device turns at " +
                            "${fmt(deg)}°/s. What Coriolis force acts on it, in nN? Use F = 2mvΩ and round to 3 decimal places.",
                        answer = nn, unit = "nN", decimals = 3,
                        explanation = "Ω = ${fmt(deg)}°/s = ${fmt(omega, 3)}rad/s. F = 2 × ${fmt(ug)}×10⁻⁹ × ${fmt(vmm / 1000)} × " +
                            "${fmt(omega, 3)} = ${fmt(nn, 3)}nN"
                    )
                }
            )
        )
    )
}
