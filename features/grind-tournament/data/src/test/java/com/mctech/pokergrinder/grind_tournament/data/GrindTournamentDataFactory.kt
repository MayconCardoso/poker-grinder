package com.mctech.pokergrinder.grind_tournament.data

import com.mctech.pokergrinder.grind_tournament.data.database.SessionTournamentRoomEntity

/**
 * Creates a new Session tournament for test purpose.
 */
internal fun newDatabaseTournament(
  id: String = "0",
  idSession: String = "0",
  idTransactionProfit: String? = "0",
  idTransactionBuyIn: String = "0",
  title: String = "",
  buyIn: Double = 0.0,
  profit: Double = 0.0,
  startTimeInMs: Long = 0,
) = SessionTournamentRoomEntity(
  id = id,
  idSession = idSession,
  idTransactionProfit = idTransactionProfit,
  idTransactionBuyIn = idTransactionBuyIn,
  title = title,
  buyIn = buyIn,
  profit = profit,
  startTimeInMs = startTimeInMs,
)
