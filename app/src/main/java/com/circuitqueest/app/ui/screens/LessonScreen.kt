package com.circuitqueest.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.circuitqueest.app.data.content.LessonSection
import com.circuitqueest.app.ui.components.FormulaDisplay
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.viewmodel.LessonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    viewModel: LessonViewModel,
    onBack: () -> Unit,
    onStartQuiz: (String) -> Unit
) {
    val topic by viewModel.topic.collectAsState()
    val lessonCompleted by viewModel.lessonCompleted.collectAsState()

    val currentTopic = topic ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = currentTopic.lesson.title,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(currentTopic.lesson.sections) { section ->
                LessonSectionCard(section = section)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                if (!lessonCompleted) {
                    Button(
                        onClick = { viewModel.markLessonComplete() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Complete Lesson (+50 XP)")
                    }
                } else {
                    Button(
                        onClick = { onStartQuiz(currentTopic.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Start Boss Battle (Quiz)")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun LessonSectionCard(section: LessonSection) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = section.heading,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = section.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            section.formula?.let { formula ->
                Spacer(modifier = Modifier.height(12.dp))
                FormulaDisplay(formula = formula)
            }

            section.keyPoint?.let { keyPoint ->
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = CqGold.copy(alpha = 0.15f)
                    )
                ) {
                    Text(
                        text = "\uD83D\uDD11 $keyPoint",
                        style = MaterialTheme.typography.labelLarge,
                        color = CqGold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}
