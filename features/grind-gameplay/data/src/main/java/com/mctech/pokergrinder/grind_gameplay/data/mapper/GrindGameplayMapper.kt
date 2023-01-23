package com.mctech.pokergrinder.grind_gameplay.data.mapper

import com.mctech.pokergrinder.grind_gameplay.data.database.SessionTournamentFlipRoomEntity
import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip

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