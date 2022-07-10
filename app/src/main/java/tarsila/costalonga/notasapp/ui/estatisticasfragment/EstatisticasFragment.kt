package tarsila.costalonga.notasapp.ui.estatisticasfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.databinding.FragmentEstatisticasBinding

@AndroidEntryPoint
class EstatisticasFragment : Fragment() {

    private lateinit var binding: FragmentEstatisticasBinding

    private val viewModel: EstatisticasViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(false)
        binding = FragmentEstatisticasBinding.inflate(inflater, container, false)

        viewModel.carregarNotas()
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.totalCriadas.observe(viewLifecycleOwner) {
            binding.numCriadas.text = it.toString()
        }

        viewModel.notasAtivas.observe(viewLifecycleOwner) {
            binding.numAtvs.text = it.toString()
        }
        viewModel.notasFinalizadas.observe(viewLifecycleOwner) {
            binding.numFnlz.text = it.toString()
        }
    }
}
