package com.mctech.pokergrinder

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.bankroll.presentation.balance_component.BankrollBalanceComponent
import com.mctech.pokergrinder.databinding.ActivityHomeBinding
import com.mctech.pokergrinder.design.compose.PokerGrinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

  // region Variables

  /**
   * Indicates which features  that main app toolbar will be hidden.
   */
  private val mainAppContainerHiddenFragments by lazy {
    listOf(
      R.id.session_details,
      R.id.session_register,
      R.id.statement_register,
      R.id.statement_withdraw,
      R.id.tournament_register,
      R.id.ranges_viewer_fragment,
      R.id.session_details_tournament,
      R.id.session_details_tournament_flip,
    )
  }

  /**
   * Home Ui Binding
   */
  private val binding by viewBinding(ActivityHomeBinding::inflate)

  /**
   * Holds the navigator callback used to handle toolbar and bottom navigator.
   */
  private val navigatorCallback by lazy {
    PokerGrinderNavigator.Callback { destination ->
      renderToolbarBasedOnDestination(destination)
      renderBottomNavigatorBasedOnDestination(destination)
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

    // Setup toolbar component
    setupToolbar()

    // Setup navigator component
    setupNavigator()

    // Setup bottom component
    setUpBottomNavigation()
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

  private fun setupToolbar() {
    binding.toolbar.setContent {
      PokerGrinder.PokerGrinderTheme {
        BankrollBalanceComponent()
      }
    }
  }

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

  private fun renderToolbarBasedOnDestination(destination: NavDestination) {
    binding.toolbar.isVisible = !mainAppContainerHiddenFragments.contains(destination.id)
  }

  private fun renderBottomNavigatorBasedOnDestination(destination: NavDestination) {
    binding.bottomNavigationMain.isVisible = !mainAppContainerHiddenFragments.contains(destination.id)
  }

  // endregion
}