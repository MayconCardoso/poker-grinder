package com.mctech.pokergrinder.bankroll.domain

import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType

object BankrollTransactionData {
  fun single(amount: Double = 0.0, dateInMs: Long = 0) = BankrollTransaction(
    id = "1",
    type = BankrollTransactionType.BUY_IN,
    amount = amount,
    description = "Hello",
    dateInMs = dateInMs,
  )
}