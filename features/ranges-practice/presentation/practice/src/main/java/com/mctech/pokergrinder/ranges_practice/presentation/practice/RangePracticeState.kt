package com.mctech.pokergrinder.ranges_practice.presentation.practice

import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeQuestion

/**
 * Describes the practice state.
 */
data class RangePracticeState(
  val question: RangePracticeQuestion,
  val infoMessage: Int?,
  val isShowingNextQuestion: Boolean,
  val isShowingActionButtons: Boolean,
  val isShowingRangeButtonVisible: Boolean,
)