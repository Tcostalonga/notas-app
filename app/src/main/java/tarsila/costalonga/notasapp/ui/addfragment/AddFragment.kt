package tarsila.costalonga.notasapp.ui.addfragment

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.AddFragmentBinding

class AddFragment : Fragment() {

    private lateinit var binding: AddFragmentBinding

    lateinit var viewModel: AddViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddFragmentBinding.inflate(layoutInflater)


        return binding.root


    }


}