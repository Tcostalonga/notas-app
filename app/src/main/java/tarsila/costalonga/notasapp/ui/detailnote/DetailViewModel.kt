package tarsila.costalonga.notasapp.ui.detailnote

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.repository.NoteDataRepository

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: NoteDataRepository) : ViewModel() {
    val noteDetail = MutableStateFlow(Notas(titulo = "", anotacao = "", ordem = 0))

    val title by mutableStateOf(TextFieldState())
    val description by mutableStateOf(TextFieldState())

    fun setNoteDetail(noteId: Long) {
        viewModelScope.launch {
            noteDetail.value = repository.getNoteById(noteId)
            title.edit {
                replace(0, this.length, noteDetail.value.titulo)
            }
            description.edit {
                replace(0, this.length, noteDetail.value.anotacao)
            }
        }
    }

    private fun updateNota(nota: Notas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNota(nota)
        }
    }

    fun removerNota() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUmaNota(noteDetail.value)
        }
    }

    fun getFormattedData(field: Long): String {
        return SimpleDateFormat.getDateInstance(3).format(field)
    }

    fun updateNote() {
        /*                            makeToast(requireContext(), getString(R.string.nota_update))
                            findNavController().popBackStack()*/
        val obj = noteDetail.value
        obj.titulo = title.text.toString()
        obj.anotacao = description.text.toString()
        obj.dtAtualizado = System.currentTimeMillis()
        updateNota(obj)
    }
}
