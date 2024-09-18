package com.itmo.smarthome.smarthomemobile.app.ui.devices

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.itmo.smarthome.smarthomemobile.app.AddNewDeviceActivity
import com.itmo.smarthome.smarthomemobile.app.LoginActivity
import com.itmo.smarthome.smarthomemobile.app.databinding.FragmentDevicesBinding
import com.itmo.smarthome.smarthomemobile.app.model.Device

class DevicesFragment : Fragment() {

    private var _binding: FragmentDevicesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var devicesAdapter: DevicesAdapter
    private lateinit var devicesViewModel: DevicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        devicesViewModel = ViewModelProvider(this).get(DevicesViewModel::class.java)

        // Initialize RecyclerView
        val recyclerView = binding.recyclerViewDevices
        recyclerView.layoutManager = LinearLayoutManager(context)


        devicesAdapter = DevicesAdapter(emptyList())
        recyclerView.adapter = devicesAdapter

        devicesViewModel.devices.observe(viewLifecycleOwner) { devices ->
            devicesAdapter.updateDevices(devices)
        }

        binding.btFragmentDeviceAdd.setOnClickListener {
            val intent = Intent(activity, AddNewDeviceActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}