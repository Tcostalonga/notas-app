package tarsila.costalonga.notasapp.ui.mainfragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio


class MainViewModel @ViewModelInject constructor(val repositorio: NotasRepositorio) : ViewModel() {

    val allNotas = repositorio.getAllNotas

    // Acionar fab_add
    private var _fabAdd = MutableLiveData<Int>()
    val fabAdd: LiveData<Int>
        get() = _fabAdd

    fun checkboxStatus(objNota: Notas, checkStatus: Boolean) {
        objNota.finalizado = checkStatus
        repositorio.updateNota(objNota)
    }
}
