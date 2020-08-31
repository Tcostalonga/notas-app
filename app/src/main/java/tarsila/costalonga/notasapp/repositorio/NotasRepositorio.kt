package tarsila.costalonga.notasapp.repositorio

import kotlinx.coroutines.*
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.bd.NotasDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotasRepositorio @Inject constructor(val dtDao: NotasDao) {

    //Retorna todas as notas no bd
    val getAllNotas = dtDao.getAllNotas()

    //Retorna a qnt de notas no bd
    val numTotalNotas = dtDao.numTotalNotas()

    private var job = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + job)

    fun insertNota(nota: Notas) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dtDao.insertNota(nota)
            }
        }
    }

    fun updateNota(nota: Notas) {
        uiScope.launch {
            dtDao.updateNota(nota)
        }
    }

    fun deleteUmaNota(nota: Notas) {
        uiScope.launch {
            dtDao.deleteUmaNota(nota)
        }
    }

    suspend fun getAllNotas(): List<Notas> {
        return dtDao.getTodasNotas()
    }
}
