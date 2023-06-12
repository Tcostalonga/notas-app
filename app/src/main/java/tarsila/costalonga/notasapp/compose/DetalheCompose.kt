package tarsila.costalonga.notasapp.compose

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.detalhefragment.DetailMode
import tarsila.costalonga.notasapp.ui.detalhefragment.DetalheViewModel
import tarsila.costalonga.notasapp.ui.detalhefragment.MenuType

@Composable
fun DetalheCompose(
    onMenuClicked: (MenuType) -> Unit = {},
    onFabClicked: (String, String) -> Unit = { _, _ -> },
) {
    BottomBarWithFab(onMenuClicked = onMenuClicked, onFabClicked = onFabClicked)
}

@Composable
fun InsertText(
    modifier: Modifier,
    text: String,
    @ColorRes textColor: Color = Color.Unspecified,
    textStyle: TextStyle,
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        style = textStyle,
    )
}

@Composable
fun NotasShowAndEdit(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    textStyle: TextStyle,
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        enabled = isEnabled,
        textStyle = textStyle,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomBarWithFab(
    viewModel: DetalheViewModel = hiltViewModel(),
    onMenuClicked: (MenuType) -> Unit,
    onFabClicked: (String, String) -> Unit,
) {
    val notaDetalhe by viewModel.notaDetalhe.collectAsState()
    var titulo by rememberSaveable { mutableStateOf(notaDetalhe.titulo) }
    var anotacao by rememberSaveable { mutableStateOf(notaDetalhe.anotacao) }

    var editNotaClick by rememberSaveable { mutableStateOf(DetailMode.VIEW) }
    var fabIcon by remember { mutableStateOf(Icons.Default.Edit) }

    val inputService = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { MyTopAppBar() },
        scaffoldState = rememberScaffoldState(),
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape, content = {
                IconButton(onClick = { onMenuClicked(MenuType.SHARE) }) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = null,
                    )
                }
                Spacer(Modifier.padding(end = 8.dp))

                IconButton(onClick = { onMenuClicked(MenuType.DELETE) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            })
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    when (editNotaClick) {
                        DetailMode.VIEW -> {
                            editNotaClick = DetailMode.EDIT
                            fabIcon = Icons.Default.Done
                            scope.launch {
                                delay(200)
                                focusRequester.requestFocus()
                            }
                        }

                        DetailMode.EDIT -> {
                            editNotaClick = DetailMode.VIEW
                            fabIcon = Icons.Default.Edit
                            onFabClicked(titulo, anotacao)
                        }
                    }
                },
            ) {
                Icon(
                    fabIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.background,
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .padding(dimensionResource(id = R.dimen.margin_media)),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    InsertText(
                        modifier = Modifier.wrapContentSize(),
                        textStyle = MaterialTheme.typography.overline,
                        text = stringResource(
                            R.string.text_dtCriacao_format,
                            viewModel.getFormattedData(notaDetalhe.dtCriacao),
                        ),
                    )

                    InsertText(
                        modifier = Modifier.wrapContentSize(),
                        textStyle = MaterialTheme.typography.overline,
                        text = stringResource(
                            R.string.text_dtAtualizado_format,
                            viewModel.getFormattedData(notaDetalhe.dtAtualizado),
                        ),
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
                    textStyle = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onPrimary),
                )

                NotasShowAndEdit(
                    text = anotacao,
                    onTextChange = { anotacao = it },
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.margin_pequena))
                        .fillMaxSize(),
                    isEnabled = editNotaClick == DetailMode.EDIT,
                    textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onPrimary),
                )
            }
        },
    )
}

@Preview(showBackground = true, uiMode = 1)
@Composable
fun PreviewDetalheCompose() {
    NotaComposeTheme() {
        DetalheCompose()
    }
}
