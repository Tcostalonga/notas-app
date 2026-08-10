package tarsila.costalonga.notasapp.ui.main.compose

import tarsila.costalonga.notasapp.ui.main.ThemeMode

data class MainUiState(
    val themeMode: List<ThemeMode> = emptyList(),
    val isSearchEnabled: Boolean = false,
)
