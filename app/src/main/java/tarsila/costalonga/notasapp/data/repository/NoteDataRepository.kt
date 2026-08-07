package tarsila.costalonga.notasapp.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.data.local.NoteDao

class NoteDataRepository @Inject constructor(private val dtDao: NoteDao) : NoteRepository {

    override suspend fun insertNote(nota: Note) {
        dtDao.insertNote(nota)
    }

    override suspend fun updateNote(nota: Note) {
        dtDao.updateNote(nota)
    }

    override suspend fun deleteNote(nota: Note) {
        dtDao.deleteNote(nota)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return dtDao.getAllNotes()
    }

    override fun getNoteById(id: Long): Flow<Note> = dtDao.getNoteById(id)

    override suspend fun getNotesCount(): Int = dtDao.getNotesCount()

    override suspend fun getDoneNotes(): Int {
        return dtDao.getDoneNotes()
    }

    override suspend fun getActiveNotes(): Int = dtDao.getActiveNotes()

    override suspend fun getLastItemId(): Long = dtDao.getLastItemId()
}
