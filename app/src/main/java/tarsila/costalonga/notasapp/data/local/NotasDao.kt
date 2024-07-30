package tarsila.costalonga.notasapp.data.local

import androidx.lifecycle.LiveData
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
    fun insertNota(nota: Notas)

    @Update
    fun updateNota(nota: Notas)

    @Delete
    fun deleteUmaNota(nota: Notas)

    @Query("SELECT * from notas_table WHERE id = :key")
    suspend fun getNoteById(key: Long): Notas

    @Query("SELECT COUNT(*) FROM notas_table")
    fun numTotalNotas(): LiveData<Int>

    @Query("SELECT * FROM notas_table ORDER BY ordem ASC")
    fun getTodasNotas(): Flow<List<Notas>>

    @Query("SELECT COUNT(id) FROM notas_table")
    fun getNotesCount(): Int

    @Query("SELECT COUNT(id) FROM notas_table where finalizado is 1")
    fun getDoneNotes(): Int

    @Query("SELECT COUNT(id) FROM notas_table where finalizado is 0")
    fun getActiveNotes(): Int

    @Query("SELECT id FROM notas_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastItemId(): Long
}
