package tarsila.costalonga.notasapp.compose

import androidx.annotation.ColorRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.compose.util.PreviewParams
import tarsila.costalonga.notasapp.ui.detalhefragment.DetailMode
import tarsila.costalonga.notasapp.ui.detalhefragment.DetalheViewModel
import tarsila.costalonga.notasapp.ui.detalhefragment.MenuType

@Composable
internal fun DetailScreen(
    viewModel: DetalheViewModel = hiltViewModel(),
    onMenuClicked: (MenuType) -> Unit = {},
    onFabClicked: (String, String) -> Unit = { _, _ -> },
) {
    val notaDetalhe by viewModel.notaDetalhe.collectAsState()

    val formattedDtCriacao by remember {
        mutableStateOf(viewModel.getFormattedData(notaDetalhe.dtCriacao))
    }

    val formattedDtAtualizado by remember {
        mutableStateOf(viewModel.getFormattedData(notaDetalhe.dtAtualizado))
    }

    BottomBarWithFab(
        notaDetalhe,
        formattedDtCriacao,
        formattedDtAtualizado,
        onMenuClicked = onMenuClicked,
        onFabClicked = onFabClicked,
    )
}

@Composable
fun InsertText(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    @ColorRes textColor: Color = Color.Unspecified,
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        style = textStyle,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotasShowAndEdit(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    textStyle: TextStyle,
) {
    BasicTextField2(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        enabled = isEnabled,
        textStyle = textStyle,
        cursorBrush = SolidColor(Color.Red),
    )
}

@Composable
private fun BottomBarWithFab(
    notaDetalhe: Notas,
    formattedDtCriacao: String,
    formattedDtAtualizado: String,
    onMenuClicked: (MenuType) -> Unit,
    onFabClicked: (String, String) -> Unit,
) {
    var titulo by rememberSaveable { mutableStateOf(notaDetalhe.titulo) }
    var anotacao by rememberSaveable { mutableStateOf(notaDetalhe.anotacao) }

    var editNotaClick by rememberSaveable { mutableStateOf(DetailMode.VIEW) }

    val inputService = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = { MyTopAppBar() },
        bottomBar = { CustomBottomAppBar(onMenuClicked, onFabClicked) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(dimensionResource(id = R.dimen.margin_media)),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    InsertText(
                        text = stringResource(
                            R.string.text_dtCriacao_format,
                            formattedDtCriacao,
                        ),
                        textStyle = MaterialTheme.typography.labelSmall,
                    )

                    InsertText(
                        text = stringResource(
                            R.string.text_dtAtualizado_format,
                            formattedDtAtualizado,
                        ),
                        textStyle = MaterialTheme.typography.labelSmall,
                    )
                }
                NotasShowAndEdit(
                    text = titulo,
                    onTextChange = { titulo = it },
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.margin_larga))
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            println(it.hasFocus)
                            if (it.hasFocus) {
                                inputService?.show()
                            } else {
                                inputService?.hide()
                            }
                        }
                        .fillMaxWidth(),
                    isEnabled = editNotaClick == DetailMode.EDIT,
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
                )

                NotasShowAndEdit(
                    text = anotacao,
                    onTextChange = { anotacao = it },
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.margin_pequena))
                        .fillMaxSize(),
                    isEnabled = editNotaClick == DetailMode.EDIT,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                )
            }
        },
    )
}

@Composable
fun CustomBottomAppBar(
    onMenuClicked: (MenuType) -> Unit,
    onFabClicked: (String, String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var fabIcon by remember { mutableStateOf(Icons.Filled.Edit) }
    val focusRequester = remember { FocusRequester() }
    var editNotaClick by rememberSaveable { mutableStateOf(DetailMode.VIEW) }

    BottomAppBar(
        actions = {
            IconButton(onClick = { onMenuClicked(MenuType.SHARE) }) {
                Icon(
                    Icons.Filled.Share,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
            Spacer(Modifier.padding(end = 8.dp))

            IconButton(onClick = { onMenuClicked(MenuType.DELETE) }) {
                Icon(
                    Icons.Filled.Delete,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    contentDescription = null,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (editNotaClick) {
                        DetailMode.VIEW -> {
                            editNotaClick = DetailMode.EDIT
                            fabIcon = Icons.Filled.Done
                            scope.launch {
                                delay(200)
                                focusRequester.requestFocus()
                            }
                        }

                        DetailMode.EDIT -> {
                            editNotaClick = DetailMode.VIEW
                            fabIcon = Icons.Filled.Edit
                            onFabClicked("titulo", "anotacao")
                        }
                    }
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
            ) {
                Icon(
                    fabIcon,
                    contentDescription = null,
                )
            }
        },
    )
}

@PreviewLightDark
@Composable
fun PreviewDetalheCompose(
    @PreviewParameter(PreviewParams::class, limit = 1) nota: List<Notas>,
) {
    NotaComposeTheme {
        BottomBarWithFab(nota[0], "123", "1234", {}, { _, _ -> })
    }
}
