package com.example.fooders.ui.mainScreen

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fooders.databinding.FrMainBinding
import com.google.firebase.storage.FirebaseStorage

class MainFragment: Fragment() {

    private var _binding: FrMainBinding? = null

    private val binding get() = _binding!!

    private val storage = FirebaseStorage.getInstance("gs://fooders-4d468.appspot.com")
    private val storageRef = storage.reference
    private val imageRef = storageRef.child("/main_menu_categories/ic_salad.jpg")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        val ONE_MEGABYTE = (1024 * 1024).toLong()
        imageRef.getBytes(ONE_MEGABYTE)
            .addOnSuccessListener { bytes ->
                // Обработка успешной загрузки изображения
                try {
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    //binding.imageButton.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    // Обработка ошибки
                }
            }
            .addOnFailureListener {
                // Обработка ошибки загрузки изображения
            }
        return binding.root

    }
}