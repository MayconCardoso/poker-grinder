package com.mctech.pokergrinder.ranges.domain.entities

import java.io.Serializable

/**
 * Defines a range of hands that should be played from [handPosition] with the [effectiveStack].
 */
data class Range(
  val id: String,
  val name: String,
  val action: RangeAction,
  val effectiveStack: Int,
  val handPosition: List<RangePosition>,
): Serializable
