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
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.detailnote.compose.DetailScreen
import tarsila.costalonga.notasapp.ui.utils.makeToast

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setNoteDetail()

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    DetailScreen(
                        onMenuClicked = { menuType ->
                            when (menuType) {
                                MenuType.SHARE -> criarShare()
                                MenuType.DELETE -> {
                                    makeToast(requireContext(), getString(R.string.nota_delete))
                                    findNavController().navigateUp()
                                }
                            }
                        },
                    )
                }
            }
        }
    }

    private fun setNoteDetail() {
        viewModel.setNoteDetail(args.noteId)
    }

    private fun criarShare() {
        val text = "${viewModel.title.text}\n${viewModel.description.text}"

        val shareIntent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
        startActivity(Intent.createChooser(shareIntent, "Compartilhar anotação"))
    }
}
