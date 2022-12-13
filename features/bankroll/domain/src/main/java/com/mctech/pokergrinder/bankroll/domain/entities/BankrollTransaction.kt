package com.mctech.pokergrinder.bankroll.domain.entities

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

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
  fun formattedDate(): String {
    return DateFormat.getDateInstance().format(dateInMs)
  }

  /**
   * Formats the transaction amount.
   */
  fun formattedAmount(): String = DecimalFormat(
    "$#0.00",
    DecimalFormatSymbols(Locale.ENGLISH)
  ).format(amount)
}
