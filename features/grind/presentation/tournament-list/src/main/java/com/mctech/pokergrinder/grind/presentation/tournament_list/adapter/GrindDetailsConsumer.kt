package com.mctech.pokergrinder.grind.presentation.tournament_list.adapter

internal interface GrindDetailsConsumer {
  fun consume(event: GrindDetailsConsumerEvent)
}