package com.bangkit.rechef.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FoodResponse(

	@field:SerializedName("recipes")
	val recipes: List<RecipesItem>,

	@field:SerializedName("_cont")
	val cont: String,

	@field:SerializedName("page")
	val page: Page
)

data class Page(

	@field:SerializedName("from")
	val from: Int,

	@field:SerializedName("to")
	val to: Int
)

@Parcelize
data class RecipesItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("preparation_time")
	val preparationTime: Int? = null,

	@field:SerializedName("ingredients")
	val ingredients: List<String>? = null,

	@field:SerializedName("calories")
	val calories: Double? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable
