package com.mctech.pokergrinder.tournament.presentation.list_component

import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

public interface TournamentListCallback {
  public fun onTournamentClicked(tournament: Tournament)
}