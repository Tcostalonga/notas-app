package tarsila.costalonga.notasapp.ui.core.compose

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    isMainFragment: Boolean = false,
    onMenuClick: (ItemMenuType) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        colors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        title = {
            Image(
                painter = painterResource(id = R.drawable.photo_mode),
                contentDescription = stringResource(id = R.string.app_name),
            )
        },
        actions = {
            if (isMainFragment) {
                // Search Button
                IconButton(
                    onClick = {
                        onMenuClick(ItemMenuType.SEARCH)
                    },
                ) {
                    Icon(Icons.Filled.Search, null)
                }

                // 3 vertical dots icon
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                }

                // drop down menu
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 5.dp, y = (-10).dp),
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onMenuClick(ItemMenuType.ESTATISTICAS)
                        },
                        text = {
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                text = stringResource(id = R.string.estatisticas),
                            )
                        },
                    )

                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onMenuClick(ItemMenuType.SOBRE)
                        },
                        text = {
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                text = stringResource(id = R.string.sobre),
                            )
                        },
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onMenuClick(ItemMenuType.TEMA)
                        },
                        text = {
                            Text(text = stringResource(id = R.string.trocar_tema))
                        },
                    )
                }
            }
        },
    )
}

@PreviewLightDark
@Composable
fun PreviewTopBar() {
    NotaComposeTheme {
        MyTopAppBar()
    }
}

enum class ItemMenuType {
    SEARCH,
    ESTATISTICAS,
    SOBRE,
    TEMA,
}
