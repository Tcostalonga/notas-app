package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.FragmentAlarmBinding
import tarsila.costalonga.notasapp.utils.dataFormatada
import tarsila.costalonga.notasapp.utils.horaFormatada
import tarsila.costalonga.notasapp.utils.makeToast
import tarsila.costalonga.notasapp.utils.minFormatada
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AlarmFragment : Fragment() {

    private lateinit var binding: FragmentAlarmBinding

    private val viewModel: AlarmViewModel by viewModels()

    private var isAlarmOn: Boolean = false

    @Inject
    lateinit var today: Calendar

    @Inject
    lateinit var alarmUtils: AlarmUtils

    private lateinit var notaObj: Notas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm, container, false)
        notaObj = AlarmFragmentArgs.fromBundle(requireArguments()).objAlarm

        timePickerConfigs()
        updateTextDate()
        viewModel.createChannel()


        binding.btTextDate.setOnClickListener {
            createDatePickerDialog()
        }

        binding.btCreateAlarm.setOnClickListener {
            it as MaterialButton
            getTimePickerProperties()

            when (isAlarmOn) {
                false -> {
                    if (today.timeInMillis >= System.currentTimeMillis()) {

                        alarmUtils.createAlarm(notaObj, today.timeInMillis)
                        makeToast(requireContext(), getString(R.string.alarmToastOn))

                        it.text = getString(R.string.ligado)
                        it.setBackgroundColor(requireContext().getColor(R.color.colorVerde))
                        it.setTextColor(requireContext().getColor(R.color.colorPrimary))
                        isAlarmOn = true
                    } else {
                        makeToast(requireContext(), "Hora inválida")

                    }

                }
                true -> {
                    alarmUtils.cancelAlarm(notaObj)
                    makeToast(requireContext(), getString(R.string.alarmToastOff))

                    it.text = getString(R.string.desligado)
                    it.setBackgroundColor(requireContext().getColor(R.color.colorAccent))
                    it.setTextColor(requireContext().getColor(R.color.colorPrimary))
                    isAlarmOn = false
                }
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

        binding.btTextDate.text = dataFormatada(today.timeInMillis)
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
            binding.btTimepicker.hour = horaFormatada(notaObj.alarmClock)
            binding.btTimepicker.minute = minFormatada(notaObj.alarmClock)
            binding.btTextDate.text =
                dataFormatada(notaObj.alarmClock)
            isAlarmOn = true
            binding.btCreateAlarm.text = getString(R.string.ligado)
            binding.btCreateAlarm.setBackgroundColor(requireContext().getColor(R.color.colorVerde))
            binding.btCreateAlarm.setTextColor(requireContext().getColor(R.color.colorPrimary))
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