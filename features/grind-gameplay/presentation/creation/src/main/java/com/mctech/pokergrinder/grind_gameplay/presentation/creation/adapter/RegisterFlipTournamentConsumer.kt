package com.mctech.pokergrinder.grind_gameplay.presentation.creation.adapter

/**
 * Flip event consumer that will be notified when an [RegisterFlipTournamentConsumerEvent] is sent.
 */
internal interface RegisterFlipTournamentConsumer {

  /**
   * Called to consume a new event.
   */
  fun consume(event: RegisterFlipTournamentConsumerEvent)
}