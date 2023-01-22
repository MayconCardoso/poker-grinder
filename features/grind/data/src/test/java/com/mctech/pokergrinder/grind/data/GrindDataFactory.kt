package com.mctech.pokergrinder.grind.data

import com.mctech.pokergrinder.grind.data.database.SessionDetailRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionTournamentFlipRoomEntity


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