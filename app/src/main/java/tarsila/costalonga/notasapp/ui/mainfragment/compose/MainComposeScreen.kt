package tarsila.costalonga.notasapp.ui.mainfragment.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.ui.compose.ItemMenuType
import tarsila.costalonga.notasapp.ui.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.compose.PESQUISAR
import tarsila.costalonga.notasapp.ui.compose.SearchLayoutBar
import tarsila.costalonga.notasapp.ui.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.compose.util.PreviewParams
import tarsila.costalonga.notasapp.ui.compose.util.getTextDecoration
import tarsila.costalonga.notasapp.ui.mainfragment.MainViewModel

@Composable
internal fun MainComposeScreen(
    onFabClicked: (Int) -> Unit = {},
    onItemListClicked: (Notas) -> Unit = {},
    onMenuClick: (ItemMenuType) -> Unit = {},
    viewModel: MainViewModel = hiltViewModel(),
) {
    val allNotas by viewModel.allNotas.collectAsStateWithLifecycle()
    val isSearchEnabled by viewModel.isSearchEnabled.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.carregarNotas()
    }

    MainCompose(
        isSearchEnabled,
        allNotas,
        onMenuClick,
        onFabClicked,
        onItemListClicked,
        onCheckedChange = { nota, checkedStatus -> viewModel.checkboxStatus(nota, checkedStatus) },
        onArrowBackClicked = { viewModel.isSearchEnabled.value = false },
    )
}

@Composable
private fun MainCompose(
    isSearchEnabled: Boolean,
    allNotas: List<Notas>,
    onMenuClick: (ItemMenuType) -> Unit = {},
    onFabClicked: (Int) -> Unit = {},
    onItemListClicked: (Notas) -> Unit = {},
    onCheckedChange: (Notas, Boolean) -> Unit = { _, _ -> },
    onArrowBackClicked: () -> Unit = {},
) {
    var searchTerm by rememberSaveable { mutableStateOf(PESQUISAR) }
    var filteredNotas: List<Notas>

    Scaffold(
        topBar = {
            if (isSearchEnabled) {
                SearchLayoutBar(
                    onArrowBackClicked = {
                        onArrowBackClicked()
                        searchTerm = ""
                    },
                    searchTerm = searchTerm,
                    onSearchTermChanged = { searchTerm = it },
                )
            } else {
                MyTopAppBar(true, onMenuClick)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFabClicked(allNotas.size) },
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                )
            }
        },
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .padding(it)
                    .padding(dimensionResource(id = R.dimen.margin_pequena)),
            content = {
                filteredNotas = performFilterInTitle(searchTerm, allNotas)

                items(filteredNotas.size) {
                    val nota = filteredNotas[it]
                    ItemList(
                        nota = nota,
                        onItemClicked = { onItemListClicked(nota) },
                        onCheckedChange = { checkedStatus ->
                            onCheckedChange(nota, checkedStatus)
                        },
                    )
                }
            },
        )
    }
}

fun performFilterInTitle(
    searchedText: String,
    allNotas: List<Notas>,
): List<Notas> {
    return if (searchedText != PESQUISAR && searchedText.isNotEmpty()) {
        val resultList = mutableListOf<Notas>()
        for (nota in allNotas) {
            if (nota.titulo.lowercase().contains(searchedText.lowercase())) {
                resultList.add(nota)
            }
        }
        resultList.toList()
    } else {
        allNotas
    }
}

@Composable
fun ItemList(
    nota: Notas,
    onItemClicked: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
) {
    var checkedState by rememberSaveable { mutableStateOf(nota.finalizado) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.margin_extra_pequena)),
    ) {
        /* TODO: drag and drop is disabled until a solution for compose is released
         Icon(
                painter = painterResource(id = R.drawable.drag_indicator_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryVariant
            ) */
        Checkbox(
            checked = checkedState,
            onCheckedChange = {
                checkedState = it
                onCheckedChange(it)
            },
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable { onItemClicked() },
            text = nota.titulo,
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = getTextDecoration(checkedState),
                ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewMainCompose(
    @PreviewParameter(PreviewParams::class) listOfNotas: List<Notas>,
) {
    NotaComposeTheme {
        MainCompose(false, listOfNotas)
    }
}
