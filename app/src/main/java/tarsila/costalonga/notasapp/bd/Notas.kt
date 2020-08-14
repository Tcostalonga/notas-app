package tarsila.costalonga.notasapp.bd

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notas_table")
data class Notas (
    @PrimaryKey (autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "titulo") var titulo: String,
    @ColumnInfo(name = "anotacao") var anotacao: String,
    @ColumnInfo(name = "dt_criacao") val dt_criacao: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "dt_atualizado") var dt_atualizado: Long = dt_criacao,
    @ColumnInfo(name = "img_path") var img_path: String? = "",
    @ColumnInfo(name = "finalizado") var finalizado: Boolean = false
) : Parcelable {

}