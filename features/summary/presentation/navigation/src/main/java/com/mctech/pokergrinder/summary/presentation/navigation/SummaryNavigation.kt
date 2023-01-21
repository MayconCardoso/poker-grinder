package com.mctech.pokergrinder.summary.presentation.navigation

import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary

/**
 * Used to navigate through grind feature components.
 */
interface SummaryNavigation {

  /**
   * Goes to tournament summary detail screen.
   */
  fun goToTournamentSummaryDetail(tournamentSummary: TournamentSummary)

}