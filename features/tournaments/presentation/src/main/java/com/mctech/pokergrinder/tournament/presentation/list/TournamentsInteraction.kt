package com.mctech.pokergrinder.tournament.presentation.list

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.tournament.presentation.list.adapter.TournamentsAdapterConsumerEvent

internal sealed class TournamentsInteraction : UserInteraction {
  data class OnTournamentEvent(
    val event: TournamentsAdapterConsumerEvent,
  ) : TournamentsInteraction()
}