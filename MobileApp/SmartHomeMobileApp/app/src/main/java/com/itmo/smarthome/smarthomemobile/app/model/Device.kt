package com.itmo.smarthome.smarthomemobile.app.model

data class Device(
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    var isOn: Boolean
)
