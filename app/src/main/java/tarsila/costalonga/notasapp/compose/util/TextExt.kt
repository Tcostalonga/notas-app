package tarsila.costalonga.notasapp.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun getTextDecoration(finalizado: Boolean): TextDecoration {
    return if (finalizado) {
        TextDecoration.LineThrough
    } else {
        TextDecoration.None
    }
}
