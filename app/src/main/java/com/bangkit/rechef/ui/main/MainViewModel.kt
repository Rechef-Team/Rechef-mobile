package com.bangkit.rechef.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.rechef.data.remote.ApiConfig
import com.bangkit.rechef.data.response.FoodResponse
import com.bangkit.rechef.data.response.RecipesItem
import com.bangkit.rechef.ui.Food
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _food = MutableLiveData<List<RecipesItem>>()
    val food: LiveData<List<RecipesItem>> = _food

    private val _foodDetail = MutableLiveData<RecipesItem>()
    val foodDetail: LiveData<RecipesItem> = _foodDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _hasResult = MutableLiveData<Boolean>()
    val hasResult: LiveData<Boolean> = _hasResult

    companion object {
        private const val TAG = "MainViewModel"
        private const val FOOD = "rice"
    }

    init {
        fetchRecipes(FOOD)
    }

    fun fetchRecipes(food: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFoodRecipes(food)

        client.enqueue(object : Callback<FoodResponse> {
            override fun onResponse(
                call: Call<FoodResponse>,
                response: Response<FoodResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _food.value = response.body()?.recipes
                    val items = response.body()?.recipes ?: emptyList()
                    _hasResult.value = items.isNotEmpty()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetail(recipe: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRecipeDetail(recipe)

        client.enqueue(object : Callback<RecipesItem> {
            override fun onResponse(
                call: Call<RecipesItem>,
                response: Response<RecipesItem>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _foodDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecipesItem>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}