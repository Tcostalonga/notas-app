package tarsila.costalonga.notasapp.ui.statistics.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme
import tarsila.costalonga.notasapp.ui.statistics.StatisticsUiState
import tarsila.costalonga.notasapp.ui.statistics.StatisticsViewModel

@Composable
internal fun StatisticsScreen(viewModel: StatisticsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatisticsCompose(uiState)
}

@Composable
private fun StatisticsCompose(uiState: StatisticsUiState) {
    Scaffold(
        topBar = { MyTopAppBar() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(NoteTheme.spacing.spacer24)
                .padding(it),
        ) {
            StatisticsRow(
                icon = R.drawable.icon_note_active,
                text = R.string.notas_ativas,
                numNota = uiState.allActiveNotes.toString(),
            )
            ViewDivider()
            StatisticsRow(
                icon = R.drawable.icon_note_done,
                text = R.string.notas_finalizadas,
                numNota = uiState.allDoneNotes.toString(),
            )
            ViewDivider()
            StatisticsRow(
                icon = R.drawable.icon_note_created,
                text = R.string.notas_criadas,
                numNota = uiState.allNotes.toString(),
            )
        }
    }
}

@Composable
fun ViewDivider() {
    VerticalDivider(
        modifier = Modifier
            .padding(vertical = NoteTheme.spacing.spacer12)
            .padding(start = NoteTheme.spacing.spacer12)
            .height(80.dp),
    )
}

@Composable
fun StatisticsRow(
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
                .weight(1F)
                .padding(start = NoteTheme.spacing.spacer8),
        )
        Text(
            text = numNota,
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewStatistics() {
    NotaComposeTheme {
        StatisticsCompose(StatisticsUiState(40, 12, 28))
    }
}
