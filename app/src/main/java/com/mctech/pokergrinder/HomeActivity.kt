package com.mctech.pokergrinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    setUpBottomNavigation()
  }

  override fun onSupportNavigateUp(): Boolean {
    return getNavigationController().navigateUp()
  }

  private fun getNavigationController() = Navigation.findNavController(this, R.id.feature_fragment)

  private fun setUpBottomNavigation() {
    val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation_main)
    NavigationUI.setupWithNavController(bottomNav, getNavigationController())
  }
}