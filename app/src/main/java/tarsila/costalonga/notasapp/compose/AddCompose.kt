package tarsila.costalonga.notasapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
                    Icons.Filled.Done,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
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
                textStyle = MaterialTheme.typography.titleSmall,
                singleLine = true,
            )
            CustomAddTextField(
                value = rascunho.description,
                onValueChange = { viewModel.updateDescription(it) },
                modifier = Modifier.fillMaxSize(),
                labelText = R.string.anotacao,
                textStyle = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview(showBackground = true, uiMode = 1)
@Composable
fun PreviewAddCompose() {
    NotaComposeTheme {
        AddCompose(viewModel()) { _, _ -> }
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
    /*    val textFieldColors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            background = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
        )*/

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
        //   colors = textFieldColors,
    )
}
