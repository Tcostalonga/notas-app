package tarsila.costalonga.notasapp.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.repository.NoteRepository

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState(0, 0, 0))
    val uiState = _uiState.asStateFlow()

    fun loadStatistics() {
        viewModelScope.launch {
            val deferredAllNotes = async { repository.getNotesCount() }
            val deferredAllDoneNotes = async { repository.getDoneNotes() }

            val allNotes = deferredAllNotes.await()
            val allDoneNotes = deferredAllDoneNotes.await()

            val allActiveNotes = allNotes.minus(allDoneNotes)

            _uiState.update {
                it.copy(allNotes = allNotes, allDoneNotes = allDoneNotes, allActiveNotes = allActiveNotes)
            }
        }
    }
}
