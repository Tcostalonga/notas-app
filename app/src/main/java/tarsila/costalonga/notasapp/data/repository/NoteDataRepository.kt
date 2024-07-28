package tarsila.costalonga.notasapp.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
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

    override fun getTodasNotas(): Flow<List<Notas>> {
        return dtDao.getTodasNotas()
    }

    override suspend fun getNoteById(id: Long): Notas = dtDao.getNoteById(id)

    override fun getNotesCount() = dtDao.getNotesCount()

    override fun getDoneNotes() = dtDao.getDoneNotes()

    override fun getActiveNotes() = dtDao.getActiveNotes()

    override suspend fun getLastItemId(): Long = dtDao.getLastItemId()
}
