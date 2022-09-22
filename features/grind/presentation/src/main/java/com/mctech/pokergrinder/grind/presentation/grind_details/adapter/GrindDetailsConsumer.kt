package com.mctech.pokergrinder.grind.presentation.grind_details.adapter

internal interface GrindDetailsConsumer {
  fun consume(event: GrindDetailsConsumerEvent)
}