package tarsila.costalonga.notasapp.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.estatisticasfragment.EstatisticasViewModel

@Composable
fun EstatisticasCompose(viewModel: EstatisticasViewModel = viewModel()) {
    Scaffold(
        topBar = { MyTopAppBar() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.margin_gigante))
                .padding(it.calculateBottomPadding())
        ) {
            Column() {
                EstatisticasRow(
                    icon = R.drawable.est_criadas,
                    text = R.string.notas_criadas,
                    numNota = viewModel.totalCriadas()
                )
                ViewSpacer()
                EstatisticasRow(
                    icon = R.drawable.est_ativas,
                    text = R.string.notas_ativas,
                    numNota = viewModel.notasAtivas()
                )
                ViewSpacer()
                EstatisticasRow(
                    icon = R.drawable.est_finalizadas,
                    text = R.string.notas_finalizadas,
                    numNota = viewModel.notasFinalizadas()
                )
            }
        }
    }
}

@Composable
fun ViewSpacer() {
    Spacer(
        modifier = Modifier
            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
            .width(1.dp).height(80.dp)
            .background(MaterialTheme.colors.secondaryVariant)
    )
}

@Composable
fun EstatisticasRow(icon: Int, text: Int, numNota: String) {
    Row() {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colors.secondary
        )
        Text(
            color = MaterialTheme.colors.onPrimary,
            text = stringResource(id = text),
            modifier = Modifier
                .weight(0.5F)
                .padding(start = dimensionResource(id = R.dimen.margin_pequena))
        )
        Text(
            text = numNota,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview(showBackground = true, uiMode = 1)
@Composable
fun PreviewEstatisticasCompose() {
    NotaComposeTheme() {
        EstatisticasCompose()
    }
}
