package com.circuitqueest.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.theme.GoldAmber
import com.circuitqueest.app.ui.theme.SlateLighter

@Composable
fun TopicCard(
    title: String,
    subtitle: String,
    icon: String,
    isLocked: Boolean,
    lessonCompleted: Boolean,
    quizScore: Int?,
    totalQuestions: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { if (!isLocked) onClick() },
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (isLocked) 0.5f else 1f),
        colors = CardDefaults.cardColors(
            containerColor = if (isLocked) SlateLighter else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isLocked) "\uD83D\uDD12" else icon,
                fontSize = 36.sp,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isLocked) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isLocked) "Complete previous topic to unlock"
                    else subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (!isLocked && (lessonCompleted || quizScore != null)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (lessonCompleted) {
                            Text(
                                text = "\u2705 Lesson",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        if (quizScore != null && totalQuestions != null) {
                            Text(
                                text = "\uD83C\uDFC6 Quiz: $quizScore/$totalQuestions",
                                style = MaterialTheme.typography.labelMedium,
                                color = GoldAmber
                            )
                        }
                    }
                }
            }
        }
    }
}
