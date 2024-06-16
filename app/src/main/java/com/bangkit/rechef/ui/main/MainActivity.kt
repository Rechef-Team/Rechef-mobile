package com.bangkit.rechef.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rechef.R
import com.bangkit.rechef.ui.Food
import com.bangkit.rechef.ui.FoodAdapter
import com.bangkit.rechef.ui.GridSpacing
import com.bangkit.rechef.ui.auth.SplashActivity
import com.bangkit.rechef.ui.bookmark.BookmarkFragment
import com.bangkit.rechef.ui.scan.ScanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()


        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            handleNavigation(item.itemId)
        }

        // Set default selection to "Home" screen
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
            bottomNavigationView.selectedItemId = R.id.nav_home // Set to "Home" screen
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun handleNavigation(itemId: Int): Boolean {
        when (itemId) {
            R.id.nav_home -> {
                loadFragment(HomeFragment())
                return true
            }
            R.id.nav_scan -> {
                loadFragment(ScanFragment())
                return true
            }
            R.id.nav_bookmark -> {
                loadFragment(BookmarkFragment())
                return true
            }
            else -> return false
        }
    }

    fun logout() {
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

