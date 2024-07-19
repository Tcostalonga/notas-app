package tarsila.costalonga.notasapp.ui.main.compose

import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.ui.main.ThemeMode

data class MainUiState(
    val allNotes: List<Notas> = emptyList(),
    val themeMode: List<ThemeMode> = emptyList(),
    val isSearchEnabled: Boolean = false,
    val event: MainEvent? = null,
)
