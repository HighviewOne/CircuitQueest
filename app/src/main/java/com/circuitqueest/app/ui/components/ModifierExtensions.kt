package com.circuitqueest.app.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

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
