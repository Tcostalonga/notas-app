package tarsila.costalonga.notasapp.ui.estatisticasfragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio

class EstatisticasViewModel @ViewModelInject constructor(val repositorio: NotasRepositorio) :
    ViewModel() {

     private val allNotas = MutableLiveData<List<Notas>>()

    fun carregarNotas() {
        viewModelScope.launch {
            allNotas.value = repositorio.getTodasNotas()
        }
    }

    val totalCriadas = Transformations.map(allNotas){
        it.count()
    }

    val notasAtivas = Transformations.map(allNotas) {
        it.count { nota ->
            nota.isCompleted
        }
    }

    val notasFinalizadas = Transformations.map(allNotas) {
        it.count { nota ->
            !nota.isCompleted
        }
    }
}


