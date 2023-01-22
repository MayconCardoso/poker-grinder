package com.mctech.pokergrinder.grind_tournament.testing

import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament

/**
 * Creates a new Session tournament for test purpose.
 */
fun newTournament(
  id: String = "0",
  idSession: String = "0",
  idTransactionProfit: String? = "0",
  idTransactionBuyIn: String = "0",
  title: String = "",
  buyIn: Double = 0.0,
  profit: Double = 0.0,
  isGrouped: Boolean = false,
  startTimeInMs: Long = 0,
) = SessionTournament(
  id = id,
  idSession = idSession,
  idTransactionProfit = idTransactionProfit,
  idTransactionBuyIn = idTransactionBuyIn,
  title = title,
  buyIn = buyIn,
  profit = profit,
  isGrouped = isGrouped,
  startTimeInMs = startTimeInMs,
)