package tarsila.costalonga.notasapp.data.repository

import androidx.annotation.StringRes

data class TimelineEvent(
    val id: Int,
    @StringRes val date: Int,
    @StringRes val description: Int,
    val isExpanded: Boolean = false,
)
