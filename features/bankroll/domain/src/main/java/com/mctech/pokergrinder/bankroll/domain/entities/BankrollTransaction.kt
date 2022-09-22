package com.mctech.pokergrinder.bankroll.domain.entities

import java.text.DateFormat
import java.text.DecimalFormat

data class BankrollTransaction(
  val id: String,
  val type: BankrollTransactionType,
  val amount: Double,
  val description: String,
  val dateInMs: Long,
) {
  fun formattedDate(): String {
    return DateFormat.getDateInstance().format(dateInMs)
  }

  fun formattedAmount(): String = DecimalFormat("$#0.00").format(amount)
}
