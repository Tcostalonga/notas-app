package tarsila.costalonga.notasapp.ui.main.compose

import tarsila.costalonga.notasapp.ui.core.compose.ItemMenuType

sealed class MainEvent {
    data object OnAddNoteClicked : MainEvent()
    data class OnItemListClicked(val noteId: Long) : MainEvent()
    data class OnOptionsMenuClicked(val itemMenu: ItemMenuType) : MainEvent()
    data class OnThemeOptionClicked(val themeMode: Int) : MainEvent()
}
