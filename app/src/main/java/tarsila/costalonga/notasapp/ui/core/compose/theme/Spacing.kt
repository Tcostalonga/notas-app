package tarsila.costalonga.notasapp.ui.core.compose.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

object Spacing {
    val spacer0 = 0.dp
    val spacer1 = 1.dp
    val spacer4 = 4.dp
    val spacer8 = 8.dp
    val spacer12 = 12.dp
    val spacer16 = 16.dp
    val spacer18 = 18.dp
    val spacer20 = 20.dp
    val spacer24 = 24.dp
}

val LocalThemeSpacing = staticCompositionLocalOf { Spacing }
