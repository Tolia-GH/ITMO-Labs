package com.itmo.smarthome.smarthomemobile.app.ui.devices

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.itmo.smarthome.smarthomemobile.app.R
import com.itmo.smarthome.smarthomemobile.app.model.Device

class DevicesAdapter(private var devicesList: List<Device>) : RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder>() {

    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceName: TextView = itemView.findViewById(R.id.text_device_name)
        val deviceDescription: TextView = itemView.findViewById(R.id.text_device_description)
        val deviceSwitch: SwitchCompat = itemView.findViewById(R.id.switch_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate((R.layout.listitem_device), parent, false)
        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devicesList[position]
        holder.deviceName.text = device.name
        holder.deviceDescription.text= device.description
        holder.deviceSwitch.isChecked = device.isOn

        holder.deviceSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Update device status
            device.isOn = isChecked
        }
    }

    override fun getItemCount() = devicesList.size

    fun updateDevices(newDevices: List<Device>) {
        devicesList = newDevices
        notifyDataSetChanged()
    }

}
