package com.mctech.pokergrinder.grind.presentation.list.adapter

import com.mctech.pokergrinder.grind.presentation.list.GrindState

internal sealed class GrindAdapterConsumerEvent {
  data class SessionClicked(val state: GrindState) : GrindAdapterConsumerEvent()
}