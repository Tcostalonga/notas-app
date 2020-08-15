package tarsila.costalonga.notasapp.ui.addfragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio


class AddViewModel @ViewModelInject constructor(val repositorio: NotasRepositorio) : ViewModel() {


    val numTotalNotas = repositorio.numTotalNotas


    fun addNota(titulo: String, anotacao: String): Boolean {

        return if (titulo.isNotEmpty() && anotacao.isNotEmpty()) {
            val objNota = Notas(
                titulo = titulo,
                anotacao = anotacao)
            insertNota(objNota)
            true
        } else false
    }


    fun insertNota(nota: Notas) {
        repositorio.insertNota(nota)
    }

    fun updateNota(nota: Notas) {
        repositorio.updateNota(nota)
    }

    fun deleteUmaNota(nota: Notas) {
        repositorio.deleteUmaNota(nota)
    }


}