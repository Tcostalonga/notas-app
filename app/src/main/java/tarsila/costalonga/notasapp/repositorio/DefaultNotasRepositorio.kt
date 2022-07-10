package tarsila.costalonga.notasapp.repositorio

import tarsila.costalonga.notasapp.bd.Notas

interface DefaultNotasRepositorio {

    fun insertNota(nota: Notas)

    fun updateNota(nota: Notas)

    fun deleteUmaNota(nota: Notas)

    suspend fun getTodasNotas(): List<Notas>
}
