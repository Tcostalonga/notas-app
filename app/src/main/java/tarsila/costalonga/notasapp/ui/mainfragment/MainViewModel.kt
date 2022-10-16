package tarsila.costalonga.notasapp.ui.mainfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repositorio: NotasRepositorio) : ViewModel() {

    private val _allNotas = MutableLiveData<List<Notas>>()
    val allNotas: LiveData<List<Notas>> = _allNotas

    fun checkboxStatus(objNota: Notas, checkStatus: Boolean) {
        objNota.finalizado = checkStatus
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.updateNota(objNota)
        }
    }

    fun carregarNotas() {
        viewModelScope.launch(Dispatchers.IO) {
            _allNotas.postValue(repositorio.getTodasNotas())
        }
    }

    fun updateNota(nota: Notas) {
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
