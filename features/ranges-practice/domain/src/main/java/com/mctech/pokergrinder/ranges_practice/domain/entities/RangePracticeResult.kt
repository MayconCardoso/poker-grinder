package com.mctech.pokergrinder.ranges_practice.domain.entities

import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition

/**
 * Represents a hand analysis of a training session.
 * @property cards which hand the action is about.
 * @property action which action is being performed.
 * @property heroPosition which position the player is.
 * @property effectiveStack which stack the player has.
 * @property isAnswerCorrect indicates if the answer is correct.
 */
data class RangePracticeResult(
  val id: String,
  val cards: String,
  val action: String,
  val heroPosition: RangePlayerPosition,
  val villainPosition: RangePlayerPosition?,
  val effectiveStack: Int,
  val isAnswerCorrect: Boolean,
)