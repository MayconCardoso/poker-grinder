package com.mctech.pokergrinder.grind_tournament.domain.entities

import com.mctech.pokergrinder.formatter.asFormattedCurrency
import java.io.Serializable
import java.text.DecimalFormat

/**
 * Declares a grind session tournament instance.
 *
 * @property id UUID session id.
 * @property idSession UUID session tournament id.
 * @property idTransactionProfit UUID profit transaction id.
 * @property idTransactionBuyIn UUID buy in transaction id.
 * @property title tournament title.
 * @property buyIn tournament buy-in
 * @property profit tournament profit.
 * @property startTimeInMs when the tournament has started.
 * @property isGrouped indicates if the entity is grouping the same tournament in a single instance.
 */
data class SessionTournament(
  val id: String,
  val idSession: String,
  val idTransactionProfit: String?,
  val idTransactionBuyIn: String,
  val title: String,
  val buyIn: Double,
  val profit: Double,
  val startTimeInMs: Long,
  val isGrouped: Boolean,
) : Serializable {

  /**
   * Computes the session balance.
   * The balance is basically the cashed amount minus buy-ins.
   */
  fun computesBalance(): Double = profit - buyIn

  /**
   * Formats the spent cash in buy-in in a currency string.
   */
  fun formattedBuyIn(): String = buyIn.asFormattedCurrency()

  /**
   * Formats the session profit in a currency string.
   */
  fun formattedProfit(): String = profit.asFormattedCurrency()

  /**
   * Formats the session balance in a currency string.
   */
  fun formattedBalance(): String = computesBalance().asFormattedCurrency()
}