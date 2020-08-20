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

    private lateinit var obj : Notas


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

        setarCamposAddFrag()


        return binding.root

    }

    private fun setarCamposAddFrag() {

        obj = requireArguments().getParcelable<Notas>("nota") ?: Notas(titulo = "", anotacao = "")

            binding.addTitulo.setText(obj.titulo)
            binding.addAnotacao.setText(obj.anotacao)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveTaskFab.setOnClickListener {
            it.hideKeyboard()
            val edtTitulo = binding.addTitulo.text.toString()
            val edtAnotacao = binding.addAnotacao.text.toString()
            when {
                (edtTitulo.trim().isEmpty() || edtAnotacao.trim().isEmpty()) -> {
                    makeToast(requireContext(), getString(R.string.obrigatoriedade_de_campo))
                }
                (obj.titulo != "") -> {
                    obj.titulo = edtTitulo
                    obj.anotacao = edtAnotacao
                    viewModel.updateNota(obj)
                    makeToast(requireContext(), getString(R.string.nota_update))
                    findNavController().navigate(AddFragmentDirections.actionAddFragmentToMainFragment())
                }
                else -> {
                    val newNota = Notas(titulo = edtTitulo, anotacao = edtAnotacao)
                    viewModel.insertNota(newNota)
                    makeToast(requireContext(), getString(R.string.nota_insert))
                    findNavController().navigate(AddFragmentDirections.actionAddFragmentToMainFragment())
                }

            }
        }
    }

}


