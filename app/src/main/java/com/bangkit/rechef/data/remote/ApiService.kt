package com.bangkit.rechef.data.remote

import com.bangkit.rechef.data.response.FoodResponse
import com.bangkit.rechef.data.response.PredictionResponse
import com.bangkit.rechef.data.response.RecipesItem
import com.bangkit.rechef.ui.Food
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("recipes")
    fun getFoodRecipes(
        @Query("q") query: String
    ): Call<FoodResponse>

    @Multipart
    @POST("prediction")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<PredictionResponse>
}
