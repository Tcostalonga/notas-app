package tarsila.costalonga.notasapp.ui.detailnote

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
    val notaDetalhe = MutableStateFlow(Notas(titulo = "", anotacao = "", ordem = 0))

    private fun updateNota(nota: Notas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNota(nota)
        }
    }

    fun removerNota(nota: Notas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUmaNota(nota)
        }
    }

    fun setNotaDetalhe(arguments: Notas) {
        notaDetalhe.value = arguments
    }

    fun getFormattedData(field: Long): String {
        return SimpleDateFormat.getDateInstance(3).format(field)
    }

    fun onEditNota(
        titulo: String,
        anotacao: String,
    ) {
        val obj = notaDetalhe.value
        obj.titulo = titulo
        obj.anotacao = anotacao
        obj.dtAtualizado = System.currentTimeMillis()
        updateNota(obj)
    }
}
