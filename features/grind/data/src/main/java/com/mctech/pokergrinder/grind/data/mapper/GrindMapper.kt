package com.mctech.pokergrinder.grind.data.mapper

import com.mctech.pokergrinder.grind.data.database.SessionDetailRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionTournamentFlipRoomEntity
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip

// region Session

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

// endregion

// region Session Tournament Flip

/**
 * Converts a list of tournament flips database entity onto a business one known by the modules.
 */
internal fun List<SessionTournamentFlipRoomEntity>.asBusinessTournamentFlips(): List<SessionTournamentFlip> {
  return this.map { dbEntity ->
    dbEntity.asBusinessTournamentFlips()
  }
}

/**
 * Converts a tournament flip database entity onto a business one known by the modules.
 */
internal fun SessionTournamentFlipRoomEntity.asBusinessTournamentFlips() = SessionTournamentFlip(
  id = id,
  idSession = idSession,
  tournament = tournament,
  heroHand = heroHand,
  villainHand = villainHand,
  board = board,
  won = won,
)

/**
 * Converts a tournament flip database entity onto a business one known by the modules.
 */
internal fun SessionTournamentFlip.asBusinessTournamentFlips() = SessionTournamentFlipRoomEntity(
  id = id,
  idSession = idSession,
  tournament = tournament,
  heroHand = heroHand,
  villainHand = villainHand,
  board = board,
  won = won,
)

// endregion