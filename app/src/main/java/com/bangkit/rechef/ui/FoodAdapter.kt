package com.bangkit.rechef.ui

import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rechef.R
import com.bangkit.rechef.data.response.RecipesItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

data class Food(val name: String, val image: String, val preparationTime: Int, val calories: Int)

class FoodAdapter(private var foodList: List<RecipesItem>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]

        // Log the image URL
        d("FoodAdapter", "Loading image: ${food.image}")
        val testImageUrl = "https://edamam-product-images.s3.amazonaws.com/web-img/816/816e8f81389feef3d11a15681ff8185d.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEGgaCXVzLWVhc3QtMSJGMEQCIAcHKxB8O1jiYl3AP3ifaZm9BYMo8yIsrd%2FbzjGaLLa5AiBmtuDdfOR1a5frLGGZcTaJOznsGjHAyAC65Lj75YNycCq5BQgQEAAaDDE4NzAxNzE1MDk4NiIMKL0sQgCxgVmi3nAGKpYFGve3ASJ8zXHGbagnFuwePJirkLqmxKWJ6kpKNao6zM21HWtCGutvOjC6UodVhadSu0rmdN2oEN5ZUi6q%2Byhluo9e%2BOh1bLe3hA0ZfU4n3%2BkWhwFD6ST05h5INYzFGw2X96e3V3U3UKtcJo%2BjrW8C2TjMQEaC7iuGPZ1MISZ%2Bfo%2BMaqzp0Us4TdLZAfu8RPFn1QFGUk%2BQRi5ou0QulyXgORLnm7b%2FHC7VLH4%2BUWnpcTDEmzdKGMuouZYPSuFd%2BIsOSc1ONYon6af5G%2FzhlPP6%2FrYRtGnh69GAjzrD9Bav4qiCB00fHuqa%2FhY5HAKL65D14EmCJA9%2BaVzbB3ERp35kWdDFr%2F%2BOEZ6lGh7ZayLe7UcnTEp9NmZ9jLx9BNk4cE43HOBg9cy1Q1SBVCwFryBUGGYzsyeYsvrJxshUeMpslBdSDO0uy1HpEY%2Fhvmxock%2BnLl8RDGTtowzSHn9PN7B9XM%2BUg96VsgOjnmrknoJJ9y1Qqb%2FEE1W7J3rBeaAXtfxZe8SqC2gZSwXF%2BMnJcCC1wJiHXc7ETPGOQmqg1mYuiHIfKMLjheFW%2FkTwgJBjsT9fRsgqvATzfMjm%2BHtrx52V2kmQ%2BofCkTWWB1MAm7SWkiucfz2CHMZRmOiy0xvIZC6tDNEOJ1Urrf6khUfjM0Uht3ySMLsvs1u3TfEWZcidEWCkdR58ieFBsmRWY2WzV0GsfMt%2BsPT5NBuCjeamuMthC5%2BM7cQjJPFCgk21TOAu83dmeptoQIKXSA7HTxfdGOPC3n9ocZtTmBtT4l2mnCP3x41KsAKZrJw%2FC6DljdxfqrtvEixmp8os7IR4ch0Ke%2Bq4a2NioBc4SQugNGB3NNNZeM9zilQ6kdEpuO8JQbS5NHjUPiNwHqgw%2F%2BXEswY6sgES5VPYydP8aAvNTcqjvM9DOYsoxkwH%2F%2FUNat1dNemhHEI931kMs2riVu2LH%2FKnJ1jrr%2FepkrAxJo2PXl0WAaGi0BkWUXDhAC4%2FTtbw%2FloAD7t%2FquV8T3GmciTF%2B2XC3gQvFARsT866EiwRGVQIkudv%2BiqGf7AKoEIxxgMS7E3zMP%2B%2BavvIBRyEkv3xVK7BaMHm8%2BR9nC5d78EQUFMIhPA6eJATr%2BcwLrO2oWnEjD4aXmsL&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240618T072805Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFK4AKACO6%2F20240618%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=f47fe7a12d23099f80d005dde55afa1b82f03654fa6d33168962a676a7b38beb"


        holder.foodName.text = food.name
        Glide.with(holder.itemView.context)
            .load(food.image)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
            .into(holder.foodImage)

        holder.timeTextView.text = "${food.preparationTime} Min"
        holder.caloriesTextView.text = "${Math.round(food.calories)} kcal"
    }

    override fun getItemCount() = foodList.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.imageView)
        val foodName: TextView = itemView.findViewById(R.id.foodTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)
    }
}
