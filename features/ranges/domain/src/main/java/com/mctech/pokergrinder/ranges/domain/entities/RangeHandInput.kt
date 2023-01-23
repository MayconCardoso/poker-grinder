package com.mctech.pokergrinder.ranges.domain.entities

/**
 * Defines a input hand.
 */
data class RangeHandInput(
  val hand: RangeHand,
  val frequency: Float = 1.0F,
  val filledColor: String = "#FF7045",
)