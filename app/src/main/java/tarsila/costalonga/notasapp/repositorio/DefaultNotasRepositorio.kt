package tarsila.costalonga.notasapp.repositorio

import androidx.lifecycle.LiveData
import tarsila.costalonga.notasapp.bd.Notas

interface DefaultNotasRepositorio {

    fun insertNota(nota: Notas)

    fun updateNota(nota: Notas)

    fun deleteUmaNota(nota: Notas)

    suspend fun getTodasNotas(): List<Notas>

    suspend fun itemMaiorOrdem(): Int

    fun getAlarmsOnSuspend(): LiveData<List<Notas>>

}