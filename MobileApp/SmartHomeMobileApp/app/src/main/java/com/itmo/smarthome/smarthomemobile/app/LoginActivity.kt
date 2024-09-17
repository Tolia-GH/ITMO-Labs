package com.itmo.smarthome.smarthomemobile.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide() // hide action bar on the top of app

        val etUsername: EditText = findViewById(R.id.et_login_username)
        val etPassword: EditText = findViewById(R.id.et_login_password)
        val btnLogin: Button = findViewById(R.id.bt_login)
        val btnRegister: Button = findViewById(R.id.bt_register)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // 处理登录逻辑
                Toast.makeText(this, "Welcome! $username", Toast.LENGTH_SHORT).show()
                // 在这里添加验证逻辑
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}