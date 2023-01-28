package com.mctech.pokergrinder.ranges.domain.entities

import java.io.Serializable

/**
 * Defines a input hand.
 */
data class RangeHandInput(
  val hand: RangeHand,
  val frequency: Float = 1.0F,
  val filledColor: String = "#FF7045",
): Serializable