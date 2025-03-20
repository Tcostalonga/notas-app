package tarsila.costalonga.notasapp.ui.detailnote

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.DispatcherProvider
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.repository.NoteDataRepository

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: NoteDataRepository) : ViewModel() {
    private val _noteDetail = MutableStateFlow(Notas(titulo = "", anotacao = "", ordem = 0))
    val noteDetail = _noteDetail.asStateFlow()

    val title by mutableStateOf(TextFieldState())
    val description by mutableStateOf(TextFieldState())

    fun setNoteDetail(noteId: Long) {
        viewModelScope.launch(DispatcherProvider.io) {
            repository.getNoteById(noteId)
                .collect { note ->
                    _noteDetail.update {
                        it.copy(
                            id = note.id,
                            titulo = note.titulo,
                            anotacao = note.anotacao,
                            dtCriacao = note.dtCriacao,
                            dtAtualizado = note.dtAtualizado,
                            imgPath = null,
                            finalizado = note.finalizado,
                            ordem = note.ordem,
                        )
                    }
                    title.edit {
                        replace(0, this.length, noteDetail.value.titulo)
                    }
                    description.edit {
                        replace(0, this.length, noteDetail.value.anotacao)
                    }
                }
        }
    }

    private fun updateNota(nota: Notas) {
        viewModelScope.launch {
            repository.updateNota(nota)
        }
    }

    fun removerNota() {
        viewModelScope.launch {
            repository.deleteUmaNota(noteDetail.value)
        }
    }

    fun getFormattedData(field: Long): String {
        return SimpleDateFormat.getDateInstance(3).format(field)
    }

    fun updateNote() {
        _noteDetail.update {
            it.copy(
                titulo = title.text.toString(),
                anotacao = description.text.toString(),
                dtAtualizado = System.currentTimeMillis(),
            )
        }
        updateNota(_noteDetail.value)
    }
}
