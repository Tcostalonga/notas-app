package tarsila.costalonga.notasapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.alert.ShowScratchAlert
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.compose.util.rememberLifecycleEvent
import tarsila.costalonga.notasapp.ui.addfragment.AddViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddCompose(
    viewModel: AddViewModel,
    onAddNoteClickButton: (String, String) -> Unit,
) {
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

    Scaffold(
        topBar = { MyTopAppBar() },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.margin_pequena)),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    keyboard?.hide()
                    onAddNoteClickButton(rascunho.title, rascunho.description)
                },
            ) {
                Icon(
                    Icons.Default.Done,
                    contentDescription = null,
                    tint = MaterialTheme.colors.background,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(Modifier.padding(top = dimensionResource(id = R.dimen.margin_pequena) + it.calculateTopPadding())) {
            CustomAddTextField(
                value = rascunho.title,
                onValueChange = { viewModel.updateTitle(it) },
                modifier = Modifier.fillMaxWidth(),
                labelText = R.string.titulo,
                textStyle = MaterialTheme.typography.subtitle1,
                singleLine = true,
            )
            CustomAddTextField(
                value = rascunho.description,
                onValueChange = { viewModel.updateDescription(it) },
                modifier = Modifier.fillMaxSize(),
                labelText = R.string.anotacao,
                textStyle = MaterialTheme.typography.body1,
            )
        }
    }
}

@Preview(showBackground = true, uiMode = 1)
@Composable
fun PreviewAddCompose() {
    NotaComposeTheme() {
        AddCompose(viewModel(), { _, _ -> })
    }
}

@Composable
fun CustomAddTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    labelText: Int,
    textStyle: TextStyle,
    singleLine: Boolean = false,
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        backgroundColor = Color.Transparent,
        cursorColor = MaterialTheme.colors.primaryVariant,
        focusedLabelColor = MaterialTheme.colors.primaryVariant,
    )

    TextField(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        textStyle = textStyle,
        label = {
            Text(
                text = stringResource(id = labelText),
                style = textStyle,
            )
        },
        singleLine = singleLine,
        colors = textFieldColors,
    )
}
