package com.mctech.pokergrinder.ranges_practice.presentation.filter

/**
 * Defines the range filter state.
 */
data class RangePracticeFilterState(
  val stackOption: List<RangeFilterOption>,
  val positionOption: List<RangeFilterOption>,
)

data class RangeFilterOption(
  val name: String?,
  val isSelected: Boolean,
)