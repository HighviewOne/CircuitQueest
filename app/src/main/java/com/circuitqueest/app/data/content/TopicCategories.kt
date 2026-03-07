package com.circuitqueest.app.data.content

data class TopicCategory(
    val name: String,
    val icon: String,
    val topicIds: List<String>
)

object TopicCategories {
    val categories = listOf(
        TopicCategory(
            name = "Circuit Fundamentals",
            icon = "\u26A1",
            topicIds = listOf(
                "ohms_law", "series_parallel", "kirchhoffs",
                "capacitors_inductors", "ac_circuits", "thevenin_norton"
            )
        ),
        TopicCategory(
            name = "Semiconductor Devices",
            icon = "\uD83D\uDD0C",
            topicIds = listOf(
                "diodes_rectifiers", "transistors_bjt", "mosfets",
                "semiconductor_physics"
            )
        ),
        TopicCategory(
            name = "Analog Design",
            icon = "\u3030\uFE0F",
            topicIds = listOf(
                "op_amps", "filters", "analog_circuits", "audio_electronics"
            )
        ),
        TopicCategory(
            name = "Digital Systems",
            icon = "\uD83D\uDCBB",
            topicIds = listOf(
                "digital_logic", "digital_systems", "vlsi_design", "ml_hardware"
            )
        ),
        TopicCategory(
            name = "Signals & Control",
            icon = "\uD83D\uDCCA",
            topicIds = listOf(
                "signals_systems", "control_systems", "dsp"
            )
        ),
        TopicCategory(
            name = "Power & Energy",
            icon = "\uD83D\uDD0B",
            topicIds = listOf(
                "transformers", "power_electronics", "power_systems",
                "renewable_energy", "electric_vehicles", "battery_storage"
            )
        ),
        TopicCategory(
            name = "RF & Communications",
            icon = "\uD83D\uDCE1",
            topicIds = listOf(
                "electromagnetics", "transmission_lines", "communication_systems",
                "antenna_design", "rf_circuits", "fiber_optics", "radar_systems"
            )
        ),
        TopicCategory(
            name = "Hardware & Embedded",
            icon = "\uD83D\uDD27",
            topicIds = listOf(
                "pcb_design", "embedded_systems", "sensors_measurement",
                "signal_integrity", "iot_wireless"
            )
        ),
        TopicCategory(
            name = "Specialized Applications",
            icon = "\uD83C\uDFE5",
            topicIds = listOf(
                "electric_machines", "mems", "biomedical_electronics"
            )
        )
    )

    private val topicToCategoryMap: Map<String, TopicCategory> by lazy {
        categories.flatMap { category ->
            category.topicIds.map { topicId -> topicId to category }
        }.toMap()
    }

    fun categoryFor(topicId: String): TopicCategory? = topicToCategoryMap[topicId]
}
