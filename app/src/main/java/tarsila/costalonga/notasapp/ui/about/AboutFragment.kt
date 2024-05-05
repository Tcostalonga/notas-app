package tarsila.costalonga.notasapp.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import tarsila.costalonga.notasapp.ui.about.compose.AboutScreen
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    AboutScreen()
                }
            }
        }
    }
}
