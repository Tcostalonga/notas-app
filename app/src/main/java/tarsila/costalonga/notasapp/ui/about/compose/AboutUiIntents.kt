package tarsila.costalonga.notasapp.ui.about.compose

sealed class AboutUiIntents {
    data class OnExpandClick(val id: Int) : AboutUiIntents()
}
