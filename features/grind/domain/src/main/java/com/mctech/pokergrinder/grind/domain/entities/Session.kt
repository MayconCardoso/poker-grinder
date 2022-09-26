package com.mctech.pokergrinder.grind.domain.entities

import java.io.Serializable
import java.text.DecimalFormat

data class Session(
  val id: String,
  val cash: Double,
  val buyIn: Double,
  val title: String,
  val isOpened: Boolean,
  val startTimeInMs: Long,
  val tournamentsPlayed: Int,
) : Serializable {

  val roi: Double
    get() = (cash - buyIn) / buyIn * 100

  val outcome: Double
    get() = cash - buyIn

  fun formattedAmount(): String = DecimalFormat("$#0.00").format(outcome)

  fun formattedRoi(): String {
    return DecimalFormat("#0.00").format(roi) + "%"
  }
}
