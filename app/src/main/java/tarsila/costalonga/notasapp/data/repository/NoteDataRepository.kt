package tarsila.costalonga.notasapp.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import tarsila.costalonga.notasapp.DispatcherProvider
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.local.NotasDao

class NoteDataRepository @Inject constructor(private val dtDao: NotasDao) : NoteRepository {

    override suspend fun insertNota(nota: Notas) {
        withContext(DispatcherProvider.io) {
            dtDao.insertNota(nota)
        }
    }

    override suspend fun updateNota(nota: Notas) {
        withContext(DispatcherProvider.io) {
            dtDao.updateNota(nota)
        }
    }

    override suspend fun deleteUmaNota(nota: Notas) {
        withContext(DispatcherProvider.io) {
            dtDao.deleteUmaNota(nota)
        }
    }

    override fun getTodasNotas(): Flow<List<Notas>> {
        return dtDao.getTodasNotas()
    }

    override fun getNoteById(id: Long): Flow<Notas> = dtDao.getNoteById(id)

    override suspend fun getNotesCount(): Int {
        return withContext(DispatcherProvider.io) {
            dtDao.getNotesCount()
        }
    }

    override suspend fun getDoneNotes(): Int {
        return withContext(DispatcherProvider.io) {
            dtDao.getDoneNotes()
        }
    }

    override suspend fun getActiveNotes(): Int {
        return withContext(DispatcherProvider.io) {
            dtDao.getActiveNotes()
        }
    }

    override suspend fun getLastItemId(): Long {
        return withContext(DispatcherProvider.io) {
            dtDao.getLastItemId()
        }
    }
}
