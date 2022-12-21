package com.mctech.pokergrinder.grind.presentation.summary

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session

/**
 * Holds the available interactions for the feature.
 */
internal sealed class GrindSummaryInteraction : UserInteraction {

  /**
   * Used to initialize the screen.
   */
  data class ScreenFirstOpen(val session: Session) : GrindSummaryInteraction()
}