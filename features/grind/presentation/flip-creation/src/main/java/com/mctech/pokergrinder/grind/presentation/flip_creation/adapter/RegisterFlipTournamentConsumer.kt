package com.mctech.pokergrinder.grind.presentation.flip_creation.adapter

/**
 * Flip event consumer that will be notified when an [RegisterFlipTournamentConsumerEvent] is sent.
 */
internal interface RegisterFlipTournamentConsumer {

  /**
   * Called to consume a new event.
   */
  fun consume(event: RegisterFlipTournamentConsumerEvent)
}