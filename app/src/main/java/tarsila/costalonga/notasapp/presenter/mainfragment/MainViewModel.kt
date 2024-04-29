package tarsila.costalonga.notasapp.presenter.mainfragment

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.repository.NotasRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val repositorio: NotasRepository,
        private val sharedPreferences: SharedPreferences,
    ) : ViewModel() {
        val allNotas = MutableStateFlow<List<Notas>>(emptyList())

        var isSearchEnabled = MutableStateFlow(false)

        fun checkboxStatus(
            objNota: Notas,
            checkStatus: Boolean,
        ) {
            objNota.finalizado = checkStatus
            updateNota(objNota)
        }

        fun carregarNotas() {
            viewModelScope.launch(Dispatchers.IO) {
                allNotas.value = repositorio.getTodasNotas()
            }
        }

        private fun updateNota(nota: Notas) {
            viewModelScope.launch(Dispatchers.IO) {
                repositorio.updateNota(nota)
            }
        }

        fun ordenarRecyclerView(lista: List<Notas>) {
            lista.forEachIndexed { index, nota ->
                nota.ordem = index
                updateNota(nota)
            }
        }

        fun getThemePreferences() = sharedPreferences.getInt(TEMACOR, AppCompatDelegate.MODE_NIGHT_NO)

        private fun putThemePreferences(value: Int) {
            sharedPreferences.edit().putInt(TEMACOR, value).apply()
        }

        fun changeTheme(): Int {
            return if (getThemePreferences() == AppCompatDelegate.MODE_NIGHT_YES) {
                val night = AppCompatDelegate.MODE_NIGHT_NO
                putThemePreferences(night)
                night
            } else {
                val light = AppCompatDelegate.MODE_NIGHT_YES
                putThemePreferences(AppCompatDelegate.MODE_NIGHT_YES)
                light
            }
        }

        companion object {
            private const val TEMACOR = "mudar_tema"
        }
    }
