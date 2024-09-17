package com.itmo.smarthome.smarthomemobile.app.ui.automation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AutomationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is automation Fragment"
    }
    val text: LiveData<String> = _text
}