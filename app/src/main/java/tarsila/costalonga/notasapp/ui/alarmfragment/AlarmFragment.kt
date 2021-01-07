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
import javax.inject.Inject


@AndroidEntryPoint
class AlarmFragment : Fragment() {

    private lateinit var binding: AlarmFragmentBinding

    private val viewModel: AlarmViewModel by viewModels()

    @Inject
    lateinit var today: Calendar

    @Inject
    lateinit var alarmUtils: AlarmUtils

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
            getTimePickerProperties()

            if (today.timeInMillis >= System.currentTimeMillis()) {
                if (it.isChecked) {

                    alarmUtils.createAlarm(notaObj)
                    binding.btTextInfo.text = getString(R.string.txtAlarmOn)
                    makeToast(requireContext(), getString(R.string.alarmToastOn))
                } else {
                    alarmUtils.cancelAlarm(notaObj)
                    binding.btTextInfo.text = getString(R.string.txtAlarmOff)
                    makeToast(requireContext(), getString(R.string.alarmToastOff))

                }
            } else {
                makeToast(requireContext(), "Hora inválida")
                it.isChecked = false
            }

        }


        checkAlarmTurnedOn()
        return binding.root
    }

    private fun datePicker(): DatePickerDialog.OnDateSetListener {

        return DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->

            today.set(Calendar.YEAR, p1)
            today.set(Calendar.MONTH, p2)
            today.set(Calendar.DAY_OF_MONTH, p3)
            updateTextDate()
        }
    }

    private fun createDatePickerDialog() {

        val dateDialog =
            DatePickerDialog(
                requireContext(), R.style.PickerStyle,
                datePicker(), today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
            )
        dateDialog.datePicker.minDate = System.currentTimeMillis()
        dateDialog.show()
    }

    private fun updateTextDate() {

        binding.btTextDate.text =
            SimpleDateFormat.getDateInstance(3).format(today.timeInMillis)
    }

    private fun timePickerConfigs() {
        binding.btTimepicker.setIs24HourView(true)

    }

    private fun getTimePickerProperties() {
        today.set(Calendar.HOUR_OF_DAY, binding.btTimepicker.hour)
        today.set(Calendar.MINUTE, binding.btTimepicker.minute)
    }


    private fun checkAlarmTurnedOn() {

        if (notaObj.alarmClock != notaObj.dtCriacao && notaObj.alarmClock > System.currentTimeMillis()) {
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