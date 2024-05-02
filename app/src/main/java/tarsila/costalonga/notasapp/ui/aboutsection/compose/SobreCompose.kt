package tarsila.costalonga.notasapp.ui.aboutsection.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme

private const val LINK1_WORD = "LinkedIn"
private const val LINK2_WORD = "Email"
private const val LINK1 = "https://www.linkedin.com/in/tarsilacostalonga/"
private const val LINK2 = "mailto:tarsila.costalonga@gmail.com"
private const val TAG_LINK1 = "URL_LINKEDIN"
private const val TAG_LINK2 = "URL_EMAIL"

@Composable
fun AboutScreen() {
    val getFirstLinkPositionPair = getWordPosition(word = LINK1_WORD)
    val getSecondLinkPositionPair = getWordPosition(word = LINK2_WORD)

    val firstLinkPosition by remember {
        mutableStateOf(getFirstLinkPositionPair)
    }
    val secondLinkPosition by remember {
        mutableStateOf(getSecondLinkPositionPair)
    }

    Scaffold(topBar = { MyTopAppBar() }) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(it),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_icone_splash),
                contentDescription = null,
                modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(percent = 100),
                    ),
            )

            val aboutText = buildAnnotatedString {
                append(stringResource(id = R.string.disclaimer))

                AddTextStyle(firstLinkPosition)
                AddTextStyle(secondLinkPosition)

                addStringAnnotation(
                    tag = TAG_LINK1,
                    annotation = LINK1,
                    start = firstLinkPosition.first,
                    end = firstLinkPosition.second,
                )

                addStringAnnotation(
                    tag = TAG_LINK2,
                    annotation = LINK2,
                    start = secondLinkPosition.first,
                    end = secondLinkPosition.second,
                )
            }

            LinkableText(myAnnotatedString = aboutText, listOf(TAG_LINK1, TAG_LINK2))
        }
    }
}

@Composable
private fun AnnotatedString.Builder.AddTextStyle(wordPosition: Pair<Int, Int>) {
    addStyle(
        style = SpanStyle(
            color = MaterialTheme.colorScheme.primaryContainer,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
        ),
        start = wordPosition.first,
        end = wordPosition.second,
    )
}

@Composable
private fun getWordPosition(word: String): Pair<Int, Int> {
    val start = stringResource(id = R.string.disclaimer).indexOf(word)
    val end = start + word.length
    return Pair(start, end)
}

@Composable
fun LinkableText(myAnnotatedString: AnnotatedString, tags: List<String>) {
    val context = LocalContext.current

    ClickableText(
        text = myAnnotatedString,
        style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        onClick = { offset ->
            tags.forEach { tag ->
                myAnnotatedString.getStringAnnotations(tag = tag, start = offset, end = offset).firstOrNull()
                    ?.let { annotation ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                        context.startActivity(intent)
                        return@ClickableText
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
