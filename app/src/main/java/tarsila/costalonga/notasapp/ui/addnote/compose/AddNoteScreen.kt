package tarsila.costalonga.notasapp.ui.addnote.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.addnote.AddViewModel
import tarsila.costalonga.notasapp.ui.core.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.core.compose.ShowAlert
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme

@Composable
internal fun AddNoteScreen(viewModel: AddViewModel = hiltViewModel()) {
    val showSketchAlert by viewModel.showSketchAlert.collectAsStateWithLifecycle()
    val keyboard = LocalSoftwareKeyboardController.current

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        viewModel.addSketch()
    }

    LaunchedEffect(Unit) {
        viewModel.getSavedSketches()
    }

    AddNoteCompose(
        titleState = viewModel.titleTextFieldState,
        descriptionState = viewModel.descriptionTextFieldState,
        onFabClicked = {
            keyboard?.hide()
            viewModel.addNota()
        },
    )

    if (showSketchAlert) {
        ShowAlert(
            alertTitle = R.string.sketch_title,
            alertDescription = R.string.sketch_text,
            confirmButtonTitle = R.string.sketch_keep,
            dismissButtonTitle = R.string.sketch_remove,
            onConfirmButtonClick = { viewModel.hideSketchAlert() },
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
    titleState: TextFieldState,
    descriptionState: TextFieldState,
    onFabClicked: () -> Unit,
) {
    Scaffold(
        topBar = { MyTopAppBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClicked,
            ) {
                Icon(
                    Icons.Filled.Done,
                    contentDescription = null,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(horizontal = NoteTheme.spacing.spacer16),
        ) {
            CustomAddTextField(
                labelText = R.string.titulo,
                textFieldState = titleState,
                modifier = Modifier.fillMaxWidth(),
                textStyle = NoteTheme.typography.titleMedium.copy(color = NoteTheme.colors.onBackground),
                singleLine = true,
            )
            Spacer(modifier = Modifier.size(NoteTheme.spacing.spacer4))
            CustomAddTextField(
                labelText = R.string.anotacao,
                textFieldState = descriptionState,
                modifier = Modifier.fillMaxWidth(),
                textStyle = NoteTheme.typography.bodyLarge.copy(color = NoteTheme.colors.onBackground),
            )
        }
    }
}

@Composable
fun CustomAddTextField(
    @StringRes labelText: Int,
    textFieldState: TextFieldState,
    modifier: Modifier,
    textStyle: TextStyle,
    singleLine: Boolean = false,
) {
    BasicTextField(
        state = textFieldState,
        modifier = modifier,
        textStyle = textStyle,
        cursorBrush = SolidColor(NoteTheme.colors.primary),
        lineLimits = if (singleLine) {
            TextFieldLineLimits.SingleLine
        } else {
            TextFieldLineLimits.Default
        },
        decorator = { innerTextField ->
            if (textFieldState.text.isEmpty()) {
                Text(
                    text = stringResource(id = labelText),
                    style = textStyle,
                )
            }
            innerTextField()
        },
    )
}

@PreviewLightDark
@Composable
fun PreviewAdd() {
    NotaComposeTheme {
        AddNoteCompose(
            titleState = TextFieldState(),
            descriptionState = TextFieldState(),
            {},
        )
    }
}
