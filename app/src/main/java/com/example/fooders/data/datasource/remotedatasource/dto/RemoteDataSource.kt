package com.example.fooders.data.datasource.remotedatasource.dto

import com.example.fooders.data.datasource.remotedatasource.dto.randomrecipe.RandomRecipes

interface RemoteDataSource {
    suspend fun getRandomRecipes(): RandomRecipes
}