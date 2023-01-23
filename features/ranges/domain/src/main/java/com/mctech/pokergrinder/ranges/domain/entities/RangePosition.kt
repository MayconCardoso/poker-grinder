package com.mctech.pokergrinder.ranges.domain.entities

/**
 * Defines player position
 */
data class RangePosition(
  val position: RangePlayerPosition,
  val hands: List<RangeHandInput>,
)