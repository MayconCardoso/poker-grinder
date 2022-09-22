package com.mctech.pokergrinder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.databinding.ActivityHomeBinding
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.current_grind.CurrentGrindViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

  /**
   * Holds the current grind view model.
   */
  private val currentGrindViewModel by viewModels<CurrentGrindViewModel>()

  /**
   * Home Ui Binding
   */
  private val binding by viewBinding(ActivityHomeBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    // Setup component
    setUpBottomNavigation()

    // Starts observing changes on current session
    currentGrindViewModel.initialize()

    // Observers current session change
    bindState(currentGrindViewModel.componentState, ::renderCurrentSession)
  }

  override fun onSupportNavigateUp(): Boolean {
    return getNavigationController().navigateUp()
  }

  private fun getNavigationController() = Navigation.findNavController(this, R.id.feature_fragment)

  private fun setUpBottomNavigation() {
    val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation_main)
    NavigationUI.setupWithNavController(bottomNav, getNavigationController())
  }

  private fun renderCurrentSession(session: Session?) {
    binding.toolbar.title = getString(
      if (session == null) com.mctech.pokergrinder.localization.R.string.app_name
      else com.mctech.pokergrinder.localization.R.string.current_session
    )
    binding.toolbar.subtitle = session?.formattedAmount()
  }

}