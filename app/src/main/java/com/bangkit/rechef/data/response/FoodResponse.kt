package com.bangkit.rechef.data.response

import com.google.gson.annotations.SerializedName

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

data class RecipesItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("preparation_time")
	val preparationTime: Int,

	@field:SerializedName("ingredients")
	val ingredients: List<String>,

	@field:SerializedName("calories")
	val calories: Double,

	@field:SerializedName("url")
	val url: String
)
