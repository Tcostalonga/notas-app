package tarsila.costalonga.notasapp.bd

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notas_table")
data class Notas constructor(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "titulo") var titulo: String,
    @ColumnInfo(name = "anotacao") var anotacao: String,
    @ColumnInfo(name = "dt_criacao") val dtCriacao: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "dt_atualizado") var dtAtualizado: Long = dtCriacao,
    @ColumnInfo(name = "img_path") var imgPath: String? = "",
    @ColumnInfo(name = "finalizado") var finalizado: Boolean = false,
    @ColumnInfo(name = "ordem") var ordem: Int,
) : Parcelable
