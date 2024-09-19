package com.itmo.smarthome.smarthomemobile.app.ui.automation

import android.R
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.itmo.smarthome.smarthomemobile.app.databinding.FragmentAddAutomationBinding
import com.itmo.smarthome.smarthomemobile.app.databinding.FragmentAddDeviceBinding
import com.itmo.smarthome.smarthomemobile.app.model.Automation
import com.itmo.smarthome.smarthomemobile.app.model.Device

class AddNewAutomationFragment : Fragment() {

    private var _binding: FragmentAddAutomationBinding? = null
    private val binding get() = _binding!!

    private lateinit var automationViewModel: AutomationViewModel
    private lateinit var operationSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAutomationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        automationViewModel = ViewModelProvider(requireActivity()).get(AutomationViewModel::class.java)

        binding.imgBtnAddNewAutomationBackward.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btAddNewAutomationConfirm.setOnClickListener {
            addAutomation()
        }

        binding.etAddNewAutomationTime.setOnClickListener {
            showTimePickerDialog()
        }

        operationSpinner = binding.spAddNewAutomationOperation
        setupOperationSpinner()

        return root
    }

    private fun setupOperationSpinner() {
        // 定义操作选项
        val operations = arrayOf("Select Operation", "Turn On", "Turn Off")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, operations)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        operationSpinner.adapter = adapter

        // 处理选择事件
        operationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 处理选择的操作
                if (position > 0) {
                    val selectedOperation = operations[position]
                    // 将选中的操作保存到 ViewModel 或进行其他处理
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 处理未选择任何操作
            }
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                binding.etAddNewAutomationTime.setText(time)
            },
            hour,
            minute,
            true // 24-hour format
        )

        timePickerDialog.show()
    }



    private fun addAutomation() {
        val id = (0..1000).random() // Replace with a unique ID logic if needed
        val name = binding.etAddNewAutomationName.text.toString()
        val description = binding.etAddNewAutomationDescription.text.toString()
        val time = binding.etAddNewAutomationTime.text.toString()
        val deviceIdText = binding.etAddNewAutomationDeviceId.text.toString()
        val deviceId = deviceIdText.toInt()
        val operation = when (operationSpinner.selectedItem.toString()) {
            "Turn On" -> true
            "Turn Off" -> false
            else -> false
        }
        val isOn = false

        val newAutomation = Automation(id, name, description, time, deviceId, operation, isOn)

        // Add the automation to the ViewModel
        automationViewModel.addAutomation(newAutomation)

        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}