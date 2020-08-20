package tarsila.costalonga.notasapp.ui.estatisticasfragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio

class EstatisticasViewModel @ViewModelInject constructor(val repositorio: NotasRepositorio) :
    ViewModel() {

    val allNotas = repositorio.getAllNotas

    val totalCriadas = allNotas.value?.size

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


