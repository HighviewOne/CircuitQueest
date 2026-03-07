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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.circuitqueest.app.ui.components.CategoryHeader
import com.circuitqueest.app.ui.components.TopicCard
import com.circuitqueest.app.ui.components.XpProgressBar
import com.circuitqueest.app.viewmodel.CategoryState
import com.circuitqueest.app.viewmodel.HomeViewModel
import com.circuitqueest.app.viewmodel.TopicState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTopicClick: (String) -> Unit
) {
    val categorizedTopics by viewModel.categorizedTopics.collectAsState()
    val totalXp by viewModel.totalXp.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val expandedCategories = remember { mutableMapOf<String, Boolean>() }

    // Initialize first category as expanded
    if (expandedCategories.isEmpty() && categorizedTopics.isNotEmpty()) {
        expandedCategories[categorizedTopics.first().category.name] = true
    }

    val isSearching = searchQuery.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CircuitQuEEst",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your Quest Progress",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                XpProgressBar(currentXp = totalXp)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text("Search topics...")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (!isSearching) {
                    Text(
                        text = "Choose Your Quest",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            if (isSearching) {
                val query = searchQuery.trim().lowercase()
                val filteredTopics = categorizedTopics.flatMap { it.topics }
                    .filter { state ->
                        state.topic.title.lowercase().contains(query) ||
                            state.topic.subtitle.lowercase().contains(query)
                    }

                if (filteredTopics.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No topics found",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(filteredTopics, key = { it.topic.id }) { state ->
                        TopicCard(
                            title = state.topic.title,
                            subtitle = state.topic.subtitle,
                            icon = state.topic.icon,
                            isLocked = state.isLocked,
                            lessonCompleted = state.progress?.lessonCompleted == true,
                            quizScore = state.progress?.bestScore?.takeIf { it > 0 },
                            totalQuestions = state.progress?.totalQuestions?.takeIf { it > 0 },
                            onClick = { onTopicClick(state.topic.id) }
                        )
                    }
                }
            } else {
                categorizedTopics.forEach { categoryState ->
                    val categoryName = categoryState.category.name
                    val isExpanded = expandedCategories[categoryName] ?: false
                    val completedCount = categoryState.topics.count {
                        it.progress?.quizCompleted == true
                    }

                    item(key = "header_$categoryName") {
                        CategoryHeader(
                            icon = categoryState.category.icon,
                            name = categoryName,
                            completedCount = completedCount,
                            totalCount = categoryState.topics.size,
                            isExpanded = isExpanded,
                            onClick = {
                                expandedCategories[categoryName] = !isExpanded
                            }
                        )
                    }

                    if (isExpanded) {
                        items(
                            categoryState.topics,
                            key = { it.topic.id }
                        ) { state ->
                            TopicCard(
                                title = state.topic.title,
                                subtitle = state.topic.subtitle,
                                icon = state.topic.icon,
                                isLocked = state.isLocked,
                                lessonCompleted = state.progress?.lessonCompleted == true,
                                quizScore = state.progress?.bestScore?.takeIf { it > 0 },
                                totalQuestions = state.progress?.totalQuestions?.takeIf { it > 0 },
                                onClick = { onTopicClick(state.topic.id) }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
