package com.example.fooders.ui.searchScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fooders.R
import com.example.fooders.databinding.FrSearchBinding

class SearchFragment: Fragment() {

    private var _binding: FrSearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrSearchBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        binding.cardView.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchTv.visibility = View.GONE
        }
        return binding.root
    }
}