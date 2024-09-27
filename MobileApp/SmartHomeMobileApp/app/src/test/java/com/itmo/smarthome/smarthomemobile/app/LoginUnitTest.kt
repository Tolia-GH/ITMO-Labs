package com.itmo.smarthome.smarthomemobile.app

import android.app.Activity
import org.junit.Test

import org.junit.Assert.*
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.test.core.app.ActivityScenario
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginUnitTest {

    private lateinit var scenario: ActivityScenario<LoginActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(LoginActivity::class.java)
    }

    @Test
    fun testSuccessfulLogin() {
        scenario.onActivity { activity ->
            val etUsername: EditText = activity.findViewById(R.id.et_login_username)
            val etPassword: EditText = activity.findViewById(R.id.et_login_password)
        }
    }
}