package com.mctech.pokergrinder.summary.presentation.tournaments.details

import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary

internal data class SummaryTournamentDetailsState(
  val tournaments: List<TournamentSummary>,
  val groupedSummary: TournamentSummary,
)