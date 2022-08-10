package com.example.myrecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.databinding.ListViewRecipeItemBinding
import com.example.myrecipes.data.Recipe

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class RecipeListAdapter(val clickListener: RecipeListener) :
    ListAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(DiffCallback) {

    class RecipeViewHolder(
        var binding: ListViewRecipeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: RecipeListener, recipe: Recipe) {
            binding.recipe = recipe
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.title == newItem.title && oldItem.summary == newItem.summary
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecipeViewHolder(
            ListViewRecipeItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(clickListener, recipe)
    }
}

class RecipeListener(val clickListener: (recipe: Recipe) -> Unit) {
    fun onClick(recipe: Recipe) = clickListener(recipe)
}