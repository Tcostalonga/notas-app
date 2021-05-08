package tarsila.costalonga.notasapp.repositorio

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.bd.NotasDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotasRepositorio @Inject constructor(val dtDao: NotasDao) : DefaultNotasRepositorio {

    private var job = Job()

    private var uiScope = CoroutineScope(Dispatchers.Main + job)

    override suspend fun insertNota(nota: Notas) {
        dtDao.insertNota(nota)
    }

    override fun updateNota(nota: Notas) {
        uiScope.launch {
            dtDao.updateNota(nota)
        }
    }

    override suspend fun deleteUmaNota(nota: Notas) {
        dtDao.deleteUmaNota(nota)
    }

    override suspend fun getTodasNotas(): List<Notas> {
        return dtDao.getTodasNotas()
    }

    override suspend fun itemMaiorOrdem(): Int {
        return dtDao.itemMaiorOrdem()
    }

    override suspend fun getAlarmsReschedule(): List<Notas> {
        return dtDao.getAlarmsOnBD()
    }
}

