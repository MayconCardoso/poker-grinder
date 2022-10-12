package com.mctech.pokergrinder.deck.domain

import java.io.Serializable

enum class CardSuit(val sufix: String) : Serializable {
  CLUBS("c"),
  SPADES("s"),
  HEARTS("h"),
  DIAMONDS("d"),
}