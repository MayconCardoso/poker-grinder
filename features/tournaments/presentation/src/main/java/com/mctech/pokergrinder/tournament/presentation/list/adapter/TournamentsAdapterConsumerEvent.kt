package com.mctech.pokergrinder.tournament.presentation.list.adapter

import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

internal sealed class TournamentsAdapterConsumerEvent {
  data class TournamentClicked(val tournament: Tournament): TournamentsAdapterConsumerEvent()
}