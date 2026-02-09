package com.circuitqueest.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.circuitqueest.app.data.content.Question
import com.circuitqueest.app.ui.components.AnswerFeedback
import com.circuitqueest.app.ui.components.MultipleChoiceQuestion
import com.circuitqueest.app.ui.components.NumericInputQuestion
import com.circuitqueest.app.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onBack: () -> Unit,
    onQuizComplete: (String, Int, Int) -> Unit
) {
    val quizState by viewModel.quizState.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val feedback by viewModel.feedback.collectAsState()
    val quizComplete by viewModel.quizComplete.collectAsState()

    LaunchedEffect(quizComplete) {
        if (quizComplete) {
            onQuizComplete(
                quizState.topicId,
                quizState.score,
                quizState.totalQuestions
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = quizState.quizTitle,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            LinearProgressIndicator(
                progress = {
                    if (quizState.totalQuestions > 0)
                        quizState.currentIndex.toFloat() / quizState.totalQuestions
                    else 0f
                },
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            currentQuestion?.let { question ->
                when (question) {
                    is Question.MultipleChoice -> {
                        MultipleChoiceQuestion(
                            questionText = question.questionText,
                            options = question.options,
                            questionNumber = quizState.currentIndex + 1,
                            totalQuestions = quizState.totalQuestions,
                            onAnswer = { selectedIndex ->
                                viewModel.answerMultipleChoice(selectedIndex)
                            }
                        )
                    }
                    is Question.NumericInput -> {
                        NumericInputQuestion(
                            questionText = question.questionText,
                            unit = question.unit,
                            questionNumber = quizState.currentIndex + 1,
                            totalQuestions = quizState.totalQuestions,
                            onAnswer = { value ->
                                viewModel.answerNumeric(value)
                            }
                        )
                    }
                }
            }

            feedback?.let { fb ->
                Spacer(modifier = Modifier.height(16.dp))
                AnswerFeedback(
                    isCorrect = fb.isCorrect,
                    explanation = fb.explanation,
                    onNext = { viewModel.nextQuestion() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
