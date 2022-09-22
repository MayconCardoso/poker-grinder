package com.mctech.pokergrinder.grind.data.mapper

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
internal fun List<SessionRoomEntity>.asBusinessSessions(): List<Session> {
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
  title = title,
  buyIn = buyIn,
  profit = profit,
  startTimeInMs = startTimeInMs,
)

/**
 * Converts a tournament database entity onto a business one known by the modules.
 */
internal fun SessionRoomEntity.asBusinessSession() = Session(
  id = id,
  isOpened = isOpened,
  title = title,
  outcome = outcome,
  startTimeInMs = startTimeInMs,
  countBuyIn = countBuyIn,
)

/**
 * Converts a business tournament onto a database one.
 */
internal fun SessionTournament.asDatabaseTournaments() = SessionTournamentRoomEntity(
  id = id,
  idSession = idSession,
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
  outcome = outcome,
  startTimeInMs = startTimeInMs,
  countBuyIn = countBuyIn,
)
