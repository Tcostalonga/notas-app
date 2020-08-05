package tarsila.costalonga.notasapp.ui.detalhefragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.detalhe_fragment.*
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.DetalheFragmentBinding

class DetalheFragment : Fragment() {

    private lateinit var binding: DetalheFragmentBinding

    lateinit var viewModel: DetalheViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetalheFragmentBinding.inflate(inflater, container, false)
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