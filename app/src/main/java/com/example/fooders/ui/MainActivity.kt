package com.example.fooders.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.fooders.R
import com.example.fooders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        val mainController = findNavController(R.id.nav_host_fragment_activity_main)
        val topLevelDestinations = setOf(
            R.id.mainFragment,
            R.id.searchFragment,
            R.id.favoriteFragment,
            R.id.profileFragment
        )
        val appBarConfiguration = AppBarConfiguration(topLevelDestinations)

        setSupportActionBar(binding.toolbar)

        binding.toolbar.setupWithNavController(mainController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(mainController)
            mainController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.bottomNavigationView.visibility = if (topLevelDestinations.contains(destination.id))
                View.VISIBLE
            else
                View.GONE
        }

    }
}