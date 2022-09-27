package com.mctech.pokergrinder.deck.domain

import java.io.Serializable

data class Card(
  val suit: CardSuit,
  val name: String,
  val value: Int,
): Serializable {

  fun cardName(): String {
    return "${name}${suit.sufix}"
  }

}