package com.mctech.pokergrinder.ranges_practice.presentation.practice

import com.mctech.pokergrinder.architecture.UserInteraction

/**
 * Defines the available events that can be sent from the range practicing component.
 */
sealed class RangePracticeInteraction: UserInteraction {

  /**
   * Indicates user has clicked the next question button
   */
  object OnNextQuestionClicked : RangePracticeInteraction()

  /**
   * Indicates user has folded the action
   */
  object OnFoldClicked : RangePracticeInteraction()

  /**
   * Indicates user has clicked to see the range.
   */
  object SeeRangeButtonClicked : RangePracticeInteraction()

  /**
   * Indicates user has clicked to see the filters.
   */
  object OnFilterClicked : RangePracticeInteraction()

  /**
   * Indicates user has clicked the action
   */
  object OnActionClicked : RangePracticeInteraction()
}