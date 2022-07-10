package tarsila.costalonga.notasapp.ui.sobreFragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tarsila.costalonga.notasapp.databinding.FragmentSobreBinding

class SobreFragment : Fragment() {
    private lateinit var binding: FragmentSobreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(false)
        binding = FragmentSobreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtConteudo.movementMethod = LinkMovementMethod.getInstance()
    }
}
