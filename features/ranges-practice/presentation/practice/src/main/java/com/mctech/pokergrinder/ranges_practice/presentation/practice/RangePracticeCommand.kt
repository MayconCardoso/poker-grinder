package com.mctech.pokergrinder.ranges_practice.presentation.practice

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.ranges.domain.entities.RangePosition

/**
 * Defines the available events that can be sent from the range practicing component.
 */
sealed class RangePracticeCommand : ViewCommand {

  /**
   * Opens range viewer.
   */
  data class OpenRangeScreen(
    val range: RangePosition
  ) : RangePracticeCommand()

  /**
   * Opens filter screen.
   */
  object OpenFilterScreen : RangePracticeCommand()

}