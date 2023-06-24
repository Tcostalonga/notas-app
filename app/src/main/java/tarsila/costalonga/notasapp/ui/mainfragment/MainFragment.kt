package tarsila.costalonga.notasapp.ui.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.compose.ItemMenuType
import tarsila.costalonga.notasapp.compose.MainCompose
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.composeViewMain.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    MainCompose(
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

        AppCompatDelegate.setDefaultNightMode(viewModel.getThemePreferences())

        return binding.root
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
