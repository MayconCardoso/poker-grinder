package com.mctech.pokergrinder.bankroll.domain.entities

/**
 * Declares the available transaction types.
 */
enum class BankrollTransactionType {
  DEPOSIT,
  WITHDRAW,
  PROFIT,
  BUY_IN,
  RAKE_BACK
}