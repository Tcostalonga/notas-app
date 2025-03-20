package tarsila.costalonga.notasapp.data.repository

import kotlinx.coroutines.flow.Flow
import tarsila.costalonga.notasapp.data.local.Notas

interface NoteRepository {
    suspend fun insertNota(nota: Notas)
    suspend fun updateNota(nota: Notas)
    suspend fun deleteUmaNota(nota: Notas)
    fun getTodasNotas(): Flow<List<Notas>>
    fun getNoteById(id: Long): Flow<Notas>
    suspend fun getNotesCount(): Int
    suspend fun getDoneNotes(): Int
    suspend fun getActiveNotes(): Int
    suspend fun getLastItemId(): Long
}
