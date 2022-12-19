package com.mctech.pokergrinder.grind.presentation.creation

import com.mctech.pokergrinder.architecture.UserInteraction

/**
 * Holds the available interactions for the feature.
 */
internal sealed class NewGrindInteraction : UserInteraction {

  /**
   * Used to create a new session with given values.
   */
  data class SaveGrind(val title: String): NewGrindInteraction()
}