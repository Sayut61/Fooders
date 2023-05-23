package com.example.fooders.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooders.R
import com.example.fooders.databinding.MainBottomItemBinding
import com.example.fooders.domain.entities.RandomRecipeEntity
import com.example.fooders.ui.utils.loadImage

interface RandomRecipesListener {
    fun onRecipeClick(randomRecipe: RandomRecipeEntity)
}

class RandomRecipesAdapter(
    private val recipes: List<RandomRecipeEntity>,
    private val listener: RandomRecipesListener
) :
    RecyclerView.Adapter<RandomRecipesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomRecipesViewHolder {
        return RandomRecipesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_bottom_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RandomRecipesViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.itemView.setOnClickListener {
            listener.onRecipeClick(recipe)
        }
        holder.bind(recipe)
    }
}

class RandomRecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(randomRecipeEntity: RandomRecipeEntity) {
        val binding = MainBottomItemBinding.bind(itemView)
        loadImage(randomRecipeEntity.image, binding.logoImageView)
        binding.titleTv.text = randomRecipeEntity.title
    }
}