package tarsila.costalonga.notasapp.data.repository

import kotlinx.coroutines.flow.Flow
import tarsila.costalonga.notasapp.data.local.Note

interface NoteRepository {
    suspend fun insertNota(nota: Note)
    suspend fun updateNota(nota: Note)
    suspend fun deleteUmaNota(nota: Note)
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(id: Long): Flow<Note>
    suspend fun getNotesCount(): Int
    suspend fun getDoneNotes(): Int
    suspend fun getActiveNotes(): Int
    suspend fun getLastItemId(): Long
}
