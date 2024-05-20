package tarsila.costalonga.notasapp.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.repository.NoteDataRepository

@HiltViewModel
internal class StatisticsViewModel @Inject constructor(private val repository: NoteDataRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState(0, 0, 0))
    val uiState = _uiState.asStateFlow()

    internal fun loadStatistics() {
        viewModelScope.launch(Dispatchers.IO) {
            val allNotes = async { repository.getNotesCount() }.await()
            val allDoneNotes = async { repository.getDoneNotes() }.await()
            val allActiveNotes = allNotes.minus(allDoneNotes)
            _uiState.update {
                it.copy(allNotes = allNotes, allDoneNotes = allDoneNotes, allActiveNotes = allActiveNotes)
            }
        }
    }
}
