package tarsila.costalonga.notasapp.ui.main.compose

import tarsila.costalonga.notasapp.data.local.Note
import tarsila.costalonga.notasapp.ui.main.ThemeMode

data class MainUiState(
    val isLoading: Boolean = false,
    val allNotes: List<Note> = emptyList(),
    val themeMode: List<ThemeMode> = emptyList(),
    val isSearchEnabled: Boolean = false,
    val event: MainEvent? = null,
)
