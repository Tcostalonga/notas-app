package tarsila.costalonga.notasapp.data.repository

import tarsila.costalonga.notasapp.data.local.Notas

interface DefaultNotasRepository {
    fun insertNota(nota: Notas)

    fun updateNota(nota: Notas)

    fun deleteUmaNota(nota: Notas)

    suspend fun getTodasNotas(): List<Notas>
}
