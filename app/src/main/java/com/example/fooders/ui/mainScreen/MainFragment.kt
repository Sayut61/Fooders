package com.example.fooders.ui.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fooders.databinding.FrMainBinding

class MainFragment: Fragment() {

    private var _binding: FrMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
}