package com.circuitqueest.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.components.QuizScoreDisplay
import com.circuitqueest.app.ui.theme.CircuitGreen
import com.circuitqueest.app.ui.theme.GoldAmber
import com.circuitqueest.app.ui.theme.ShortCircuitRed

@Composable
fun ResultScreen(
    topicId: String,
    score: Int,
    totalQuestions: Int,
    onRetry: (String) -> Unit,
    onHome: () -> Unit
) {
    val percentage = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
    val passed = percentage >= 60
    val xpEarned = score * 10 + if (passed) 100 else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (passed) "\uD83C\uDFC6" else "\u26A1",
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (passed) "Quest Complete!" else "Keep Training!",
            style = MaterialTheme.typography.headlineLarge,
            color = if (passed) GoldAmber else ShortCircuitRed,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (passed)
                "You've conquered this challenge and earned XP!"
            else
                "You need 60% to pass. Review the lesson and try again!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuizScoreDisplay(score = score, total = totalQuestions)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "$percentage%",
                    style = MaterialTheme.typography.headlineLarge,
                    color = if (passed) CircuitGreen else ShortCircuitRed
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "+$xpEarned XP earned",
                    style = MaterialTheme.typography.titleMedium,
                    color = GoldAmber
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Return to Quest Map")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { onRetry(topicId) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Retry Quiz")
        }
    }
}
