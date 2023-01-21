package com.mctech.pokergrinder.summary.presentation.tournaments.details

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary

internal sealed class SummaryTournamentDetailsInteraction : UserInteraction {
  data class OnScreenFirstOpened(
    val tournamentSummary: TournamentSummary,
  ) : SummaryTournamentDetailsInteraction()
}