package tarsila.costalonga.notasapp.ui.addnote.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tarsila.costalonga.notasapp.ui.addnote.AddViewModel
import tarsila.costalonga.notasapp.ui.addnote.Rascunho
import tarsila.costalonga.notasapp.ui.core.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.core.compose.ShowScratchAlert
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme
import tarsila.costalonga.notasapp.ui.core.compose.util.rememberLifecycleEvent

@Composable
internal fun AddNoteScreen(tamanhoLista: Int, viewModel: AddViewModel = hiltViewModel()) {
    val rascunho by viewModel.rascunho.collectAsStateWithLifecycle()
    val showScratchAlert by viewModel.showScratchAlert.collectAsStateWithLifecycle()
    val keyboard = LocalSoftwareKeyboardController.current
    val lifecycle = rememberLifecycleEvent()

    LaunchedEffect(lifecycle) {
        if (lifecycle == Lifecycle.Event.ON_STOP) {
            viewModel.addRascunho(rascunho.title, rascunho.description)
        }
    }

    LaunchedEffect(true) {
        viewModel.getRascunho()
    }

    AddNoteCompose(
        rascunho,
        updateTitle = { newTitle ->
            viewModel.updateTitle(newTitle)
        },
        updateDescription = { newDescription -> viewModel.updateDescription(newDescription) },
        onFabClicked = {
            keyboard?.hide()
            viewModel.addNota(rascunho.title, rascunho.description, tamanhoLista)
        },
    )

    if (showScratchAlert) {
        ShowScratchAlert(
            showScratchAlert = {
                if (!it) {
                    viewModel.hideScratchAlert()
                }
            },
            onDismissDialogButton = {
                viewModel.clearSharedPreferences()
                viewModel.updateTitle()
                viewModel.updateDescription()
            },
        )
    }
}

@Composable
private fun AddNoteCompose(
    rascunho: Rascunho,
    updateTitle: (String) -> Unit,
    updateDescription: (String) -> Unit,
    onFabClicked: () -> Unit,
) {
    Scaffold(
        topBar = { MyTopAppBar() },
        modifier =
        Modifier
            .fillMaxSize()
            .padding(top = NoteTheme.spacing.spacer8),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFabClicked() },
            ) {
                Icon(
                    Icons.Filled.Done,
                    contentDescription = null,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { it ->
        Column(Modifier.padding(it)) {
            CustomAddTextField(
                value = rascunho.title,
                onValueChange = { updateTitle(it) },
                modifier = Modifier.fillMaxWidth(),
                //  labelText = R.string.titulo,
                textStyle = NoteTheme.typography.titleSmall,
                singleLine = true,
            )
            CustomAddTextField(
                value = rascunho.description,
                onValueChange = { updateDescription(it) },
                modifier = Modifier.fillMaxWidth(),
                // labelText = R.string.anotacao,
                textStyle = NoteTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview(showBackground = true, uiMode = 1)
@Composable
fun PreviewAdd() {
    NotaComposeTheme {
        AddNoteCompose(
            rascunho = Rascunho("aubaua", "description"),
            {},
            {},
            {},
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomAddTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    textStyle: TextStyle,
    singleLine: Boolean = false,
) {
    BasicTextField2(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        textStyle = textStyle,
        lineLimits =
        if (singleLine) {
            TextFieldLineLimits.SingleLine
        } else {
            TextFieldLineLimits.Default
        },
        cursorBrush = SolidColor(Color.Red),
    )
}
