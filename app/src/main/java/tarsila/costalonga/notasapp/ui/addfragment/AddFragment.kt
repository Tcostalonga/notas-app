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
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.FragmentAddBinding
import tarsila.costalonga.notasapp.utils.hideKeyboard
import tarsila.costalonga.notasapp.utils.makeToast

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    private val viewModel: AddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tamanhoLista = AddFragmentArgs.fromBundle(requireArguments()).tamanhoLista

        binding.saveTaskFab.setOnClickListener {
            it.hideKeyboard()
            val edtTitulo = binding.addTitulo.text.toString()
            val edtAnotacao = binding.addAnotacao.text.toString()

            when {
                (edtTitulo.trim().isEmpty() || edtAnotacao.trim().isEmpty()) -> {
                    makeToast(requireContext(), getString(R.string.obrigatoriedade_de_campo))
                }
                else -> {

//                    val newNota = Notas(titulo = edtTitulo, anotacao = edtAnotacao, ordem = sizebundle passar)
                    val newNota =
                        Notas(titulo = edtTitulo, anotacao = edtAnotacao, ordem = tamanhoLista)
                    viewModel.insertNota(newNota)
                    makeToast(requireContext(), getString(R.string.nota_insert))
                    findNavController().navigate(AddFragmentDirections.actionAddFragmentToMainFragment())
                }
            }
        }
    }
}
