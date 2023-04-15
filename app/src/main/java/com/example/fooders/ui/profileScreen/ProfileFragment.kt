package com.example.fooders.ui.profileScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fooders.databinding.FrProfileBinding

class ProfileFragment: Fragment() {

    private var _binding: FrProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrProfileBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
}