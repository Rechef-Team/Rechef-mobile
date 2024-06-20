package com.bangkit.rechef.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.ImageView
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

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var ingredientsTextView: TextView
    private lateinit var urlTextView: TextView

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

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Close the current activity and return to the previous one
        }
    }
}