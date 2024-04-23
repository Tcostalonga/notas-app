package tarsila.costalonga.notasapp.compose.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tarsila.costalonga.notasapp.bd.Notas

class PreviewParams : PreviewParameterProvider<List<Notas>> {
    override val values: Sequence<List<Notas>>
        get() = sequenceOf(
            listOf(
                Notas(
                    id = 9150,
                    titulo = "pericula",
                    anotacao = "doming",
                    dtCriacao = 4455,
                    dtAtualizado = 9991,
                    imgPath = null,
                    finalizado = true,
                    ordem = 9827,
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
