package com.mctech.pokergrinder.grind_gameplay.data

import com.mctech.pokergrinder.grind_gameplay.data.database.SessionTournamentFlipRoomEntity

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