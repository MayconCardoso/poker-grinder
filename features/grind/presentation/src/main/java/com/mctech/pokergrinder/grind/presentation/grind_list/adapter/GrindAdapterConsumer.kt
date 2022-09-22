package com.mctech.pokergrinder.grind.presentation.grind_list.adapter

internal interface GrindAdapterConsumer {
  fun consume(event: GrindAdapterConsumerEvent)
}