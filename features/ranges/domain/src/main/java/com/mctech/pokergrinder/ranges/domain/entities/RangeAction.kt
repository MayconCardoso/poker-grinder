package com.mctech.pokergrinder.ranges.domain.entities

import java.io.Serializable

/**
 * Defines player position
 */
enum class RangeAction: Serializable {
  OPEN,
  FLAT,
  BET3,
  CALL3BET,
  BET4,
  CALL4BET,
  SHOVE,
  CALL_SHOVE,
  FOLD,
}