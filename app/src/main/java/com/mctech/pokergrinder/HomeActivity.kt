package com.mctech.pokergrinder

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.databinding.ActivityHomeBinding
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.grind_current.CurrentGrindViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

  // region Variables

  /**
   * Indicates which features  that main app toolbar will be hidden
   */
  private val mainAppContainerHiddenFragments by lazy {
    listOf(
      R.id.session_details,
      R.id.session_register,
      R.id.session_details_tournament,
      R.id.statement_register,
      R.id.tournament_register,
      R.id.session_details_tournament_flip,
    )
  }

  /**
   * Home Ui Binding
   */
  private val binding by viewBinding(ActivityHomeBinding::inflate)

  /**
   * Holds the current grind view model.
   */
  private val currentGrindViewModel by viewModels<CurrentGrindViewModel>()

  /**
   * Holds the navigator callback used to handle toolbar and bottom navigator.
   */
  private val navigatorCallback by lazy {
    object : PokerGrinderNavigator.Callback {
      override fun onDestinationChanged(destination: NavDestination) {
        renderToolbarBasedOnDestination(destination)
        renderBottomNavigatorBasedOnDestination(destination)
      }
    }
  }

  /**
   * Holds the app navigator controller
   */
  private val navigatorController by lazy {
    Navigation.findNavController(this, R.id.feature_fragment)
  }

  /**
   * Used to navigate through the app.
   */
  @Inject
  lateinit var appNavigator: PokerGrinderNavigator

  // endregion

  // region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    // Setup component
    setupNavigator()

    // Setup component
    setUpBottomNavigation()

    // Starts observing changes on current session
    currentGrindViewModel.initialize()

    // Observers current session change
    bindState(currentGrindViewModel.componentState, ::renderCurrentSession)
  }

  override fun onDestroy() {
    appNavigator.unbind()
    super.onDestroy()
  }

  override fun onSupportNavigateUp(): Boolean {
    return navigatorController.navigateUp()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      navigatorController.navigateUp()
      return true
    }

    return super.onOptionsItemSelected(item)
  }

  // endregion

  // region Component setup

  private fun setupNavigator() {
    appNavigator.bindNavController(navController = navigatorController)
    appNavigator.navigatorCallback = navigatorCallback
  }

  private fun setUpBottomNavigation() {
    val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation_main)
    NavigationUI.setupWithNavController(bottomNav, navigatorController)
  }

  // endregion

  // region UI Manipulation

  private fun renderCurrentSession(session: Session?) {
    binding.toolbar.title = getString(
      if (session == null) com.mctech.pokergrinder.localization.R.string.app_name
      else com.mctech.pokergrinder.localization.R.string.current_session
    )
    binding.toolbar.subtitle = session?.formattedBalance()
  }

  private fun renderToolbarBasedOnDestination(destination: NavDestination) {
    binding.toolbar.isVisible = !mainAppContainerHiddenFragments.contains(destination.id)
  }

  private fun renderBottomNavigatorBasedOnDestination(destination: NavDestination) {
    binding.bottomNavigationMain.isVisible = !mainAppContainerHiddenFragments.contains(destination.id)
  }

  // endregion

}