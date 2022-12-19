package com.mctech.pokergrinder.grind.domain.entities

import com.mctech.pokergrinder.formatter.asFormattedCurrency
import java.io.Serializable

/**
 * Declares a grind session instance.
 *
 * @property id UUID transaction.
 * @property cash transaction type.
 * @property buyIn transaction amount in dollar.
 * @property avgBuyIn transaction description.
 * @property title the time the transaction has occurred.
 * @property isOpened the time the transaction has occurred.
 * @property startTimeInMs the time the transaction has occurred.
 * @property tournamentsPlayed the time the transaction has occurred.
 * @property balance the time the transaction has occurred.
 */
data class Session(
  val id: String,
  val cash: Double,
  val buyIn: Double,
  val avgBuyIn: Double,
  val title: String,
  val isOpened: Boolean,
  val startTimeInMs: Long,
  val tournamentsPlayed: Int,
) : Serializable {

  val balance: Double
    get() = cash - buyIn

  fun isInProfit(): Boolean = cash - buyIn > 0

  fun formattedCash(): String = cash.asFormattedCurrency()

  fun formattedBuyIn(): String = buyIn.asFormattedCurrency()

  fun formattedBalance(): String = balance.asFormattedCurrency()

  fun formattedAvgBuyIn(): String = avgBuyIn.asFormattedCurrency()

  fun formattedRoi(): String {
    val roi = if (buyIn == 0.0) 0.0 else (cash - buyIn) / buyIn * 100
    return roi.asFormattedCurrency() + "%"
  }
}
