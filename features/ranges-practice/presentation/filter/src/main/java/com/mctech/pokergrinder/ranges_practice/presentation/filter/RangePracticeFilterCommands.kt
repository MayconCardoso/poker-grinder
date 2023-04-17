package com.mctech.pokergrinder.ranges_practice.presentation.filter

import com.mctech.pokergrinder.architecture.ViewCommand

/**
 * Defines the available events that can be sent from the range practicing component.
 */
sealed class RangePracticeFilterCommands : ViewCommand {

  /**
   * Used to navigate back
   */
  object NavigateBack : RangePracticeFilterCommands()
}