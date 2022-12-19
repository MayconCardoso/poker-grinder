package com.mctech.pokergrinder.grind.data

import com.mctech.pokergrinder.grind.data.database.SessionDetailRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionTournamentFlipRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionTournamentRoomEntity
import com.mctech.pokergrinder.grind.domain.entities.Session


/**
 * Creates a new Session for test purpose.
 */
internal fun newDatabaseSession(
  id: String = "0",
  title: String = "",
  isOpened: Boolean = false,
  startTimeInMs: Long = 0,
) = SessionRoomEntity(
  id = id,
  title = title,
  isOpened = isOpened,
  startTimeInMs = startTimeInMs,
)

/**
 * Creates a new Session for test purpose.
 */
internal fun newDatabaseSessionDetail(
  id: String = "0",
  cash: Double = 0.0,
  buyIn: Double = 0.0,
  avgBuyIn: Double = 0.0,
  title: String = "",
  isOpened: Boolean = false,
  startTimeInMs: Long = 0,
  tournamentsPlayed: Int = 0,
) = SessionDetailRoomEntity(
  id = id,
  cash = cash,
  buyIn = buyIn,
  avgBuyIn = avgBuyIn,
  title = title,
  isOpened = isOpened,
  startTimeInMs = startTimeInMs,
  tournaments = tournamentsPlayed,
)

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

/**
 * Creates a new Session tournament flip for test purpose.
 */
internal fun newDatabaseSessionFlip(
  id: String = "0",
  won: Boolean = false,
  idSession: String = "0",
  tournament: String = "",
  board: String = "",
  heroHand: String = "",
  villainHand: String = "",
) = SessionTournamentFlipRoomEntity(
  id = id,
  won = won,
  idSession = idSession,
  tournament = tournament,
  board = board,
  heroHand = heroHand,
  villainHand = villainHand,
)