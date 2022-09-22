package com.mctech.pokergrinder.grind.presentation.details.adapter

import com.mctech.pokergrinder.grind.domain.entities.SessionTournament

internal sealed class GrindDetailsConsumerEvent {
  data class TournamentClicked(val tournament: SessionTournament) : GrindDetailsConsumerEvent()
}