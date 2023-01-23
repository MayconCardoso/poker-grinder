package com.mctech.pokergrinder.ranges.domain.entities

/**
 * Defines a range of [hands] that should be played from [position] with the [effectiveStack].
 */
data class Range(
  val name: String,
  val position: PlayerPosition,
  val effectiveStack: Int,
  val hands: List<RangeInput>
)
