package tarsila.costalonga.notasapp.compose

import android.text.method.LinkMovementMethod
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.databinding.SobreTextBinding

@Composable
fun SobreDescription() {
    Scaffold(topBar = { MyTopAppBar() }) {
        Column(Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_icone_circular),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
                    .padding(it)
            )

            AndroidViewBinding(
                factory = SobreTextBinding::inflate,
                modifier = Modifier.wrapContentHeight()
            ) {
                txtConteudo.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSobreCompose() {
    NotaComposeTheme() {
        SobreDescription()
    }
}
