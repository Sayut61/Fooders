package com.example.fooders.data.repositories

import com.example.fooders.data.datasource.remotedatasource.dto.RemoteDataSource
import com.example.fooders.domain.entities.RandomRecipeEntity
import com.example.fooders.domain.repository.RandomRecipeRepository
import javax.inject.Inject

class RandomRecipeImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RandomRecipeRepository {

    override suspend fun getRandomRecipes(): List<RandomRecipeEntity> {
        return remoteDataSource.getRandomRecipes().recipes.map {
            RandomRecipeEntity(
                id = it.id,
                title = it.title,
                image = it.image
            )
        }
    }
}