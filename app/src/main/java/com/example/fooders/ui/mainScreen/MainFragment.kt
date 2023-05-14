package com.example.fooders.ui.mainScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fooders.databinding.FrMainBinding
import com.example.fooders.ui.adapters.CategoriesAdapter
import com.example.fooders.ui.adapters.CategoriesListener
import com.example.fooders.ui.common.ConstantsFirebase.FIREBASE_CATEGORIES_FOLDER
import com.example.fooders.ui.common.ConstantsFirebase.FIREBASE_STORAGE
import com.example.fooders.ui.common.ConstantsFirebase.ONE_MEGABYTE
import com.example.fooders.ui.utils.changeCatName
import com.google.firebase.storage.FirebaseStorage

class MainFragment : Fragment(), CategoriesListener {
    private var _binding: FrMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        binding.categoriesRV.layoutManager = GridLayoutManager(requireContext(), 3)
        getImagesFromFirebaseStorage()

        return binding.root
    }

    private fun getImagesFromFirebaseStorage() {
        val storage = FirebaseStorage.getInstance(FIREBASE_STORAGE)
        val storageRef = storage.reference
        val imageRefs = storageRef.child(FIREBASE_CATEGORIES_FOLDER)
        val imageMap = mutableMapOf<String, Bitmap>()

        imageRefs.listAll().addOnSuccessListener { listResult ->
            listResult.items.forEach { item ->
                item.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    imageMap[changeCatName(requireContext(), item.name)] = bitmap
                    showCategories(categories = imageMap)
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

}