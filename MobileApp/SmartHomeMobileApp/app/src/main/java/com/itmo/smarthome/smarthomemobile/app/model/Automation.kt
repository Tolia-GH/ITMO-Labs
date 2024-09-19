package com.itmo.smarthome.smarthomemobile.app.model

import android.media.VolumeShaper.Operation
import java.sql.Time

data class Automation(
    val id: Int,
    val name: String,
    val description: String,
    val time: String, // using string format like HH:mm
    val deviceID: Int,
    val operation: Boolean, // true to turn on, false to turn off
    var isOn: Boolean
)
