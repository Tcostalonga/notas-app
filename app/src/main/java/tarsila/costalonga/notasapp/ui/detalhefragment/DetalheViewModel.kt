package tarsila.costalonga.notasapp.ui.detalhefragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio

class DetalheViewModel @ViewModelInject constructor(val repositorio: NotasRepositorio) :
    ViewModel() {

    fun removerNota(nota: Notas) {
        repositorio.deleteUmaNota(nota)
    }

}