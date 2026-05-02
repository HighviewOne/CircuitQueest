package com.circuitqueest.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.components.CategoryHeader
import com.circuitqueest.app.ui.components.TopicCard
import com.circuitqueest.app.ui.components.XpBar
import com.circuitqueest.app.ui.icons.SchematicIcons
import com.circuitqueest.app.ui.theme.CqBg
import com.circuitqueest.app.ui.theme.CqBorder
import com.circuitqueest.app.ui.theme.CqSurface
import com.circuitqueest.app.ui.theme.CqText
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.CqTextFaint
import com.circuitqueest.app.ui.theme.JetBrainsMono
import com.circuitqueest.app.ui.theme.MonoLabel
import com.circuitqueest.app.ui.theme.SpaceGrotesk
import com.circuitqueest.app.ui.theme.Spacing
import com.circuitqueest.app.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTopicClick: (String) -> Unit
) {
    val categorizedTopics by viewModel.categorizedTopics.collectAsState()
    val totalXp by viewModel.totalXp.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val expandedCategories = remember { mutableStateMapOf<String, Boolean>() }

    // First category starts expanded
    if (expandedCategories.isEmpty() && categorizedTopics.isNotEmpty()) {
        expandedCategories[categorizedTopics.first().category.name] = true
    }

    // Identify the current (first unlocked, incomplete) topic
    val currentTopicId = remember(categorizedTopics) {
        categorizedTopics.flatMap { it.topics }
            .firstOrNull { !it.isLocked && it.progress?.quizCompleted != true }
            ?.topic?.id
    }

    val isSearching = searchQuery.isNotBlank()

    Scaffold(containerColor = CqBg) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Spacing.s20),
            verticalArrangement = Arrangement.spacedBy(Spacing.s12)
        ) {
            // ── App header ──────────────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(Spacing.s16))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "CircuitQuEEst",
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        letterSpacing = 0.9.sp,
                        color = CqText
                    )
                    // Level badge
                    val level = totalXp / 500 + 1
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(CqSurface)
                            .border(1.dp, CqBorder, RoundedCornerShape(100.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "LVL $level",
                            style = MonoLabel,
                            color = CqTextDim
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Spacing.s12))
            }

            // ── XP bar ──────────────────────────────────────────────────
            item {
                XpBar(totalXp = totalXp)
            }

            // ── Search field ────────────────────────────────────────────
            item {
                val searchShape = RoundedCornerShape(12.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(searchShape)
                        .background(CqSurface)
                        .border(1.dp, CqBorder, searchShape)
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = CqTextFaint,
                            modifier = Modifier.size(16.dp)
                        )
                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search quests, formulas, components…",
                                    fontFamily = SpaceGrotesk,
                                    fontSize = 14.sp,
                                    color = CqTextFaint
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontFamily = SpaceGrotesk,
                                    fontSize = 14.sp,
                                    color = CqText
                                ),
                                cursorBrush = SolidColor(CqText),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { searchQuery = "" },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = CqTextFaint,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }

            // ── Content ─────────────────────────────────────────────────
            if (isSearching) {
                val query = searchQuery.trim().lowercase()
                val filtered = categorizedTopics.flatMap { it.topics }
                    .filter {
                        it.topic.title.lowercase().contains(query) ||
                            it.topic.subtitle.lowercase().contains(query)
                    }

                if (filtered.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No quests found",
                                fontFamily = SpaceGrotesk,
                                fontSize = 15.sp,
                                color = CqTextDim
                            )
                        }
                    }
                } else {
                    items(filtered, key = { it.topic.id }) { state ->
                        val accent = SchematicIcons.accentForCategory(
                            categorizedTopics.find { cat ->
                                cat.topics.any { it.topic.id == state.topic.id }
                            }?.category?.name ?: ""
                        )
                        TopicCard(
                            topicNumber = state.topic.order,
                            title = state.topic.title,
                            subtitle = state.topic.subtitle,
                            topicIcon = state.topic.icon,
                            isLocked = state.isLocked,
                            isCurrent = state.topic.id == currentTopicId,
                            lessonCompleted = state.progress?.lessonCompleted == true,
                            quizCompleted = state.progress?.quizCompleted == true,
                            quizScore = state.progress?.bestScore?.takeIf { it > 0 },
                            totalQuestions = state.progress?.totalQuestions?.takeIf { it > 0 },
                            accentColor = accent,
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
                    val categoryXp = categoryState.topics.sumOf {
                        it.progress?.xpEarned ?: 0
                    }
                    val accent = SchematicIcons.accentForCategory(categoryName)

                    item(key = "header_$categoryName") {
                        CategoryHeader(
                            imageVector = SchematicIcons.forCategory(categoryName),
                            name = categoryName,
                            completedCount = completedCount,
                            totalCount = categoryState.topics.size,
                            categoryXp = categoryXp,
                            accentColor = accent,
                            isExpanded = isExpanded,
                            onClick = {
                                expandedCategories[categoryName] = !isExpanded
                            }
                        )
                    }

                    item(key = "topics_$categoryName") {
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(animationSpec = tween(200)),
                            exit = shrinkVertically(animationSpec = tween(200))
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(Spacing.s8),
                                modifier = Modifier.padding(top = Spacing.s8)
                            ) {
                                categoryState.topics.forEach { state ->
                                    TopicCard(
                                        topicNumber = state.topic.order,
                                        title = state.topic.title,
                                        subtitle = state.topic.subtitle,
                                        topicIcon = state.topic.icon,
                                        isLocked = state.isLocked,
                                        isCurrent = state.topic.id == currentTopicId,
                                        lessonCompleted = state.progress?.lessonCompleted == true,
                                        quizCompleted = state.progress?.quizCompleted == true,
                                        quizScore = state.progress?.bestScore?.takeIf { it > 0 },
                                        totalQuestions = state.progress?.totalQuestions?.takeIf { it > 0 },
                                        accentColor = accent,
                                        onClick = { onTopicClick(state.topic.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(Spacing.s32)) }
        }
    }
}
