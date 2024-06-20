package com.bangkit.rechef.ui.detail

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.rechef.R
import com.bangkit.rechef.data.response.RecipesItem
import com.bangkit.rechef.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var ingredientsTextView: TextView
    private lateinit var urlTextView: TextView
    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private var isBookmarked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        window.statusBarColor = getColor(R.color.primary)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ingredientsTextView = findViewById(R.id.ingredientsTextView)
        urlTextView = findViewById(R.id.urlTextView)

        val food: RecipesItem? = intent.getParcelableExtra("food")
        food?.let {
            binding.recipeTextView.text = food.name
            Glide.with(this)
                .load(food.image)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(500)))
                .into(binding.imageView)
            binding.timeTextView.text = getString(R.string.time, food.preparationTime.toString())
            binding.caloriesTextView.text = getString(R.string.calories, Math.round(food.calories).toString())

            // Set ingredients as bulleted list using HTML
            val ingredients = food.ingredients.joinToString("<br/> &bull; ")
            val formattedIngredients = "&bull; $ingredients"
            ingredientsTextView.text = Html.fromHtml(formattedIngredients, Html.FROM_HTML_MODE_COMPACT)

            // Set URL
            urlTextView.apply {
                text = "${food.url}"
                movementMethod = LinkMovementMethod.getInstance()  // Enable links to be clickable
                setOnClickListener { view ->
                    val url = (view as TextView).text.toString()
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
        }

        // Initialize bookmark FAB
        val fabBookmark = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        // Check if the recipe is already bookmarked and update FAB icon
        checkBookmarkStatus()

        // Toggle bookmark on FAB click
        fabBookmark.setOnClickListener {
            isBookmarked = !isBookmarked
            if (isBookmarked) {
                if (food != null) {
                    addBookmark(food)
                }
            } else {
                if (food != null) {
                    removeBookmark(food)
                }
            }
        }
    }

    private fun checkBookmarkStatus() {
        // Implement logic to check if the current recipe is bookmarked by the user
        // For now, let's assume it's not bookmarked
        isBookmarked = false
        updateBookmarkIcon()
    }

    private fun updateBookmarkIcon() {
        // Update FAB icon based on bookmark status
        if (isBookmarked) {
            binding.floatingActionButton.setImageResource(R.drawable.ic_bookmark_fill)
        } else {
            binding.floatingActionButton.setImageResource(R.drawable.ic_bookmark_no_fill)
        }
    }

    private fun addBookmark(food: RecipesItem) {
        // Implement logic to add the current recipe to bookmarks
        // For now, let's assume successful bookmarking
        firestore.collection("bookmarks")
            .add(mapOf(
                "ownerId" to auth.currentUser?.uid,
                "name" to food.name,
                "image" to food.image,
                "preparationTime" to food.preparationTime,
                "calories" to food.calories,
                "ingredients" to food.ingredients,
                "url" to food.url
            ))
            .addOnSuccessListener {
                Snackbar.make(binding.root, "Recipe bookmarked successfully!", Snackbar.LENGTH_SHORT).show()
                isBookmarked = true
                updateBookmarkIcon()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to bookmark recipe", e)
                Snackbar.make(binding.root, "Failed to bookmark recipe", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun removeBookmark(food: RecipesItem) {
        // Implement logic to remove the current recipe from bookmarks
        // For now, let's assume successful removal
        firestore.collection("bookmarks")
            .whereEqualTo("ownerId", auth.currentUser?.uid)
            .whereEqualTo("name", food.name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                }
                Snackbar.make(binding.root, "Bookmark removed successfully!", Snackbar.LENGTH_SHORT).show()
                isBookmarked = false
                updateBookmarkIcon()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to remove bookmark", e)
                Snackbar.make(binding.root, "Failed to remove bookmark", Snackbar.LENGTH_SHORT).show()
            }
    }
}