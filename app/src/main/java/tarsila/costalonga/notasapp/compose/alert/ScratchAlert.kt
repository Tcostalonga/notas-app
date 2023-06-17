package tarsila.costalonga.notasapp.compose.alert

import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme

@Composable
fun ShowScratchAlert(
    onDismissDialogButton: () -> Unit,
) {
    val showDialog = remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onBackground),
                onClick = {
                    showDialog.value = false
                },
            ) {
                Text(stringResource(R.string.scratch_keep))
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onPrimary),
                onClick = {
                    onDismissDialogButton()
                    showDialog.value = false
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
        ShowScratchAlert({})
    }
}
