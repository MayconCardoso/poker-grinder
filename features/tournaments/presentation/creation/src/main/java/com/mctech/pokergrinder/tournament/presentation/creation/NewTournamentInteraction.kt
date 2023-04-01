package com.mctech.pokergrinder.tournament.presentation.creation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.tournament.domain.entities.Tournament

/**
 * Holds the available interactions for the feature.
 */
internal sealed class NewTournamentInteraction : UserInteraction {

  /**
   * Used to initialize the screen.
   */
  data class ScreenFirstOpen(val tournament: Tournament?) : NewTournamentInteraction()

  /**
   * Used to create a new tournament registration with given values.
   */
  data class SaveTournament(
    val title: String,
    val buyIn: Float,
  ) : NewTournamentInteraction()
}