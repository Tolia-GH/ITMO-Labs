package com.itmo.smarthome.smarthomemobile.app.ui.devices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itmo.smarthome.smarthomemobile.app.model.Device

class DevicesViewModel : ViewModel() {

    private val _devices = MutableLiveData<List<Device>>()

    val devices: LiveData<List<Device>> = _devices
    init {
        val initialDevice = listOf(
            Device(1, "Light Bulb", "Smart Light Bulb", "Light", false),
            Device(2, "Thermostat", "Smart Thermostat", "Climate Control", false),
            Device(3, "Camera", "Security Camera", "Security", false),
            Device(4, "Thermostat", "Smart Thermostat", "Climate Control", false),
            Device(5, "Camera", "Security Camera", "Security", false),
            Device(6, "Light Bulb", "Smart Light Bulb", "Light", false),
            Device(7, "Thermostat", "Smart Thermostat", "Climate Control", false),
            Device(8, "Camera", "Security Camera", "Security", false),
            Device(9, "Light Bulb", "Smart Light Bulb", "Light", false),
            Device(10, "Thermostat", "Smart Thermostat", "Climate Control", false),
            Device(11, "Camera", "Security Camera", "Security", false),
            Device(12, "Light Bulb", "Smart Light Bulb", "Light", false),
            Device(13, "Thermostat", "Smart Thermostat", "Climate Control", false),
        )
        _devices.value = initialDevice
    }

    fun updateDevices(newDevices: List<Device>) {
        _devices.value = newDevices
    }

    fun addDevice(device: Device) {
        val updatedDevices = _devices.value?.toMutableList() ?: mutableListOf()
        updatedDevices.add(device)
        _devices.value = updatedDevices
    }
}