package tarsila.costalonga.notasapp.repositorio

import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.bd.NotasDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotasRepositorio @Inject constructor(val dtDao: NotasDao) : DefaultNotasRepositorio {

    override fun insertNota(nota: Notas) {
        dtDao.insertNota(nota)
    }

    override fun updateNota(nota: Notas) {
        dtDao.updateNota(nota)
    }

    override fun deleteUmaNota(nota: Notas) {
        dtDao.deleteUmaNota(nota)
    }

    override suspend fun getTodasNotas(): List<Notas> {
        return dtDao.getTodasNotas()
    }
}
