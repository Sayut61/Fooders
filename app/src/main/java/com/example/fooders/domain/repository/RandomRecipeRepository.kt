package com.example.fooders.domain.repository

import com.example.fooders.domain.entities.RandomRecipeEntity

interface RandomRecipeRepository {
    suspend fun getRandomRecipes(): List<RandomRecipeEntity>
}