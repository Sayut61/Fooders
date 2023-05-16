package com.example.fooders.data.datasource.remotedatasource.dto.randomrecipe

data class RandomRecipes(
    val recipes: List<Recipes>
)

data class Recipes(
    val id: Int,
    val title: String,
    val image: String
)