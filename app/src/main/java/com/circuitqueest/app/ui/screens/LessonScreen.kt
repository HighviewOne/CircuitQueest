@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.circuitqueest.app.ui.screens

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.data.content.LessonSection
import com.circuitqueest.app.data.content.Topic
import com.circuitqueest.app.navigation.LocalNavAnimatedVisibilityScope
import com.circuitqueest.app.navigation.LocalNavSharedTransitionScope
import com.circuitqueest.app.ui.components.FormulaTile
import com.circuitqueest.app.ui.theme.CqBlue
import com.circuitqueest.app.ui.theme.CqBlueDeep
import com.circuitqueest.app.ui.theme.CqBlueLight
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.ui.theme.CqText
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.JetBrainsMono
import com.circuitqueest.app.ui.theme.LocalCqPalette
import com.circuitqueest.app.ui.theme.MonoLabel
import com.circuitqueest.app.ui.theme.Radius
import com.circuitqueest.app.ui.theme.SpaceGrotesk
import com.circuitqueest.app.ui.theme.Spacing
import com.circuitqueest.app.viewmodel.LessonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    viewModel: LessonViewModel,
    onBack: () -> Unit,
    onStartQuiz: (String) -> Unit
) {
    val pal = LocalCqPalette.current
    val topic by viewModel.topic.collectAsState()
    val lessonCompleted by viewModel.lessonCompleted.collectAsState()
    val currentTopic = topic ?: return

    val listState = rememberLazyListState()

    // Parallax offset: track how far the hero (item index 1) has scrolled
    val parallaxOffset by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 1)
                listState.firstVisibleItemScrollOffset.toFloat()
            else 0f
        }
    }

    // CTA fades in after the user scrolls past the hero card
    val ctaAlpha by animateFloatAsState(
        targetValue = if (listState.firstVisibleItemIndex >= 2) 1f else 0f,
        animationSpec = tween(400),
        label = "cta_alpha"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = currentTopic.title,
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = CqText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = CqText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = pal.bg,
                    titleContentColor = CqText
                )
            )
        },
        bottomBar = {
            StickyCtaBar(
                lessonCompleted = lessonCompleted,
                questionCount = currentTopic.quiz.questions.size,
                bgColor = pal.bg,
                onComplete = { viewModel.markLessonComplete() },
                onStartQuiz = { onStartQuiz(currentTopic.id) },
                alpha = ctaAlpha
            )
        },
        containerColor = pal.bg
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Spacing.s20),
            verticalArrangement = Arrangement.spacedBy(Spacing.s16)
        ) {
            item { Spacer(modifier = Modifier.height(Spacing.s4)) }

            // Hero card with shared element + parallax
            item {
                HeroCard(
                    topic = currentTopic,
                    parallaxOffset = parallaxOffset
                )
            }

            // Section cards
            currentTopic.lesson.sections.forEachIndexed { index, section ->
                item(key = "section_$index") {
                    SectionCard(index = index + 1, section = section, pal = pal)
                }
            }

            item { Spacer(modifier = Modifier.height(Spacing.s8)) }
        }
    }
}

// ── Hero card ─────────────────────────────────────────────────────────────────

