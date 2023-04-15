package com.example.fooders.ui.favoriteScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fooders.databinding.FrFavoriteBinding

class FavoriteFragment: Fragment() {

    private var _binding: FrFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrFavoriteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
}