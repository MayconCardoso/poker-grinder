package com.mctech.pokergrinder.grind.domain.entities

import java.io.Serializable
import java.text.DecimalFormat

data class SessionTournament(
  val id: String,
  val idSession: String,
  val title: String,
  val buyIn: Double,
  val profit: Double,
  val startTimeInMs: Long,
) : Serializable {

  fun computesProfit(): Double {
    return profit - buyIn
  }

  fun formattedProfit(): String {
    return DecimalFormat("$#0.00").format(computesProfit())
  }

  fun formattedBuyIn(): String {
    return DecimalFormat("$#0.00").format(buyIn)
  }
}