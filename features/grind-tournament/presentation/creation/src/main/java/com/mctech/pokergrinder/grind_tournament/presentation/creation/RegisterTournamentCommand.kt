package com.mctech.pokergrinder.grind_tournament.presentation.creation

import com.mctech.pokergrinder.architecture.ViewCommand

/**
 * Holds the available command for the feature
 */
internal sealed class RegisterTournamentCommand : ViewCommand {

  /**
   * Used to close the screen and navigate back.
   */
  object CloseScreen : RegisterTournamentCommand()

  /**
   * Used to indicate insufficient while trying to buy-in for a tournament.
   */
  object InsufficientBalanceError : RegisterTournamentCommand()
}