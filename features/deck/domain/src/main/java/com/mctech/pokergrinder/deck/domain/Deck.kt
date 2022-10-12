package com.mctech.pokergrinder.deck.domain

object Deck {

  val cards = buildList {
    addAll(createCardsBySuit(CardSuit.CLUBS))
    addAll(createCardsBySuit(CardSuit.SPADES))
    addAll(createCardsBySuit(CardSuit.HEARTS))
    addAll(createCardsBySuit(CardSuit.DIAMONDS))
  }

  private fun createCardsBySuit(suit: CardSuit) = listOf(
    Card(value = 2, name = "2", suit = suit),
    Card(value = 3, name = "3", suit = suit),
    Card(value = 4, name = "4", suit = suit),
    Card(value = 5, name = "5", suit = suit),
    Card(value = 6, name = "6", suit = suit),
    Card(value = 7, name = "7", suit = suit),
    Card(value = 8, name = "8", suit = suit),
    Card(value = 9, name = "9", suit = suit),
    Card(value = 10, name = "T", suit = suit),
    Card(value = 11, name = "J", suit = suit),
    Card(value = 12, name = "Q", suit = suit),
    Card(value = 13, name = "K", suit = suit),
    Card(value = 14, name = "A", suit = suit),
  )
}