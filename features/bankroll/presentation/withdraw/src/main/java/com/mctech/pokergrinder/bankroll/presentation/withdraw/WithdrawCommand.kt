package com.mctech.pokergrinder.bankroll.presentation.withdraw

import com.mctech.pokergrinder.architecture.ViewCommand

/**
 * Holds the available command for the feature
 */
internal sealed class WithdrawCommand : ViewCommand {

  /**
   * Used to close the screen and navigate back.
   */
  object CloseScreen : WithdrawCommand()

  /**
   * Used to inform the request amount is not available for withdrawing.
   */
  object InsufficientBalanceError : WithdrawCommand()
}