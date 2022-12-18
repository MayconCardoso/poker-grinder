package com.mctech.pokergrinder.bankroll.presentation.navigation

/**
 * Used to navigate through bankroll feature components.
 */
public interface BankrollNavigation {

  /**
   * Goes to deposit screen.
   */
  public fun goToBankrollDeposit()

  /**
   * Goes to withdraw screen.
   */
  public fun goToBankrollWithdraw()

  /**
   * Navigates back.
   */
  public fun navigateBack()
}