package com.mctech.pokergrinder.grind.domain.entities

import java.io.Serializable
import java.text.DecimalFormat

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

  fun computesBalance(): Double {
    return profit - buyIn
  }

  fun formattedBalance(): String {
    return DecimalFormat("$#0.00").format(computesBalance())
  }

  fun formattedProfit(): String {
    return DecimalFormat("$#0.00").format(profit)
  }

  fun formattedBuyIn(): String {
    return DecimalFormat("$#0.00").format(buyIn)
  }
}