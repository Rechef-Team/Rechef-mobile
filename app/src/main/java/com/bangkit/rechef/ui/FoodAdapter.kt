package com.bangkit.rechef.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rechef.R
import com.bangkit.rechef.data.response.RecipesItem
import com.bangkit.rechef.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

data class Food(val name: String, val image: String, val preparationTime: Int, val calories: Int)

class FoodAdapter(private val foodList: List<RecipesItem>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]

        // Log the image URL
        d("FoodAdapter", "Loading image: ${food.image}")

        Glide.with(holder.itemView.context)
            .load(food.image)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
            .error(R.drawable.error_image) // Error image if loading fails
            .into(holder.foodImage)

        holder.foodName.text = food.name
        holder.timeTextView.text = "${food.preparationTime} Min"
        holder.caloriesTextView.text = "${Math.round(food.calories)} kcal"

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("food", food)  // Pass RecipesItem to DetailActivity
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = foodList.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.imageView)
        val foodName: TextView = itemView.findViewById(R.id.foodTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)
    }
}
