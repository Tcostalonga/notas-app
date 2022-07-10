package tarsila.costalonga.notasapp.repositorio

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.bd.NotasDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotasRepositorio @Inject constructor(val dtDao: NotasDao) : DefaultNotasRepositorio {

    private var job = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + job)

    var k: List<Notas> = emptyList()

    override fun insertNota(nota: Notas) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dtDao.insertNota(nota)
            }
        }
    }

    override fun updateNota(nota: Notas) {
        uiScope.launch {
            dtDao.updateNota(nota)
        }
    }

    override fun deleteUmaNota(nota: Notas) {
        uiScope.launch {
            dtDao.deleteUmaNota(nota)
        }
    }

    override suspend fun getTodasNotas(): List<Notas> {
        return dtDao.getTodasNotas()
    }
}
