package com.bangkit.rechef.ui.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.rechef.R
import com.bangkit.rechef.databinding.FragmentHomeBinding
import com.bangkit.rechef.databinding.FragmentRecipeBinding
import com.bangkit.rechef.databinding.FragmentScanBinding
import com.bangkit.rechef.ui.FoodAdapter
import com.bangkit.rechef.ui.main.MainViewModel
import com.bangkit.rechef.ui.utils.GridSpacing

class RecipeFragment: Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Get the className from the arguments
        val className = arguments?.getString("className") ?: ""

        // Fetch recipes with the className
        viewModel.fetchRecipes(className)
        binding.recipesResult.text = getString(R.string.recipes_result, className)

        // Observe the food LiveData from MainViewModel
        viewModel.food.observe(viewLifecycleOwner, Observer { recipes ->
            binding.recyclerView.adapter = FoodAdapter(recipes)
        })

        // Observe the loading state
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = GridLayoutManager(context, 2) // 2 columns
            addItemDecoration(GridSpacing(2, dpToPx(32), true))
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}