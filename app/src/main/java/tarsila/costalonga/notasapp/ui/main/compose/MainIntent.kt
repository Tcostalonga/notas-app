package tarsila.costalonga.notasapp.ui.main.compose

import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.ui.core.compose.ItemMenuType
import tarsila.costalonga.notasapp.ui.main.ThemeMode

sealed class MainIntent {
    data object OnArrowBackClick : MainIntent()
    data class OnCheckboxClick(val note: Notas, val checkedStatus: Boolean) : MainIntent()
    data class OnAddNoteClick(val listSize: Int) : MainIntent()
    data class OnItemListClick(val noteId: Long) : MainIntent()
    data class OnOptionsMenuClick(val itemMenu: ItemMenuType) : MainIntent()
    data class OnThemeOptionClick(val themeMode: ThemeMode) : MainIntent()
}
