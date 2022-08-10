package com.example.myrecipes.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myrecipes.R
import com.example.myrecipes.adapter.RecipeListAdapter
import com.example.myrecipes.adapter.RecipeListener
import com.example.myrecipes.databinding.FragmentRecipeListBinding
import com.example.myrecipes.viewmodel.RecipeViewModel

class RecipeListFragment : Fragment() {
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater)
        binding.apply {
            lifecycleOwner = this@RecipeListFragment
            viewModel = recipeViewModel
            recyclerView.adapter = RecipeListAdapter(RecipeListener { recipe ->
                recipeViewModel.onRecipeClicked(recipe)
                findNavController()
                    .navigate(R.id.action_recipesFragment_to_recipeDetailFragment)
            })
        }
        return binding.root
    }
}