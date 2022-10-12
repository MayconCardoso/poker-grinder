package com.mctech.pokergrinder.tournament.presentation.navigation

import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

public interface TournamentNavigation {
  public fun goToTournament(tournament: Tournament?)
  public fun navigateBack()
}