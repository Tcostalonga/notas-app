package tarsila.costalonga.notasapp.ui.mainfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.FragmentMainBinding
import tarsila.costalonga.notasapp.utils.makeToast

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
         findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetalheFragment(it))
        })
        adapter.onCheckNote = { nota, checked ->
            viewModel.checkboxStatus(nota, checked)
            Log.e("MainFragment", "${nota.finalizado}")
        }
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = adapter

        viewModel.allNotas.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()

        })

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
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.opt_menu_top, menu)
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
            ) == AppCompatDelegate.MODE_NIGHT_YES)
        {
            sharedPref.edit().putInt(TEMACOR, AppCompatDelegate.MODE_NIGHT_NO).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
/*
          Verificar depois se vou precisar recriar a atividade ao trocar para dark mode
          val i = Intent(requireContext(), MainActivity::class.java)
            startActivity(i)
            activity?.finish()*/
        } else {
            sharedPref.edit().putInt(TEMACOR, AppCompatDelegate.MODE_NIGHT_YES).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

}