package tarsila.costalonga.notasapp.repositorio

import tarsila.costalonga.notasapp.bd.Notas

interface DefaultNotasRepositorio {

   suspend fun insertNota(nota: Notas)

    fun updateNota(nota: Notas)

    suspend fun deleteUmaNota(nota: Notas)

    suspend fun getTodasNotas(): List<Notas>

    suspend fun itemMaiorOrdem(): Int

    suspend fun getAlarmsReschedule() : List<Notas>

}