package tarsila.costalonga.notasapp.bd

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNota(nota: Notas)

    @Update
    fun updateNota(nota: Notas)

    @Delete
    fun deleteUmaNota(nota: Notas)

    @Query("SELECT * from notas_table WHERE id = :key")
    fun getUmaNota(key: Long): Notas

    @Query("SELECT * FROM notas_table ORDER BY ordem ASC")
    fun getAllNotas(): LiveData<List<Notas>>

    @Query("SELECT COUNT(*) FROM notas_table")
    fun numTotalNotas(): LiveData<Int>

    @Query("SELECT * FROM notas_table ORDER BY ordem ASC")
    fun getTodasNotas(): List<Notas>
}
