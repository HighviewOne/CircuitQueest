package com.circuitqueest.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.circuitqueest.app.ui.theme.CqGreen
import com.circuitqueest.app.ui.theme.CqRed

@Composable
fun MultipleChoiceQuestion(
    questionText: String,
    options: List<String>,
    questionNumber: Int,
    totalQuestions: Int,
    onAnswer: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    var submitted by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Question $questionNumber of $totalQuestions",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = questionText,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        options.forEachIndexed { index, option ->
            OutlinedButton(
                onClick = {
                    if (!submitted) {
                        selectedIndex = index
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = when {
                        selectedIndex == index -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedIndex == index)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "${('A' + index)}) $option",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (selectedIndex >= 0 && !submitted) {
                    submitted = true
                    onAnswer(selectedIndex)
                }
            },
            enabled = selectedIndex >= 0 && !submitted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Answer")
        }
    }
}

@Composable
fun NumericInputQuestion(
    questionText: String,
    unit: String,
    questionNumber: Int,
    totalQuestions: Int,
    onAnswer: (Double?) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Question $questionNumber of $totalQuestions",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = questionText,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { if (!submitted) inputText = it },
            label = { Text("Your answer${if (unit.isNotEmpty()) " ($unit)" else ""}") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (inputText.isNotBlank() && !submitted) {
                        submitted = true
                        onAnswer(inputText.toDoubleOrNull())
                    }
                }
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = !submitted
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (inputText.isNotBlank() && !submitted) {
                    submitted = true
                    onAnswer(inputText.toDoubleOrNull())
                }
            },
            enabled = inputText.isNotBlank() && !submitted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Answer")
        }
    }
}

@Composable
fun AnswerFeedback(
    isCorrect: Boolean,
    explanation: String,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) CqGreen.copy(alpha = 0.15f)
            else CqRed.copy(alpha = 0.15f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isCorrect) "\u2705 Correct!" else "\u274C Incorrect",
                style = MaterialTheme.typography.titleMedium,
                color = if (isCorrect) CqGreen else CqRed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = explanation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Next")
            }
        }
    }
}
