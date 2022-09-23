package com.mctech.pokergrinder.summary.domain.entities

import java.text.DecimalFormat

data class InvestmentSummary(
  val profit: Double,
  val cash: Double,
  val buyIn: Double,
) {
  fun computeRoi(): Double {
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
}