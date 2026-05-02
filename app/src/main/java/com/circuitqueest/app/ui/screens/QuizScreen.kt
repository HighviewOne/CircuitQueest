package com.circuitqueest.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.data.content.Question
import com.circuitqueest.app.ui.components.AnswerFeedback
import com.circuitqueest.app.ui.components.MultipleChoiceQuestion
import com.circuitqueest.app.ui.components.NumericInputQuestion
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.ui.theme.CqText
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.JetBrainsMono
import com.circuitqueest.app.ui.theme.LocalCqPalette
import com.circuitqueest.app.ui.theme.MonoLabel
import com.circuitqueest.app.ui.theme.Spacing
import com.circuitqueest.app.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onBack: () -> Unit,
    onQuizComplete: (String, Int, Int) -> Unit
) {
    val pal = LocalCqPalette.current
    val quizState by viewModel.quizState.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val feedback by viewModel.feedback.collectAsState()
    val quizComplete by viewModel.quizComplete.collectAsState()

    LaunchedEffect(quizComplete) {
        if (quizComplete) {
            onQuizComplete(quizState.topicId, quizState.score, quizState.totalQuestions)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Question ${quizState.currentIndex + 1} of ${quizState.totalQuestions}",
                        style = MonoLabel.copy(fontSize = 12.sp),
                        color = CqTextDim
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = CqText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = pal.bg,
                    titleContentColor = CqTextDim
                )
            )
        },
        containerColor = pal.bg
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.s20),
                verticalArrangement = Arrangement.spacedBy(Spacing.s16)
            ) {
                Spacer(modifier = Modifier.height(Spacing.s4))

                QuizProgressNodes(
                    currentIndex = quizState.currentIndex,
                    totalQuestions = quizState.totalQuestions,
                    borderColor = pal.border
                )

                currentQuestion?.let { question ->
                    when (question) {
                        is Question.MultipleChoice -> MultipleChoiceQuestion(
                            questionText = question.questionText,
                            options = question.options,
                            questionNumber = quizState.currentIndex + 1,
                            correctIndex = question.correctIndex,
                            isSubmitted = feedback != null,
                            onAnswer = { viewModel.answerMultipleChoice(it) }
                        )
                        is Question.NumericInput -> NumericInputQuestion(
                            questionText = question.questionText,
                            unit = question.unit,
                            questionNumber = quizState.currentIndex + 1,
                            isSubmitted = feedback != null,
                            onAnswer = { viewModel.answerNumeric(it) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(if (feedback != null) 260.dp else 24.dp))
            }

            AnimatedVisibility(
                visible = feedback != null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                enter = slideInVertically(
                    animationSpec = tween(250),
                    initialOffsetY = { it }
                ) + fadeIn(tween(100)),
                exit = slideOutVertically(
                    animationSpec = tween(200),
                    targetOffsetY = { it }
                ) + fadeOut()
            ) {
                feedback?.let { fb ->
                    AnswerFeedback(
                        isCorrect = fb.isCorrect,
                        explanation = fb.explanation,
                        onNext = { viewModel.nextQuestion() }
                    )
                }
            }
        }
    }
}

// ── Progress nodes ────────────────────────────────────────────────────────────

@Composable
private fun QuizProgressNodes(
    currentIndex: Int,
    totalQuestions: Int,
    borderColor: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalQuestions) { index ->
            val isDone = index < currentIndex
            val isCurrent = index == currentIndex
            QuizNode(isDone = isDone, isCurrent = isCurrent, borderColor = borderColor)
            if (index < totalQuestions - 1) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(if (isDone) CqGold else borderColor)
                )
            }
        }
    }
}

@Composable
private fun QuizNode(
    isDone: Boolean,
    isCurrent: Boolean,
    borderColor: androidx.compose.ui.graphics.Color
) {
    val pal = LocalCqPalette.current
    val pulseAlpha by rememberInfiniteTransition(label = "node_pulse").animateFloat(
        initialValue = 0.25f,
        targetValue = 0.55f,
        animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse),
        label = "pulse"
    )

    Box(
        modifier = Modifier.size(28.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isCurrent) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(CqGold.copy(alpha = pulseAlpha))
            )
        }
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(if (isDone) CqGold else Color.Transparent)
                .border(
                    width = if (isDone) 0.dp else 1.5.dp,
                    color = if (isDone || isCurrent) CqGold else borderColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                Text(
                    text = "✓",
                    fontFamily = JetBrainsMono,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    color = pal.bg
                )
            }
        }
    }
}
