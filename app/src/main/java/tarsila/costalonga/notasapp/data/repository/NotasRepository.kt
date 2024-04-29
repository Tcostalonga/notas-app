package tarsila.costalonga.notasapp.data.repository

import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.local.NotasDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotasRepository
    @Inject
    constructor(val dtDao: NotasDao) : DefaultNotasRepository {
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