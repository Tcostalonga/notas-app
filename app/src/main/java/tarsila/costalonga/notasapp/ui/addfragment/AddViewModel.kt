package tarsila.costalonga.notasapp.ui.addfragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio


class AddViewModel @ViewModelInject constructor(val repositorio: NotasRepositorio) : ViewModel() {

    fun insertNota(nota: Notas) {
        repositorio.insertNota(nota)
    }
}