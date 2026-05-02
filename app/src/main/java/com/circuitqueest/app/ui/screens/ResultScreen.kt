package com.circuitqueest.app.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.data.content.Topic
import com.circuitqueest.app.data.content.TopicsService
import com.circuitqueest.app.ui.components.TopicGlyphBadge
import com.circuitqueest.app.ui.components.goldBrackets
import com.circuitqueest.app.ui.icons.SchematicIcons
import com.circuitqueest.app.ui.theme.CqBlue
import com.circuitqueest.app.ui.theme.CqBlueLight
import com.circuitqueest.app.ui.theme.CqCyan
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.ui.theme.CqGreen
import com.circuitqueest.app.ui.theme.CqRed
import com.circuitqueest.app.ui.theme.CqText
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.JetBrainsMono
import com.circuitqueest.app.ui.theme.LocalCqPalette
import com.circuitqueest.app.ui.theme.MonoLabel
import com.circuitqueest.app.ui.theme.Radius
import com.circuitqueest.app.ui.theme.SpaceGrotesk
import com.circuitqueest.app.ui.theme.Spacing
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ResultScreen(
    topicId: String,
    score: Int,
    totalQuestions: Int,
    onRetry: (String) -> Unit,
    onHome: () -> Unit,
    onNextLesson: ((String) -> Unit)? = null
) {
    val pal = LocalCqPalette.current
    val percentage = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
    val passed = percentage >= 60
    val xpEarned = score * 10 + if (passed) 100 else 0

    val topic = remember(topicId) { TopicsService.allTopics.find { it.id == topicId } }
    val topicTitle = topic?.title ?: "Quest"
    val nextTopic = remember(topicId) {
        val idx = TopicsService.allTopics.indexOfFirst { it.id == topicId }
        if (passed && idx >= 0 && idx + 1 < TopicsService.allTopics.size)
            TopicsService.allTopics[idx + 1]
        else null
    }

    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animProgress.animateTo(
            targetValue = if (totalQuestions > 0) score.toFloat() / totalQuestions else 0f,
            animationSpec = tween(
                durationMillis = 800,
                easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)
            )
        )
    }

    Scaffold(containerColor = pal.bg) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.s20),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Spacing.s24))

            Text(
                text = if (passed) "QUEST COMPLETE" else "KEEP TRAINING",
                style = MonoLabel,
                color = if (passed) CqBlueLight else CqTextDim
            )
            Spacer(modifier = Modifier.height(Spacing.s8))
            Text(
                text = topicTitle,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 34.sp,
                color = CqText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.s32))

            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                ScoreRing(
                    progress = animProgress.value,
                    passed = passed,
                    trackColor = pal.track,
                    borderColor = pal.border
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$percentage%",
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp,
                        color = if (passed) CqGold else CqRed
                    )
                    Text(
                        text = "$score/$totalQuestions correct",
                        style = MonoLabel,
                        color = CqTextDim
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.s24))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.s12)
            ) {
                StatCard(
                    value = "+$xpEarned",
                    label = "XP Earned",
                    valueColor = CqGold,
                    bgColor = pal.surface,
                    borderColor = pal.border,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    value = "$score/$totalQuestions",
                    label = "Score",
                    valueColor = CqBlueLight,
                    bgColor = pal.surface,
                    borderColor = pal.border,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    value = if (passed) "Pass" else "Fail",
                    label = "Status",
                    valueColor = if (passed) CqGreen else CqRed,
                    bgColor = pal.surface,
                    borderColor = pal.border,
                    modifier = Modifier.weight(1f)
                )
            }

            nextTopic?.let { next ->
                Spacer(modifier = Modifier.height(Spacing.s16))
                UpNextCard(
                    next = next,
                    bgColor = pal.surface2,
                    onContinue = { onNextLesson?.invoke(next.id) ?: onHome() }
                )
            }

            Spacer(modifier = Modifier.height(Spacing.s24))

            Button(
                onClick = onHome,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(Radius.md),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (passed) CqGold else CqBlue,
                    contentColor = if (passed) pal.bg else CqText
                )
            ) {
                Text(
                    text = "Back to Quest Map",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(Spacing.s8))
            OutlinedButton(
                onClick = { onRetry(topicId) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(Radius.md),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CqTextDim),
                border = androidx.compose.foundation.BorderStroke(1.dp, pal.border)
            ) {
                Text(
                    text = "Retry Quiz",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(Spacing.s32))
        }
    }
}

// ── Score ring ────────────────────────────────────────────────────────────────

@Composable
private fun ScoreRing(progress: Float, passed: Boolean, trackColor: Color, borderColor: Color) {
    val fillColors = if (passed)
        listOf(CqBlue, CqBlueLight, CqCyan, CqGold)
    else
        listOf(CqRed, CqRed.copy(alpha = 0.6f), CqRed)

    Canvas(modifier = Modifier.size(200.dp)) {
        val strokeW = 12.dp.toPx()
        val inset = strokeW / 2f
        val arcTopLeft = Offset(inset, inset)
        val arcSize = Size(size.width - strokeW, size.height - strokeW)
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = (size.minDimension / 2f) - inset

        drawArc(
            color = trackColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = arcTopLeft,
            size = arcSize,
            style = Stroke(strokeW)
        )

        repeat(12) { i ->
            val angle = (i * 30.0 - 90.0) * PI / 180.0
            val outerR = radius + inset * 0.6f
            val innerR = radius - inset * 0.6f
            drawLine(
                color = borderColor,
                start = Offset(
                    center.x + (innerR * cos(angle)).toFloat(),
                    center.y + (innerR * sin(angle)).toFloat()
                ),
                end = Offset(
                    center.x + (outerR * cos(angle)).toFloat(),
                    center.y + (outerR * sin(angle)).toFloat()
                ),
                strokeWidth = 1.dp.toPx()
            )
        }

        if (progress > 0.01f) {
            drawArc(
                brush = Brush.sweepGradient(colors = fillColors, center = center),
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(strokeW, cap = StrokeCap.Round)
            )
        }
    }
}

// ── Stat card ─────────────────────────────────────────────────────────────────

@Composable
private fun StatCard(
    value: String,
    label: String,
    valueColor: Color,
    bgColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(Radius.lg)
    Box(
        modifier = modifier
            .height(80.dp)
            .clip(shape)
            .background(bgColor)
            .border(1.dp, borderColor, shape),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = valueColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                color = CqTextDim
            )
        }
    }
}

// ── Up next card ──────────────────────────────────────────────────────────────

@Composable
private fun UpNextCard(next: Topic, bgColor: Color, onContinue: () -> Unit) {
    val shape = RoundedCornerShape(Radius.lg)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(bgColor)
            .goldBrackets()
            .padding(Spacing.s20)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
            Text(text = "UP NEXT", style = MonoLabel, color = CqGold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.s12)
            ) {
                TopicGlyphBadge(
                    label = next.icon,
                    accentColor = SchematicIcons.accentForCategory(""),
                    size = 38.dp
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = next.title,
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = CqText
                    )
                    Text(
                        text = next.subtitle,
                        fontFamily = SpaceGrotesk,
                        fontSize = 13.sp,
                        color = CqTextDim
                    )
                }
            }
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(Radius.md),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CqGold, contentColor = bgColor
                )
            ) {
                Text(
                    text = "Continue  →",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
