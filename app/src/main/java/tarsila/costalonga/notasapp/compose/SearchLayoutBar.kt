package tarsila.costalonga.notasapp.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.compose.theme.colorPrimaryDarkLight

const val PESQUISAR = "Pesquisar"

@Composable
fun SearchLayoutBar(
    onArrowBackClicked: () -> Unit = {},
    searchTerm: String = "",
    onSearchTermChanged: (String) -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier.padding(0.dp),
        title = {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.hasFocus) {
                            onSearchTermChanged.invoke("")
                        } else {
                            onSearchTermChanged.invoke(PESQUISAR)
                        }
                    },
                maxLines = 1,
                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onPrimary),
                value = searchTerm,
                onValueChange = onSearchTermChanged
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onArrowBackClicked() }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    tint = colorPrimaryDarkLight,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
fun SearchLayoutBarPreview() {
    NotaComposeTheme() {
        SearchLayoutBar()
    }
}
