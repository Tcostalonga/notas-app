package tarsila.costalonga.notasapp.ui.mainfragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio

class MainViewModel @ViewModelInject constructor(val repositorio: NotasRepositorio) : ViewModel() {

    val allNotas = MutableLiveData<List<Notas>>()

    private var uiScope = CoroutineScope(Dispatchers.Main)

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