package tarsila.costalonga.notasapp.ui.mainfragment

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelMainF = viewModel

        binding.rcView.layoutManager = LinearLayoutManager(requireContext())

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
        }

        return super.onOptionsItemSelected(item)
    }
}
