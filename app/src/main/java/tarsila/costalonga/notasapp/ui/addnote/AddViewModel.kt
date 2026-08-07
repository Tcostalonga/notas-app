package tarsila.costalonga.notasapp.ui.addnote

import android.content.SharedPreferences
import androidx.compose.foundation.text.input.TextFieldState
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.data.repository.NoteRepository

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {
    private val _showSketchAlert = MutableStateFlow(false)
    val showSketchAlert: StateFlow<Boolean> = _showSketchAlert

    val titleTextFieldState = TextFieldState()
    val descriptionTextFieldState = TextFieldState()

    private var isSketch = true

    private val _addNoteEvents = Channel<AddNoteEvents>()
    val addNoteEvents = _addNoteEvents.receiveAsFlow()

    fun addNote() {
        val titleFormatted = titleTextFieldState.text.trim()
        val descriptionFormatted = descriptionTextFieldState.text.trim()

        if (titleFormatted.isEmpty() || descriptionFormatted.isEmpty()) {
            _addNoteEvents.trySend(AddNoteEvents.UnableToCreateNote)
            return
        }

        viewModelScope.launch {
            val lastItemId = repository.getLastItemId()
            val newNota = Note(
                title = titleFormatted.toString(),
                description = descriptionFormatted.toString(),
                sort = lastItemId.plus(1).toInt(),
            )
            insertNote(newNota)
            clearSharedPreferences()
            setSketchAsDisabled()
            _addNoteEvents.trySend(AddNoteEvents.NoteSuccessfullyCreated)
        }
    }

    fun addSketch() {
        if (isSketch && (titleTextFieldState.text.isNotEmpty() || descriptionTextFieldState.text.isNotEmpty())) {
            putSketch(titleTextFieldState.text.toString(), descriptionTextFieldState.text.toString())
        }
    }

    fun getSavedSketches() {
        val title = sharedPreferences.getString(SKETCH_TITLE, "") ?: ""
        val description = sharedPreferences.getString(SKETCH_DESCRIPTION, "") ?: ""

        titleTextFieldState.edit {
            this.append(title)
        }

        descriptionTextFieldState.edit {
            this.append(description)
        }

        if (title.isNotEmpty() || description.isNotEmpty()) {
            _showSketchAlert.update { true }
        }
    }

    fun clearSharedPreferences() {
        sharedPreferences.edit { clear() }
    }

    fun clearTextFields() {
        titleTextFieldState.edit {
            this.replace(0, this.length, "")
        }
        descriptionTextFieldState.edit {
            this.replace(0, this.length, "")
        }
    }

    fun hideSketchAlert() {
        _showSketchAlert.update { false }
    }

    private suspend fun insertNote(nota: Note) {
        repository.insertNote(nota)
    }

    private fun putSketch(titulo: String, descricao: String) {
        sharedPreferences.edit {
            putString(SKETCH_TITLE, titulo)
            putString(SKETCH_DESCRIPTION, descricao)
        }
    }

    private fun setSketchAsDisabled() {
        isSketch = false
    }

    companion object {
        const val SKETCH_TITLE = "rascunho_titulo"
        const val SKETCH_DESCRIPTION = "rascunho_anotacao"
    }
}

sealed interface AddNoteEvents {
    data object NoteSuccessfullyCreated : AddNoteEvents
    data object UnableToCreateNote : AddNoteEvents
}
