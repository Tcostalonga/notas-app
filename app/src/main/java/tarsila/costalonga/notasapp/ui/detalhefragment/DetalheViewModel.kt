package tarsila.costalonga.notasapp.ui.detalhefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class DetalheViewModel @Inject constructor(val repositorio: NotasRepositorio) :
    ViewModel() {

    fun updateNota(nota: Notas) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.updateNota(nota)
        }
    }

    fun removerNota(nota: Notas) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.deleteUmaNota(nota)
        }
    }
}
