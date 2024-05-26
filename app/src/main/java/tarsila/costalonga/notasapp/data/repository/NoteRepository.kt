package tarsila.costalonga.notasapp.data.repository

import tarsila.costalonga.notasapp.data.local.Notas

interface NoteRepository {
    fun insertNota(nota: Notas)
    fun updateNota(nota: Notas)
    fun deleteUmaNota(nota: Notas)
    suspend fun getTodasNotas(): List<Notas>
    suspend fun getNoteById(id: Long): Notas
    fun getNotesCount(): Int
    fun getDoneNotes(): Int
    fun getActiveNotes(): Int
    suspend fun getLastItemId(): Long
}
