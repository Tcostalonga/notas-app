package tarsila.costalonga.notasapp.ui.addnote

import android.content.SharedPreferences
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    val titleTextFieldState by mutableStateOf(TextFieldState())
    val descriptionTextFieldState by mutableStateOf(TextFieldState())

    private var isSketch = true

    private val _addNotaStatus = MutableLiveData<AddNotaStatus>()
    val addNotaStatus: LiveData<AddNotaStatus> = _addNotaStatus

    fun addNota() {
        val titleFormatted = titleTextFieldState.text.trim()
        val descriptionFormatted = descriptionTextFieldState.text.trim()

        when {
            (titleFormatted.isEmpty() || descriptionFormatted.isEmpty()) -> {
                _addNotaStatus.postValue(AddNotaStatus.Error)
            }

            else -> {
                viewModelScope.launch {
                    val lastItemId = repository.getLastItemId()
                    val newNota = Note(
                        title = titleFormatted.toString(),
                        description = descriptionFormatted.toString(),
                        sort = lastItemId.plus(1).toInt(),
                    )
                    insertNota(newNota)
                    clearSharedPreferences()
                    setSketchAsDisabled()
                    _addNotaStatus.postValue(AddNotaStatus.Success)
                }
            }
        }
    }

    private fun insertNota(nota: Note) {
        viewModelScope.launch {
            repository.insertNota(nota)
        }
    }

    fun addSketch() {
        if (isSketch && (titleTextFieldState.text.isNotEmpty() || descriptionTextFieldState.text.isNotEmpty())) {
            putSketch(titleTextFieldState.text.toString(), descriptionTextFieldState.text.toString())
        }
    }

    private fun putSketch(titulo: String, descricao: String) {
        sharedPreferences.edit {
            putString(SKETCH_TITLE, titulo)
            putString(SKETCH_DESCRIPTION, descricao)
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

    private fun setSketchAsDisabled() {
        isSketch = false
    }

    fun hideSketchAlert() {
        _showSketchAlert.update { false }
    }

    fun updateTitle() {
        titleTextFieldState.edit {
            this.replace(0, this.length, "")
        }
    }

    fun updateDescription() {
        descriptionTextFieldState.edit {
            this.replace(0, this.length, "")
        }
    }

    companion object {
        const val SKETCH_TITLE = "rascunho_titulo"
        const val SKETCH_DESCRIPTION = "rascunho_anotacao"
    }
}

sealed class AddNotaStatus {
    data object Success : AddNotaStatus()
    data object Error : AddNotaStatus()
}
