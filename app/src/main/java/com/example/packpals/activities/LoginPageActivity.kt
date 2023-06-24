package com.example.packpals.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.example.packpals.R
import com.example.packpals.viewmodels.LoginPageViewModel

class LoginPageActivity : AppCompatActivity() {
    private val viewModel: LoginPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val addLoginButton = findViewById<Button>(R.id.login)

        addLoginButton.setOnClickListener {
            val intent = Intent(this, NavigationDrawerViewActivity::class.java)
            startActivity(intent)
        }
    }
}