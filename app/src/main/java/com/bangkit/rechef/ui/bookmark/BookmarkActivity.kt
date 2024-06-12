package com.bangkit.rechef.ui.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rechef.R
import com.bangkit.rechef.ui.main.MainActivity
import com.bangkit.rechef.ui.scan.ScanActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class BookmarkActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    navigateToHome()
                    true
                }
                R.id.nav_scan -> {
                    navigateToScan()
                    true
                }
                R.id.nav_bookmark -> {
                    true
                }
                else -> false
            }
        }
        // Set the selected item to "Scan"
        bottomNavigationView.selectedItemId = R.id.nav_bookmark
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToScan() {
        startActivity(Intent(this, ScanActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
