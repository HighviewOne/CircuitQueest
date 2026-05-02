package com.circuitqueest.app.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.circuitqueest.app.ui.theme.CqGold

fun Modifier.goldBrackets(): Modifier = drawBehind {
    val len = 18.dp.toPx()
    val inset = 10.dp.toPx()
    val sw = 1.5.dp.toPx()
    val c = CqGold
    val cap = StrokeCap.Square
    drawLine(c, Offset(inset, inset + len), Offset(inset, inset), sw, cap)
    drawLine(c, Offset(inset, inset), Offset(inset + len, inset), sw, cap)
    drawLine(c, Offset(size.width - inset - len, inset), Offset(size.width - inset, inset), sw, cap)
    drawLine(c, Offset(size.width - inset, inset), Offset(size.width - inset, inset + len), sw, cap)
    drawLine(c, Offset(inset, size.height - inset - len), Offset(inset, size.height - inset), sw, cap)
    drawLine(c, Offset(inset, size.height - inset), Offset(inset + len, size.height - inset), sw, cap)
    drawLine(c, Offset(size.width - inset, size.height - inset - len), Offset(size.width - inset, size.height - inset), sw, cap)
    drawLine(c, Offset(size.width - inset, size.height - inset), Offset(size.width - inset - len, size.height - inset), sw, cap)
}

internal fun Modifier.dashedBorder(width: Dp, color: Color, cornerRadius: Dp): Modifier =
    drawBehind {
        drawRoundRect(
            color = color,
            size = size,
            cornerRadius = CornerRadius(cornerRadius.toPx()),
            style = Stroke(
                width = width.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 5f), 0f)
            )
        )
    }
