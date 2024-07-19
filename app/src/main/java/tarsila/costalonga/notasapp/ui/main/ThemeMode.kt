package tarsila.costalonga.notasapp.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import tarsila.costalonga.notasapp.R

data class ThemeMode(
    val themeValue: Int,
    @DrawableRes val icon: Int,
    @StringRes val text: Int,
    val isChecked: Boolean,
)

val listOfThemes = mutableListOf(
    ThemeMode(
        AppCompatDelegate.MODE_NIGHT_NO,
        R.drawable.icon_light_theme,
        R.string.dialog_change_theme_light,
        false,
    ),
    ThemeMode(
        AppCompatDelegate.MODE_NIGHT_YES,
        R.drawable.icon_dark_theme,
        R.string.dialog_change_theme_dark,
        false,
    ),
    ThemeMode(
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        R.drawable.icon_system_default_theme,
        R.string.dialog_change_theme_system_default,
        false,
    ),
)
