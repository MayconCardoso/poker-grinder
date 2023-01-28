package com.mctech.pokergrinder.ranges.domain.entities

import java.io.Serializable

/**
 * Defines player position
 */
enum class RangePlayerPosition: Serializable {
  UTG,
  UTG1,
  LJ,
  HJ,
  CO,
  BTN,
  SB,
  BB,
}