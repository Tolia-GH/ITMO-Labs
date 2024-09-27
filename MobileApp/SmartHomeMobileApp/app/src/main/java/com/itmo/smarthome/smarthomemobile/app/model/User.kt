package com.itmo.smarthome.smarthomemobile.app.model

import org.w3c.dom.Text

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val role: String
)
