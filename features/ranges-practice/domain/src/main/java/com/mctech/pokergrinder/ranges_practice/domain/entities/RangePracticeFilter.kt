package com.mctech.pokergrinder.ranges_practice.domain.entities

import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition

/**
 * Defines a practice filter. When values are null it means that there is no filter applied.
 */
data class RangePracticeFilter(
  val stack: Int? = null,
  val action: RangeAction? = null,
  val position: RangePlayerPosition? = null,
)
