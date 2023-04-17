package com.mctech.pokergrinder.ranges_practice.domain.entities

import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import com.mctech.pokergrinder.ranges.domain.entities.RangeHand
import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition

/**
 * Describes a range learning question.
 * @property stack effective stack.
 * @property cards player dealt cards.
 * @property action action that should be taken.
 * @property heroPosition player position at the table.
 */
data class RangePracticeQuestion(
  val stack: Int,
  val cards: String,
  val action: RangeAction,
  val heroPosition: RangePlayerPosition,
  val villainPosition: RangePlayerPosition?,
) {

  fun isHandSuited(): Boolean {
    return cards[1] == cards[3]
  }

  fun firstCard(): String {
    return cards.substring(0, 1)
  }

  fun secondCard(): String {
    return cards.substring(2, 3)
  }

  fun asRangeHand() = RangeHand(
    firstCard = firstCard().uppercase(),
    secondCard = secondCard().uppercase(),
    suited = isHandSuited(),
  )
}