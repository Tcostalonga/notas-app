package tarsila.costalonga.notasapp.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import tarsila.costalonga.notasapp.R

enum class ThemeMode(@DrawableRes val icon: Int, @StringRes val text: Int) {
    LIGHT(R.drawable.icon_light_theme, R.string.dialog_change_theme_light),
    DARK(R.drawable.icon_dark_theme, R.string.dialog_change_theme_dark),
    SYSTEM_DEFAULT(R.drawable.icon_system_default_theme, R.string.dialog_change_theme_system_default)
}
