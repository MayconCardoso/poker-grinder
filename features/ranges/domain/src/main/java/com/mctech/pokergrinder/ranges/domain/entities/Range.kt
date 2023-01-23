package com.mctech.pokergrinder.ranges.domain.entities

/**
 * Defines a range of hands that should be played from [handPosition] with the [effectiveStack].
 */
data class Range(
  val name: String,
  val action: RangeAction,
  val effectiveStack: Int,
  val handPosition: List<RangePosition>,
)
