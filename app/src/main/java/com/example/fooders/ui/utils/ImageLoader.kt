package com.example.fooders.ui.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.fooders.R

fun loadImage(
    imageUrl: String,
    imageView: ImageView
) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_progress)
        .error(R.drawable.ic_error)
        .into(imageView)
}