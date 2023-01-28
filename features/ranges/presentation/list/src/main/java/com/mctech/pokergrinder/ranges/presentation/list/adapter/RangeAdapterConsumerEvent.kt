package com.mctech.pokergrinder.ranges.presentation.list.adapter

import com.mctech.pokergrinder.ranges.domain.entities.Range

internal sealed class RangeAdapterConsumerEvent {
  data class Clicked(val range: Range) : RangeAdapterConsumerEvent()
}