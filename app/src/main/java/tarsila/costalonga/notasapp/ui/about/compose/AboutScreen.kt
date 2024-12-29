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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.about.AboutViewModel
import tarsila.costalonga.notasapp.ui.about.Constants
import tarsila.costalonga.notasapp.ui.core.compose.MyTopAppBar
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteTheme
import tarsila.costalonga.notasapp.ui.utils.TimelineEvent

@Composable
internal fun AboutScreen(viewModel: AboutViewModel = hiltViewModel()) {
    val timelineEvents by viewModel.timelineEvents.collectAsStateWithLifecycle()
    AboutScreen(
        listOfTimeline = timelineEvents,
        uiIntents = viewModel::handleUiIntents,
    )
}

@Composable
private fun AboutScreen(
    modifier: Modifier = Modifier,
    listOfTimeline: List<TimelineEvent>,
    uiIntents: (AboutUiIntents) -> Unit,
) {

    Scaffold(topBar = { MyTopAppBar() }) {
        Column(
            modifier = modifier
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
                items(listOfTimeline, key = { item -> item.id }) { item ->
                    TimelineWithProgress(
                        date = stringResource(id = item.date),
                        description = stringResource(id = item.description),
                        isExpanded = item.isExpanded,
                        onExpandClick = { uiIntents(AboutUiIntents.OnExpandClick(item.id)) },
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
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val arrowRotation by animateFloatAsState(if (isExpanded) 180f else 0f, label = "Arrow_animation")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onExpandClick()
            }
            .padding(vertical = NoteTheme.spacing.spacer8),
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
                tint = NoteTheme.colors.primary,
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
                color = NoteTheme.colors.primary,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(text = date)

            if (isExpanded) {
                Text(
                    text = description,
                    style = NoteTheme.typography.labelLarge,
                    color = NoteTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = NoteTheme.spacing.spacer12),
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
                withLink(link = LinkAnnotation.Url(Constants.EMAIL)) {
                    append(Constants.LINK_EMAIL)
                }
            }

            append(stringResource(id = R.string.and_string))

            withStyle(
                style = SpanStyle(
                    color = NoteTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                ),
            ) {
                withLink(link = LinkAnnotation.Url(Constants.LINKEDIN)) {
                    append(Constants.LINK_LINKEDIN)
                }
            }

        },
    )
}

@PreviewLightDark
@Composable
fun PreviewAbout() {
    NotaComposeTheme {
        AboutScreen(
            listOfTimeline = listOf(
                TimelineEvent(1, R.string.about_aug2020, R.string.about_aug2020_done),
                TimelineEvent(2, R.string.about_sept2020, R.string.about_sept2020_done),
                TimelineEvent(3, R.string.about_dec2024, R.string.about_dec2024_done),
            ),
            uiIntents = {},
        )
    }
}
