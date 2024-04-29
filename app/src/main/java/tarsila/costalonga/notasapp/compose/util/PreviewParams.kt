package tarsila.costalonga.notasapp.compose.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tarsila.costalonga.notasapp.bd.Notas

class PreviewParams : PreviewParameterProvider<List<Notas>> {
    override val values: Sequence<List<Notas>>
        get() = sequenceOf(
            listOf(
                Notas(
                    id = 4244,
                    titulo = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    anotacao = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sodales nulla auctor " +
                        "turpis luctus placerat. Mauris hendrerit consequat massa, quis volutpat elit lacinia a. " +
                        "Donec posuere congue nisl, vitae vehicula ex posuere sed. Fusce maximus purus at magna " +
                        "rhoncus, ultricies dictum nibh tristique. ",
                    dtCriacao = 3553,
                    dtAtualizado = 2122,
                    imgPath = null,
                    finalizado = false,
                    ordem = 9030,
                ),
                Notas(
                    id = 7137,
                    titulo = "malesuada",
                    anotacao = "ex",
                    dtCriacao = 1747,
                    dtAtualizado = 5298,
                    imgPath = null,
                    finalizado = false,
                    ordem = 2175,
                ),
                Notas(
                    id = 4225,
                    titulo = "blandit",
                    anotacao = "decore",
                    dtCriacao = 3353,
                    dtAtualizado = 4555,
                    imgPath = null,
                    finalizado = false,
                    ordem = 5221,
                ),
            ),
        )
}
