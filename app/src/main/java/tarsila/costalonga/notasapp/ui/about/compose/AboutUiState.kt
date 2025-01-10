package tarsila.costalonga.notasapp.ui.about.compose

import tarsila.costalonga.notasapp.ui.utils.TimelineEvent

data class AboutUiState(
    val timelineEvents: List<TimelineEvent> = emptyList(),
    val isAllTimelineExpanded: Boolean = false,
)
