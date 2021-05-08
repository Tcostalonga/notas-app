package tarsila.costalonga.notasapp.bd

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNota(nota: Notas)

    @Update
    suspend fun updateNota(nota: Notas)

    @Delete
    suspend fun deleteUmaNota(nota: Notas)

    @Query("SELECT * from notas_table WHERE id = :key")
    fun getUmaNota(key: Long): Notas

   @Query("SELECT * FROM notas_table ORDER BY ordem ASC")
    fun getAllNotas(): LiveData<List<Notas>>

    @Query("SELECT COUNT(*) FROM notas_table")
    fun numTotalNotas(): LiveData<Int>

    @Query("SELECT * FROM notas_table ORDER BY ordem ASC")
    @Transaction
    suspend fun getTodasNotas(): List<Notas>

    @Query("SELECT MAX(ordem) FROM notas_table")
    suspend fun itemMaiorOrdem(): Int

    @Query("SELECT * FROM notas_table WHERE dt_criacao != alarmClock")
     suspend fun getAlarmsOnBD(): List<Notas>

}