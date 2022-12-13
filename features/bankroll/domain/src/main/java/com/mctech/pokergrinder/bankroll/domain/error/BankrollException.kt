package com.mctech.pokergrinder.bankroll.domain.error

/**
 * Declares known issues may happen on the transaction flow.
 */
sealed class BankrollException(reason: String? = null) : RuntimeException(reason) {

  /**
   * Indicates the user has no balance to proceed with the transaction.
   */
  object InsufficientBalance : BankrollException()
}