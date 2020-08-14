package tarsila.costalonga.notasapp.ui.addfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveTaskFab.setOnClickListener {
            it.hideKeyboard()
            if (binding.addAnotacao.text.isNotEmpty() && binding.addTitulo.text.isNotEmpty()) {
                val objNota = Notas(
                    titulo = binding.addTitulo.text.toString(),
                    anotacao = binding.addAnotacao.text.toString()
                )
                viewModel.insertNota(objNota)
                findNavController().navigate(AddFragmentDirections.actionAddFragmentToMainFragment())

            } else {
                makeToast(requireContext(), getString(R.string.obrigatoriedade_de_campo))
            }
        }
    }


}