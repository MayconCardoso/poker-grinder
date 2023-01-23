package com.mctech.pokergrinder.grind_tournament.data.mapper

import com.mctech.pokergrinder.grind_tournament.data.database.SessionTournamentRoomEntity
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament

// region Session Tournament

/**
 * Converts a list of tournament database entity onto a business one known by the modules.
 */
internal fun List<SessionTournamentRoomEntity>.asBusinessTournaments(): List<SessionTournament> {
  return this.map { dbEntity ->
    dbEntity.asBusinessTournaments()
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

// endregion
