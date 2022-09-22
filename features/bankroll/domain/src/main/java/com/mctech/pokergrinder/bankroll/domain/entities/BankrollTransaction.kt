package com.mctech.pokergrinder.bankroll.domain.entities

data class BankrollTransaction(
  val id: String,
  val type: BankrollTransactionType,
  val amount: Double,
  val description: String,
  val dateInMs: Long,
)
