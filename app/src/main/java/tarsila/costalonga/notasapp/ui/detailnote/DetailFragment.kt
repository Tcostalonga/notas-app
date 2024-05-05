package tarsila.costalonga.notasapp.ui.detailnote

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.detailnote.compose.DetailScreen
import tarsila.costalonga.notasapp.ui.utils.makeToast

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()

    private var btAcao = 0

    private lateinit var arguments: Notas

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        arguments = DetailFragmentArgs.fromBundle(requireArguments()).notaObj
        setarCamposDetalheFragm()

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    DetailScreen(
                        onMenuClicked = { menuType ->
                            when (menuType) {
                                MenuType.SHARE -> criarShare()
                                MenuType.DELETE -> criarAlertDialog()
                            }
                        },
                        onFabClicked = { titulo, anotacao ->
                            viewModel.onEditNota(titulo, anotacao)
                            makeToast(requireContext(), getString(R.string.nota_update))
                            findNavController().navigate(
                                DetailFragmentDirections.actionDetalheFragmentToMainFragment(),
                            )
                        },
                    )
                }
            }
        }
    }

    private fun setarCamposDetalheFragm() {
        viewModel.setNotaDetalhe(arguments)
    }

    private fun criarAlertDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.alert_title))
            .setMessage(resources.getString(R.string.alert_message))
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("OK") { _, _ ->
                viewModel.removerNota(arguments)
                makeToast(requireContext(), getString(R.string.nota_delete))
                findNavController().navigate(DetailFragmentDirections.actionDetalheFragmentToMainFragment())
            }
            .show()
    }

    private fun criarShare() {
        val textsArray = "${arguments.titulo}\n${arguments.anotacao}"

        val shareIntent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textsArray)
                type = "text/plain"
            }
        startActivity(Intent.createChooser(shareIntent, "Compartilhar anotação"))
    }
}
