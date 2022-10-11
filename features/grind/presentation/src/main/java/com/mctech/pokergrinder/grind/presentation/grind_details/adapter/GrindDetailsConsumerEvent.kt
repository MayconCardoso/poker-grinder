package com.mctech.pokergrinder.grind.presentation.grind_details.adapter

import com.mctech.pokergrinder.grind.domain.entities.SessionTournament

internal sealed class GrindDetailsConsumerEvent {
  data class TournamentClicked(val tournament: SessionTournament) : GrindDetailsConsumerEvent()
  data class DuplicateClicked(val tournament: SessionTournament) : GrindDetailsConsumerEvent()
}