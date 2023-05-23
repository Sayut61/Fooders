package com.example.fooders.domain.usecases

import com.example.fooders.domain.entities.RandomRecipeEntity
import com.example.fooders.domain.repository.RandomRecipeRepository
import javax.inject.Inject

class RecipesUseCases @Inject constructor(
    private val randomRecipeRepository: RandomRecipeRepository
) {
    suspend fun getRandomRecipes(): List<RandomRecipeEntity> {
        return randomRecipeRepository.getRandomRecipes()
    }
}