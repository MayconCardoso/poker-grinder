package com.mctech.pokergrinder.grind_tournament.presentation.list.adapter

import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament

internal sealed class GrindDetailsConsumerEvent {
  data class TournamentClicked(val tournament: SessionTournament) : GrindDetailsConsumerEvent()
  data class DuplicateClicked(val tournament: SessionTournament) : GrindDetailsConsumerEvent()
}