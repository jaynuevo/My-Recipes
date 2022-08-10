package com.example.myrecipes.network

import com.example.myrecipes.data.IngredientList
import com.example.myrecipes.data.RecipeList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.spoonacular.com/"
private const val RANDOM_RECIPE_URL = "recipes/random"
private const val RECIPE_URL = "recipes/{id}/{ingredientWidget}"
private const val NUMBER_URL = "number"
private const val API_KEY_URL = "apiKey"
//CHANGE API KEY
const val API_KEY = "ecd86a9035e04bca882759cb41f2525b"

// Build the Moshi object with Kotlin adapter factory that Retrofit will be using to parse JSON
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Build a Retrofit object with the Moshi converter
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RecipeApiService {
    @GET(RANDOM_RECIPE_URL)
    suspend fun getRecipes(
        @Query(NUMBER_URL) number: Int,
        @Query(API_KEY_URL) apiKey: String
    ): RecipeList

    @GET(RECIPE_URL)
    suspend fun getIngredients(
        @Path("id") id: Long,
        @Path("ingredientWidget") ingredientWidget: String,
        @Query(API_KEY_URL) apiKey: String
    ): IngredientList
}

object RecipeApi {
    val retrofitService: RecipeApiService by lazy {
        retrofit.create(RecipeApiService::class.java)
    }
}