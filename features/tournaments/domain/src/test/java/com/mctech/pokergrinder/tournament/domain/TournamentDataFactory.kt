package com.mctech.pokergrinder.tournament.domain

import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.domain.entities.TournamentType

/**
 * Creates a new tournament for test purpose.
 */
fun newTournament(id: String = "", buyIn: Float = 0F) = Tournament(
  id = id,
  type = TournamentType.TURBO,
  buyIn = buyIn,
  title = "Example",
  countReBuy = 1,
  guaranteed = 10,
  isBounty = false,
  startTimeInMs = 1,
)