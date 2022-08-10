package com.example.myrecipes.data

data class IngredientList(val ingredients: List<Ingredient>)
data class Ingredient(
    val image: String,
    val name: String,
    val amount: Amount
)

data class Amount(
    val metric: Metric,
    val us: Us
)

data class Us(
    val value: String,
    val unit: String
)

data class Metric(
    val value: String,
    val unit: String
)