package com.example.fooders.ui.mainScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.example.fooders.ui.common.ConstantsFirebase
import com.example.fooders.ui.utils.changeCatName
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment(), CategoriesListener, RandomRecipesListener {
    private val viewModel: MainViewModel by viewModels()
    private var _binding: FrMainBinding? = null
    private val binding get() = _binding!!

    private var imageMap = mutableMapOf<String, Bitmap>()

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

        viewModel.checkNetwork()
        loadCategories(requireContext())

        if (viewModel.randomRecipesData.value == null) {
            viewModel.loadData()
        }

        viewModel.randomRecipesData.observe(viewLifecycleOwner) {
            showRandomRecipes(it)
        }

        binding.errorScreen.reloadBtn.setOnClickListener {
            if (viewModel.isConnectState.value == true) {
                viewModel.loadData()
                loadCategories(requireContext())
            } else {

            }
        }

        viewModel.screenStatus.observe(viewLifecycleOwner) {
            when (it) {
                MainScreenStatus.ERROR -> {
                    showError()
                }

                MainScreenStatus.CONTENT -> {
                    showContent()
                }

                MainScreenStatus.SKELETON -> {

                }

                else -> {
                }
            }
        }
    }

    private fun showContent() {
        binding.errorScreen.root.visibility = View.GONE
        binding.mainTopMenu.root.visibility = View.VISIBLE
        binding.rv1.visibility = View.VISIBLE
        binding.rv2.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.errorScreen.root.visibility = View.VISIBLE
        binding.mainTopMenu.root.visibility = View.GONE
        binding.rv1.visibility = View.GONE
        binding.rv2.visibility = View.GONE
    }

    private fun loadCategories(context: Context) {
        if (imageMap.isNotEmpty()) {
            showCategories(imageMap)
        } else {
            val storage = FirebaseStorage.getInstance(ConstantsFirebase.FIREBASE_STORAGE)
            val storageRef = storage.reference
            val imageRefs = storageRef.child(ConstantsFirebase.FIREBASE_CATEGORIES_FOLDER)

            imageRefs.listAll().addOnSuccessListener { listResult ->
                listResult.items.forEach { item ->
                    item.getBytes(ConstantsFirebase.ONE_MEGABYTE)
                        .addOnSuccessListener { bytes ->
                            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            imageMap[changeCatName(context, item.name)] = bitmap
                            showCategories(imageMap)
                        }
                }
            }
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