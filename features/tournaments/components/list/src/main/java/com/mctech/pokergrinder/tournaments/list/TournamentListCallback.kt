package com.mctech.pokergrinder.tournaments.list

import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

public interface TournamentListCallback {
  public fun onTournamentClicked(tournament: Tournament)
}