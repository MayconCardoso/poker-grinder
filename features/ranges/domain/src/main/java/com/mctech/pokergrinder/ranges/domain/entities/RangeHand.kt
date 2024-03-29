package com.mctech.pokergrinder.ranges.domain.entities

import java.io.Serializable

/**
 * Describes a range hand.
 * @property firstCard hols the first card.
 * @property secondCard hols the first card.
 * @property suited indicates if hand is suited.
 */
data class RangeHand(
  val firstCard: String,
  val secondCard: String,
  val suited: Boolean,
) : Serializable {

  /**
   * True when hand is a pocket pair.
   */
  fun isPockedPair() = firstCard == secondCard

  /**
   * Formats the hand.
   */
  fun formattedName(): String {
    return firstCard + secondCard + if (suited) "s" else if (isPockedPair()) "" else "o"
  }
}
