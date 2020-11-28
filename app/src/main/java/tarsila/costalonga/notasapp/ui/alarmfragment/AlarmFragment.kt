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
import java.text.SimpleDateFormat
import java.util.*

class AlarmFragment : Fragment() {

    private lateinit var binding: AlarmFragmentBinding

    private lateinit var viewModel: AlarmViewModel

    private val today: Calendar = Calendar.getInstance()

    private val datePicker = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->

        today.set(Calendar.YEAR, p1)
        today.set(Calendar.MONTH, p2)
        today.set(Calendar.DAY_OF_MONTH, p3)
        updateTextView()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.alarm_fragment, container, false)

        binding.btTextDate.setOnClickListener {
            createDatePickerDialog()

        }
        timePickerConfigs()
binding.btTextDate.text = SimpleDateFormat.getDateInstance(3).format(System.currentTimeMillis())
        return binding.root
    }

    private fun updateTextView() {
        binding.btTextDate.text = SimpleDateFormat.getDateInstance(3).format(today.time)
    }

    private fun createDatePickerDialog() {


        val dateDialog =
            DatePickerDialog(
                requireContext(), R.style.PickerStyle,
                datePicker, today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
            )
        dateDialog.datePicker.minDate = System.currentTimeMillis()
        dateDialog.show()


    }

    fun timePickerConfigs() {
        binding.btTimepicker.setIs24HourView(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)
    }

}