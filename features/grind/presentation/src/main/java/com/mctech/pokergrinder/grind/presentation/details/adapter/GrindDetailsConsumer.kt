package com.mctech.pokergrinder.grind.presentation.details.adapter

internal interface GrindDetailsConsumer {
  fun consume(event: GrindDetailsConsumerEvent)
}