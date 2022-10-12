package com.mctech.pokergrinder.tournament.presentation.list

import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

internal data class TournamentsState(
  val averageBuyIn: String,
  val investmentPerSession: String,
  val tournaments: List<Tournament>,
)