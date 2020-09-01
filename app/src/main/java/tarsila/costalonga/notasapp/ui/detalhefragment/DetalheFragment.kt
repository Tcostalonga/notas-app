package tarsila.costalonga.notasapp.ui.detalhefragment

import android.content.Intent

import android.os.Bundle
import android.view.*
import androidx.constraintlayout.solver.SolverVariable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.FragmentDetalheBinding
import tarsila.costalonga.notasapp.utils.hideKeyboard
import tarsila.costalonga.notasapp.utils.makeToast
import tarsila.costalonga.notasapp.utils.showKeyboard
import java.text.SimpleDateFormat


@AndroidEntryPoint
class DetalheFragment : Fragment() {

    private lateinit var binding: FragmentDetalheBinding

    private val viewModel: DetalheViewModel by viewModels()

    private var btAcao = 0



    private lateinit var arguments: Notas
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detalhe, container, false)
        arguments = DetalheFragmentArgs.fromBundle(requireArguments()).notaObj

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelDetalheF = viewModel

        setarCamposDetalheFragm()

        binding.fabEdit.setOnClickListener {

            when (btAcao) {
                0 -> {
                    binding.showTitulo.isEnabled = true
                    binding.showAnotacao.isEnabled = true
                    binding.showTitulo.requestFocus(1)
                    it.showKeyboard()
                    it as FloatingActionButton
                    it.setImageResource(R.drawable.done_24)
                    btAcao = 1
                }
                1 -> {
                    val obj = arguments
                    obj.titulo = binding.showTitulo.text.toString()
                    obj.anotacao = binding.showAnotacao.text.toString()
                    obj.dt_atualizado = System.currentTimeMillis()
                    it.hideKeyboard()
                    viewModel.updateNota(obj)
                    makeToast(requireContext(), getString(R.string.nota_update))
                    findNavController().navigate(DetalheFragmentDirections.actionDetalheFragmentToMainFragment())
                    btAcao = 0
                }
            }
        }

        binding.bottomBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share -> {
                    criarShare()
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

    private fun criarShare() {

        val textsArray = "${binding.showTitulo.text}\n${binding.showAnotacao.text}"

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textsArray)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Compartilhar anotação"))
    }
}