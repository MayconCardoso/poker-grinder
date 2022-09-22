package com.mctech.pokergrinder.tournaments.domain.entities

import java.io.Serializable
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

data class Tournament(
  val id: String,
  val type: TournamentType,
  val buyIn: Float,
  val title: String,
  val countReBuy: Int,
  val guaranteed: Int,
  val isBounty: Boolean,
  val startTimeInMs: Long,
) : Serializable {
  fun formattedTime() = String.format(
    "%02d:%02d",
    TimeUnit.MILLISECONDS.toHours(startTimeInMs),
    TimeUnit.MILLISECONDS.toMinutes(startTimeInMs) % TimeUnit.HOURS.toMinutes(1),
  )

  fun formattedBuyIn(): String = DecimalFormat("$#.00").format(buyIn)

  fun formattedGuaranteed(): String = DecimalFormat("$#.00").format(guaranteed)
}
