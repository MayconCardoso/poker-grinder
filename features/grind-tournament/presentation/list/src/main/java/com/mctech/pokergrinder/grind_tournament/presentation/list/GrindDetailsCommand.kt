package com.mctech.pokergrinder.grind_tournament.presentation.list

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament

/**
 * Holds the available command for the feature
 */
internal sealed class GrindDetailsCommand : ViewCommand {

  /**
   * Used to close the screen and navigate back.
   */
  object CloseScreen : GrindDetailsCommand()

  /**
   * Used to show grouped tournaments.
   */
  data class ShowTournamentSelection(
    val options: List<SessionTournament>,
  ) : GrindDetailsCommand()

  /**
   * Used to navigate to tournament editor.
   */
  data class GoToTournamentEditor(
    val session: Session,
    val sessionTournament: SessionTournament?,
  ) : GrindDetailsCommand()

  /**
   * Used to inform the request amount is not available for withdrawing.
   */
  object InsufficientBalanceError : GrindDetailsCommand()
}