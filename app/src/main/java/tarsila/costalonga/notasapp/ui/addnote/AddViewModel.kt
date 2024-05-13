package tarsila.costalonga.notasapp.ui.addnote

import android.content.SharedPreferences
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.repository.NoteDataRepository

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: NoteDataRepository,
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
        when {
            (titleTextFieldState.text.trim().isEmpty() || descriptionTextFieldState.text.trim().isEmpty()) -> {
                _addNotaStatus.postValue(AddNotaStatus.Error)
            }

            else -> {
                //TODO: Colocar valor correto de ordenação
                val newNota = Notas(
                    titulo = titleTextFieldState.text.toString(),
                    anotacao = descriptionTextFieldState.text.toString(),
                    ordem = 1,
                )
                insertNota(newNota)
                clearSharedPreferences()
                setIsSketchDisabled()
                _addNotaStatus.postValue(AddNotaStatus.Success)
            }
        }
    }

    private fun insertNota(nota: Notas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNota(nota)
        }
    }

    fun addSketch() {
        if (isSketch && (titleTextFieldState.text.isNotEmpty() || descriptionTextFieldState.text.isNotEmpty())) {
            putSketch(titleTextFieldState.text.toString(), descriptionTextFieldState.text.toString())
        }
    }

    private fun putSketch(
        titulo: String,
        descricao: String,
    ) {
        sharedPreferences.edit().apply {
            putString(SKETCH_TITLE, titulo)
            putString(SKETCH_DESCRIPTION, descricao)
        }.apply()
    }

    fun getSavedSketches() {
        val title = sharedPreferences.getString(SKETCH_TITLE, "") ?: ""
        val description = sharedPreferences.getString(SKETCH_DESCRIPTION, "") ?: ""

        titleTextFieldState.edit {
            this.append(title)
        }

        if (title.isNotEmpty() || description.isNotEmpty()) {
            _showSketchAlert.update { true }
        }
    }

    fun clearSharedPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    private fun setIsSketchDisabled() {
        isSketch = false
    }

    fun hideSketchAlert() {
        _showSketchAlert.update { false }
    }

    fun updateTitle(title: String = "") {
        titleTextFieldState.edit {
            this.append(title)
        }
    }

    fun updateDescription(description: String = "") {
        descriptionTextFieldState.edit {
            this.append(description)
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
