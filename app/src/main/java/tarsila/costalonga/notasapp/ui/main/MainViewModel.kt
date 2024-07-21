package tarsila.costalonga.notasapp.ui.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.repository.NoteDataRepository
import tarsila.costalonga.notasapp.ui.main.compose.MainEvent
import tarsila.costalonga.notasapp.ui.main.compose.MainIntent
import tarsila.costalonga.notasapp.ui.main.compose.MainUiState

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NoteDataRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {
    private var _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val themeModeObj = updateTheme(getThemePreferences(), listOfThemes)
        _uiState.update { it.copy(themeMode = themeModeObj) }
    }

    fun loadNotes() {
        viewModelScope.launch {
            val allNotes = repository.getTodasNotas()
            _uiState.update { it.copy(allNotes = allNotes) }
        }
    }

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.OnAddNoteClick ->
                _uiState.update { it.copy(event = MainEvent.OnAddNoteClicked) }

            is MainIntent.OnItemListClick ->
                _uiState.update { it.copy(event = MainEvent.OnItemListClicked(intent.noteId)) }

            is MainIntent.OnOptionsMenuClick ->
                _uiState.update { it.copy(event = MainEvent.OnOptionsMenuClicked(intent.itemMenu)) }

            is MainIntent.OnThemeOptionClick -> {
                val themeValue = intent.themeMode.themeValue
                _uiState.update {
                    it.copy(
                        themeMode = updateTheme(themeValue, _uiState.value.themeMode),
                        event = MainEvent.OnThemeOptionClicked(themeValue),
                    )
                }
                putThemePreferences(themeValue)
            }

            MainIntent.OnArrowBackClick -> {
                _uiState.update { it.copy(isSearchEnabled = false) }
            }

            is MainIntent.OnCheckboxClick -> {
                checkboxStatus(intent.note, intent.checkedStatus)
            }
        }
    }

    fun consumeEvent() {
        _uiState.update { it.copy(event = null) }
    }

    fun getThemePreferences() = sharedPreferences.getInt(TEMACOR, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    private fun putThemePreferences(value: Int) {
        sharedPreferences.edit().putInt(TEMACOR, value).apply()
    }

    fun updateIsSearchEnabled(isSearchEnabled: Boolean) {
        _uiState.update { it.copy(isSearchEnabled = isSearchEnabled) }
    }

    fun ordenarRecyclerView(lista: List<Notas>) {
        lista.forEachIndexed { index, nota ->
            nota.ordem = index
            updateNota(nota)
        }
    }

    private fun updateTheme(themeModeValue: Int, themeList: List<ThemeMode>): List<ThemeMode> {
        val currentList = themeList.toMutableList()
        currentList.forEachIndexed { index, item ->
            if (item.themeValue == themeModeValue) {
                val newItem = item.copy(isChecked = true)
                currentList[index] = newItem
            } else {
                val newItem = item.copy(isChecked = false)
                currentList[index] = newItem
            }
        }
        return currentList.toList()
    }

    private fun checkboxStatus(
        objNota: Notas,
        checkStatus: Boolean,
    ) {
        objNota.finalizado = checkStatus
        updateNota(objNota)
    }

    private fun updateNota(nota: Notas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNota(nota)
        }
    }

    companion object {
        private const val TEMACOR = "changeTheme"
    }
}
