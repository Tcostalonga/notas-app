package tarsila.costalonga.notasapp.ui.mainfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.FragmentMainBinding

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
/*        adapter.onCheckNote = { nota, checked ->
            viewModel.checkboxStatus(nota, checked)
        }*/

        adapter.listener = object : ClicksAcao {
            override fun checkCliqk(nota: Notas, boolean: Boolean) {
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

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddFragment())
        }
/*
        val iTH = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                TODO("Not yet implemented")
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                binding.rcView
                adapter.listaFixa.size
                (recyclerView.adapter as MainAdapter).listaFixa
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }
        }

        */

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