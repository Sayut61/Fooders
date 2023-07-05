package com.example.fooders.ui.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooders.domain.entities.RandomRecipeEntity
import com.example.fooders.domain.usecases.RecipesUseCases
import com.example.fooders.ui.utils.INetworkConnectivityService
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

    private val _isConnectState = MutableLiveData<Boolean>()
    val isConnectState: LiveData<Boolean> = _isConnectState

    fun checkNetwork() {
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

    fun loadData() {
        viewModelScope.launch {
            try {
                _randomRecipesLiveData.value = recipesUseCases.getRandomRecipes()
            } catch (ex: Exception) {
                screenStatus.postValue(MainScreenStatus.ERROR)
            }
        }
        screenStatus.postValue(MainScreenStatus.CONTENT)
    }
}