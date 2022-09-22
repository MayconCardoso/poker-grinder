package com.mctech.pokergrinder.grind.presentation.list.adapter

internal interface GrindAdapterConsumer {
  fun consume(event: GrindAdapterConsumerEvent)
}