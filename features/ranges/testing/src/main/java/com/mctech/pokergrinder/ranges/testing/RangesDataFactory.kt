package com.mctech.pokergrinder.ranges.testing

import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangeAction

/**
 * Creates a new Range for test purpose.
 */
fun newRange(
  id: String = "0",
  name: String = "0",
  action: RangeAction = RangeAction.OPEN,
  effectiveStack: Int = 100,
) = Range(
  id = id,
  name = name,
  action = action,
  effectiveStack = effectiveStack,
  handPosition = listOf(),
)