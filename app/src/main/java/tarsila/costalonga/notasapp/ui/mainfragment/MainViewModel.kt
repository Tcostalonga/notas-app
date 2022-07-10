package tarsila.costalonga.notasapp.ui.mainfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repositorio: NotasRepositorio) : ViewModel() {

    val allNotas = MutableLiveData<List<Notas>>()

    private var uiScope = CoroutineScope(Dispatchers.Main)

    // Acionar fab_add
    private var _fabAdd = MutableLiveData<Int>()
    val fabAdd: LiveData<Int>
        get() = _fabAdd

    fun checkboxStatus(objNota: Notas, checkStatus: Boolean) {
        objNota.finalizado = checkStatus
        repositorio.updateNota(objNota)
    }

    fun carregarNotas() {
        uiScope.launch {
            allNotas.value = repositorio.getTodasNotas()
        }
    }

    fun updateNota(nota: Notas) {
        repositorio.updateNota(nota)
    }

    fun ordenarRecyclerView(lista: List<Notas>) {

        lista.forEachIndexed { index, nota ->
            nota.ordem = index
            updateNota(nota)
        }
    }
}
