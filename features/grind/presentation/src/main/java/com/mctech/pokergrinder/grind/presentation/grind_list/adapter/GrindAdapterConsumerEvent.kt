package com.mctech.pokergrinder.grind.presentation.grind_list.adapter

import com.mctech.pokergrinder.grind.domain.entities.Session

internal sealed class GrindAdapterConsumerEvent {
  data class SessionClicked(val session: Session) : GrindAdapterConsumerEvent()
}