package com.circuitqueest.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.circuitqueest.app.data.content.AcCircuitsContent
import com.circuitqueest.app.data.content.AnalogCircuitsContent
import com.circuitqueest.app.data.content.AntennaDesignContent
import com.circuitqueest.app.data.content.AudioElectronicsContent
import com.circuitqueest.app.data.content.BatteryStorageContent
import com.circuitqueest.app.data.content.BiomedicalElectronicsContent
import com.circuitqueest.app.data.content.CapacitorsInductorsContent
import com.circuitqueest.app.data.content.CommunicationSystemsContent
import com.circuitqueest.app.data.content.ControlSystemsContent
import com.circuitqueest.app.data.content.DigitalLogicContent
import com.circuitqueest.app.data.content.DigitalSystemsContent
import com.circuitqueest.app.data.content.DiodesContent
import com.circuitqueest.app.data.content.DspContent
import com.circuitqueest.app.data.content.ElectricMachinesContent
import com.circuitqueest.app.data.content.ElectricVehiclesContent
import com.circuitqueest.app.data.content.ElectromagneticsContent
import com.circuitqueest.app.data.content.EmbeddedSystemsContent
import com.circuitqueest.app.data.content.FiberOpticsContent
import com.circuitqueest.app.data.content.FiltersContent
import com.circuitqueest.app.data.content.IotWirelessContent
import com.circuitqueest.app.data.content.KirchhoffsContent
import com.circuitqueest.app.data.content.MemsContent
import com.circuitqueest.app.data.content.MlHardwareContent
import com.circuitqueest.app.data.content.MosfetsContent
import com.circuitqueest.app.data.content.OhmsLawContent
import com.circuitqueest.app.data.content.OpAmpsContent
import com.circuitqueest.app.data.content.PcbDesignContent
import com.circuitqueest.app.data.content.PowerElectronicsContent
import com.circuitqueest.app.data.content.PowerSystemsContent
import com.circuitqueest.app.data.content.RadarSystemsContent
import com.circuitqueest.app.data.content.RenewableEnergyContent
import com.circuitqueest.app.data.content.RfCircuitsContent
import com.circuitqueest.app.data.content.SemiconductorPhysicsContent
import com.circuitqueest.app.data.content.SensorsMeasurementContent
import com.circuitqueest.app.data.content.SeriesParallelContent
import com.circuitqueest.app.data.content.SignalIntegrityContent
import com.circuitqueest.app.data.content.SignalsSystemsContent
import com.circuitqueest.app.data.content.TheveninNortonContent
import com.circuitqueest.app.data.content.TransformersContent
import com.circuitqueest.app.data.content.TransistorsContent
import com.circuitqueest.app.data.content.TransmissionLinesContent
import com.circuitqueest.app.data.content.VlsiDesignContent
import com.circuitqueest.app.data.content.Topic
import com.circuitqueest.app.data.content.TopicCategories
import com.circuitqueest.app.data.content.TopicCategory
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

data class CategoryState(
    val category: TopicCategory,
    val topics: List<TopicState>
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
            CommunicationSystemsContent.topic,
            ElectromagneticsContent.topic,
            SemiconductorPhysicsContent.topic,
            DigitalSystemsContent.topic,
            ElectricMachinesContent.topic,
            PcbDesignContent.topic,
            EmbeddedSystemsContent.topic,
            PowerSystemsContent.topic,
            SensorsMeasurementContent.topic,
            AntennaDesignContent.topic,
            VlsiDesignContent.topic,
            SignalIntegrityContent.topic,
            BatteryStorageContent.topic,
            RfCircuitsContent.topic,
            AnalogCircuitsContent.topic,
            RenewableEnergyContent.topic,
            IotWirelessContent.topic,
            DspContent.topic,
            FiberOpticsContent.topic,
            ElectricVehiclesContent.topic,
            AudioElectronicsContent.topic,
            MemsContent.topic,
            RadarSystemsContent.topic,
            BiomedicalElectronicsContent.topic,
            MlHardwareContent.topic
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

    class Factory(private val repository: ProgressRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}
