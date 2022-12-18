package com.mctech.pokergrinder.bankroll.testing

import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType

/**
 * Creates a new Transaction for test purpose.
 */
fun newTransaction(
  id: String = "0",
  amount: Double = 0.0,
  description: String = "",
  type: BankrollTransactionType = BankrollTransactionType.DEPOSIT,
  dateInMs: Long = 0,
) = BankrollTransaction(
  id = id,
  type = type,
  amount = amount,
  description = description,
  dateInMs = dateInMs,
)