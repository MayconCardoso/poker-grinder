package com.mctech.pokergrinder.ranges_practice.domain.entities

import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition

/**
 * Describes a range learning question.
 * @property stack effective stack.
 * @property cards player dealt cards.
 * @property action action that should be taken.
 * @property position player position at the table.
 */
data class RangePracticeQuestion(
  val stack: Int,
  val cards: String,
  val action: RangeAction,
  val position: RangePlayerPosition,
)