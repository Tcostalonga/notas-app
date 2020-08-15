package tarsila.costalonga.notasapp.ui.addfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.FragmentAddBinding
import tarsila.costalonga.notasapp.utils.hideKeyboard
import tarsila.costalonga.notasapp.utils.makeToast


@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    private val viewModel: AddViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveTaskFab.setOnClickListener {
            it.hideKeyboard()
            if (viewModel.addNota(binding.addTitulo.text.toString(), binding.addAnotacao.text.toString())) {
                findNavController().navigate(AddFragmentDirections.actionAddFragmentToMainFragment())
            } else {
                makeToast(requireContext(), getString(R.string.obrigatoriedade_de_campo))
            }

        }
    }


}