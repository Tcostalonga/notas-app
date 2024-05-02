package tarsila.costalonga.notasapp.ui.core.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun NotaComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = NotaTypography,
        content = content,
    )
}

private val LightColors =
    lightColorScheme(
        primary = colorPrimaryLight,
        onPrimary = colorOnPrimaryLight,
        primaryContainer = colorPrimaryContainerLight,
        onPrimaryContainer = colorOnPrimaryContainerLight,
        secondary = colorSecondaryLight,
        onSecondary = colorOnSecondaryLight,
        secondaryContainer = colorSecondaryContainerLight,
        onSecondaryContainer = colorOnSecondaryContainerLight,
        background = backgroundLight,
        onBackground = onBackgroundLight,
        surface = surfaceLight,
        onSurface = onSurfaceLight,
        outline = outlineLight,
    )

private val DarkColors =
    darkColorScheme(
        primary = colorPrimaryDark,
        onPrimary = colorOnPrimaryDark,
        primaryContainer = colorPrimaryContainerDark,
        onPrimaryContainer = colorOnPrimaryContainerDark,
        secondary = colorSecondaryDark,
        onSecondary = colorOnSecondaryDark,
        secondaryContainer = colorSecondaryContainerDark,
        onSecondaryContainer = colorOnSecondaryContainerDark,
        background = backgroundDark,
        onBackground = onBackgroundDark,
        surface = surfaceDark,
        onSurface = onSurfaceDark,
        outline = outlineDark,
    )
