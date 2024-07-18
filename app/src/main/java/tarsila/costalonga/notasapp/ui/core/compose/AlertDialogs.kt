package tarsila.costalonga.notasapp.ui.core.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.window.Dialog
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme
import tarsila.costalonga.notasapp.ui.main.ThemeMode

@Composable
fun ShowAlert(
    @StringRes alertTitle: Int,
    @StringRes alertDescription: Int,
    @StringRes confirmButtonTitle: Int,
    @StringRes dismissButtonTitle: Int,
    onConfirmButtonClick: () -> Unit,
    onDismissDialogButton: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = onConfirmButtonClick,
            ) {
                Text(stringResource(confirmButtonTitle))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissDialogButton,
            ) {
                Text(stringResource(dismissButtonTitle))
            }
        },
        title = {
            Text(stringResource(alertTitle))
        },
        text = {
            Text(stringResource(alertDescription))
        },
    )
}

@Composable
@Preview
fun ShowSketchAlertPreview() {
    NotaComposeTheme {
        ShowAlert(
            alertTitle = R.string.sketch_title,
            alertDescription = R.string.sketch_text,
            confirmButtonTitle = R.string.sketch_keep,
            dismissButtonTitle = R.string.sketch_remove,
            onConfirmButtonClick = {},
            onDismissDialogButton = {},
        )

    }
}

@Composable
fun ChangeThemeDialog(
    themeModes: List<ThemeMode>,
    onThemeClick: (themeMode: ThemeMode) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = NoteTheme.spacing.spacer8,
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = NoteTheme.spacing.spacer18,
                    vertical = NoteTheme.spacing.spacer12,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = stringResource(R.string.dialog_change_theme), style = NoteTheme.typography.titleMedium)

                List(3) { index ->
                    val item = themeModes[index]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    )
                    {
                        Row {
                            Icon(painter = painterResource(id = item.icon), contentDescription = null)
                            Spacer(modifier = Modifier.size(NoteTheme.spacing.spacer8))
                            Text(text = stringResource(item.text))
                        }
                        RadioButton(
                            selected = true,
                            onClick = { onThemeClick(item) },
                        )

                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DialogChangeTheme() {
    NotaComposeTheme {
        ChangeThemeDialog(themeModes = listOf(ThemeMode.LIGHT, ThemeMode.DARK, ThemeMode.SYSTEM_DEFAULT), {}, {})
    }
}
