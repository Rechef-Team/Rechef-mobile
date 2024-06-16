package com.bangkit.rechef.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rechef.R
import com.bangkit.rechef.ui.Food
import com.bangkit.rechef.ui.FoodAdapter
import com.bangkit.rechef.ui.GridSpacing

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
            // Call logout function in MainActivity
            if (activity is MainActivity) {
                (activity as MainActivity).logout()
            }
        }

        val gridLayoutManager = GridLayoutManager(context, 2) // 2 columns
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addItemDecoration(GridSpacing(2, dpToPx(32), true))

        val adapter = FoodAdapter(getFoodItems())
        recyclerView.adapter = adapter
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    // This function simulates fetching the list of food items
    private fun getFoodItems(): List<Food> {
        return listOf(
            Food("Corn Soup", R.drawable.ic_rechef, 30, 90),
            Food("Fried Shrimp", R.drawable.ic_rechef, 30, 90),
            Food("Corn Soup", R.drawable.ic_rechef, 30, 90),
            Food("Fried Shrimp", R.drawable.ic_rechef, 30, 90),
            Food("Corn Soup", R.drawable.ic_rechef, 30, 90),
            Food("Fried Shrimp", R.drawable.ic_rechef, 30, 90),
            Food("Corn Soup", R.drawable.ic_rechef, 30, 90),
            Food("Fried Shrimp", R.drawable.ic_rechef, 30, 90),
        )
    }
}