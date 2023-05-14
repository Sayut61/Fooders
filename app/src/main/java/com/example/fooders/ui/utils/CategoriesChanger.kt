package com.example.fooders.ui.utils

import android.content.Context
import com.example.fooders.R

fun changeCatName(context: Context, name: String): String {
    return when (name) {
        "ic_dessert.jpg" -> context.getString(R.string.dessert)
        "ic_garnish.jpg" -> context.getString(R.string.garnish)
        "ic_pastry.jpg" -> context.getString(R.string.pastry)
        "ic_salad.jpg" -> context.getString(R.string.salad)
        "ic_sauce.jpg" -> context.getString(R.string.sauce)
        "ic_snack.jpg" -> context.getString(R.string.snack)
        else -> ""
    }
}