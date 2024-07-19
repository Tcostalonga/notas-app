package tarsila.costalonga.notasapp.ui.core.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLayoutBar(
    hasFocus: Boolean,
    onArrowBackClicked: () -> Unit = {},
    searchTerm: String = "",
    onSearchTermChanged: (String) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = hasFocus) {
        focusRequester.requestFocus()
    }

    TopAppBar(
        modifier = Modifier.padding(0.dp),
        colors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = NoteTheme.colors.background,
        ),
        title = {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                textStyle = NoteTheme.typography.bodyLarge.copy(color = NoteTheme.colors.onBackground),
                singleLine = true,
                cursorBrush = SolidColor(NoteTheme.colors.primary),
                value = searchTerm,
                onValueChange = onSearchTermChanged,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onArrowBackClicked() },
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}

@PreviewLightDark
@Composable
fun SearchLayoutBarPreview() {
    NotaComposeTheme {
        SearchLayoutBar(true)
    }
}
