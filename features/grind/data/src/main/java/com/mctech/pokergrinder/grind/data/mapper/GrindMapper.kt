package com.mctech.pokergrinder.grind.data.mapper

import com.mctech.pokergrinder.grind.data.database.SessionDetailRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind.domain.entities.Session

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
 * Converts a tournament database entity onto a business one known by the modules.
 */
internal fun Session.asDatabaseSession() = SessionRoomEntity(
  id = id,
  isOpened = isOpened,
  title = title,
  startTimeInMs = startTimeInMs,
)