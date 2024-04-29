package tarsila.costalonga.notasapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notas::class], version = 2, exportSchema = false)
abstract class NotasRoom : RoomDatabase() {
    abstract val notasDao: NotasDao
}
