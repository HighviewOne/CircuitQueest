package com.circuitqueest.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.theme.CqBorder
import com.circuitqueest.app.ui.theme.CqSurface
import com.circuitqueest.app.ui.theme.CqText
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.MonoLabel
import com.circuitqueest.app.ui.theme.Radius
import com.circuitqueest.app.ui.theme.SpaceGrotesk
import com.circuitqueest.app.ui.theme.Spacing

@Composable
fun CategoryHeader(
    imageVector: ImageVector,
    name: String,
    completedCount: Int,
    totalCount: Int,
    categoryXp: Int,
    accentColor: Color,
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(Radius.lg)
    val chevronRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(200),
        label = "chevron"
    )
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(CqSurface)
            .border(1.dp, CqBorder, shape)
            .clickable(onClick = onClick)
            .padding(Spacing.s16)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s12)
        ) {
            TopicGlyphBadge(
                imageVector = imageVector,
                accentColor = accentColor,
                size = 40.dp
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = CqText
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$completedCount of $totalCount quests · $categoryXp XP",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = CqTextDim
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Mini progress bar
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(CqBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .background(accentColor)
                    )
                }
                Text(
                    text = "▾",
                    style = MonoLabel,
                    color = CqTextDim,
                    modifier = Modifier.rotate(chevronRotation)
                )
            }
        }
    }
}
