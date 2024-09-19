package com.itmo.smarthome.smarthomemobile.app.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.itmo.smarthome.smarthomemobile.app.databinding.FragmentAddDeviceBinding
import com.itmo.smarthome.smarthomemobile.app.databinding.FragmentDevicesBinding
import com.itmo.smarthome.smarthomemobile.app.model.Device

class AddNewDeviceFragment : Fragment() {

    private var _binding: FragmentAddDeviceBinding? = null
    private val binding get() = _binding!!

    private lateinit var devicesViewModel: DevicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddDeviceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        devicesViewModel = ViewModelProvider(requireActivity()).get(DevicesViewModel::class.java)

        binding.imgBtnAddNewDeviceBackward.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btAddNewDeviceConfirm.setOnClickListener {
            addDevice()
        }

        return root
    }

    private fun addDevice() {
        val id = (0..1000).random() // Replace with a unique ID logic if needed
        val name = binding.etAddNewDeviceName.text.toString()
        val description = binding.etAddNewDeviceDescription.text.toString()
        val type = binding.etAddNewDeviceType.text.toString()
        val isOn = false

        // Create a new Device object
        val newDevice = Device(id, name, description, type, isOn)

        // Add the device to the ViewModel
        devicesViewModel.addDevice(newDevice)

        // Navigate back to the DevicesFragment
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}