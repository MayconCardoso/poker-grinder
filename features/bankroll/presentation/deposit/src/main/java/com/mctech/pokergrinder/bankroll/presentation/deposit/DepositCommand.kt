package com.mctech.pokergrinder.bankroll.presentation.deposit

import com.mctech.pokergrinder.architecture.ViewCommand

/**
 * Holds the available command for the feature
 */
internal sealed class DepositCommand : ViewCommand {

  /**
   * Used to close the screen and navigate back.
   */
  object CloseScreen : DepositCommand()
}