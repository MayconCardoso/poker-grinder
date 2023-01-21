package com.mctech.pokergrinder.summary.domain.entities

import com.mctech.pokergrinder.formatter.asFormattedDate
import java.io.Serializable
import java.text.DecimalFormat

data class TournamentSummary(
  val title: String,
  val cash: Double,
  val buyIn: Double,
  val profit: Double,
  val tournaments: Int,
  val playedTimeInMs: Long,
): Serializable {

  fun computeRoi(): Double {
    if (buyIn == 0.0) {
      return 0.0
    }
    return (cash - buyIn) / buyIn * 100
  }

  fun formattedRoi(): String {
    return DecimalFormat("#0.00").format(computeRoi()) + "%"
  }

  fun formattedProfit(): String {
    return DecimalFormat("$#0.00").format(profit)
  }

  fun formattedCash(): String {
    return DecimalFormat("$#0.00").format(cash)
  }

  fun formattedBuyIn(): String {
    return DecimalFormat("$#0.00").format(buyIn)
  }

  fun formattedDate(): String {
    return playedTimeInMs.asFormattedDate()
  }
}