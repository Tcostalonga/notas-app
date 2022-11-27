package tarsila.costalonga.notasapp.ui.mainfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.compose.MainCompose
import tarsila.costalonga.notasapp.compose.theme.NotaComposeTheme
import tarsila.costalonga.notasapp.databinding.FragmentMainBinding

const val TEMACOR = "Mudar tema"

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences

    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.composeViewMain.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NotaComposeTheme {
                    MainCompose(
                        onFabClicked = {
                            findNavController().navigate(
                                MainFragmentDirections.actionMainFragmentToAddFragment(it)
                            )
                        },
                        onItemListClicked = {
                            findNavController().navigate(
                                MainFragmentDirections.actionMainFragmentToDetalheFragment(it)
                            )
                        }
                    )
                }
            }
        }

        // Iniciando SharedPrefs e atribuindo valor default
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(
            sharedPref.getInt(
                TEMACOR,
                AppCompatDelegate.MODE_NIGHT_NO
            )
        )

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.opt_menu_top, menu)
        val item = menu.findItem(R.id.action_search)

        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> true
            R.id.estatisticas -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToEstatisticasFragment())
            R.id.sobre -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToSobreFragment())
            R.id.theme -> changeTema()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeTema() {
        if (sharedPref.getInt(
                TEMACOR,
                AppCompatDelegate.MODE_NIGHT_NO
            ) == AppCompatDelegate.MODE_NIGHT_YES
        ) {
            sharedPref.edit().putInt(TEMACOR, AppCompatDelegate.MODE_NIGHT_NO).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            sharedPref.edit().putInt(TEMACOR, AppCompatDelegate.MODE_NIGHT_YES).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregarNotas()
    }
}
