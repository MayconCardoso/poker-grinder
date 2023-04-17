package com.mctech.pokergrinder.ranges_practice.presentation.filter

import com.mctech.pokergrinder.architecture.UserInteraction

/**
 * Defines the available events that can be sent from the range practicing component.
 */
sealed class RangePracticeFilterInteraction : UserInteraction {

  /**
   * Indicates save button has been clicked.
   */
  object OnSaveClicked: RangePracticeFilterInteraction()

  /**
   * Indicates a new stack size has been clicked.
   */
  data class OnStackClicked(val option: RangeFilterOption) : RangePracticeFilterInteraction()

  /**
   * Indicates a new position has been clicked.
   */
  data class OnHeroPositionClicked(val option: RangeFilterOption) : RangePracticeFilterInteraction()
}