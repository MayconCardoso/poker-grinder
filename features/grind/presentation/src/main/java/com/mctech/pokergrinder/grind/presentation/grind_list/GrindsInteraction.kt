package com.mctech.pokergrinder.grind.presentation.grind_list

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.presentation.grind_list.adapter.GrindAdapterConsumerEvent

internal sealed class GrindsInteraction : UserInteraction {
  data class OnGrindEvent(
    val event: GrindAdapterConsumerEvent,
  ) : GrindsInteraction()
}