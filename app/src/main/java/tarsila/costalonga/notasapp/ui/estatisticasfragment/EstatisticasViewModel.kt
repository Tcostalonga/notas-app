package tarsila.costalonga.notasapp.ui.estatisticasfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class EstatisticasViewModel @Inject constructor(val repositorio: NotasRepositorio) :
    ViewModel() {

    private val allNotas = mutableListOf<Notas>()

    internal fun carregarNotas() {
        viewModelScope.launch(Dispatchers.IO) {
            allNotas.addAll(repositorio.getTodasNotas())
        }
    }

    internal fun totalCriadas() = allNotas.count().toString()

    internal fun notasAtivas() = allNotas.count { !it.finalizado }.toString()

    internal fun notasFinalizadas() = allNotas.count { it.finalizado }.toString()
}
