package tarsila.costalonga.notasapp.repositorio

import androidx.lifecycle.MutableLiveData
import tarsila.costalonga.notasapp.bd.Notas

class FakeNotasRepositorio : DefaultNotasRepositorio {

    private val notasItems = mutableListOf<Notas>()
      val observableNotasItems = MutableLiveData<List<Notas>>(notasItems)

      fun refreshLiveData() {
        observableNotasItems.postValue(notasItems)
    }

    override fun insertNota(nota: Notas) {
        notasItems.add(nota)
        refreshLiveData()
    }

    override fun updateNota(nota: Notas) {
        notasItems.add(nota)
        refreshLiveData()
    }

    override fun deleteUmaNota(nota: Notas) {
        notasItems.remove(nota)
        refreshLiveData()
    }

    override suspend fun getTodasNotas(): List<Notas> {
        return notasItems
    }
}