package tarsila.costalonga.notasapp.ui.detalhefragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.FragmentDetalheBinding
import tarsila.costalonga.notasapp.utils.makeToast
import java.text.SimpleDateFormat


@AndroidEntryPoint
class DetalheFragment : Fragment() {

    private lateinit var binding: FragmentDetalheBinding

    val viewModel: DetalheViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detalhe, container, false)


        setarCamposDetalheFragm()


        binding.viewModelDetalheF = viewModel

        binding.fabEdit.setOnClickListener {
            makeToast(requireContext(), "Fab editar")
        }

        binding.bottomBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

     binding.bottomBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share -> {
                    Toast.makeText(
                        requireContext(),
                        "teste remove",
                        Toast.LENGTH_SHORT
                    ).show()    // Handle search icon press
                    true
                }
                R.id.remove -> {
                    // Handle more item (inside overflow menu) press
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

    private fun setarCamposDetalheFragm() {
        val arguments = DetalheFragmentArgs.fromBundle(requireArguments()).notaObj

        binding.showTitulo.text = arguments.titulo
        binding.showAnotacao.text = arguments.anotacao
        binding.dtCriado.text = getString(
            R.string.text_dtCriacao_format,
            SimpleDateFormat.getDateInstance(3).format(arguments.dt_criacao))
        binding.dtAtualizado.text = getString(
            R.string.text_dtAtualizado_format,
            SimpleDateFormat.getDateInstance(3).format(arguments.dt_atualizado))

    }

   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.opt_menu_bot, menu)

    }

}