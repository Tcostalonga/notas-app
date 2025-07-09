package tarsila.costalonga.notasapp.ui.about.compose

sealed interface AboutUiIntents {
    data class OnExpandClick(val id: Int) : AboutUiIntents
    data object OnExpandAllClick : AboutUiIntents
}
