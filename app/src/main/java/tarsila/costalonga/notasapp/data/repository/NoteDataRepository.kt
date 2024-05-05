package tarsila.costalonga.notasapp.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.local.NotasDao

@Singleton
class NoteDataRepository @Inject constructor(private val dtDao: NotasDao) : NoteRepository {
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

    override fun getNotesCount() = dtDao.getNotesCount()

    override fun getDoneNotes() = dtDao.getDoneNotes()

    override fun getActiveNotes() = dtDao.getActiveNotes()
}
