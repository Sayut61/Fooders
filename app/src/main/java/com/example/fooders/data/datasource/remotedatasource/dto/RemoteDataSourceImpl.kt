package com.example.fooders.data.datasource.remotedatasource.dto

import com.example.fooders.data.datasource.remotedatasource.dto.randomrecipe.RandomRecipes
import com.example.fooders.data.datasource.remotedatasource.interceptors.ErrorInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor() : RemoteDataSource {

    private val client = OkHttpClient()
        .newBuilder()
        //.addInterceptor(HeaderInterceptor())
        .addInterceptor(ErrorInterceptor())
        .build()

    private var spoonApi = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var serviceForSpoonApi = spoonApi.create(RestSpoonApi::class.java)

    private interface RestSpoonApi {
        @GET(value = "/recipes/random?number=10")
        suspend fun getRandomRecipe(): RandomRecipes
    }

    override suspend fun getRandomRecipes(): RandomRecipes {
        return serviceForSpoonApi.getRandomRecipe()
    }
}