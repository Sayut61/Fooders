package com.example.fooders.ui.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooders.R
import com.example.fooders.databinding.MainCenterItemBinding

interface CategoriesListener {
    fun onCategoryClick(category: Any)
}

class CategoriesAdapter(
    private val categoriesName: List<String>,
    private val categoriesImage: List<Bitmap>,
    private val listener: CategoriesListener,
) : RecyclerView.Adapter<CategoriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_center_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categoriesName.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val categoriesName = categoriesName[position]
        val categoriesImage = categoriesImage[position]
        holder.itemView.setOnClickListener {
            listener.onCategoryClick(categoriesName)
            listener.onCategoryClick(categoriesImage)
        }
        holder.bind(categoriesName, categoriesImage)
    }
}

class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = MainCenterItemBinding.bind(itemView)
    fun bind(categoriesName: String, categoriesImage: Bitmap) {
        binding.nameCategory.text = categoriesName
        binding.logoImageView.setImageBitmap(categoriesImage)
    }
}