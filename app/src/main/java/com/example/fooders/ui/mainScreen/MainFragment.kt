package com.example.fooders.ui.mainScreen

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.fooders.databinding.FrMainBinding
import com.example.fooders.domain.entities.RandomRecipeEntity
import com.example.fooders.ui.adapters.CategoriesAdapter
import com.example.fooders.ui.adapters.CategoriesListener
import com.example.fooders.ui.adapters.RandomRecipesAdapter
import com.example.fooders.ui.adapters.RandomRecipesListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), CategoriesListener, RandomRecipesListener {
    private val viewModel: MainViewModel by viewModels()
    private var _binding: FrMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.categoriesRV.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.randomRecipesRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(binding.randomRecipesRV)


        if (viewModel.randomRecipesData.value == null) {
            viewModel.loadRecipes()
        }

        if (viewModel.categoriesFromFirebase.value == null) {
            viewModel.loadCategoriesFromFirebaseStorage(requireContext())
        }

        viewModel.randomRecipesData.observe(viewLifecycleOwner) {
            showRandomRecipes(it)
        }

        viewModel.categoriesFromFirebase.observe(viewLifecycleOwner) {
            showCategories(it)
        }
    }

    private fun showCategories(categories: Map<String, Bitmap>) {
        val adapter = CategoriesAdapter(categories.keys.toList(), categories.values.toList(), this)
        binding.categoriesRV.adapter = adapter
    }

    override fun onCategoryClick(category: Any) {
        TODO("Not yet implemented")
    }

    private fun showRandomRecipes(recipes: List<RandomRecipeEntity>) {
        val adapter = RandomRecipesAdapter(recipes, this)
        binding.randomRecipesRV.adapter = adapter
    }

    override fun onRecipeClick(randomRecipe: RandomRecipeEntity) {
        TODO("Not yet implemented")
    }

}