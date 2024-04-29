package tarsila.costalonga.notasapp.presenter.estatisticasfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.compose.EstatisticasScreen
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.databinding.FragmentEstatisticasBinding

@AndroidEntryPoint
class EstatisticasFragment : Fragment() {
    private lateinit var binding: FragmentEstatisticasBinding

    private val viewModel: EstatisticasViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setHasOptionsMenu(false)
        binding = FragmentEstatisticasBinding.inflate(inflater, container, false)

        viewModel.carregarNotas()

        binding.composeViewEstatisticas.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    EstatisticasScreen(viewModel)
                }
            }
        }

        return binding.root
    }
}
