package com.mctech.pokergrinder.tournament.presentation.list

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

internal sealed class TournamentsInteraction : UserInteraction {
  data class OnTournamentClicked(val tournament: Tournament) : TournamentsInteraction()
}