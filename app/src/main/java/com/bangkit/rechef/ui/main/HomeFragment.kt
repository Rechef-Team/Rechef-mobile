package com.bangkit.rechef.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rechef.R
import com.bangkit.rechef.data.remote.RetrofitClient
import com.bangkit.rechef.ui.Food
import com.bangkit.rechef.ui.FoodAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bangkit.rechef.ui.utils.GridSpacing

class HomeFragment : Fragment() {

    private lateinit var logoutButton: View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutButton = view.findViewById(R.id.logoutButton)
        recyclerView = view.findViewById(R.id.recyclerView)

        logoutButton.setOnClickListener {
            if (activity is MainActivity) {
                (activity as MainActivity).logout()
            }
        }

        val gridLayoutManager = GridLayoutManager(context, 2) // 2 columns
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addItemDecoration(GridSpacing(2, dpToPx(32), true))

        fetchFoodItems()
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    private fun fetchFoodItems() {
        RetrofitClient.instance.getFoodItems().enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    val foodItems = response.body() ?: emptyList()
                    recyclerView.adapter = FoodAdapter(foodItems)
                } else {
                    // Handle the error
                }
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                // Handle the failure
            }
        })
    }
}
