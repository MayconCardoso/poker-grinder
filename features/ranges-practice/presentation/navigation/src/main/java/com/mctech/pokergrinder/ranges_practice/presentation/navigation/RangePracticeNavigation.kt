package com.mctech.pokergrinder.ranges_practice.presentation.navigation

/**
 * Used to navigate through range practice feature components.
 */
interface RangePracticeNavigation {

  /**
   * Navigates to practice component.
   */
  fun goToRangePracticeTrainer()

  /**
   * Navigates to filter component.
   */
  fun goToRangePracticeFilter()

  /**
   * Used to navigate back.
   */
  fun navigateBack()
}