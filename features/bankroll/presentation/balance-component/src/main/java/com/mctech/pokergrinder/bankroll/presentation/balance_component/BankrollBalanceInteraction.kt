package com.mctech.pokergrinder.bankroll.presentation.balance_component

import com.mctech.pokergrinder.architecture.UserInteraction

/**
 * Defines the available events that can be sent from the bankroll balance component.
 */
sealed class BankrollBalanceInteraction: UserInteraction {

  /**
   * Indicates user has clicked the balance.
   */
  object OnBalanceClicked : BankrollBalanceInteraction()
}