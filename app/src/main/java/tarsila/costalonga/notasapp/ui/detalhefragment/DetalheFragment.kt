package tarsila.costalonga.notasapp.ui.detalhefragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.FragmentDetalheBinding

class DetalheFragment : Fragment() {

    private lateinit var binding: FragmentDetalheBinding

    lateinit var viewModel: DetalheViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detalhe, container, false)

        viewModel = ViewModelProviders.of(this).get(DetalheViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelDetalheF = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.bottomBar)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.opt_menu_bot, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> Toast.makeText(
                requireContext(),
                "teste share",
                Toast.LENGTH_SHORT
            ).show()
            R.id.remove -> {     Toast.makeText(
                requireContext(),
                "teste remove",
                Toast.LENGTH_SHORT
                ).show()}
        }

        return super.onOptionsItemSelected(item)
    }
}