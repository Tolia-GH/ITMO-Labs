package com.itmo.smarthome.smarthomemobile.app.ui.automation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itmo.smarthome.smarthomemobile.app.model.Automation

class AutomationViewModel : ViewModel() {

    private val _automations = MutableLiveData<List<Automation>>()

    val automations: LiveData<List<Automation>> = _automations
    init {
        _automations.value = listOf(
            Automation(1, "Wakeup", "Wakeup in the morning", "08:20", 1, true, true)
        )
    }

    fun updateAutomations(newAutomations: List<Automation>) {
        _automations.value = newAutomations
    }

    fun addAutomation(automation: Automation) {
        val updatedAutomations = _automations.value?.toMutableList() ?: mutableListOf()
        updatedAutomations.add(automation)
        _automations.value = updatedAutomations
    }

    fun setAutomationStatus(automationID: Int, isOn: Boolean) {
        val updatedAutomations = _automations.value?.map { automation ->
            if (automation.id == automationID) automation.copy(isOn = isOn) else automation
        }
        _automations.value = updatedAutomations!!
    }
}