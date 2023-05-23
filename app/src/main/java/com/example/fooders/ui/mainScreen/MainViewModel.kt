package com.example.fooders.ui.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooders.domain.entities.RandomRecipeEntity
import com.example.fooders.domain.usecases.RecipesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipesUseCases: RecipesUseCases
): ViewModel() {
    private val _randomRecipesLiveData = MutableLiveData<List<RandomRecipeEntity>>()
    val randomRecipesData: LiveData<List<RandomRecipeEntity>> = _randomRecipesLiveData

    fun loadRecipes() {
        viewModelScope.launch {
            _randomRecipesLiveData.value = recipesUseCases.getRandomRecipes()
        }
    }
}