package com.mctech.pokergrinder.grind.domain.entities

import com.mctech.pokergrinder.formatter.asFormattedCurrency
import com.mctech.pokergrinder.formatter.asFormattedPercentage
import java.io.Serializable

/**
 * Declares a grind session instance.
 *
 * @property id UUID session id.
 * @property cash amount of cash in the session in dollar.
 * @property buyIn amount of buy-in in the session in dollar.
 * @property avgBuyIn avg of buy-in in the session in dollar.
 * @property title session title.
 * @property isOpened indicates if session is still in play.
 * @property startTimeInMs the time the session has started.
 * @property tournamentsPlayed the amount of played tournaments.
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

  /**
   * Computes the session balance.
   * The balance is basically the cashed amount minus buy-ins.
   */
  fun computesBalance(): Double = cash - buyIn

  /**
   * Computes the session roi.
   */
  fun computesRoi(): Double {
    return if (buyIn == 0.0) 0.0 else (cash - buyIn) / buyIn
  }

  /**
   * Indicates if session is in profit or not.
   * A profitable session is where player has cashed more than spent in buy-in.
   */
  fun isInProfit(): Boolean = cash - buyIn > 0

  /**
   * Formats the amount of cash in a currency string.
   */
  fun formattedCash(): String = cash.asFormattedCurrency()

  /**
   * Formats the spent cash in buy-in in a currency string.
   */
  fun formattedBuyIn(): String = buyIn.asFormattedCurrency()

  /**
   * Formats the session balance in a currency string.
   */
  fun formattedBalance(): String = computesBalance().asFormattedCurrency()

  /**
   * Formats the session average of buy-in in a currency string.
   */
  fun formattedAvgBuyIn(): String = avgBuyIn.asFormattedCurrency()

  /**
   * Formats the return of investment of the session in a percentage string.
   */
  fun formattedRoi(): String = computesRoi().asFormattedPercentage()
}
