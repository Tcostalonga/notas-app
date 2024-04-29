package tarsila.costalonga.notasapp.ui.addnote

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
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.repository.NotasRepository
import javax.inject.Inject

@HiltViewModel
class AddViewModel
    @Inject
    constructor(
        private val repositorio: NotasRepository,
        private val sharedPreferences: SharedPreferences,
    ) : ViewModel() {
        private val _rascunho = MutableStateFlow(Rascunho())
        val rascunho: StateFlow<Rascunho> = _rascunho

        private val _showScratchAlert = MutableStateFlow(false)
        val showScratchAlert: StateFlow<Boolean> = _showScratchAlert

        private var isRascunho = true

        private val _addNotaStatus = MutableLiveData<AddNotaStatus>()
        val addNotaStatus: LiveData<AddNotaStatus> = _addNotaStatus

        fun addNota(
            title: String,
            description: String,
            listSize: Int,
        ) {
            when {
                (title.trim().isEmpty() || description.trim().isEmpty()) -> {
                    _addNotaStatus.postValue(AddNotaStatus.Error)
                }

                else -> {
                    val newNota = Notas(titulo = title, anotacao = description, ordem = listSize)
                    insertNota(newNota)
                    clearSharedPreferences()
                    setIsRascunhoDisabled()
                    _addNotaStatus.postValue(AddNotaStatus.Success)
                }
            }
        }

        private fun insertNota(nota: Notas) {
            viewModelScope.launch(Dispatchers.IO) {
                repositorio.insertNota(nota)
            }
        }

        fun addRascunho(
            title: String,
            description: String,
        ) {
            if (isRascunho && (title.isNotEmpty() || description.isNotEmpty())) {
                putRascunho(title, description)
            }
        }

        private fun putRascunho(
            titulo: String,
            descricao: String,
        ) {
            sharedPreferences.edit().apply {
                putString(RASCUNHO_TITULO, titulo)
                putString(RASCUNHO_ANOTACAO, descricao)
            }.apply()
        }

        fun getRascunho() {
            val title = sharedPreferences.getString(RASCUNHO_TITULO, "") ?: ""
            val description = sharedPreferences.getString(RASCUNHO_ANOTACAO, "") ?: ""

            _rascunho.update {
                it.copy(
                    title = title,
                    description = description,
                )
            }

            if (title.isNotEmpty() || description.isNotEmpty()) {
                _showScratchAlert.update { true }
            }
        }

        fun clearSharedPreferences() {
            sharedPreferences.edit().clear().apply()
        }

        private fun setIsRascunhoDisabled() {
            isRascunho = false
        }

        fun hideScratchAlert() {
            _showScratchAlert.update { false }
        }

        fun updateTitle(title: String = "") {
            _rascunho.update {
                it.copy(title = title)
            }
        }

        fun updateDescription(description: String = "") {
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
