package com.mctech.pokergrinder.grind.presentation.list

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapterConsumerEvent

/**
 * Holds the available interactions for the feature.
 */
internal sealed class GrindsInteraction : UserInteraction {

  /**
   * Called whenever a new session button is clicked.
   */
  object NewSessionClicked: GrindsInteraction()

  /**
   * Called whenever a new grind [event] is sent.
   */
  data class OnGrindEvent(val event: GrindAdapterConsumerEvent) : GrindsInteraction()
}