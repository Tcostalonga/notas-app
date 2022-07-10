package tarsila.costalonga.notasapp.ui.detalhefragment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class DetalheViewModel @Inject constructor(val repositorio: NotasRepositorio) :
    ViewModel() {

    fun updateNota(nota: Notas) {
        repositorio.updateNota(nota)
    }

    fun removerNota(nota: Notas) {
        repositorio.deleteUmaNota(nota)
    }
}
