package tarsila.costalonga.notasapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.presenter.estatisticasfragment.EstatisticasViewModel

@Composable
internal fun EstatisticasScreen(viewModel: EstatisticasViewModel = viewModel()) {
    val totalNotasCriadas by viewModel.totalCriadas().collectAsStateWithLifecycle(0)
    val totalNotasAtivas by viewModel.totalAtivas.collectAsStateWithLifecycle(0)
    val totalNotasFinalizadas by viewModel.notasFinalizadas().collectAsStateWithLifecycle(0)

    EstatisticasCompose(totalNotasCriadas, totalNotasAtivas, totalNotasFinalizadas)
}

@Composable
private fun EstatisticasCompose(
    numTotalCriadas: Int,
    totalNotasAtivas: Int,
    totalNotasFinalizadas: Int,
) {
    Scaffold(
        topBar = { MyTopAppBar() },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.margin_gigante))
                    .padding(it),
        ) {
            EstatisticasRow(
                icon = R.drawable.est_criadas,
                text = R.string.notas_criadas,
                numNota = numTotalCriadas.toString(),
            )
            ViewDivider()
            EstatisticasRow(
                icon = R.drawable.est_ativas,
                text = R.string.notas_ativas,
                numNota = totalNotasAtivas.toString(),
            )
            ViewDivider()
            EstatisticasRow(
                icon = R.drawable.est_finalizadas,
                text = R.string.notas_finalizadas,
                numNota = totalNotasFinalizadas.toString(),
            )
        }
    }
}

@Composable
fun ViewDivider() {
    VerticalDivider(
        modifier =
            Modifier
                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
                .width(1.dp)
                .height(80.dp),
    )
}

@Composable
fun EstatisticasRow(
    icon: Int,
    text: Int,
    numNota: String,
) {
    Row {
        Icon(
            painter = painterResource(id = icon),
            tint = Color.Unspecified,
            contentDescription = null,
        )
        Text(
            text = stringResource(id = text),
            modifier =
                Modifier
                    .weight(0.5F)
                    .padding(start = dimensionResource(id = R.dimen.margin_pequena)),
        )
        Text(
            text = numNota,
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewEstatisticasCompose() {
    NotaComposeTheme {
        EstatisticasCompose(3, 2, 1)
    }
}
