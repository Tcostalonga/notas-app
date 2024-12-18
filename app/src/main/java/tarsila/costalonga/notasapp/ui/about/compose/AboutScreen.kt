package tarsila.costalonga.notasapp.ui.about.compose

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme
import tarsila.costalonga.notasapp.ui.utils.AboutListFactory

private const val LINK1_WORD = "\nLinkedIn"
private const val LINK2_WORD = "\nEmail"
private const val LINK1 = "https://www.linkedin.com/in/tarsilacostalonga/"
private const val LINK2 = "mailto:tarsila.costalonga@gmail.com"

@Composable
internal fun AboutScreen() {
    val list = AboutListFactory().generateListOfTimelineEvents().reversed()
    Scaffold(topBar = { MyTopAppBar() }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(NoteTheme.spacing.spacer16),
        ) {
            LazyColumn {
                item {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.icon_splash),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = NoteTheme.spacing.spacer16)
                                .background(
                                    NoteTheme.colors.primaryContainer,
                                    shape = RoundedCornerShape(percent = 100),
                                ),
                        )
                        LinkableText()

                        Spacer(modifier = Modifier.size(NoteTheme.spacing.spacer12))
                    }
                }
                items(list, key = { item -> item.id }) { item ->
                    TimelineWithProgress(
                        date = stringResource(id = item.date),
                        description = stringResource(id = item.description),
                    )
                }
            }
        }

    }
}

@Composable
fun TimelineWithProgress(
    date: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val arrowRotation by animateFloatAsState(if (isExpanded) 180f else 0f, label = "Arrow_animation")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                isExpanded = !isExpanded
            }
            .padding(vertical = NoteTheme.spacing.spacer4),
    ) {
        Column(
            Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .rotate(arrowRotation),
                tint = NoteTheme.colors.outline,
            )

            VerticalDivider(
                modifier = Modifier
                    .animateContentSize()
                    .then(
                        if (isExpanded) {
                            Modifier.fillMaxHeight()
                        } else {
                            Modifier.height(0.dp)
                        },
                    )
                    .width(NoteTheme.spacing.spacer1),
                color = NoteTheme.colors.outline,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = date,
                style = NoteTheme.typography.labelLarge,
            )
            if (isExpanded) {
                Text(
                    text = description,
                    style = NoteTheme.typography.labelSmall,
                    modifier = Modifier.padding(vertical = NoteTheme.spacing.spacer18),
                )
            }
        }
    }
}

@Composable
fun LinkableText() {
    Text(
        buildAnnotatedString {
            append(stringResource(id = R.string.about_disclaimer))

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

@Preview(showBackground = true)
@Composable
fun PreviewAbout() {
    NotaComposeTheme {
        AboutScreen()
    }
}



