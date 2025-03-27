package tarsila.costalonga.notasapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNota(nota: Note)

    @Update
    suspend fun updateNota(nota: Note)

    @Delete
    suspend fun deleteUmaNota(nota: Note)

    @Query("SELECT * from notas_table WHERE id = :key")
    fun getNoteById(key: Long): Flow<Note>

    @Query("SELECT * FROM notas_table ORDER BY sort ASC")
    fun getTodasNotas(): Flow<List<Note>>

    @Query("SELECT COUNT(id) FROM notas_table")
    suspend fun getNotesCount(): Int

    @Query("SELECT COUNT(id) FROM notas_table where is_finished is 1")
    suspend fun getDoneNotes(): Int

    @Query("SELECT COUNT(id) FROM notas_table where is_finished is 0")
    suspend fun getActiveNotes(): Int

    @Query("SELECT id FROM notas_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastItemId(): Long
}
