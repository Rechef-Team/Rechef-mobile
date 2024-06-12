package com.bangkit.rechef.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rechef.R
import com.bangkit.rechef.ui.scan.ScanActivity
import com.bangkit.rechef.ui.auth.SplashActivity
import com.bangkit.rechef.ui.bookmark.BookmarkActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            logout()
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            handleNavigation(item.itemId)
        }

        // Set default selection to "Home" screen
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_home // Set to "Home" screen
        }
    }

    private fun handleNavigation(itemId: Int): Boolean {
        when (itemId) {
            R.id.nav_scan -> {
                navigateToScan()
                return true
            }
            R.id.nav_bookmark -> {
                navigateToBookmark()
                return true
            }
            else -> return false
        }
    }

    private fun navigateToScan() {
        startActivity(Intent(this, ScanActivity::class.java))
    }

    private fun navigateToBookmark() {
        startActivity(Intent(this, BookmarkActivity::class.java))
    }

    private fun logout() {
        // Sign out from Firebase
        auth.signOut()

        // Clear login state
        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        // Redirect to SplashActivity
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }
}

