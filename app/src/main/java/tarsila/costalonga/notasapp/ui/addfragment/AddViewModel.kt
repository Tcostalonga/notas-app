package tarsila.costalonga.notasapp.ui.addfragment

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    val repositorio: NotasRepositorio,
    val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _rascunho = MutableStateFlow(Rascunho())
    val rascunho: StateFlow<Rascunho> = _rascunho

    private var isRascunho = true

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
                isRascunho = false
                putRascunho("", "")
                _addNotaStatus.postValue(AddNotaStatus.Success)
            }
        }
    }

    private fun insertNota(nota: Notas) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.insertNota(nota)
        }
    }

    fun addRascunho(title: String, description: String) {
        if (isRascunho) putRascunho(title, description)
    }

    private fun putRascunho(titulo: String, descricao: String) {
        sharedPreferences.edit().apply {
            putString(RASCUNHO_TITULO, titulo)
            putString(RASCUNHO_ANOTACAO, descricao)
        }.apply()
    }

    fun getRascunho() {
        _rascunho.update {
            it.copy(
                title = sharedPreferences.getString(RASCUNHO_TITULO, "") ?: "",
                description = sharedPreferences.getString(RASCUNHO_ANOTACAO, "") ?: "",
            )
        }
    }

    fun updateTitle(title: String) {
        _rascunho.update {
            it.copy(title = title)
        }
    }

    fun updateDescription(description: String) {
        _rascunho.update {
            it.copy(description = description)
        }
    }

    companion object {
        const val RASCUNHO_TITULO = "rascunho_titulo"
        const val RASCUNHO_ANOTACAO = "rascunho_anotacao"
    }
}

data class Rascunho(
    val title: String = "",
    val description: String = "",
)

sealed class AddNotaStatus {
    object Success : AddNotaStatus()
    object Error : AddNotaStatus()
}
