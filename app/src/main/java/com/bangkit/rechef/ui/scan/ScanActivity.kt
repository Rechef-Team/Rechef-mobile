package com.bangkit.rechef.ui.scan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rechef.R
import com.bangkit.rechef.ui.bookmark.BookmarkActivity
import com.bangkit.rechef.ui.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ScanActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    navigateToHome()
                    true
                }
                R.id.nav_scan -> {
                    true
                }
                R.id.nav_bookmark -> {
                    navigateToBookmark()
                    true
                }
                else -> false
            }
        }
        // Set the selected item to "Scan"
        bottomNavigationView.selectedItemId = R.id.nav_scan
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Optional: Close the current activity to prevent it from being kept in the back stack
    }

    private fun navigateToBookmark() {
        startActivity(Intent(this, BookmarkActivity::class.java))
        finish() // Optional: Close the current activity to prevent it from being kept in the back stack
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
