package tarsila.costalonga.notasapp.ui.mainfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.FragmentMainBinding
import java.util.*
import kotlin.collections.ArrayList

const val TEMACOR = "Mudar tema"

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences

    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelMainF = viewModel

        //Configuração da recycler_view
        adapter = MainAdapter(NotasListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetalheFragment(
                    it
                )
            )
        })

        adapter.listener = object : ClicksAcao {
            override fun checkClick(nota: Notas, boolean: Boolean) {
                viewModel.checkboxStatus(nota, boolean)
            }
        }

        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = adapter


        val observer = Observer<List<Notas>> {
            adapter.listaFixa = it
            adapter.listaDoFiltro = ArrayList(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.allNotas.observe(viewLifecycleOwner, observer)

        //Iniciando SharedPrefs e atribuindo valor default
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(
            sharedPref.getInt(
                TEMACOR,
                AppCompatDelegate.MODE_NIGHT_NO
            )
        )

        arrastarNotas()

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToAddFragment(
                    adapter.listaFixa.size
                )
            )

        }

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

    fun arrastarNotas() {

        val iTH = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                Collections.swap(adapter.listaFixa, from, to)
                viewModel.ordenarRecyclerView(adapter.listaFixa)
                adapter.notifyItemMoved(from, to)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }
        })

        iTH.attachToRecyclerView(binding.rcView)
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregarNotas()
    }

}