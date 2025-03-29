package tarsila.costalonga.notasapp.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import tarsila.costalonga.notasapp.DispatcherProvider
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.data.local.NoteDao

class NoteDataRepository @Inject constructor(private val dtDao: NoteDao) : NoteRepository {

    override suspend fun insertNota(nota: Note) {
        withContext(DispatcherProvider.io) {
            dtDao.insertNota(nota)
        }
    }

    override suspend fun updateNota(nota: Note) {
        withContext(DispatcherProvider.io) {
            dtDao.updateNota(nota)
        }
    }

    override suspend fun deleteUmaNota(nota: Note) {
        withContext(DispatcherProvider.io) {
            dtDao.deleteUmaNota(nota)
        }
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return dtDao.getAllNotes()
    }

    override fun getNoteById(id: Long): Flow<Note> = dtDao.getNoteById(id)

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
