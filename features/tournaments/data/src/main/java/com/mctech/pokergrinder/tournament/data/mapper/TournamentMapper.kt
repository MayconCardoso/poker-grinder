package com.mctech.pokergrinder.tournament.data.mapper

import com.mctech.pokergrinder.tournament.data.database.TournamentRoomEntity
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import com.mctech.pokergrinder.tournaments.domain.entities.TournamentType

/**
 * Converts a list of tournament database entity onto a business one known by the modules.
 */
internal fun List<TournamentRoomEntity>.asBusinessTournaments(): List<Tournament> {
  return this.map { dbEntity ->
    dbEntity.asBusinessTournament()
  }
}

/**
 * Converts a tournament database entity onto a business one known by the modules.
 */
internal fun TournamentRoomEntity.asBusinessTournament() = Tournament(
  id = id,
  type = TournamentType.valueOf(type),
  buyIn = buyIn,
  title = title,
  countReBuy = countReBuy,
  guaranteed = guaranteed,
  isBounty = isBounty,
  startTimeInMs = startTimeInMs,
)

/**
 * Converts a business tournament onto a database one.
 */
internal fun Tournament.asDatabaseTournament() = TournamentRoomEntity(
  id = id,
  type = type.name,
  buyIn = buyIn,
  title = title,
  countReBuy = countReBuy,
  guaranteed = guaranteed,
  isBounty = isBounty,
  startTimeInMs = startTimeInMs,
)
