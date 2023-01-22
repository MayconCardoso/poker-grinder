package com.mctech.pokergrinder.grind.testing

import com.mctech.pokergrinder.grind.domain.entities.Session

/**
 * Creates a new Session for test purpose.
 */
fun newSession(
  id: String = "0",
  cash: Double = 0.0,
  buyIn: Double = 0.0,
  avgBuyIn: Double = 0.0,
  title: String = "",
  isOpened: Boolean = false,
  startTimeInMs: Long = 0,
  tournamentsPlayed: Int = 0,
) = Session(
  id = id,
  cash = cash,
  buyIn = buyIn,
  avgBuyIn = avgBuyIn,
  title = title,
  isOpened = isOpened,
  startTimeInMs = startTimeInMs,
  tournamentsPlayed = tournamentsPlayed,
)