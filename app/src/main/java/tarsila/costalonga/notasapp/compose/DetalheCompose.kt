package tarsila.costalonga.notasapp.compose

import android.annotation.SuppressLint
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme

@Composable
fun DetalheCompose() {
    BottomBarWithFab()
}

@Composable
fun InsertText(
    modifier: Modifier,
    @StringRes text: Int,
    @ColorRes textColor: Color = Color.Unspecified,
    textStyle: TextStyle
) {
    Text(
        modifier = modifier,
        text = stringResource(id = text, 3),
        color = textColor,
        style = textStyle
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomBarWithFab() {
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape, content = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(id = R.drawable.share_24),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.padding(end = 8.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(id = R.drawable.delete_24),
                        contentDescription = null
                    )
                }
            })
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {}
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(id = R.drawable.edit_24),
                        contentDescription = null,
                        tint = MaterialTheme.colors.background
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(id = R.dimen.margin_media))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InsertText(
                        modifier = Modifier.wrapContentSize(),
                        textStyle = MaterialTheme.typography.overline,
                        text = R.string.text_dtCriacao_format
                    )
                    InsertText(
                        modifier = Modifier.wrapContentSize(),
                        textStyle = MaterialTheme.typography.overline,
                        text = R.string.text_dtAtualizado_format
                    )
                }

                InsertText(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.margin_larga))
                        .fillMaxWidth(),
                    textStyle = MaterialTheme.typography.subtitle1,
                    text = R.string.titulo,
                    textColor = MaterialTheme.colors.onPrimary
                )

                InsertText(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.margin_pequena),
                            bottom = 74.dp
                        )
                        .fillMaxSize(),
                    textStyle = MaterialTheme.typography.body1,
                    text = R.string.tools_nota,
                    textColor = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}

@Preview(showBackground = true, uiMode = 1)
@Composable
fun PreviewDetalheCompose() {
    NotaComposeTheme() {
        DetalheCompose()
    }
}
