package com.circuitqueest.app.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.circuitqueest.app.ui.theme.CqBlue
import com.circuitqueest.app.ui.theme.CqBlueLight
import com.circuitqueest.app.ui.theme.CqCyan
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.LocalCqPalette
import com.circuitqueest.app.ui.theme.MonoLabel
import com.circuitqueest.app.ui.theme.Radius
import com.circuitqueest.app.ui.theme.Spacing

private const val XP_PER_LEVEL = 500

private data class LevelInfo(val level: Int, val xpInLevel: Int, val xpToNext: Int, val progress: Float)

private fun computeLevel(totalXp: Int): LevelInfo {
    val level = totalXp / XP_PER_LEVEL + 1
    val xpInLevel = totalXp % XP_PER_LEVEL
    val xpToNext = XP_PER_LEVEL - xpInLevel
    return LevelInfo(level, xpInLevel, xpToNext, xpInLevel.toFloat() / XP_PER_LEVEL)
}

@Composable
fun XpBar(
    totalXp: Int,
    modifier: Modifier = Modifier
) {
    val pal = LocalCqPalette.current
    val info = computeLevel(totalXp)
    val shape = RoundedCornerShape(Radius.lg)

    val fillProgress by animateFloatAsState(
        targetValue = info.progress,
        animationSpec = tween(durationMillis = 800),
        label = "xp_fill"
    )

    val shimmerAnim by rememberInfiniteTransition(label = "shimmer").animateFloat(
        initialValue = -200f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_offset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape)
            .background(pal.surface)
            .border(1.dp, pal.border, shape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fillProgress)
                .background(
                    Brush.linearGradient(
                        colors = listOf(CqBlue, CqBlueLight, CqCyan, CqGold)
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.28f),
                                Color.Transparent
                            ),
                            start = Offset(shimmerAnim, 0f),
                            end = Offset(shimmerAnim + 200f, 0f)
                        )
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing.s16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Level ${info.level} · ${totalXp} XP",
                style = MonoLabel,
                color = CqTextDim
            )
            Text(
                text = "→ ${info.xpToNext} to Level ${info.level + 1}",
                style = MonoLabel,
                color = CqTextDim
            )
        }
    }
}
