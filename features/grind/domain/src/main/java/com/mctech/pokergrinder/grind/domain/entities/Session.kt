package com.mctech.pokergrinder.grind.domain.entities

import java.io.Serializable
import java.text.DecimalFormat

data class Session(
  val id: String,
  val title: String,
  val outcome: Double,
  val countBuyIn: Int,
  val isOpened: Boolean,
  val startTimeInMs: Long,
) : Serializable {
  fun formattedAmount(): String = DecimalFormat("$#0.00").format(outcome)
}
