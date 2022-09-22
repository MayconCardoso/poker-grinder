package com.mctech.pokergrinder.grind.presentation.list.adapter

import com.mctech.pokergrinder.grind.domain.entities.Session

internal sealed class GrindAdapterConsumerEvent {
  data class SessionClicked(val session: Session) : GrindAdapterConsumerEvent()
}