package com.mctech.pokergrinder.tournament.presentation.creation

import com.mctech.pokergrinder.architecture.ViewCommand

/**
 * Holds the available command for the feature
 */
internal sealed class NewTournamentCommand : ViewCommand {

  /**
   * Used to close the screen and navigate back.
   */
  object CloseScreen : NewTournamentCommand()
}