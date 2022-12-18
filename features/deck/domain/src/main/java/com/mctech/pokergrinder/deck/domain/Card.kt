package com.mctech.pokergrinder.deck.domain

import java.io.Serializable

/**
 * Describe a deck card
 */
data class Card(
  val suit: CardSuit,
  val name: String,
  val value: Int,
): Serializable {

  /**
   * Formats a card name.
   */
  fun formattedName(): String {
    return "${name}${suit.suffix}".lowercase()
  }

}