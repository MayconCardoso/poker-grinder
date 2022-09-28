package com.mctech.pokergrinder.grind.data.mapper

import com.mctech.pokergrinder.grind.data.database.SessionDetailRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionTournamentRoomEntity
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament

/**
 * Converts a list of tournament database entity onto a business one known by the modules.
 */
internal fun List<SessionTournamentRoomEntity>.asBusinessTournaments(): List<SessionTournament> {
  return this.map { dbEntity ->
    dbEntity.asBusinessTournaments()
  }
}

/**
 * Converts a list of tournament database entity onto a business one known by the modules.
 */
internal fun List<SessionDetailRoomEntity>.asBusinessSessions(): List<Session> {
  return this.map { dbEntity ->
    dbEntity.asBusinessSession()
  }
}

/**
 * Converts a tournament database entity onto a business one known by the modules.
 */
internal fun SessionTournamentRoomEntity.asBusinessTournaments() = SessionTournament(
  id = id,
  idSession = idSession,
  idTransactionBuyIn = idTransactionBuyIn,
  idTransactionProfit = idTransactionProfit,
  title = title,
  buyIn = buyIn,
  profit = profit,
  startTimeInMs = startTimeInMs,
  isGrouped = false,
)

/**
 * Converts a tournament database entity onto a business one known by the modules.
 */
internal fun SessionDetailRoomEntity.asBusinessSession() = Session(
  id = id,
  cash = cash,
  buyIn = buyIn,
  isOpened = isOpened,
  avgBuyIn = avgBuyIn,
  title = title,
  startTimeInMs = startTimeInMs,
  tournamentsPlayed = tournaments,
)

/**
 * Converts a business tournament onto a database one.
 */
internal fun SessionTournament.asDatabaseTournaments() = SessionTournamentRoomEntity(
  id = id,
  idSession = idSession,
  idTransactionBuyIn = idTransactionBuyIn,
  idTransactionProfit = idTransactionProfit,
  title = title,
  buyIn = buyIn,
  profit = profit,
  startTimeInMs = startTimeInMs,
)


/**
 * Converts a tournament database entity onto a business one known by the modules.
 */
internal fun Session.asDatabaseSession() = SessionRoomEntity(
  id = id,
  isOpened = isOpened,
  title = title,
  startTimeInMs = startTimeInMs,
)
