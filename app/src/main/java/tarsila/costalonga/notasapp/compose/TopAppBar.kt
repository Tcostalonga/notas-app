package tarsila.costalonga.notasapp.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme

@Composable
fun MyTopAppBar(
    isMainFragment: Boolean = false,
    onMenuClick: (ItemMenuType) -> Unit = {}
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.photo_mode),
                modifier = Modifier.padding(top = 14.dp, bottom = 14.dp),
                contentDescription = stringResource(id = R.string.app_name)
            )
        },
        actions = {
            if (isMainFragment) {
                // Search Button
                IconButton(onClick = {
                    onMenuClick(ItemMenuType.SEARCH)
                }) {
                    Icon(Icons.Default.Search, null)
                }

                // 3 vertical dots icon
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }

                // drop down menu
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 10.dp, y = (-60).dp)
                ) {
                    DropdownMenuItem(onClick = {
                        onMenuClick(ItemMenuType.ESTATISTICAS)
                        expanded = false
                    }) {
                        Text(stringResource(id = R.string.estatisticas))
                    }

                    DropdownMenuItem(onClick = {
                        onMenuClick(ItemMenuType.SOBRE)
                        expanded = false
                    }) {
                        Text(stringResource(id = R.string.sobre))
                    }

                    DropdownMenuItem(onClick = {
                        onMenuClick(ItemMenuType.TEMA)
                        expanded = false
                    }) {
                        Text(stringResource(id = R.string.trocar_tema))
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewTopBar() {
    NotaComposeTheme() {
        MyTopAppBar()
    }
}

enum class ItemMenuType {
    SEARCH, ESTATISTICAS, SOBRE, TEMA
}