@Composable
private fun HeroCard(topic: Topic, parallaxOffset: Float = 0f) {
    val sharedScope = LocalNavSharedTransitionScope.current
    val animScope = LocalNavAnimatedVisibilityScope.current
    val shape = RoundedCornerShape(Radius.xl)

    val sharedModifier: Modifier = if (sharedScope != null && animScope != null) {
        with(sharedScope) {
            Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "topic_card_${topic.id}"),
                animatedVisibilityScope = animScope,
                enter = androidx.compose.animation.fadeIn(tween(200)),
                exit = androidx.compose.animation.fadeOut(tween(150))
            )
        }
    } else Modifier

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(sharedModifier)
            .height(210.dp)
            .clip(shape)
            .background(
                Brush.linearGradient(
                    colors = listOf(CqBlueDeep, CqBlue),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        // PCB trace overlay
        androidx.compose.foundation.Canvas(modifier = Modifier.matchParentSize()) {
            val c = Color.White.copy(alpha = 0.08f)
            val sw = 1.5.dp.toPx()
            val r = 3.dp.toPx()
            drawLine(c, Offset(0f, size.height * 0.28f), Offset(size.width * 0.45f, size.height * 0.28f), sw)
            drawLine(c, Offset(size.width * 0.45f, size.height * 0.28f), Offset(size.width * 0.45f, size.height * 0.6f), sw)
            drawLine(c, Offset(size.width * 0.45f, size.height * 0.6f), Offset(size.width, size.height * 0.6f), sw)
            drawCircle(c, r, Offset(size.width * 0.45f, size.height * 0.28f))
            drawCircle(c, r, Offset(size.width * 0.45f, size.height * 0.6f))
            drawLine(c, Offset(0f, size.height * 0.75f), Offset(size.width * 0.25f, size.height * 0.75f), sw)
            drawLine(c, Offset(size.width * 0.25f, size.height * 0.75f), Offset(size.width * 0.25f, size.height), sw)
            drawCircle(c, r, Offset(size.width * 0.25f, size.height * 0.75f))
        }

        // Inner content slides up slower than scroll (parallax at 0.4×)
        Column(
            modifier = Modifier
                .padding(Spacing.s24)
                .graphicsLayer { translationY = -parallaxOffset * 0.4f }
        ) {
            Text(
                text = "QUEST · ${String.format("%02d", topic.order)}",
                style = MonoLabel,
                color = CqBlueLight
            )
            Spacer(modifier = Modifier.height(Spacing.s12))
            Text(
                text = topic.title,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 38.sp,
                color = CqText
            )
            Spacer(modifier = Modifier.height(Spacing.s8))
            Text(
                text = topic.subtitle,
                fontFamily = SpaceGrotesk,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = Color.White.copy(alpha = 0.75f)
            )
            Spacer(modifier = Modifier.height(Spacing.s16))
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                StatPill("~${topic.lesson.sections.size + 1} min")
                StatPill("${topic.quiz.questions.size} questions")
                StatPill("+50 XP")
            }
        }
    }
}

@Composable
private fun StatPill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(Color.White.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.85f)
        )
    }
}

// ── Section card ──────────────────────────────────────────────────────────────

@Composable
private fun SectionCard(
    index: Int,
    section: LessonSection,
    pal: com.circuitqueest.app.ui.theme.CqPalette
) {
    val shape = RoundedCornerShape(Radius.lg)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(pal.surface)
            .border(1.dp, pal.border, shape)
            .padding(Spacing.s20)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.s12)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(pal.bg2)
                        .border(1.dp, pal.border, RoundedCornerShape(100.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = String.format("%02d", index),
                        style = MonoLabel,
                        color = CqTextDim
                    )
                }
                Text(
                    text = section.heading,
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = CqText,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = section.content,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = CqText.copy(alpha = 0.9f)
            )

            section.formula?.let { FormulaTile(formula = it) }
            section.keyPoint?.let { KeyInsightCallout(keyPoint = it, pal = pal) }
        }
    }
}

@Composable
private fun KeyInsightCallout(
    keyPoint: String,
    pal: com.circuitqueest.app.ui.theme.CqPalette
) {
    val shape = RoundedCornerShape(Radius.md)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(CqGold.copy(alpha = 0.06f))
            .border(1.dp, CqGold.copy(alpha = 0.35f), shape)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight()
                    .background(CqGold)
            )
            Column(
                modifier = Modifier.padding(horizontal = Spacing.s16, vertical = Spacing.s12)
            ) {
                Text(text = "KEY INSIGHT", style = MonoLabel, color = CqGold)
                Spacer(modifier = Modifier.height(Spacing.s4))
                Text(
                    text = keyPoint,
                    fontFamily = SpaceGrotesk,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = CqText
                )
            }
        }
    }
}

// ── Sticky CTA ────────────────────────────────────────────────────────────────

@Composable
private fun StickyCtaBar(
    lessonCompleted: Boolean,
    questionCount: Int,
    bgColor: androidx.compose.ui.graphics.Color,
    alpha: Float,
    onComplete: () -> Unit,
    onStartQuiz: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { this.alpha = alpha }
            .background(bgColor)
            .padding(horizontal = Spacing.s20, vertical = Spacing.s12)
    ) {
        Button(
            onClick = if (lessonCompleted) onStartQuiz else onComplete,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(Radius.md),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (lessonCompleted) CqGold else CqBlue,
                contentColor = if (lessonCompleted) bgColor else CqText
            )
        ) {
            Text(
                text = if (lessonCompleted)
                    "Start the $questionCount-question quiz  →"
                else
                    "Complete Lesson  +50 XP",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        }
    }
}
