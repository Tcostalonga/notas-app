package tarsila.costalonga.notasapp.ui.detailnote.compose

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.ui.core.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.core.compose.ShowAlert
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme
import tarsila.costalonga.notasapp.ui.core.compose.util.PreviewParams
import tarsila.costalonga.notasapp.ui.detailnote.DetailMode
import tarsila.costalonga.notasapp.ui.detailnote.DetailViewModel
import tarsila.costalonga.notasapp.ui.detailnote.MenuType

@Composable
internal fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onMenuClicked: (MenuType) -> Unit = {},
) {
    val noteDetail by viewModel.noteDetail.collectAsStateWithLifecycle()

    val formattedDtCreated = viewModel.getFormattedData(noteDetail.dtCriacao)
    val formattedDtUpdated = viewModel.getFormattedData(noteDetail.dtAtualizado)

    DetailCompose(
        viewModel.title,
        viewModel.description,
        formattedDtCreated,
        formattedDtUpdated,
        onMenuClicked = { menuType ->
            when (menuType) {
                MenuType.DELETE -> viewModel.removerNota()
                MenuType.SHARE -> {}
            }
            onMenuClicked(menuType)
        },
        onFabClicked = viewModel::updateNote,
    )
}

@Composable
private fun DetailCompose(
    title: TextFieldState,
    description: TextFieldState,
    formattedDtCreated: String,
    formattedDtUpdated: String,
    onMenuClicked: (MenuType) -> Unit,
    onFabClicked: () -> Unit,
) {
    var detailMode by rememberSaveable { mutableStateOf(DetailMode.VIEW) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = detailMode) {
        if (detailMode == DetailMode.EDIT) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { MyTopAppBar() },
        bottomBar = {
            CustomBottomAppBar(
                onMenuClicked,
                onFabClicked,
                detailMode,
                onDetailModeChange = { dm ->
                    detailMode = dm
                },
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(horizontal = NoteTheme.spacing.spacer16),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    InsertText(
                        text = stringResource(R.string.text_dtCriacao_format, formattedDtCreated),
                        textStyle = NoteTheme.typography.labelSmall,
                    )

                    InsertText(
                        text = stringResource(R.string.text_dtAtualizado_format, formattedDtUpdated),
                        textStyle = NoteTheme.typography.labelSmall,
                    )
                }
                ShowAndEditNote(
                    textFieldState = title,
                    isEnabled = detailMode == DetailMode.EDIT,
                    textStyle = NoteTheme.typography.titleMedium.copy(color = NoteTheme.colors.onBackground),
                    modifier = Modifier
                        .padding(top = NoteTheme.spacing.spacer18)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                )

                ShowAndEditNote(
                    textFieldState = description,
                    isEnabled = detailMode == DetailMode.EDIT,
                    textStyle = NoteTheme.typography.bodyLarge.copy(color = NoteTheme.colors.onBackground),
                    modifier = Modifier
                        .padding(top = NoteTheme.spacing.spacer8)
                        .fillMaxSize(),
                )
            }
        },
    )
}

@Composable
fun CustomBottomAppBar(
    onMenuClicked: (MenuType) -> Unit,
    onFabClicked: () -> Unit,
    detailMode: DetailMode,
    onDetailModeChange: (DetailMode) -> Unit,
) {
    var fabIcon by remember { mutableStateOf(Icons.Filled.Edit) }

    var showRemoveNoteDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (showRemoveNoteDialog) {
        ShowAlert(
            alertTitle = R.string.alert_title,
            alertDescription = R.string.alert_message,
            confirmButtonTitle = R.string.default_ok,
            dismissButtonTitle = R.string.default_cancel,
            onConfirmButtonClick = {
                onMenuClicked(MenuType.DELETE)
                showRemoveNoteDialog = false
            },
            onDismissDialogButton = { showRemoveNoteDialog = false },
        )
    }

    BottomAppBar(
        actions = {
            IconButton(onClick = { onMenuClicked(MenuType.SHARE) }) {
                Icon(
                    Icons.Filled.Share,
                    contentDescription = null,
                    tint = NoteTheme.colors.onSecondaryContainer,
                )
            }
            Spacer(Modifier.padding(end = NoteTheme.spacing.spacer4))

            IconButton(onClick = { showRemoveNoteDialog = true }) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = null,
                    tint = NoteTheme.colors.onSecondaryContainer,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (detailMode) {
                        DetailMode.VIEW -> {
                            onDetailModeChange(DetailMode.EDIT)
                            fabIcon = Icons.Filled.Done
                        }

                        DetailMode.EDIT -> {
                            onDetailModeChange(DetailMode.VIEW)
                            fabIcon = Icons.Filled.Edit
                            onFabClicked()
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

@Composable
fun ShowAndEditNote(
    textFieldState: TextFieldState,
    isEnabled: Boolean,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
) {
    BasicTextField(
        state = textFieldState,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        enabled = isEnabled,
        textStyle = textStyle,
        cursorBrush = SolidColor(NoteTheme.colors.primary),
        lineLimits = if (singleLine) {
            TextFieldLineLimits.SingleLine
        } else {
            TextFieldLineLimits.Default
        },
    )
}

@PreviewLightDark
@Composable
fun PreviewDetail(
    @PreviewParameter(PreviewParams::class, limit = 1) nota: List<Notas>,
) {
    NotaComposeTheme {
        DetailCompose(
            title = TextFieldState(nota.first().titulo),
            description = TextFieldState(nota.first().anotacao),
            formattedDtCreated = "22/07/2024",
            formattedDtUpdated = "03/09/2024",
            onMenuClicked = {},
            onFabClicked = {},
        )
    }
}
