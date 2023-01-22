package com.mctech.pokergrinder.grind_tournament.presentation.creation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament

/**
 * Holds the available interactions for the feature.
 */
internal sealed class RegisterTournamentInteraction : UserInteraction {

  /**
   * Used to initialize the screen.
   */
  data class ScreenFirstOpen(
    val session: Session,
    val tournament: SessionTournament?,
  ) : RegisterTournamentInteraction()

  /**
   * Used to create a new tournament with given values.
   */
  data class SaveTournament(
    val title: String,
    val buyIn: Double,
    val profit: Double,
    val addNewProfit: Double,
  ) : RegisterTournamentInteraction()

}