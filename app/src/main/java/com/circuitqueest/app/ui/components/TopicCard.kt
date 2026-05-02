package com.circuitqueest.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.theme.CqBg
import com.circuitqueest.app.ui.theme.CqBg2
import com.circuitqueest.app.ui.theme.CqBorder
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.ui.theme.CqSurface
import com.circuitqueest.app.ui.theme.CqText
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.CqTextFaint
import com.circuitqueest.app.ui.theme.JetBrainsMono
import com.circuitqueest.app.ui.theme.MonoLabel
import com.circuitqueest.app.ui.theme.Radius
import com.circuitqueest.app.ui.theme.SpaceGrotesk
import com.circuitqueest.app.ui.theme.Spacing
import kotlinx.coroutines.launch

@Composable
fun TopicCard(
    topicNumber: Int,
    title: String,
    subtitle: String,
    topicIcon: String,
    isLocked: Boolean,
    isCurrent: Boolean,
    lessonCompleted: Boolean,
    quizCompleted: Boolean,
    quizScore: Int?,
    totalQuestions: Int?,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shakeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val shape = RoundedCornerShape(Radius.md)

    val chipStatus = when {
        quizCompleted -> ChipStatus.DONE
        lessonCompleted -> ChipStatus.IN_PROGRESS
        isLocked -> ChipStatus.LOCKED
        else -> null
    }
    val chipLabel = when {
        quizCompleted && quizScore != null && totalQuestions != null ->
            "✓ $quizScore/$totalQuestions"
        else -> null
    }

    val cardBg = if (isLocked) CqBg2 else CqSurface
    val borderModifier: Modifier = when {
        isCurrent -> Modifier.border(1.5.dp, CqGold, shape)
        isLocked -> Modifier.dashedBorder(1.dp, CqBorder, Radius.md)
        else -> Modifier.border(1.dp, CqBorder, shape)
    }

    Box(
        modifier = modifier
            .offset(x = shakeOffset.value.dp)
            .fillMaxWidth()
            .clip(shape)
            .background(cardBg)
            .then(borderModifier)
            .alpha(if (isLocked) 0.65f else 1f)
            .clickable {
                if (isLocked) {
                    scope.launch {
                        repeat(3) {
                            shakeOffset.animateTo(4f, tween(55))
                            shakeOffset.animateTo(-4f, tween(55))
                        }
                        shakeOffset.animateTo(0f, tween(55))
                    }
                } else {
                    onClick()
                }
            }
            .padding(Spacing.s14)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.s8)
        ) {
            // Topic number
            Text(
                text = String.format("%02d", topicNumber),
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                color = CqTextFaint,
                modifier = Modifier.size(20.dp)
            )

            // Glyph badge
            TopicGlyphBadge(
                label = topicIcon,
                accentColor = accentColor,
                size = 38.dp
            )

            // Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = if (isLocked) CqTextDim else CqText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if (isLocked) "Complete previous quest to unlock" else subtitle,
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = CqTextDim,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (chipStatus != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    StatusChip(status = chipStatus, label = chipLabel)
                }
            }

            // Trailing chevron
            Text(
                text = "›",
                style = MonoLabel.copy(fontSize = 16.sp),
                color = CqTextFaint
            )
        }
    }
}

private val Spacing.s14 get() = 14.dp
private val Radius.md get() = 12.dp
