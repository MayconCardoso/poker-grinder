package com.mctech.pokergrinder.grind.presentation.grind_summary

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsConsumerEvent

internal sealed class GrindSummaryInteraction : UserInteraction {
  data class ScreenFirstOpen(val session: Session) : GrindSummaryInteraction()
}