package tarsila.costalonga.notasapp.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.repository.NoteRepository

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState(0, 0, 0))
    val uiState = _uiState
        .onStart {
            loadStatistics()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            StatisticsUiState(0, 0, 0),
        )

    private fun loadStatistics() {
        viewModelScope.launch {
            try {
                val deferredAllNotes = async { repository.getNotesCount() }
                val deferredAllDoneNotes = async { repository.getDoneNotes() }

                val allNotes = deferredAllNotes.await()
                val allDoneNotes = deferredAllDoneNotes.await()

                _uiState.update {
                    it.copy(
                        allNotes = allNotes,
                        allDoneNotes = allDoneNotes,
                        allActiveNotes = allNotes - allDoneNotes,
                    )
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
            }
        }
    }
}
