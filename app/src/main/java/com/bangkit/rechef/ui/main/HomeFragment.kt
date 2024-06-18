package com.bangkit.rechef.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rechef.R
import com.bangkit.rechef.data.remote.ApiConfig
import com.bangkit.rechef.data.remote.RetrofitClient
import com.bangkit.rechef.data.response.FoodResponse
import com.bangkit.rechef.databinding.FragmentHomeBinding
import com.bangkit.rechef.ui.Food
import com.bangkit.rechef.ui.FoodAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bangkit.rechef.ui.utils.GridSpacing
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    private val PREFS_NAME = "com.bangkit.rechef.ui.main"
    private val SEARCH_QUERY_KEY = "search_query"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutButton.setOnClickListener {
            if (activity is MainActivity) {
                (activity as MainActivity).logout()
            }
        }

        setupRecyclerView()
        setupSearch()

        viewModel.food.observe(viewLifecycleOwner) { recipes ->
            binding.recyclerView.adapter = FoodAdapter(recipes)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Fetch initial food items
        viewModel.fetchRecipes("rice") // Default search
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = GridLayoutManager(context, 2) // 2 columns
            addItemDecoration(GridSpacing(2, dpToPx(32), true))
        }
    }

    private fun setupSearch() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString()
                if (query.isNotEmpty()) {
                    viewModel.fetchRecipes(query)
                    searchView.hide()
                }
                false
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    override fun onPause() {
        super.onPause()
        val editor = sharedPreferences.edit()
        editor.putString(SEARCH_QUERY_KEY, binding.searchView.text.toString())
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val query = sharedPreferences.getString(SEARCH_QUERY_KEY, "rice")
        binding.searchView.setText(query)
        viewModel.fetchRecipes(query ?: "rice")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
