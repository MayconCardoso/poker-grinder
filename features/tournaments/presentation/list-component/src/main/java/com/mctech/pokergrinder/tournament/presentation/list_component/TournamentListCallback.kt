package com.mctech.pokergrinder.tournament.presentation.list_component

import com.mctech.pokergrinder.tournament.domain.entities.Tournament

/**
 * Used to communicate with client.
 */
fun interface TournamentListCallback {

  /**
   * Called whenever a [tournament] is clicked.
   */
  fun onTournamentClicked(tournament: Tournament)
}