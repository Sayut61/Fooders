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
import com.example.fooders.ui.utils.INetworkConnectivityService
import com.example.fooders.ui.utils.changeCatName
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class MainScreenStatus {
    SKELETON,
    CONTENT,
    ERROR,
    NONE
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipesUseCases: RecipesUseCases,
    private val networkConnectivityService: INetworkConnectivityService
) : ViewModel() {

    var screenStatus = MutableLiveData(MainScreenStatus.NONE)

    private val _randomRecipesLiveData = MutableLiveData<List<RandomRecipeEntity>>()
    val randomRecipesData: LiveData<List<RandomRecipeEntity>> = _randomRecipesLiveData

    private val _categoriesFromFirebase = MutableLiveData<Map<String, Bitmap>>()
    val categoriesFromFirebase: LiveData<Map<String, Bitmap>> = _categoriesFromFirebase

    private val _isConnectState = MutableLiveData<Boolean>()
    val isConnectState: LiveData<Boolean> = _isConnectState

    init {
        viewModelScope.launch {
            networkConnectivityService.checkNetworkConnection().catch {}.collect {
                if (screenStatus.value == MainScreenStatus.ERROR && it) {
                    _isConnectState.postValue(true)
                } else {
                    _isConnectState.postValue(false)
                }
            }
        }
    }

    fun loadRecipes() {
        viewModelScope.launch {
            try {
                _randomRecipesLiveData.value = recipesUseCases.getRandomRecipes()
                screenStatus.postValue(MainScreenStatus.CONTENT)
            } catch (ex: Exception) {
                screenStatus.postValue(MainScreenStatus.ERROR)
            }
        }
    }

    fun loadCategoriesFromFirebaseStorage(context: Context) {
        val storage = FirebaseStorage.getInstance(ConstantsFirebase.FIREBASE_STORAGE)
        val storageRef = storage.reference
        val imageRefs = storageRef.child(ConstantsFirebase.FIREBASE_CATEGORIES_FOLDER)
        val imageMap = mutableMapOf<String, Bitmap>()

        viewModelScope.launch {
            try {
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
                screenStatus.postValue(MainScreenStatus.CONTENT)
            } catch (ex: Exception) {
                screenStatus.postValue(MainScreenStatus.ERROR)
            }
        }
    }
}