package com.mctech.pokergrinder.ranges.presentation.navigation

import com.mctech.pokergrinder.ranges.domain.entities.Range

/**
 * Used to navigate through range feature components.
 */
interface RangeNavigation {

  /**
   * Goes to session details screen.
   */
  fun goToRangeDetails(range: Range)

  /**
   * Navigates back.
   */
  fun navigateBack()
}