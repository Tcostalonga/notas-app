package tarsila.costalonga.notasapp.ui.estatisticasfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class EstatisticasViewModel @Inject constructor(val repositorio: NotasRepositorio) :
    ViewModel() {

    private var uiScope = CoroutineScope(Dispatchers.Main)

    val allNotas = MutableLiveData<List<Notas>>()

    fun carregarNotas() {
        uiScope.launch {
            allNotas.value = repositorio.getTodasNotas()
        }
    }

    val totalCriadas = Transformations.map(allNotas) {
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

    // val totalCriadas = notasAtivas.value.plus(notasFinalizadas.value)
}
