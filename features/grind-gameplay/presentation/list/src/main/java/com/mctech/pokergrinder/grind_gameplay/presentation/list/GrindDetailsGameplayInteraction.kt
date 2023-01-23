package com.mctech.pokergrinder.grind_gameplay.presentation.list

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session

/**
 * Holds the available interactions for the feature.
 */
internal sealed class GrindDetailsGameplayInteraction : UserInteraction {

  /**
   * Used to initialize the screen.
   */
  data class ScreenFirstOpen(val session: Session) : GrindDetailsGameplayInteraction()
}