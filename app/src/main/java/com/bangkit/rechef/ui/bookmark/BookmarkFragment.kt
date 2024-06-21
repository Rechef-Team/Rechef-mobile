package com.bangkit.rechef.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rechef.R
import com.bangkit.rechef.data.response.RecipesItem
import com.bangkit.rechef.databinding.FragmentBookmarkBinding
import com.bangkit.rechef.ui.FoodAdapter
import com.bangkit.rechef.ui.utils.GridSpacing
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects

class BookmarkFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: FoodAdapter
    private val bookmarks = mutableListOf<RecipesItem>()
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerView)
        setupRecyclerView()
        fetchBookmarks()
        return view
    }

    private fun fetchBookmarks() {
        val userId = auth.currentUser?.uid ?: return
        progressBar.visibility = View.VISIBLE

        firestore.collection("bookmarks")
            .whereEqualTo("ownerId", userId)
            .get()
            .addOnSuccessListener { result ->
                bookmarks.clear()
                bookmarks.addAll(result.toObjects<RecipesItem>())
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                progressBar.visibility = View.GONE
            }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 columns
        recyclerView.addItemDecoration(GridSpacing(2, dpToPx(32), true))
        adapter = FoodAdapter(bookmarks)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        fetchBookmarks()
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}