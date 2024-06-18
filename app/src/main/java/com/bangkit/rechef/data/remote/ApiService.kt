package com.bangkit.rechef.data.remote

import com.bangkit.rechef.ui.Food
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("recipes") // Replace with your actual endpoint
    fun getFoodItems(): Call<List<Food>>


}
