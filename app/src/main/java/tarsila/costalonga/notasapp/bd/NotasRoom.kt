package tarsila.costalonga.notasapp.bd

import androidx.room.Database
import androidx.room.RoomDatabase

private lateinit var INSTANCE: NotasRoom

@Database(entities = [Notas::class], version = 2, exportSchema = false)
abstract class NotasRoom : RoomDatabase() {

    abstract val notasDao: NotasDao

}