package com.mctech.pokergrinder.bankroll.domain.entities

import com.mctech.pokergrinder.formatter.asFormattedCurrency
import com.mctech.pokergrinder.formatter.asFormattedDate

/**
 * Declares a bankroll transaction instance.
 *
 * @property id UUID transaction.
 * @property type transaction type.
 * @property amount transaction amount in dollar.
 * @property description transaction description.
 * @property dateInMs the time the transaction has occurred.
 */
data class BankrollTransaction(
  val id: String,
  val type: BankrollTransactionType,
  val amount: Double,
  val description: String,
  val dateInMs: Long,
) {

  /**
   * Formats the transaction date.
   */
  fun formattedDate(): String = dateInMs.asFormattedDate()

  /**
   * Formats the transaction amount.
   */
  fun formattedAmount(): String = amount.asFormattedCurrency()
}
