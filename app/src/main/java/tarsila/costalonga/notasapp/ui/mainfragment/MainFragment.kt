package tarsila.costalonga.notasapp.ui.mainfragment

import android.content.Context
import android.content.SharedPreferences
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

const val TEMACOR = "Mudar tema"

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences

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

        // Iniciando SharedPrefs e atribuindo valor default
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(
            sharedPref.getInt(
                TEMACOR,
                AppCompatDelegate.MODE_NIGHT_NO,
            ),
        )

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
                changeTema()
            }
        }
    }

    private fun changeTema() {
        if (sharedPref.getInt(
                TEMACOR,
                AppCompatDelegate.MODE_NIGHT_NO,
            ) == AppCompatDelegate.MODE_NIGHT_YES
        ) {
            sharedPref.edit().putInt(TEMACOR, AppCompatDelegate.MODE_NIGHT_NO).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            sharedPref.edit().putInt(TEMACOR, AppCompatDelegate.MODE_NIGHT_YES).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
