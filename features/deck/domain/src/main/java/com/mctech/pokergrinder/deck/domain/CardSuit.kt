package com.mctech.pokergrinder.deck.domain

import java.io.Serializable

/**
 * Describes a card suit
 * @property suffix card suffix globally used for naming convention.
 */
enum class CardSuit(val suffix: String) : Serializable {
  CLUBS("c"),
  SPADES("s"),
  HEARTS("h"),
  DIAMONDS("d"),
}