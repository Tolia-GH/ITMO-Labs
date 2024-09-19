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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutomationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate((R.layout.listitem_automation), parent, false)
        return AutomationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutomationViewHolder, position: Int) {
        val automation = automationList[position]
        holder.automationName.text = automation.name
        holder.automationDescription.text= automation.description
    }

    override fun getItemCount() = automationList.size

    fun updateAutomations(newAutomations: List<Automation>) {
        automationList = newAutomations
        notifyDataSetChanged()
    }

}
