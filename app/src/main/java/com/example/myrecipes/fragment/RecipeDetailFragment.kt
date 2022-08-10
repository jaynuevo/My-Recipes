package com.example.myrecipes.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myrecipes.R
import com.example.myrecipes.adapter.IngredientListAdapter
import com.example.myrecipes.databinding.FragmentRecipeDetailBinding
import com.example.myrecipes.viewmodel.RecipeViewModel

class RecipeDetailFragment : Fragment() {
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater)
        recipeViewModel.getIngredientList()
        binding.apply {
            lifecycleOwner = this@RecipeDetailFragment
            viewModel = recipeViewModel
            recyclerView.adapter = IngredientListAdapter()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeViewModel.ingredients.observe(this.viewLifecycleOwner) {
            binding.ingredientsLabel.text = getString(
                R.string.label_ingredients,
                recipeViewModel.ingredients.value?.ingredients?.size.toString()
            )
        }
    }
}