package com.bangkit.rechef.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rechef.R
import com.bangkit.rechef.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val loginButton: Button = findViewById(R.id.loginButton)
            val registerButton: Button = findViewById(R.id.registerButton)

            loginButton.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            registerButton.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setStatusBarColorAndIcons(colorResId: Int, isLightIcons: Boolean) {
        window.statusBarColor = getColor(colorResId)
        if (isLightIcons) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}
