package tarsila.costalonga.notasapp.ui.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import tarsila.costalonga.notasapp.data.repository.ListOfAboutUpdates
import tarsila.costalonga.notasapp.ui.about.compose.AboutUiIntents
import tarsila.costalonga.notasapp.ui.about.compose.AboutUiState

class AboutViewModel : ViewModel() {

    private val _timelineEvents = MutableStateFlow(AboutUiState())
    val timelineEvents = _timelineEvents
        .onStart {
            loadTimelineEventsList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AboutUiState(),
        )

    private fun loadTimelineEventsList() {
        _timelineEvents.update { it.copy(timelineEvents = ListOfAboutUpdates.getAboutUpdatesList()) }
    }

    fun handleUiIntents(uiIntents: AboutUiIntents) {
        when (uiIntents) {
            is AboutUiIntents.OnExpandClick -> {
                val newList = _timelineEvents.value.timelineEvents.map { item ->
                    if (uiIntents.id == item.id) {
                        item.copy(isExpanded = !item.isExpanded)
                    } else {
                        item
                    }
                }

                _timelineEvents.update { it.copy(timelineEvents = newList) }
            }

            is AboutUiIntents.OnExpandAllClick -> {
                val expandedNewValue = !_timelineEvents.value.isAllTimelineExpanded
                val newList = _timelineEvents.value.timelineEvents.map { item ->
                    item.copy(isExpanded = expandedNewValue)
                }
                _timelineEvents.update { it.copy(timelineEvents = newList, isAllTimelineExpanded = expandedNewValue) }
            }
        }
    }
}
