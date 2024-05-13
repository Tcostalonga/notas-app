package tarsila.costalonga.notasapp.ui.core.compose

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme

@Composable
fun ShowSketchAlert(
    showSketchAlert: (Boolean) -> Unit,
    onDismissDialogButton: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { showSketchAlert(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    showSketchAlert(false)
                },
            ) {
                Text(stringResource(R.string.Sketch_keep))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissDialogButton()
                    showSketchAlert(false)
                },
            ) {
                Text(stringResource(R.string.Sketch_remove))
            }
        },
        title = {
            Text(stringResource(R.string.Sketch_title))
        },
        text = {
            Text(stringResource(R.string.Sketch_text))
        },
    )
}

@Composable
@Preview
fun ShowSketchAlertPreview() {
    NotaComposeTheme {
        ShowSketchAlert({}, {})
    }
}
