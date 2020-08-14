package tarsila.costalonga.notasapp.bd

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNota(nota: Notas)

    @Update
    suspend fun updateNota(nota: Notas)

    @Delete
    suspend fun deleteUmaNota(nota: Notas)

    @Query("SELECT * from notas_table WHERE id = :key")
    fun getUmaNota(key: Long): Notas

    @Query("SELECT * FROM notas_table ORDER BY dt_criacao DESC")
    fun getAllNotas(): LiveData<List<Notas>>

    @Query("SELECT COUNT(*) FROM notas_table")
    fun numTotalNotas(): LiveData<Int>

}