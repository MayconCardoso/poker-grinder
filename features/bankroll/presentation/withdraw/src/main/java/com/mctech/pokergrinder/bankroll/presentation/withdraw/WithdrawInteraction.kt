package com.mctech.pokergrinder.bankroll.presentation.withdraw

import com.mctech.pokergrinder.architecture.UserInteraction

/**
 * Holds the available interactions for the feature.
 */
internal sealed class WithdrawInteraction : UserInteraction {

  /**
   * Used to create a new deposit with given values.
   */
  data class SaveWithdraw(val title: String, val amount: Double) : WithdrawInteraction()
}