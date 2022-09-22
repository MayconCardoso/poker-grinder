package com.mctech.pokergrinder.tournament.presentation.creation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

internal sealed class TournamentInteraction : UserInteraction {

  data class ScreenFirstOpen(val tournament: Tournament?): TournamentInteraction()

  data class SaveTournament(
    val title: String,
    val guaranteed: Int,
    val countBuyIn: Int,
    val buyIn: Float,
  ): TournamentInteraction()
}