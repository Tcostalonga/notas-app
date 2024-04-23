package tarsila.costalonga.notasapp.ui.addfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.AddCompose
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.databinding.FragmentAddBinding
import tarsila.costalonga.notasapp.utils.makeToast

@AndroidEntryPoint
class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding

    private val viewModel: AddViewModel by viewModels()
    private val args: AddFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setHasOptionsMenu(false)
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.composeViewAdd.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    AddCompose(
                        viewModel,
                    ) { title, description ->
                        viewModel.addNota(title, description, args.tamanhoLista)
                    }
                }
            }
        }
        return binding.root
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
