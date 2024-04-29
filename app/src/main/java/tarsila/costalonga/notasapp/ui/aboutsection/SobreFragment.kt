package tarsila.costalonga.notasapp.ui.aboutsection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import tarsila.costalonga.notasapp.ui.aboutsection.compose.SobreDescription
import tarsila.costalonga.notasapp.ui.compose.theme.NotaComposeTheme

class SobreFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    SobreDescription()
                }
            }
        }
    }
}
