package tarsila.costalonga.notasapp.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import tarsila.costalonga.notasapp.R

@Composable
fun NotaComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = NotaTypography,
        content = content
    )
}

private val LightColors = lightColors(
    primary = colorPrimaryLight,
    onPrimary = primaryTextColorLight,
    primaryVariant = colorPrimaryDarkLight,
    background = colorbkgLight,
    secondary = colorVerde,
    onSecondary = secondaryTextColorLight,
    secondaryVariant = colorAccentLight
)

private val DarkColors = darkColors(
    primary = colorPrimaryDark,
    onPrimary = primaryTextColorDark,
    primaryVariant = colorPrimaryDarkLight,
    background = colorbkgDark,
    secondary = colorVerde,
    onSecondary = secondaryTextColorDark,
    secondaryVariant = colorAccentDark
)

private val ProximaNova = FontFamily(
    Font(R.font.proxima_nova_regular, FontWeight.Normal)
)

private val ProximaNovaCondensed = FontFamily(
    Font(R.font.proxima_nova, FontWeight.Normal)
)

val NotaTypography = Typography(
    body1 = TextStyle(
        fontFamily = ProximaNova,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = ProximaNovaCondensed,
        fontSize = 18.sp
    ),
    overline = TextStyle(
        fontFamily = ProximaNovaCondensed,
        color = colorPrimaryDarkLight,
        fontSize = 14.sp
    )
)
