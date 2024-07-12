package tarsila.costalonga.notasapp.ui.core.compose

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme

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
