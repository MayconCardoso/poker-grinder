package com.mctech.pokergrinder.grind_tournament.presentation.list

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind_tournament.presentation.list.adapter.GrindDetailsConsumerEvent

/**
 * Holds the available interactions for the feature.
 */
internal sealed class GrindDetailsInteraction : UserInteraction {

  /**
   * Used to initialize the screen.
   */
  data class ScreenFirstOpen(val session: Session) : GrindDetailsInteraction()

  /**
   * Used to indicate a new tournament event.
   */
  data class OnTournamentEvent(val event: GrindDetailsConsumerEvent) : GrindDetailsInteraction()

  /**
   * Used to indicate a register button has been clicked.
   */
  object RegisterTournamentClicked : GrindDetailsInteraction()
}