package com.mctech.pokergrinder.tournament.presentation.list_component

import com.mctech.pokergrinder.architecture.UserInteraction

/**
 * Holds the available interactions for the feature.
 */
sealed class TournamentListInteraction : UserInteraction {

  /**
   * Used to filter tournaments.
   */
  data class NewFilterQuery(
    val text: String,
  ) : TournamentListInteraction()

}