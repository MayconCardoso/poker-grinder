package com.mctech.pokergrinder.grind.presentation.flip_creation.adapter

import com.mctech.pokergrinder.grind.domain.entities.SessionTournament

internal sealed class RegisterFlipTournamentConsumerEvent {
  data class TournamentClicked(val tournament: SessionTournament) : RegisterFlipTournamentConsumerEvent()
  data class DuplicateClicked(val tournament: SessionTournament) : RegisterFlipTournamentConsumerEvent()
}