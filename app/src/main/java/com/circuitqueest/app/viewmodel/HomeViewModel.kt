package com.circuitqueest.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.circuitqueest.app.data.content.AcCircuitsContent
import com.circuitqueest.app.data.content.CapacitorsInductorsContent
import com.circuitqueest.app.data.content.CommunicationSystemsContent
import com.circuitqueest.app.data.content.ControlSystemsContent
import com.circuitqueest.app.data.content.DigitalLogicContent
import com.circuitqueest.app.data.content.DiodesContent
import com.circuitqueest.app.data.content.FiltersContent
import com.circuitqueest.app.data.content.KirchhoffsContent
import com.circuitqueest.app.data.content.MosfetsContent
import com.circuitqueest.app.data.content.OhmsLawContent
import com.circuitqueest.app.data.content.OpAmpsContent
import com.circuitqueest.app.data.content.PowerElectronicsContent
import com.circuitqueest.app.data.content.SeriesParallelContent
import com.circuitqueest.app.data.content.SignalsSystemsContent
import com.circuitqueest.app.data.content.TheveninNortonContent
import com.circuitqueest.app.data.content.TransformersContent
import com.circuitqueest.app.data.content.TransistorsContent
import com.circuitqueest.app.data.content.TransmissionLinesContent
import com.circuitqueest.app.data.content.Topic
import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.data.repository.ProgressRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class TopicState(
    val topic: Topic,
    val progress: TopicProgress?,
    val isLocked: Boolean
)

class HomeViewModel(
    private val repository: ProgressRepository
) : ViewModel() {

    companion object {
        val allTopics = listOf(
            OhmsLawContent.topic,
            SeriesParallelContent.topic,
            KirchhoffsContent.topic,
            CapacitorsInductorsContent.topic,
            AcCircuitsContent.topic,
            OpAmpsContent.topic,
            TheveninNortonContent.topic,
            DiodesContent.topic,
            TransistorsContent.topic,
            DigitalLogicContent.topic,
            MosfetsContent.topic,
            FiltersContent.topic,
            TransformersContent.topic,
            SignalsSystemsContent.topic,
            PowerElectronicsContent.topic,
            ControlSystemsContent.topic,
            TransmissionLinesContent.topic,
            CommunicationSystemsContent.topic
        ).sortedBy { it.order }
    }

    val topicStates: StateFlow<List<TopicState>> = repository.getAllProgress()
        .map { progressList ->
            val progressMap = progressList.associateBy { it.topicId }
            allTopics.mapIndexed { index, topic ->
                val progress = progressMap[topic.id]
                val isLocked = if (index == 0) false else {
                    val prevTopic = allTopics[index - 1]
                    val prevProgress = progressMap[prevTopic.id]
                    prevProgress?.quizCompleted != true
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
            initialValue = allTopics.mapIndexed { index, topic ->
                TopicState(topic = topic, progress = null, isLocked = index > 0)
            }
        )

    val totalXp: StateFlow<Int> = repository.getTotalXp()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    class Factory(private val repository: ProgressRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}
