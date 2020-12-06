package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.databinding.AlarmFragmentBinding
import tarsila.costalonga.notasapp.utils.makeToast
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

    private var dtCriacaoArgs: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.alarm_fragment, container, false)
        dtCriacaoArgs = AlarmFragmentArgs.fromBundle(requireArguments()).dtCriacao


        timePickerConfigs()

        binding.btTextDate.text =
            SimpleDateFormat.getDateInstance(3).format(System.currentTimeMillis())



        binding.btTextDate.setOnClickListener {
            createDatePickerDialog()

        }

        binding.btSwitchCreateAlarm.setOnClickListener {
            getTimeProperties()
            createAlarm(dtCriacaoArgs)
            makeToast(requireContext(), getString(R.string.alarmToast))

        }


        return binding.root
    }

    private fun updateTextView() {
        binding.btTextDate.text = SimpleDateFormat.getDateInstance(3).format(today.timeInMillis)
        // binding.btTextDate.text = SimpleDateFormat.getTimeInstance(3).format(today.timeInMillis)
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

    fun getTimeProperties() {

        today.set(Calendar.HOUR_OF_DAY, binding.btTimepicker.hour)
        today.set(Calendar.MINUTE, binding.btTimepicker.minute)

    }

    fun createAlarm(alarmId: Long) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager


        val intent = Intent(requireContext(), MyAlarmManager::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            alarmId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            today.timeInMillis,
            pendingIntent
        )


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)
    }
}