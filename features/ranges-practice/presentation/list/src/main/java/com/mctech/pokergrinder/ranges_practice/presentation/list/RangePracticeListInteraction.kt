package com.mctech.pokergrinder.ranges_practice.presentation.list

import com.mctech.pokergrinder.architecture.UserInteraction

/**
 * Defines the available events that can be sent from the range practicing component.
 */
sealed class RangePracticeListInteraction: UserInteraction {

  /**
   * Indicates user has clicked the start practicing button.
   */
  object OnStartPracticing : RangePracticeListInteraction()
}