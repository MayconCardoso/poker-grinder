package com.mctech.pokergrinder.bankroll.presentation.list

import com.mctech.pokergrinder.architecture.UserInteraction

/**
 * Defines the available events that can be sent from the bankroll transaction component.
 */
sealed class StatementInteraction: UserInteraction {

  /**
   * Indicates user has clicked the deposit button.
   */
  object OnDepositClicked : StatementInteraction()

  /**
   * Indicates user has clicked the withdraw button.
   */
  object OnWithDrawClicked : StatementInteraction()
}