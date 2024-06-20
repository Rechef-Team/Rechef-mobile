package com.bangkit.rechef.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rechef.R
import com.bangkit.rechef.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        enableEdgeToEdge()


        auth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)
        val googleButton: MaterialButton = findViewById(R.id.btn_google)

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Check if user is already logged in
        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email, password)
            } else {
                Toast.makeText(this, "Please fill out all the fields.", Toast.LENGTH_SHORT).show()
            }
        }

        googleButton.setOnClickListener {
            signInWithGoogle()
        }

        val signUp = findViewById<TextView>(R.id.sign_in)
        signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val forgot_password = findViewById<TextView>(R.id.tv_forgot_password)
        forgot_password.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Authentication Successful.", Toast.LENGTH_SHORT).show()

                    // Save login state
                    val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Google sign in successful.", Toast.LENGTH_SHORT).show()

                    // Save login state
                    val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(baseContext, "Google sign in failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
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
