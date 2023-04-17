package com.mctech.pokergrinder.ranges_practice.presentation.navigation

import com.mctech.pokergrinder.ranges.domain.entities.RangePosition

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
   * Navigates to range viewer component.
   */
  fun goToRangeViewer(range: RangePosition)

  /**
   * Used to navigate back.
   */
  fun navigateBack()
}