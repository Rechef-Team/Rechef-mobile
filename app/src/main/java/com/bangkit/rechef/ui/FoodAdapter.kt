package com.bangkit.rechef.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rechef.R

data class Food(val name: String, val imageResId: Int, val time: Int, val calories: Int)

class FoodAdapter(private val foodList: List<Food>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.foodName.text = food.name
        holder.foodImage.setImageResource(food.imageResId)
        holder.timeTextView.text = "${food.time} Min"
        holder.caloriesTextView.text = "${food.calories} kcal"
    }

    override fun getItemCount() = foodList.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.imageView)
        val foodName: TextView = itemView.findViewById(R.id.textView8)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)
    }
}