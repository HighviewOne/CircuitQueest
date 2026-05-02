package com.circuitqueest.app.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import com.circuitqueest.app.ui.theme.CqBlue
import com.circuitqueest.app.ui.theme.CqBlueLight
import com.circuitqueest.app.ui.theme.CqCyan
import com.circuitqueest.app.ui.theme.CqGold
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

private fun buildIcon(block: ImageVector.Builder.() -> Unit): ImageVector =
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply(block).build()

private fun ImageVector.Builder.strokePath(pathStr: String, alpha: Float = 1f) {
    addPath(
        pathData = PathParser().parsePathString(pathStr).toNodes(),
        fill = null,
        stroke = SolidColor(Color.Black),
        strokeAlpha = alpha,
        strokeLineWidth = 1.6f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    )
}

private fun ImageVector.Builder.fillPath(pathStr: String) {
    addPath(
        pathData = PathParser().parsePathString(pathStr).toNodes(),
        fill = SolidColor(Color.Black),
        stroke = null
    )
}

object SchematicIcons {

    // Category icons
    val Resistor: ImageVector by lazy {
        buildIcon {
            strokePath("M2 12 H5 L6.5 8 L9 16 L11 8 L13 16 L15 8 L17.5 16 L19 12 H22")
        }
    }

    val Transistor: ImageVector by lazy {
        buildIcon {
            strokePath("M21 12 a9 9 0 1 0 -18 0 a9 9 0 1 0 18 0")
            strokePath("M9 6 V18 M9 12 L18 6 M9 12 L18 18")
            fillPath("M14 16 L18 18 L17 14")
            strokePath("M3 12 H9 M18 6 V3 M18 18 V21")
        }
    }

    val OpAmp: ImageVector by lazy {
        buildIcon {
            strokePath("M5 5 L5 19 L20 12 Z")
            strokePath("M2 9 H5 M2 15 H5 M20 12 H22")
            strokePath("M6 8 H8 M7 7 V9")
            strokePath("M6 16 H8")
        }
    }

    val Digital: ImageVector by lazy {
        buildIcon {
            strokePath("M5 6 H11 a7 6 0 0 1 0 12 H5 Z")
            strokePath("M21.4 12 a1.4 1.4 0 1 0 -2.8 0 a1.4 1.4 0 1 0 2.8 0")
            strokePath("M2 9 H5 M2 15 H5 M21.4 12 H24")
        }
    }

    val Signal: ImageVector by lazy {
        buildIcon {
            strokePath("M2 12 Q6 4 9 12 T15 12 T22 12")
            strokePath("M2 20 H22", alpha = 0.5f)
        }
    }

    val Power: ImageVector by lazy {
        buildIcon {
            strokePath("M2 12 H8 M16 12 H22")
            strokePath("M8 6 V18 M11 9 V15 M13 6 V18 M16 9 V15")
        }
    }

    val Antenna: ImageVector by lazy {
        buildIcon {
            strokePath("M12 14 L7 22 M12 14 L17 22 M12 14 V4")
            strokePath("M8 7 a6 6 0 0 1 8 0", alpha = 0.7f)
            strokePath("M5 5 a10 10 0 0 1 14 0", alpha = 0.4f)
            fillPath("M13.2 14 a1.2 1.2 0 1 0 -2.4 0 a1.2 1.2 0 1 0 2.4 0")
        }
    }

    val Chip: ImageVector by lazy {
        buildIcon {
            strokePath("M7 6 H17 Q18 6 18 7 V17 Q18 18 17 18 H7 Q6 18 6 17 V7 Q6 6 7 6 Z")
            strokePath("M9 9 H15 V15 H9 Z")
            strokePath("M8 3 V6 M12 3 V6 M16 3 V6 M8 18 V21 M12 18 V21 M16 18 V21")
            strokePath("M3 8 H6 M3 12 H6 M3 16 H6 M18 8 H21 M18 12 H21 M18 16 H21")
        }
    }

    val Specialized: ImageVector by lazy {
        buildIcon {
            strokePath("M2 12 H6 L8 6 L11 18 L14 9 L16 12 H22")
        }
    }

    // Topic-level icons
    val Capacitor: ImageVector by lazy {
        buildIcon {
            strokePath("M2 12 H10 M14 12 H22")
            strokePath("M10 6 V18 M14 6 V18")
        }
    }

    val Inductor: ImageVector by lazy {
        buildIcon {
            strokePath("M2 14 H4 a3 3 0 1 1 6 0 a3 3 0 1 1 6 0 a3 3 0 1 1 6 0 H22")
        }
    }

    val Diode: ImageVector by lazy {
        buildIcon {
            strokePath("M2 12 H8 M16 12 H22")
            fillPath("M8 6 L8 18 L16 12 Z")
            strokePath("M16 6 V18")
        }
    }

    val Ground: ImageVector by lazy {
        buildIcon {
            strokePath("M12 4 V12 M5 12 H19 M8 16 H16 M11 20 H13")
        }
    }

    val Voltmeter: ImageVector by lazy {
        buildIcon {
            strokePath("M21 12 a9 9 0 1 0 -18 0 a9 9 0 1 0 18 0")
            strokePath("M7.5 8.5 L12 15.5 L16.5 8.5")
        }
    }

    val Ammeter: ImageVector by lazy {
        buildIcon {
            strokePath("M21 12 a9 9 0 1 0 -18 0 a9 9 0 1 0 18 0")
            strokePath("M7.5 16 L12 8 L16.5 16 M9.5 13 H14.5")
        }
    }

    fun accentForCategory(name: String): Color = when (name) {
        "Circuit Fundamentals" -> CqBlue
        "Semiconductor Devices" -> CqCyan
        "Analog Design" -> CqGold
        "Digital Systems" -> CqBlueLight
        "Signals & Control" -> CqCyan
        "Power & Energy" -> CqGold
        "RF & Communications" -> CqBlueLight
        "Hardware & Embedded" -> CqCyan
        else -> CqGold
    }

    fun forCategory(name: String): ImageVector = when (name) {
        "Circuit Fundamentals" -> Resistor
        "Semiconductor Devices" -> Transistor
        "Analog Design" -> OpAmp
        "Digital Systems" -> Digital
        "Signals & Control" -> Signal
        "Power & Energy" -> Power
        "RF & Communications" -> Antenna
        "Hardware & Embedded" -> Chip
        else -> Specialized
    }
}
