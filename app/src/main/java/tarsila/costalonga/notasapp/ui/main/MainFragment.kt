package tarsila.costalonga.notasapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.ui.core.compose.ItemMenuType
import tarsila.costalonga.notasapp.ui.core.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.ui.main.compose.MainScreen

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        AppCompatDelegate.setDefaultNightMode(viewModel.getThemePreferences())

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    MainScreen(
                        onFabClicked = {
                            findNavController().navigate(
                                MainFragmentDirections.actionMainFragmentToAddFragment(it),
                            )
                        },
                        onMenuClick = {
                            handleOnMenuClick(it)
                        },
                        onItemListClicked = {
                            findNavController().navigate(
                                MainFragmentDirections.actionMainFragmentToDetalheFragment(it),
                            )
                        },
                    )
                }
            }
        }
    }

    private fun handleOnMenuClick(itemMenu: ItemMenuType) {
        when (itemMenu) {
            ItemMenuType.SEARCH -> {
                viewModel.isSearchEnabled.value = true
            }

            ItemMenuType.ESTATISTICAS -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToEstatisticasFragment(),
                )
            }

            ItemMenuType.SOBRE -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToSobreFragment(),
                )
            }

            ItemMenuType.TEMA -> {
                AppCompatDelegate.setDefaultNightMode(viewModel.changeTheme())
            }
        }
    }
}
