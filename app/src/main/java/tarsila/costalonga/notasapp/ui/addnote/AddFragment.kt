package tarsila.costalonga.notasapp.ui.addnote

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
import tarsila.costalonga.notasapp.ui.addnote.compose.AddNoteScreen
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.utils.makeToast

@AndroidEntryPoint
class AddFragment : Fragment() {
    private val viewModel: AddViewModel by viewModels()
    private val args: AddFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    AddNoteScreen(args.tamanhoLista)
                }
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.addNotaStatus.observe(this.viewLifecycleOwner) {
            when (it) {
                AddNotaStatus.Success -> {
                    makeToast(requireContext(), getString(R.string.nota_insert))
                    findNavController().navigate(AddFragmentDirections.actionAddFragmentToMainFragment())
                }

                AddNotaStatus.Error -> {
                    makeToast(requireContext(), getString(R.string.obrigatoriedade_de_campo))
                }
            }
        }
    }
}
