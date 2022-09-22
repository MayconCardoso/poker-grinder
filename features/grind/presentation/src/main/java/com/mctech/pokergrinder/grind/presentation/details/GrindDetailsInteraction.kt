package com.mctech.pokergrinder.grind.presentation.details

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.details.adapter.GrindDetailsConsumerEvent

internal sealed class GrindDetailsInteraction : UserInteraction {
  data class ScreenFirstOpen(val session: Session) : GrindDetailsInteraction()

  data class OnTournamentEvent(val event: GrindDetailsConsumerEvent) : GrindDetailsInteraction()
}