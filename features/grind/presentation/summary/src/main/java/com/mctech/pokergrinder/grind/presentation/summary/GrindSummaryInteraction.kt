package com.mctech.pokergrinder.grind.presentation.summary

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session

internal sealed class GrindSummaryInteraction : UserInteraction {
  data class ScreenFirstOpen(val session: Session) : GrindSummaryInteraction()
}