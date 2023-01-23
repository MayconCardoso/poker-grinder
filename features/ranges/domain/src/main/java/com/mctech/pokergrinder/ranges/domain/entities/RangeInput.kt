package com.mctech.pokergrinder.ranges.domain.entities

/**
 * Defines a input hand.
 */
data class RangeInput(
  val hand: EmptyRangeHand,
  val frequency: Float = 1.0F,
  val filledColor: String,
)