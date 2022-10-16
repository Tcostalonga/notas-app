package tarsila.costalonga.notasapp.ui.sobreFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import tarsila.costalonga.notasapp.compose.SobreDescription
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.databinding.FragmentSobreBinding

class SobreFragment : Fragment() {
    private lateinit var binding: FragmentSobreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(false)
        binding = FragmentSobreBinding.inflate(inflater, container, false)

        binding.composeViewSobre.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    SobreDescription()
                }
            }
        }

        return binding.root
    }

/*
    TODO: Adicionar clicável em link
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtConteudo.movementMethod = LinkMovementMethod.getInstance()
    }*/
}
