package tarsila.costalonga.notasapp.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.ui.addnote.compose.AddNoteScreen
import tarsila.costalonga.notasapp.ui.core.compose.theme.NoteComposeTheme

@AndroidEntryPoint
class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NoteComposeTheme {
                    AddNoteScreen(
                        navigateBack = { findNavController().navigateUp() },
                    )
                }
            }
        }
    }
}
