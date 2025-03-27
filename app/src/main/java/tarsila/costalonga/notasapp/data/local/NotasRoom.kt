package tarsila.costalonga.notasapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Notas::class],
    version = 3,
    exportSchema = true,
)

abstract class NotasRoom : RoomDatabase() {
    abstract val notasDao: NotasDao

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL(
                    """
            CREATE TABLE notas_table_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                created_at INTEGER NOT NULL,
                updated_at INTEGER NOT NULL,
                is_finished INTEGER NOT NULL,
                sort INTEGER NOT NULL
            )
        """,
                )
                db.execSQL(
                    """
            INSERT INTO notas_table_new (id, title, description, created_at, updated_at, is_finished, sort)
            SELECT id, titulo, anotacao, dt_criacao, dt_atualizado, finalizado, ordem FROM notas_table
        """,
                )

                db.execSQL("DROP TABLE notas_table")

                db.execSQL("ALTER TABLE notas_table_new RENAME TO notas_table")
            }
        }
    }
}
