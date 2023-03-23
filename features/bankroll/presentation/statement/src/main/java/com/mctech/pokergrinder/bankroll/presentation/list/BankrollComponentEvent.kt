package com.mctech.pokergrinder.bankroll.presentation.list

/**
 * Defines the available events that can be sent from the bankroll transaction component.
 */
sealed class BankrollComponentEvent {

  /**
   * Indicates user has clicked the deposit button.
   */
  object OnDepositClicked : BankrollComponentEvent()

  /**
   * Indicates user has clicked the withdraw button.
   */
  object OnWithDrawClicked : BankrollComponentEvent()
}