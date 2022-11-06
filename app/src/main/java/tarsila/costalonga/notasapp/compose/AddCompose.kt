package tarsila.costalonga.notasapp.compose

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddCompose(onAddNoteClickButton: (String, String) -> Unit) {
    var titleState by remember { mutableStateOf("") }
    var descriptionState by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.margin_pequena)),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    keyboard?.hide()
                    onAddNoteClickButton(titleState, descriptionState)
                }
            ) {
                Icon(
                    Icons.Default.Done,
                    contentDescription = null,
                    tint = MaterialTheme.colors.background
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column {
            CustomAddTextField(
                value = titleState,
                onValueChange = { titleState = it },
                modifier = Modifier
                    .fillMaxWidth(),
                labelText = R.string.titulo,
                textStyle = MaterialTheme.typography.subtitle1,
                singleLine = true
            )

            CustomAddTextField(
                value = descriptionState,
                onValueChange = { descriptionState = it },
                modifier = Modifier.fillMaxSize(),
                labelText = R.string.anotacao,
                textStyle = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true, uiMode = 1)
@Composable
fun PreviewAddCompose() {
    NotaComposeTheme() {
        AddCompose(onAddNoteClickButton = { _, _ -> })
    }
}

@Composable
fun CustomAddTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    labelText: Int,
    textStyle: TextStyle,
    singleLine: Boolean = false
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        backgroundColor = Color.Transparent,
        cursorColor = MaterialTheme.colors.primaryVariant,
        focusedLabelColor = MaterialTheme.colors.primaryVariant
    )

    TextField(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        textStyle = textStyle,
        label = {
            Text(
                text = stringResource(id = labelText),
                style = textStyle
            )
        },
        singleLine = singleLine,
        colors = textFieldColors
    )
}
