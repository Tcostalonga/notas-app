package tarsila.costalonga.notasapp.ui.detailnote

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.data.repository.NoteRepository

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val noteId: Long = savedStateHandle["noteId"] ?: 0L

    val title = TextFieldState()
    val description = TextFieldState()

    val noteDetail = repository.getNoteById(noteId)
        .onEach {
            title.edit {
                replace(0, this.length, it.title)
            }
            description.edit {
                replace(0, this.length, it.description)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            Note(0, "", "", sort = 0),
        )

    fun deleteNote() {
        viewModelScope.launch {
            repository.deleteNote(noteDetail.value)
        }
    }

    fun getFormattedData(field: Long): String {
        return SimpleDateFormat.getDateInstance(3).format(field)
    }

    private fun updateNote(nota: Note) {
        viewModelScope.launch {
            repository.updateNote(nota)
        }
    }

    fun updateNote() {
        val newNote = noteDetail.value.copy(
            title = title.text.toString(),
            description = description.text.toString(),
            updatedAt = System.currentTimeMillis(),
        )

        updateNote(newNote)
    }
}
