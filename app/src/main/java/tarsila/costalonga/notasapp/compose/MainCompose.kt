package tarsila.costalonga.notasapp.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.mainfragment.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainCompose(
    onFabClicked: (Int) -> Unit = {},
    onItemListClicked: (Notas) -> Unit = {},
    onMenuClick: (ItemMenuType) -> Unit = {},
    viewModel: MainViewModel = hiltViewModel()
) {
    val allNotas by viewModel.allNotas.collectAsState()

    Scaffold(
        topBar = {
            MyTopAppBar(true, onMenuClick)
        },
        scaffoldState = rememberScaffoldState(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { onFabClicked(allNotas.size) }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.background
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_pequena)),
            content = {
                items(allNotas.size) {
                    val nota = allNotas[it]
                    ItemList(
                        nota = nota,
                        onItemClicked = { onItemListClicked(nota) },
                        onCheckedChange = { checkedStatus ->
                            viewModel.checkboxStatus(nota, checkedStatus)
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun ItemList(nota: Notas, onItemClicked: () -> Unit, onCheckedChange: (Boolean) -> Unit) {
    var checkedState by remember { mutableStateOf(nota.finalizado) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_extra_pequena))
    ) {
        /* TODO: drag and drop is disabled until a solution for compose is released
         Icon(
                painter = painterResource(id = R.drawable.drag_indicator_24),
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant
            ) */
        Checkbox(
            checked = checkedState,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.secondaryVariant,
                uncheckedColor = MaterialTheme.colors.primaryVariant
            ),
            onCheckedChange = {
                checkedState = it
                onCheckedChange(it)
            }
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClicked() },
            text = nota.titulo,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary,
                textDecoration = getTexDecoration(checkedState)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun getTexDecoration(finalizado: Boolean): TextDecoration {
    return if (finalizado) {
        TextDecoration.LineThrough
    } else {
        TextDecoration.None
    }
}

@Preview(showBackground = true, uiMode = 1)
@Composable
fun PreviewMainCompose() {
    NotaComposeTheme {
        MainCompose()
    }
}
