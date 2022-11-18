package tarsila.costalonga.notasapp.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainCompose(
    onFabClicked: () -> Unit = {}
) {
    val mockedList = listOf(
        "Lorem Ipsum is simply dummy text of the printing and type setting industry printing and type.",
        "Tarsila, ir ao mercado",
        "It has survived not only five centuries"
    )

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { onFabClicked() }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.background
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_pequena)),
            content = {
                items(mockedList.size) {
                    ItemList(mockedList[it], true)
                }
            }
        )
    }
}

@Composable
fun ItemList(titulo: String, isChecked: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_extra_pequena))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.drag_indicator_24),
            contentDescription = null,
            tint = MaterialTheme.colors.primaryVariant
        )
        Checkbox(
            checked = isChecked,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.secondaryVariant,
                uncheckedColor = MaterialTheme.colors.primaryVariant
            ),
            onCheckedChange = {}
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = titulo,
            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onPrimary),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, uiMode = 1)
@Composable
fun PreviewMainCompose() {
    NotaComposeTheme {
        MainCompose()
    }
}
