package com.mctech.pokergrinder.grind.presentation.tournamnet_creation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament

internal sealed class RegisterTournamentInteraction : UserInteraction {

  data class ScreenFirstOpen(
    val session: Session,
    val tournament: SessionTournament?,
  ) : RegisterTournamentInteraction()

  data class SaveTournament(
    val title: String,
    val buyIn: Double,
    val profit: Double,
  ) : RegisterTournamentInteraction()

}