package com.mctech.pokergrinder.grind.presentation.grind_gameplay

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session

internal sealed class GrindDetailsGameplayInteraction : UserInteraction {
  data class ScreenFirstOpen(val session: Session) : GrindDetailsGameplayInteraction()
}