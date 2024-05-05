package tarsila.costalonga.notasapp.ui.core.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme

const val PESQUISAR = "Pesquisar"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLayoutBar(
    onArrowBackClicked: () -> Unit = {},
    searchTerm: String = "",
    onSearchTermChanged: (String) -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier.padding(0.dp),
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = NoteTheme.colors.background,
            ),
        title = {
            BasicTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.hasFocus) {
                                onSearchTermChanged.invoke("")
                            } else {
                                onSearchTermChanged.invoke(PESQUISAR)
                            }
                        },
                maxLines = 1,
                textStyle = MaterialTheme.typography.bodyLarge,
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
        SearchLayoutBar()
    }
}
