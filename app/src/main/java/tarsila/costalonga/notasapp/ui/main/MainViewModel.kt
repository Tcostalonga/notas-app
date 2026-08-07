package tarsila.costalonga.notasapp.ui.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.data.repository.NoteRepository
import tarsila.costalonga.notasapp.ui.main.compose.MainEvent
import tarsila.costalonga.notasapp.ui.main.compose.MainIntent
import tarsila.costalonga.notasapp.ui.main.compose.MainUiState

sealed interface NoteListUiState {
    data object Loading : NoteListUiState
    data class Success(val allNotes: List<Note>) : NoteListUiState
    data object Error : NoteListUiState
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _event = Channel<MainEvent>()
    val event = _event.receiveAsFlow()

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val themeModeObj = updateTheme(getThemePreferences(), listOfThemes)
        _uiState.update { it.copy(themeMode = themeModeObj) }
    }

    val noteListUiState = repository.getAllNotes()
        .map<List<Note>, NoteListUiState> {
            NoteListUiState.Success(it)
        }
        .catch {
            emit(NoteListUiState.Error)
        }
        .onStart {
            emit(NoteListUiState.Loading)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NoteListUiState.Loading,
        )

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.OnAddNoteClick ->
                _event.trySend(MainEvent.OnAddNoteClicked)

            is MainIntent.OnItemListClick ->
                _event.trySend(MainEvent.OnItemListClicked(intent.noteId))

            is MainIntent.OnOptionsMenuClick ->
                _event.trySend(MainEvent.OnOptionsMenuClicked(intent.itemMenu))

            is MainIntent.OnThemeOptionClick -> {
                val themeValue = intent.themeMode.themeValue
                _uiState.update {
                    it.copy(
                        themeMode = updateTheme(themeValue, _uiState.value.themeMode),
                    )
                }
                putThemePreferences(themeValue)
                _event.trySend(MainEvent.OnThemeOptionClicked(themeValue))
            }

            MainIntent.OnArrowBackClick -> {
                _uiState.update { it.copy(isSearchEnabled = false) }
            }

            is MainIntent.OnCheckboxClick -> {
                checkboxStatus(intent.note, intent.checkedStatus)
            }
        }
    }

    fun getThemePreferences() = sharedPreferences.getInt(TEMACOR, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    private fun putThemePreferences(value: Int) {
        sharedPreferences.edit { putInt(TEMACOR, value) }
    }

    fun updateIsSearchEnabled(isSearchEnabled: Boolean) {
        _uiState.update { it.copy(isSearchEnabled = isSearchEnabled) }
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
        objNota: Note,
        checkStatus: Boolean,
    ) {
        updateNote(objNota.copy(isFinished = checkStatus))
    }

    private fun updateNote(nota: Note) {
        viewModelScope.launch {
            repository.updateNote(nota)
        }
    }

    companion object {
        private const val TEMACOR = "changeTheme"
    }
}
