package tarsila.costalonga.notasapp.ui.core.compose.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import tarsila.costalonga.notasapp.R

private val ProximaNova = FontFamily(Font(R.font.proxima_nova_regular, FontWeight.Normal))

private val ProximaNovaCondensed = FontFamily(Font(R.font.proxima_nova, FontWeight.Normal))

val NotaTypography = Typography(
    titleMedium =
    TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    bodyLarge =
    TextStyle(
        fontFamily = ProximaNova,
        fontSize = 18.sp,
    ),
    bodyMedium =
    TextStyle(
        fontFamily = ProximaNovaCondensed,
        fontSize = 18.sp,
    ),
    labelSmall =
    TextStyle(
        fontFamily = ProximaNovaCondensed,
        color = colorSecondaryLight,
        fontSize = 14.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
)
