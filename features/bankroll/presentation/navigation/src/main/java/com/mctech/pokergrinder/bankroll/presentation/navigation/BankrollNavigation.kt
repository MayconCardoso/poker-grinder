package com.mctech.pokergrinder.bankroll.presentation.navigation

/**
 * Used to navigate through bankroll feature components.
 */
interface BankrollNavigation {

  /**
   * Goes to deposit screen.
   */
  fun goToBankrollDeposit()

  /**
   * Goes to withdraw screen.
   */
  fun goToBankrollWithdraw()

  /**
   * Navigates back.
   */
  fun navigateBack()
}