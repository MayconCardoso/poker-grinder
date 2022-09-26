package com.mctech.pokergrinder.grind.domain.entities

import java.io.Serializable
import java.text.DecimalFormat

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

  val roi: Double
    get() = if (buyIn == 0.0) 0.0 else (cash - buyIn) / buyIn * 100

  val balance: Double
    get() = cash - buyIn

  fun formattedCash(): String = DecimalFormat("$#0.00").format(cash)

  fun formattedBuyIn(): String = DecimalFormat("$#0.00").format(buyIn)

  fun formattedBalance(): String = DecimalFormat("$#0.00").format(balance)

  fun formattedAvgBuyIn(): String = DecimalFormat("$#0.00").format(avgBuyIn)

  fun formattedRoi(): String {
    return DecimalFormat("#0.00").format(roi) + "%"
  }
}
