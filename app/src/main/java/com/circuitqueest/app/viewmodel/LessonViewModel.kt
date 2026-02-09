package com.circuitqueest.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.circuitqueest.app.data.content.Topic
import com.circuitqueest.app.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LessonViewModel(
    private val topicId: String,
    private val repository: ProgressRepository
) : ViewModel() {

    private val _topic = MutableStateFlow<Topic?>(null)
    val topic: StateFlow<Topic?> = _topic.asStateFlow()

    val lessonCompleted: StateFlow<Boolean> = repository.getProgress(topicId)
        .map { it?.lessonCompleted == true }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        _topic.value = HomeViewModel.allTopics.find { it.id == topicId }
    }

    fun markLessonComplete() {
        viewModelScope.launch {
            repository.markLessonCompleted(topicId)
        }
    }

    class Factory(
        private val topicId: String,
        private val repository: ProgressRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LessonViewModel(topicId, repository) as T
        }
    }
}
