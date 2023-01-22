package com.mctech.pokergrinder.grind_gameplay.testing

import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip

/**
 * Creates a new Session tournament flip for test purpose.
 */
fun newSessionFlip(
  id: String = "0",
  won: Boolean = false,
  idSession: String = "0",
  tournament: String = "",
  board: String = "",
  heroHand: String = "",
  villainHand: String = "",
) = SessionTournamentFlip(
  id = id,
  won = won,
  idSession = idSession,
  tournament = tournament,
  board = board,
  heroHand = heroHand,
  villainHand = villainHand,
)