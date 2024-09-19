package com.itmo.smarthome.smarthomemobile.app.ui.automation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return root
    }

    private fun addAutomation() {
        val id = (0..1000).random() // Replace with a unique ID logic if needed
        val name = binding.etAddNewAutomationName.text.toString()
        val description = binding.etAddNewAutomationDescription.text.toString()
        val isOn = false

//        val newAutomation = Automation(id, name, description, isOn)
//
//        // Add the automation to the ViewModel
//        automationViewModel.addAutomation(newAutomation)

        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}