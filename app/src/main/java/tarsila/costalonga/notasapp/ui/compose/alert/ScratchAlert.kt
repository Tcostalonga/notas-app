package tarsila.costalonga.notasapp.ui.compose.alert

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.compose.theme.NotaComposeTheme

@Composable
fun ShowScratchAlert(
    showScratchAlert: (Boolean) -> Unit,
    onDismissDialogButton: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { showScratchAlert(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    showScratchAlert(false)
                },
            ) {
                Text(stringResource(R.string.scratch_keep))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissDialogButton()
                    showScratchAlert(false)
                },
            ) {
                Text(stringResource(R.string.scratch_remove))
            }
        },
        title = {
            Text(stringResource(R.string.scratch_title))
        },
        text = {
            Text(stringResource(R.string.scratch_text))
        },
    )
}

@Composable
@Preview
fun ShowScratchAlertPreview() {
    NotaComposeTheme {
        ShowScratchAlert({}, {})
    }
}
