package com.example.fooders.ui.mainScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooders.domain.entities.RandomRecipeEntity
import com.example.fooders.domain.usecases.RecipesUseCases
import com.example.fooders.ui.common.ConstantsFirebase
import com.example.fooders.ui.utils.changeCatName
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipesUseCases: RecipesUseCases
) : ViewModel() {
    private val _randomRecipesLiveData = MutableLiveData<List<RandomRecipeEntity>>()
    val randomRecipesData: LiveData<List<RandomRecipeEntity>> = _randomRecipesLiveData

    private val _categoriesFromFirebase = MutableLiveData<Map<String, Bitmap>>()
    val categoriesFromFirebase: LiveData<Map<String, Bitmap>> = _categoriesFromFirebase

    private var isCategoriesLoaded = false
    private var isRandomRecipesLoaded = false

    fun loadRecipes() {
        if (isRandomRecipesLoaded) {
            return
        }
        viewModelScope.launch {
            _randomRecipesLiveData.value = recipesUseCases.getRandomRecipes()
        }
        isRandomRecipesLoaded = true
    }

    fun loadCategoriesFromFirebaseStorage(context: Context) {
        if (isCategoriesLoaded) {
            return
        }
        val storage = FirebaseStorage.getInstance(ConstantsFirebase.FIREBASE_STORAGE)
        val storageRef = storage.reference
        val imageRefs = storageRef.child(ConstantsFirebase.FIREBASE_CATEGORIES_FOLDER)
        val imageMap = mutableMapOf<String, Bitmap>()

        viewModelScope.launch {
            imageRefs.listAll().addOnSuccessListener { listResult ->
                listResult.items.forEach { item ->
                    item.getBytes(ConstantsFirebase.ONE_MEGABYTE)
                        .addOnSuccessListener { bytes ->
                            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            imageMap[changeCatName(context, item.name)] = bitmap
                            _categoriesFromFirebase.value = imageMap
                        }
                }
            }
        }
        isCategoriesLoaded = true
    }
}