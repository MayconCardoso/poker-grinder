package com.mctech.pokergrinder.summary.presentation.investment

/**
 * Represents the state of the investment summary
 */
data class SummaryInvestmentState(
  val roi: String,
  val cash: String,
  val buyIn: String,
  val profit: Double,
  val formattedProfit: String,
)