package tarsila.costalonga.notasapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNota(nota: Notas)

    @Update
    suspend fun updateNota(nota: Notas)

    @Delete
    suspend fun deleteUmaNota(nota: Notas)

    @Query("SELECT * from notas_table WHERE id = :key")
    fun getNoteById(key: Long): Flow<Notas>

    @Query("SELECT * FROM notas_table ORDER BY sort ASC")
    fun getTodasNotas(): Flow<List<Notas>>

    @Query("SELECT COUNT(id) FROM notas_table")
    suspend fun getNotesCount(): Int

    @Query("SELECT COUNT(id) FROM notas_table where is_finished is 1")
    suspend fun getDoneNotes(): Int

    @Query("SELECT COUNT(id) FROM notas_table where is_finished is 0")
    suspend fun getActiveNotes(): Int

    @Query("SELECT id FROM notas_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastItemId(): Long
}
