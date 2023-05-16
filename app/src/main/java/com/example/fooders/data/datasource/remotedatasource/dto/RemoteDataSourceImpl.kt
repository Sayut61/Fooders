package com.example.fooders.data.datasource.remotedatasource.dto

import com.example.fooders.data.datasource.remotedatasource.dto.randomrecipe.RandomRecipes
import com.example.fooders.data.datasource.remotedatasource.interceptors.ErrorInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RemoteDataSourceImpl : RemoteDataSource {

    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(ErrorInterceptor())
        .build()

    private var spoonApi = Retrofit.Builder()
        .baseUrl("https://statsapi.web.nhl.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var serviceForSpoonApi = spoonApi.create(RestSpoonApi::class.java)

    private interface RestSpoonApi {
        @GET(value = "/recipes/random")
        suspend fun getRandomRecipe(@Query("number") listSize: Int): RandomRecipes
    }

    override suspend fun getRandomRecipes(listSize: Int): RandomRecipes {
        return serviceForSpoonApi.getRandomRecipe(listSize)
    }
}