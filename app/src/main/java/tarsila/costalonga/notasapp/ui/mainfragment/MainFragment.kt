package tarsila.costalonga.notasapp.ui.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private var viewModel: MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false, R.layout.fragment_main)
        binding.lifecycleOwner = viewLifecycleOwner
binding.viewModelMainF = viewModel
        return binding.root
    }


}