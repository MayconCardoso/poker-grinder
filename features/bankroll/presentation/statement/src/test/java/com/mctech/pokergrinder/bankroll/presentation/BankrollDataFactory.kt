package com.mctech.pokergrinder.bankroll.presentation

import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType

/**
 * Creates a new Transaction for test purpose.
 */
internal fun newTransaction(
  id: String = "0",
  amount: Double = 0.0,
  description: String = "",
  type: BankrollTransactionType = BankrollTransactionType.DEPOSIT,
) = BankrollTransaction(
  id = id,
  type = type,
  amount = amount,
  description = description,
  dateInMs = 0L,
)