package tarsila.costalonga.notasapp.ui.about.compose

import tarsila.costalonga.notasapp.data.repository.TimelineEvent

data class AboutUiState(
    val timelineEvents: List<TimelineEvent> = emptyList(),
    val isAllTimelineExpanded: Boolean = false,
)
