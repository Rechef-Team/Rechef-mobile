package com.bangkit.rechef.data.remote

import com.bangkit.rechef.data.response.FoodResponse
import com.bangkit.rechef.data.response.RecipesItem
import com.bangkit.rechef.ui.Food
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("recipes") // Replace with your actual endpoint
    fun getFoodItems(): Call<List<Food>>

    @GET("get_recipes")
    fun getFoodRecipes(
        @Query("q") query: String
    ): Call<FoodResponse>

    @GET("get_recipe/{id}")
    fun getRecipeDetail(
        @Path("id") id: String
    ): Call<RecipesItem>
}
