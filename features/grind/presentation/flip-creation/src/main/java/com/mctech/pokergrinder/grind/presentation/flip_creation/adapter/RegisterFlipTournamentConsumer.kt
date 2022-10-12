package com.mctech.pokergrinder.grind.presentation.flip_creation.adapter

internal interface RegisterFlipTournamentConsumer {
  fun consume(event: RegisterFlipTournamentConsumerEvent)
}