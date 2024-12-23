package tarsila.costalonga.notasapp.ui.about

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.utils.TimelineEvent

class AboutViewModel : ViewModel() {

    private val _timelineEvents = MutableStateFlow<List<TimelineEvent>>(emptyList())
    val timelineEvents = _timelineEvents
        .onStart {
            loadTimelineEventsList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList(),
        )

    private fun loadTimelineEventsList() {

        val list = mutableListOf<TimelineEvent>()

        list.add(TimelineEvent(1, R.string.about_aug2020, R.string.about_aug2020_done))
        list.add(TimelineEvent(2, R.string.about_sept2020, R.string.about_sept2020_done))
        list.add(TimelineEvent(3, R.string.about_oct2020, R.string.about_oct2020_done))
        list.add(TimelineEvent(4, R.string.about_jul2022, R.string.about_jul2022_done))
        list.add(TimelineEvent(5, R.string.about_aug2022, R.string.about_aug2022_done))
        list.add(TimelineEvent(6, R.string.about_oct2022, R.string.about_oct2022_done))
        list.add(TimelineEvent(7, R.string.about_dec2022, R.string.about_dec2022_done))
        list.add(TimelineEvent(8, R.string.about_jun2023, R.string.about_jun2023_done))
        list.add(TimelineEvent(9, R.string.about_mar2024, R.string.about_mar2024_done))
        list.add(TimelineEvent(10, R.string.about_apr2024, R.string.about_apr2024_done))
        list.add(TimelineEvent(11, R.string.about_aug2024, R.string.about_aug2024_done))
        list.add(TimelineEvent(12, R.string.about_dec2024, R.string.about_dec2024_done))
        _timelineEvents.update { list }
    }
}
