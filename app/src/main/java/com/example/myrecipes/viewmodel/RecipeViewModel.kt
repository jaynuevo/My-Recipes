package com.example.myrecipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipes.data.IngredientList
import com.example.myrecipes.data.Recipe
import com.example.myrecipes.data.RecipeList
import com.example.myrecipes.network.*
import kotlinx.coroutines.launch

enum class RecipeApiStatus { LOADING, ERROR, DONE }

class RecipeViewModel : ViewModel() {

    private val _status = MutableLiveData<RecipeApiStatus>()
    val status: LiveData<RecipeApiStatus> = _status

    private val _recipes = MutableLiveData<RecipeList>()
    val recipes: LiveData<RecipeList> = _recipes

    private val _ingredients = MutableLiveData<IngredientList>()
    val ingredients: LiveData<IngredientList> = _ingredients

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> = _recipe

    init {
        getRecipeList()
    }

    fun getRecipeList() {
        viewModelScope.launch {
            _status.value = RecipeApiStatus.LOADING
            try {
                _recipes.value = RecipeApi.retrofitService.getRecipes(20, API_KEY)
                _status.value = RecipeApiStatus.DONE
            } catch (e: Exception) {
                _status.value = RecipeApiStatus.ERROR
            }
        }
    }

    fun getIngredientList() {
        viewModelScope.launch {
            try {
                _ingredients.value =
                    _recipe.value?.let {
                        RecipeApi.retrofitService.getIngredients(
                            it.id,
                            "ingredientWidget.json", API_KEY
                        )
                    }
            } catch (e: Exception) {
            }
        }
    }

    fun onRecipeClicked(recipe: Recipe) {
        _recipe.value = recipe
    }
}