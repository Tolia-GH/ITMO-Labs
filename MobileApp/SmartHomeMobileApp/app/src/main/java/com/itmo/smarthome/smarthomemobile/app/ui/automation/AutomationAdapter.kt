package com.itmo.smarthome.smarthomemobile.app.ui.automation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.itmo.smarthome.smarthomemobile.app.R
import com.itmo.smarthome.smarthomemobile.app.model.Automation

class AutomationAdapter(private var automationList: List<Automation>) : RecyclerView.Adapter<AutomationAdapter.AutomationViewHolder>() {

    inner class AutomationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val automationName: TextView = itemView.findViewById(R.id.text_automation_name)
        val automationDescription: TextView = itemView.findViewById(R.id.text_automation_description)
        val automationTime: TextView = itemView.findViewById(R.id.text_automation_time)
        val automationOperation: TextView = itemView.findViewById(R.id.text_automation_operation_status)
        val automationDeviceId: TextView = itemView.findViewById(R.id.text_automation_device_id)
        val automationSwitch: SwitchCompat = itemView.findViewById(R.id.switch_automation_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutomationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate((R.layout.listitem_automation), parent, false)
        return AutomationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutomationViewHolder, position: Int) {
        val automation = automationList[position]
        holder.automationName.text = automation.name
        holder.automationDescription.text= automation.description
        holder.automationTime.text = automation.time
        if (automation.operation) {
            holder.automationOperation.text = "On"
        } else {
            holder.automationOperation.text = "Off"
        }
        holder.automationDeviceId.text = automation.deviceID.toString()
        holder.automationSwitch.isChecked = automation.isOn

        holder.automationSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Update device status
            automation.isOn = isChecked
        }
    }

    override fun getItemCount() = automationList.size

    fun updateAutomations(newAutomations: List<Automation>) {
        automationList = newAutomations
        notifyDataSetChanged()
    }

}
