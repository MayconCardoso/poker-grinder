package com.mctech.pokergrinder.tournaments.domain.entities

import java.io.Serializable
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Declares a tournament instance.
 *
 * @property id UUID tournament.
 * @property type tournament type.
 * @property buyIn tournament buy-in in dollar.
 * @property title tournament title.
 * @property countReBuy count of re-buy allowed in the tournament.
 * @property guaranteed the guaranteed prize pool.
 * @property isBounty indicates if tournament is a bounty format.
 * @property startTimeInMs the time the tournament starts.
 */
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

  /**
   * Formats the tournament [buyIn] in a american dollar string.
   */
  fun formattedBuyIn(): String = DecimalFormat(
    "$#0.00",
    DecimalFormatSymbols(Locale.ENGLISH)
  ).format(buyIn)

}
