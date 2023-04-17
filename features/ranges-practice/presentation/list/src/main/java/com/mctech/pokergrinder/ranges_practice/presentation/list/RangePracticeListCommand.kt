package com.mctech.pokergrinder.ranges_practice.presentation.list

import com.mctech.pokergrinder.architecture.ViewCommand

/**
 * Defines the available events that can be sent from the range practicing component.
 */
sealed class RangePracticeListCommand: ViewCommand {

  /**
   * Indicates user has clicked the start practicing button.
   */
  object GoToPracticing : RangePracticeListCommand()
}