package com.circuitqueest.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.circuitqueest.app.data.content.TopicsService
import com.circuitqueest.app.data.content.TopicCategories
import com.circuitqueest.app.data.content.TopicCategory
import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.data.repository.ProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class TopicState(
    val topic: com.circuitqueest.app.data.content.Topic,
    val progress: TopicProgress?,
    val isLocked: Boolean
)

data class CategoryState(
    val category: TopicCategory,
    val topics: List<TopicState>
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProgressRepository
) : ViewModel() {

    val topicStates: StateFlow<List<TopicState>> = repository.getAllProgress()
        .map { progressList ->
            val progressMap = progressList.associateBy { it.topicId }
            TopicsService.allTopics.mapIndexed { index, topic ->
                val progress = progressMap[topic.id]
                // A topic unlocks once the previous one's quiz is passed. Anything the
                // player has already started stays open, so progress made under an
                // older unlock order never gets re-locked.
                val alreadyStarted = progress?.lessonCompleted == true ||
                    progress?.quizCompleted == true
                val isLocked = when {
                    index == 0 || alreadyStarted -> false
                    else -> {
                        val prevTopic = TopicsService.allTopics[index - 1]
                        progressMap[prevTopic.id]?.quizCompleted != true
                    }
                }
                TopicState(
                    topic = topic,
                    progress = progress,
                    isLocked = isLocked
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TopicsService.allTopics.mapIndexed { index, topic ->
                TopicState(topic = topic, progress = null, isLocked = index > 0)
            }
        )

    val categorizedTopics: StateFlow<List<CategoryState>> = topicStates
        .map { states ->
            val stateMap = states.associateBy { it.topic.id }
            TopicCategories.categories.map { category ->
                CategoryState(
                    category = category,
                    topics = category.topicIds.mapNotNull { stateMap[it] }
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TopicCategories.categories.map { category ->
                CategoryState(
                    category = category,
                    topics = emptyList()
                )
            }
        )

    val totalXp: StateFlow<Int> = repository.getTotalXp()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    companion object {
        val allTopics = TopicsService.allTopics
    }
}
