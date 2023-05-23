package com.example.fooders.di

import com.example.fooders.data.datasource.remotedatasource.dto.RemoteDataSourceImpl
import com.example.fooders.data.repositories.RandomRecipeImpl
import com.example.fooders.domain.repository.RandomRecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRandomRecipesRepository(remoteDataSource: RemoteDataSourceImpl): RandomRecipeRepository {
        return RandomRecipeImpl(remoteDataSource)
    }
}