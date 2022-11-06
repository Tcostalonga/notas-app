package tarsila.costalonga.notasapp.ui.detalhefragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.compose.DetalheCompose
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.databinding.FragmentDetalheBinding
import tarsila.costalonga.notasapp.utils.makeToast

@AndroidEntryPoint
class DetalheFragment : Fragment() {

    private lateinit var binding: FragmentDetalheBinding

    private val viewModel: DetalheViewModel by viewModels()

    private var btAcao = 0

    private lateinit var arguments: Notas

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetalheBinding.inflate(inflater, container, false)
        arguments = DetalheFragmentArgs.fromBundle(requireArguments()).notaObj

        binding.composeViewDetalhe.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    DetalheCompose(
                        onMenuClicked = { menuType ->
                            when (menuType) {
                                MenuType.SHARE -> criarShare()
                                MenuType.DELETE -> criarAlertDialog()
                            }
                        },
                        onFabClicked = { titulo, anotacao ->
                            viewModel.onEditNota(titulo, anotacao)
                            makeToast(requireContext(), getString(R.string.nota_update))
                            findNavController().navigate(DetalheFragmentDirections.actionDetalheFragmentToMainFragment())
                        }
                    )
                }
            }
        }

        setarCamposDetalheFragm()
/*
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
                    obj.dtAtualizado = System.currentTimeMillis()
                    it.hideKeyboard()
                    viewModel.updateNota(obj)
                    makeToast(requireContext(), getString(R.string.nota_update))
                    findNavController().navigate(DetalheFragmentDirections.actionDetalheFragmentToMainFragment())
                    btAcao = 0
                }
            }
        }

 */
        return binding.root
    }

    private fun setarCamposDetalheFragm() {
        viewModel.setNotaDetalhe(arguments)
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
        val textsArray = "${arguments.titulo}\n${arguments.anotacao}"

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textsArray)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Compartilhar anotação"))
    }
}
