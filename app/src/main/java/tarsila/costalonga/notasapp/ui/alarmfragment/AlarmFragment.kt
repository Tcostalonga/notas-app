package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.AlarmFragmentBinding
import tarsila.costalonga.notasapp.utils.hourFormatada
import tarsila.costalonga.notasapp.utils.makeToast
import tarsila.costalonga.notasapp.utils.minFormatada
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AlarmFragment : Fragment() {

    private lateinit var binding: AlarmFragmentBinding

    private val viewModel: AlarmViewModel by viewModels()

    private lateinit var notaObj: Notas


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.alarm_fragment, container, false)
        notaObj = AlarmFragmentArgs.fromBundle(requireArguments()).objAlarm

        timePickerConfigs()
        viewModel.createChannel()

        binding.btTextDate.text =
            SimpleDateFormat.getDateInstance(3).format(System.currentTimeMillis())

        binding.btTextDate.setOnClickListener {
            createDatePickerDialog()
        }

        binding.btSwitchCreateAlarm.setOnClickListener {
            it as SwitchCompat
            if (it.isChecked) {
                getTimePickerProperties()
                viewModel.createAlarm(notaObj)
                makeToast(requireContext(), getString(R.string.alarmToastOn))

            } else {
                viewModel.cancelAlarm(notaObj)
                makeToast(requireContext(), getString(R.string.alarmToastOff))


            }

        }

        checkAlarmTurnedOn()
        return binding.root
    }


    private fun datePicker(): DatePickerDialog.OnDateSetListener {

        return DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->

            viewModel.today.set(Calendar.YEAR, p1)
            viewModel.today.set(Calendar.MONTH, p2)
            viewModel.today.set(Calendar.DAY_OF_MONTH, p3)
            updateTextDate()
        }
    }

    private fun createDatePickerDialog() {

        val dateDialog =
            DatePickerDialog(
                requireContext(), R.style.PickerStyle,
                datePicker(), viewModel.today.get(Calendar.YEAR),
                viewModel.today.get(Calendar.MONTH),
                viewModel.today.get(Calendar.DAY_OF_MONTH)
            )
        dateDialog.datePicker.minDate = System.currentTimeMillis()
        dateDialog.show()
    }

    private fun updateTextDate() {

        binding.btTextDate.text =
            SimpleDateFormat.getDateInstance(3).format(viewModel.today.timeInMillis)
    }

    private fun timePickerConfigs() {
        binding.btTimepicker.setIs24HourView(true)
    }

/*
    fun setLabelSwitch() {
        if (binding.btSwitchCreateAlarm.isChecked) {
            binding.btSwitchCreateAlarm.text = getString(R.string.ligado)
            binding.btTextInfo.text = getString(R.string.txtAlarmOn)
        } else
            binding.btSwitchCreateAlarm.text = getString(R.string.desligado)
        binding.btTextInfo.text = getString(R.string.txtAlarmOff)
    }
*/

    private fun getTimePickerProperties() {
        viewModel.today.set(Calendar.HOUR_OF_DAY, binding.btTimepicker.hour)
        viewModel.today.set(Calendar.MINUTE, binding.btTimepicker.minute)
    }


    private fun checkAlarmTurnedOn() {

        if (notaObj.alarmClock != notaObj.dtCriacao) {
            binding.btTimepicker.hour = hourFormatada(notaObj.alarmClock)
            binding.btTimepicker.minute = minFormatada(notaObj.alarmClock)
            binding.btTextDate.text =
                SimpleDateFormat.getDateInstance(3).format(notaObj.alarmClock)
            binding.btSwitchCreateAlarm.isChecked = true
            binding.btTextInfo.text = getString(R.string.txtAlarmOn)

        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT

    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR

    }

}