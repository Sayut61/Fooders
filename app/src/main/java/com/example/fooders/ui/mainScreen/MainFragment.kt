package com.example.fooders.ui.mainScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fooders.databinding.FrMainBinding
import com.example.fooders.ui.adapters.CategoriesAdapter
import com.example.fooders.ui.adapters.CategoriesListener
import com.example.fooders.ui.common.ConstantsFirebase.FIREBASE_STORAGE
import com.example.fooders.ui.common.ConstantsFirebase.ONE_MEGABYTE
import com.example.fooders.ui.common.ImagesAsset
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
        val imageRefs = ImagesAsset.CATEGORIES.values
        val images = mutableListOf<Bitmap>()

        imageRefs.forEach { bm ->
            storageRef.child(bm).getBytes(ONE_MEGABYTE)
                .addOnSuccessListener { bytes ->
                    try {
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        images.add(bitmap)

                        showCategories(ImagesAsset.CATEGORIES.keys.zip(images).toMap())
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { ex ->
                    Toast.makeText(requireContext(), ex.message, Toast.LENGTH_LONG).show()
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