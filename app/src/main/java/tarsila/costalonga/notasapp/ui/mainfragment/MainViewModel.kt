package tarsila.costalonga.notasapp.ui.mainfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repositorio: NotasRepositorio) : ViewModel() {

    val allNotas = MutableStateFlow<List<Notas>>(emptyList())

    var isSearchEnabled = MutableStateFlow(false)

    fun checkboxStatus(objNota: Notas, checkStatus: Boolean) {
        objNota.finalizado = checkStatus
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.updateNota(objNota)
        }
    }

    fun carregarNotas() {
        viewModelScope.launch(Dispatchers.IO) {
            allNotas.value = repositorio.getTodasNotas()
        }
    }

    private fun updateNota(nota: Notas) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.updateNota(nota)
        }
    }

    fun ordenarRecyclerView(lista: List<Notas>) {
        lista.forEachIndexed { index, nota ->
            nota.ordem = index
            updateNota(nota)
        }
    }
}
