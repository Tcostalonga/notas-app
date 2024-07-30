package tarsila.costalonga.notasapp.ui.about.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme

private const val LINK1_WORD = "\nLinkedIn"
private const val LINK2_WORD = "\nEmail"
private const val LINK1 = "https://www.linkedin.com/in/tarsilacostalonga/"
private const val LINK2 = "mailto:tarsila.costalonga@gmail.com"
private const val TAG_LINK1 = "URL_LINKEDIN"
private const val TAG_LINK2 = "URL_EMAIL"

@Composable
internal fun AboutScreen() {
    Scaffold(topBar = { MyTopAppBar() }) {
        Column(
            modifier = Modifier
                .padding(horizontal = NoteTheme.spacing.spacer16)
                .padding(it),
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_splash),
                contentDescription = null,
                modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = NoteTheme.spacing.spacer16)
                    .background(
                        NoteTheme.colors.primaryContainer,
                        shape = RoundedCornerShape(percent = 100),
                    ),
            )

            LinkableText()
        }
    }
}

@Composable
fun LinkableText() {

    Text(
        buildAnnotatedString {
            append(stringResource(id = R.string.disclaimer))

            withStyle(
                style = SpanStyle(
                    color = NoteTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                ),
            ) {
                withLink(link = LinkAnnotation.Url(LINK1)) {
                    append(LINK1_WORD)
                }

                withLink(link = LinkAnnotation.Url(LINK2)) {
                    append(LINK2_WORD)
                }
            }
        },
    )
}

@PreviewLightDark
@Composable
fun PreviewAbout() {
    NotaComposeTheme {
        AboutScreen()
    }
}
