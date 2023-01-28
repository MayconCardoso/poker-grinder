package com.mctech.pokergrinder.ranges.presentation.list.adapter

internal interface RangeAdapterConsumer {
  fun consume(event: RangeAdapterConsumerEvent)
}