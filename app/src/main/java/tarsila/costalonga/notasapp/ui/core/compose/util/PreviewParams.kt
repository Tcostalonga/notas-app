package tarsila.costalonga.notasapp.ui.core.compose.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tarsila.costalonga.notasapp.data.local.Note

class PreviewParams : PreviewParameterProvider<List<Note>> {
    override val values: Sequence<List<Note>>
        get() = sequenceOf(
            listOf(
                Note(
                    id = 4244,
                    title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sodales nulla auctor " +
                        "turpis luctus placerat. Mauris hendrerit consequat massa, quis volutpat elit lacinia a. " +
                        "Donec posuere congue nisl, vitae vehicula ex posuere sed. Fusce maximus purus at magna " +
                        "rhoncus, ultricies dictum nibh tristique. ",
                    createdAt = 3553,
                    updatedAt = 2122,
                    isFinished = false,
                    sort = 9030,
                ),
                Note(
                    id = 7137,
                    title = "malesuada",
                    description = "ex",
                    createdAt = 1747,
                    updatedAt = 5298,
                    isFinished = false,
                    sort = 2175,
                ),
                Note(
                    id = 4225,
                    title = "blandit",
                    description = "decore",
                    createdAt = 3353,
                    updatedAt = 4555,
                    isFinished = false,
                    sort = 5221,
                ),
            ),
        )
}
