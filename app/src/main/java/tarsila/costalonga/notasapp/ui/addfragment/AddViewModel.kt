package tarsila.costalonga.notasapp.ui.addfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(val repositorio: NotasRepositorio) : ViewModel() {

    private val _addNotaStatus = MutableLiveData<AddNotaStatus>()
    val addNotaStatus: LiveData<AddNotaStatus> = _addNotaStatus

    fun addNota(title: String, description: String, listSize: Int) {
        when {
            (title.trim().isEmpty() || description.trim().isEmpty()) -> {
                _addNotaStatus.postValue(AddNotaStatus.Error)
            }
            else -> {
                val newNota = Notas(titulo = title, anotacao = description, ordem = listSize)
                insertNota(newNota)
                _addNotaStatus.postValue(AddNotaStatus.Success)
            }
        }
    }

    private fun insertNota(nota: Notas) {
        repositorio.insertNota(nota)
    }
}

sealed class AddNotaStatus {
    object Success : AddNotaStatus()
    object Error : AddNotaStatus()
}
