package com.mctech.pokergrinder.bankroll.presentation.deposit

import com.mctech.pokergrinder.architecture.UserInteraction

/**
 * Holds the available interactions for the feature.
 */
internal sealed class DepositInteraction : UserInteraction {

  /**
   * Used to create a new deposit with given values.
   */
  data class SaveDeposit(val title: String, val amount: Double) : DepositInteraction()
}