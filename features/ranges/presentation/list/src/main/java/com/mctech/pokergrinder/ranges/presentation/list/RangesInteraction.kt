package com.mctech.pokergrinder.ranges.presentation.list

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.ranges.presentation.list.adapter.RangeAdapterConsumerEvent

/**
 * Holds the available interactions for the feature.
 */
internal sealed class RangesInteraction : UserInteraction {

  /**
   * Called whenever a new grind [event] is sent.
   */
  data class OnRangeEvent(val event: RangeAdapterConsumerEvent) : RangesInteraction()
}