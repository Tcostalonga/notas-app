package tarsila.costalonga.notasapp.ui.estatisticasfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.FragmentEstatisticasBinding

@AndroidEntryPoint
class EstatisticasFragment : Fragment() {

    private lateinit var binding: FragmentEstatisticasBinding

    private val viewModel: EstatisticasViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_estatisticas, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelEstF = viewModel

        viewModel.carregarNotas()

        return binding.root
    }
}