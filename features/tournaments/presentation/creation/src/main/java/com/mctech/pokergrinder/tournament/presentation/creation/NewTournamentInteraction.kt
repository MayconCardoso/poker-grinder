package com.mctech.pokergrinder.tournament.presentation.creation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

internal sealed class NewTournamentInteraction : UserInteraction {

  data class ScreenFirstOpen(val tournament: Tournament?): NewTournamentInteraction()

  data class SaveTournament(
    val title: String,
    val guaranteed: Int,
    val countBuyIn: Int,
    val buyIn: Float,
  ): NewTournamentInteraction()
}