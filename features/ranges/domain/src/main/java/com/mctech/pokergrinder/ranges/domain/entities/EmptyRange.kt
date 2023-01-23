package com.mctech.pokergrinder.ranges.domain.entities

/**
 * Holds a range of cards.
 */
object EmptyRange {

  /**
   * Holds the cards values
   */
  private val cardValues = listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2")

  /**
   * Hold all range hands
   */
  val rangeHands = buildList {
    // Iterate first card
    for (firstCardIndex in 0..12) {
      // Iterate second card
      for (secondCardIndex in 0..12) {
        // Get cards
        val firstCard = cardValues[Math.min(firstCardIndex, secondCardIndex)]
        val secondCard = cardValues[Math.max(firstCardIndex, secondCardIndex)]

        // Create hand.
        val hand = RangeHand(
          firstCard = firstCard,
          secondCard = secondCard,
          suited = secondCardIndex > firstCardIndex,
        )

        // Add hand to the list.
        add(hand)
      }
    }
  }
}