package com.example.myrecipes.adapter

import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipes.R
import com.example.myrecipes.data.IngredientList
import com.example.myrecipes.data.RecipeList
import com.example.myrecipes.viewmodel.RecipeApiStatus

/**
 * Updates the data shown in the [RecyclerView]
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: RecipeList?) {
    val adapter = recyclerView.adapter as RecipeListAdapter
    adapter.submitList(data?.recipes)
}

/**
 * Updates the data shown in the [RecyclerView]
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: IngredientList?) {
    val adapter = recyclerView.adapter as IngredientListAdapter
    adapter.submitList(data?.ingredients)
}

/**
 * Removes unnecessary characters and tags from text
 */
@BindingAdapter("stripHtml")
fun bindTextView(textView: TextView, text: String) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString();
    } else {
        textView.text = Html.fromHtml(text).toString();
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView)
            .load(imgUrl)
            .error(R.drawable.ic_broken_image)
            .fallback(R.drawable.ic_broken_image)
            .placeholder(R.drawable.loading_animation)
            .dontAnimate()
            .override(imgView.width, 500)
            .into(imgView)
    }
}

@BindingAdapter("ingredientImageUrl")
fun bindIngredientImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView)
            .load("https://spoonacular.com/cdn/ingredients_100x100/$imgUrl")
            .circleCrop()
            .error(R.drawable.ic_broken_image)
            .fallback(R.drawable.ic_broken_image)
            .into(imgView)
    }
}

/**
 * This binding adapter displays the [RecipeApiStatus] of the network request in an image view.
 * When the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: RecipeApiStatus?) {
    when (status) {
        RecipeApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        RecipeApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        RecipeApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        null -> {}
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(tryAgainButton: Button, status: RecipeApiStatus?) {
    when (status) {
        RecipeApiStatus.LOADING -> {
            tryAgainButton.visibility = View.GONE
        }
        RecipeApiStatus.DONE -> {
            tryAgainButton.visibility = View.GONE
        }
        RecipeApiStatus.ERROR -> {
            tryAgainButton.visibility = View.VISIBLE
        }
        null -> {}
    }
}