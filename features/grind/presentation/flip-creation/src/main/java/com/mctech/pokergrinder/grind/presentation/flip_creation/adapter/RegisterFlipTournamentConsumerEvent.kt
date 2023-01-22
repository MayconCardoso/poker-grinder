package com.mctech.pokergrinder.grind.presentation.flip_creation.adapter

import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament

/**
 * Available events that can be sent from new flip flow.
 */
internal sealed class RegisterFlipTournamentConsumerEvent {

  /**
   * Indicates a tournament has been clicked.
   */
  data class TournamentClicked(val tournament: SessionTournament) : RegisterFlipTournamentConsumerEvent()
}