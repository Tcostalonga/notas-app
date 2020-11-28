package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.AlarmFragmentBinding
import java.util.*

class AlarmFragment : Fragment() {

    private lateinit var binding: AlarmFragmentBinding

    private lateinit var viewModel: AlarmViewModel

    val today = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.alarm_fragment, container, false)

        val datePicker = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
/*            today.set(Calendar.YEAR, p1)
            today.set(Calendar.MONTH, p2)
            today.set(Calendar.DAY_OF_MONTH, p3)*/
        }


        binding.btEditDate.setOnClickListener {
            val dateDialog = DatePickerDialog(
                requireContext(), datePicker, today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH))
            dateDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            dateDialog.show()

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)
    }

}