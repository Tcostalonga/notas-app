package tarsila.costalonga.notasapp.ui.addfragment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(val repositorio: NotasRepositorio) : ViewModel() {

    fun insertNota(nota: Notas) {
        repositorio.insertNota(nota)
    }
}
