package com.example.myrecipes.data

data class RecipeList(val recipes: List<Recipe>)

data class Recipe(
    val id: Long,
    val image: String,
    val title: String,
    val summary: String,
    val pricePerServing: String
)
