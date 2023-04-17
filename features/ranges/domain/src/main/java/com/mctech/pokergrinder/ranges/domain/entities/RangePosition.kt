package com.mctech.pokergrinder.ranges.domain.entities

import java.io.Serializable

/**
 * Defines player position
 */
data class RangePosition(
  val heroPosition: RangePlayerPosition,
  val villainPosition: RangePlayerPosition?,
  val hands: List<RangeHandInput>,
): Serializable