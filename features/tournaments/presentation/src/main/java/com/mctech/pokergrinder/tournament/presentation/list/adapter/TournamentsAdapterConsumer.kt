package com.mctech.pokergrinder.tournament.presentation.list.adapter

internal interface TournamentsAdapterConsumer {
  fun consume(event: TournamentsAdapterConsumerEvent)
}