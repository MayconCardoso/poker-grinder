package com.mctech.pokergrinder.tournament.presentation.navigation

import com.mctech.pokergrinder.tournament.domain.entities.Tournament

interface TournamentNavigation {
  fun goToTournament(tournament: Tournament?)
  fun navigateBack()
}