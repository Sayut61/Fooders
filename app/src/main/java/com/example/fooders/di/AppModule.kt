package com.example.fooders.di

import android.content.Context
import com.example.fooders.data.datasource.remotedatasource.dto.RemoteDataSourceImpl
import com.example.fooders.data.repositories.RandomRecipeImpl
import com.example.fooders.domain.repository.RandomRecipeRepository
import com.example.fooders.ui.utils.INetworkConnectivityService
import com.example.fooders.ui.utils.NetworkConnectivityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNetworkConnectivityService(@ApplicationContext context: Context): INetworkConnectivityService {
        return NetworkConnectivityService(context)
    }
    @Provides
    @Singleton
    fun provideRandomRecipesRepository(remoteDataSource: RemoteDataSourceImpl): RandomRecipeRepository {
        return RandomRecipeImpl(remoteDataSource)
    }
}