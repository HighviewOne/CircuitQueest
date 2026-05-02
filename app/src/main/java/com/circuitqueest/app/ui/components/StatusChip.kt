package com.circuitqueest.app.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.theme.CqBorder
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.ui.theme.CqGreen
import com.circuitqueest.app.ui.theme.CqTextFaint
import com.circuitqueest.app.ui.theme.MonoLabel

enum class ChipStatus { DONE, IN_PROGRESS, LOCKED }

@Composable
fun StatusChip(
    status: ChipStatus,
    label: String? = null,
    modifier: Modifier = Modifier
) {
    val cornerRadius = 6.dp
    val shape = RoundedCornerShape(cornerRadius)

    when (status) {
        ChipStatus.DONE -> {
            val text = label ?: "✓ Done"
            Box(
                modifier = modifier
                    .clip(shape)
                    .background(CqGreen.copy(alpha = 0.10f))
                    .border(1.dp, CqGreen.copy(alpha = 0.35f), shape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = text, style = MonoLabel.copy(fontSize = 10.sp), color = CqGreen)
            }
        }

        ChipStatus.IN_PROGRESS -> {
            val text = label ?: "In progress"
            val dotAlpha by rememberInfiniteTransition(label = "dot").animateFloat(
                initialValue = 0.35f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(700),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "dot_alpha"
            )
            Box(
                modifier = modifier
                    .clip(shape)
                    .background(CqGold.copy(alpha = 0.06f))
                    .border(1.dp, CqGold.copy(alpha = 0.5f), shape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(CqGold.copy(alpha = dotAlpha))
                    )
                    Text(text = text, style = MonoLabel.copy(fontSize = 10.sp), color = CqGold)
                }
            }
        }

        ChipStatus.LOCKED -> {
            val text = label ?: "⌗ Complete previous quest"
            Box(
                modifier = modifier
                    .dashedBorder(1.dp, CqBorder, cornerRadius)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = text, style = MonoLabel.copy(fontSize = 10.sp), color = CqTextFaint)
            }
        }
    }
}

