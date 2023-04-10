package com.mctech.pokergrinder.ranges.domain.usecases

import com.mctech.pokergrinder.ranges.domain.entities.RangeHand
import javax.inject.Inject

/**
 * Decodes a poker string range onto a list of hands.
 */
class DecodePokerRangeUseCase @Inject constructor() {

  operator fun invoke(range: String): List<RangeHand> {
    val cards = "23456789TJQKA"
    val hands = mutableListOf<RangeHand>()
    val tokens = range.split(",")

    tokens.asSequence()
      .map { it.trim() }
      .forEach { token ->
        val isSuited = token.contains("s")
        val firstCardRank = token.substring(0, 1)
        val secondCardRank = token.substring(1, 2)

        // Handle single hand notation. Hands without the + operator.
        if (!token.endsWith("+")) {
          hands += RangeHand(firstCardRank, secondCardRank, isSuited)
          return@forEach
        }

        // Handle range pocket pair notation (e.g. 66+)
        if (firstCardRank == secondCardRank) {
          for (cardIndex in cards.lastIndexOf(firstCardRank) until cards.length) {
            val card = cards[cardIndex].toString()
            hands += RangeHand(card, card, isSuited)
          }
          return@forEach
        }

        // Handle range of hands notation (e.g. ATs+ or ATo+)
        for (j in cards.indexOf(secondCardRank) until cards.indexOf(firstCardRank)) {
          // Adds new hand.
          hands += RangeHand(
            firstCard = firstCardRank,
            secondCard = cards[j].toString(),
            suited = isSuited,
          )
        }
      }

    return hands
  }
}