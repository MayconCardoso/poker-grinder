package com.mctech.pokergrinder.ranges.presentation.list

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.ranges.domain.entities.Range

/**
 * Holds the available command for the feature
 */
internal sealed class RangesCommand : ViewCommand {

  /**
   * Called to navigate to the session editor.
   */
  data class NavigateToViewer(val range: Range) : RangesCommand()
}