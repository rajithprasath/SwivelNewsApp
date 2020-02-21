package com.rajith.codetest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.rajith.codetest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navController by lazy { Navigation.findNavController(this,
        R.id.navHostFragment
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(navigationListner)
    }

    private val navigationListner = NavController.OnDestinationChangedListener { _, destination, _ ->
        showBottomNavigation()
    }

    private fun showBottomNavigation() {
        bottomNavigationView.visibility = View.VISIBLE
    }
    private fun hideBottomNavigation() {
        bottomNavigationView.visibility = View.GONE
    }
}
