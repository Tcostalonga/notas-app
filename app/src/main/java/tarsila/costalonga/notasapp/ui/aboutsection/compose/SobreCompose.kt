package tarsila.costalonga.notasapp.ui.aboutsection.compose

import android.text.method.LinkMovementMethod
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.SobreTextBinding
import tarsila.costalonga.notasapp.ui.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.compose.theme.NotaComposeTheme

@Composable
fun SobreDescription() {
    Scaffold(topBar = { MyTopAppBar() }) {
        Column(Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_icone_splash),
                contentDescription = null,
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                        .padding(it)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(percent = 100),
                        ),
            )

            AndroidViewBinding(
                factory = SobreTextBinding::inflate,
                modifier = Modifier.wrapContentHeight(),
            ) {
                txtConteudo.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewSobreCompose() {
    NotaComposeTheme {
        SobreDescription()
    }
}
