package tarsila.costalonga.notasapp.ui.detalhefragment

import tarsila.costalonga.notasapp.ui.detalhefragment.DetalheFragmentArgs
import tarsila.costalonga.notasapp.ui.detalhefragment.DetalheFragmentDirections
import tarsila.costalonga.notasapp.ui.detalhefragment.DetalheViewModel
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.FragmentDetalheBinding
import tarsila.costalonga.notasapp.utils.makeToast
import java.text.SimpleDateFormat


@AndroidEntryPoint
class DetalheFragment : Fragment() {

    private lateinit var binding: FragmentDetalheBinding

    private val viewModel: DetalheViewModel by viewModels()

    private lateinit var arguments: Notas
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detalhe, container, false)
        arguments = DetalheFragmentArgs.fromBundle(requireArguments()).notaObj

        setarCamposDetalheFragm()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelDetalheF = viewModel

        binding.fabEdit.setOnClickListener {
            val bundle = bundleOf("nota" to arguments)

            findNavController().navigate(
                R.id.action_detalheFragment_to_addFragment,
                bundle)
        }


        binding.bottomBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share -> {
                    Toast.makeText(
                        requireContext(),
                        "teste share",
                        Toast.LENGTH_SHORT
                    ).show()    // Handle search icon press
                    true
                }
                R.id.remove -> {
                    criarAlertDialog()
                    true
                }
                else -> false
            }
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.opt_menu_bot, menu)

    }

    private fun setarCamposDetalheFragm() {

        binding.showTitulo.setText(arguments.titulo)
        binding.showAnotacao.setText(arguments.anotacao)
        binding.dtCriado.text = getString(
            R.string.text_dtCriacao_format,
            SimpleDateFormat.getDateInstance(3).format(arguments.dt_criacao)
        )
        binding.dtAtualizado.text = getString(
            R.string.text_dtAtualizado_format,
            SimpleDateFormat.getDateInstance(3).format(arguments.dt_atualizado)
        )

    }

    private fun criarAlertDialog() {

        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle(resources.getString(R.string.alert_title))
            .setMessage(resources.getString(R.string.alert_message))
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("OK") { _, _ ->
                viewModel.removerNota(arguments)
                makeToast(requireContext(), getString(R.string.nota_delete))
                findNavController().navigate(DetalheFragmentDirections.actionDetalheFragmentToMainFragment())
            }
            .show()
    }

}