package tarsila.costalonga.notasapp.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme

@Composable
fun SobreDescription() {
    Column(Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_icone_circular),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
        Text(
            color = MaterialTheme.colors.onPrimary,
            text = stringResource(id = R.string.disclaimer)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewSobreDescription() {
    NotaComposeTheme() {
        SobreDescription()
    }
}
