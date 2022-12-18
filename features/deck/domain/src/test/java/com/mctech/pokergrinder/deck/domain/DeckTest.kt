package com.mctech.pokergrinder.deck.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


internal class DeckTest {
  private val expectedCards = listOf(
    Card(value = 2, name = "2", suit = CardSuit.CLUBS),
    Card(value = 3, name = "3", suit = CardSuit.CLUBS),
    Card(value = 4, name = "4", suit = CardSuit.CLUBS),
    Card(value = 5, name = "5", suit = CardSuit.CLUBS),
    Card(value = 6, name = "6", suit = CardSuit.CLUBS),
    Card(value = 7, name = "7", suit = CardSuit.CLUBS),
    Card(value = 8, name = "8", suit = CardSuit.CLUBS),
    Card(value = 9, name = "9", suit = CardSuit.CLUBS),
    Card(value = 10, name = "T", suit = CardSuit.CLUBS),
    Card(value = 11, name = "J", suit = CardSuit.CLUBS),
    Card(value = 12, name = "Q", suit = CardSuit.CLUBS),
    Card(value = 13, name = "K", suit = CardSuit.CLUBS),
    Card(value = 14, name = "A", suit = CardSuit.CLUBS),

    Card(value = 2, name = "2", suit = CardSuit.SPADES),
    Card(value = 3, name = "3", suit = CardSuit.SPADES),
    Card(value = 4, name = "4", suit = CardSuit.SPADES),
    Card(value = 5, name = "5", suit = CardSuit.SPADES),
    Card(value = 6, name = "6", suit = CardSuit.SPADES),
    Card(value = 7, name = "7", suit = CardSuit.SPADES),
    Card(value = 8, name = "8", suit = CardSuit.SPADES),
    Card(value = 9, name = "9", suit = CardSuit.SPADES),
    Card(value = 10, name = "T", suit = CardSuit.SPADES),
    Card(value = 11, name = "J", suit = CardSuit.SPADES),
    Card(value = 12, name = "Q", suit = CardSuit.SPADES),
    Card(value = 13, name = "K", suit = CardSuit.SPADES),
    Card(value = 14, name = "A", suit = CardSuit.SPADES),

    Card(value = 2, name = "2", suit = CardSuit.HEARTS),
    Card(value = 3, name = "3", suit = CardSuit.HEARTS),
    Card(value = 4, name = "4", suit = CardSuit.HEARTS),
    Card(value = 5, name = "5", suit = CardSuit.HEARTS),
    Card(value = 6, name = "6", suit = CardSuit.HEARTS),
    Card(value = 7, name = "7", suit = CardSuit.HEARTS),
    Card(value = 8, name = "8", suit = CardSuit.HEARTS),
    Card(value = 9, name = "9", suit = CardSuit.HEARTS),
    Card(value = 10, name = "T", suit = CardSuit.HEARTS),
    Card(value = 11, name = "J", suit = CardSuit.HEARTS),
    Card(value = 12, name = "Q", suit = CardSuit.HEARTS),
    Card(value = 13, name = "K", suit = CardSuit.HEARTS),
    Card(value = 14, name = "A", suit = CardSuit.HEARTS),

    Card(value = 2, name = "2", suit = CardSuit.DIAMONDS),
    Card(value = 3, name = "3", suit = CardSuit.DIAMONDS),
    Card(value = 4, name = "4", suit = CardSuit.DIAMONDS),
    Card(value = 5, name = "5", suit = CardSuit.DIAMONDS),
    Card(value = 6, name = "6", suit = CardSuit.DIAMONDS),
    Card(value = 7, name = "7", suit = CardSuit.DIAMONDS),
    Card(value = 8, name = "8", suit = CardSuit.DIAMONDS),
    Card(value = 9, name = "9", suit = CardSuit.DIAMONDS),
    Card(value = 10, name = "T", suit = CardSuit.DIAMONDS),
    Card(value = 11, name = "J", suit = CardSuit.DIAMONDS),
    Card(value = 12, name = "Q", suit = CardSuit.DIAMONDS),
    Card(value = 13, name = "K", suit = CardSuit.DIAMONDS),
    Card(value = 14, name = "A", suit = CardSuit.DIAMONDS),
  )
  private val expectedCardName = listOf(
    "2c", "3c", "4c", "5c", "6c", "7c", "8c", "9c", "tc", "jc", "qc", "kc", "ac",
    "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s", "ts", "js", "qs", "ks", "as",
    "2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "th", "jh", "qh", "kh", "ah",
    "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d", "td", "jd", "qd", "kd", "ad",
  )

  @Test
  fun `assert deck card`() {
    assertThat(Deck.cards).isEqualTo(expectedCards)
  }

  @Test
  fun `assert card name`() {
    val cardNames = expectedCards.map { it.formattedName() }
    assertThat(cardNames).isEqualTo(expectedCardName)
  }
}