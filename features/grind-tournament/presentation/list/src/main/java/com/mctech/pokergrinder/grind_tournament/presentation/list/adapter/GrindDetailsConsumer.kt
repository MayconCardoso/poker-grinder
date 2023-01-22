package com.mctech.pokergrinder.grind_tournament.presentation.list.adapter

internal interface GrindDetailsConsumer {
  fun consume(event: GrindDetailsConsumerEvent)
}