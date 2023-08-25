package com.mctech.pokergrinder.grind.presentation.list

import com.mctech.pokergrinder.grind.domain.entities.Session

/**
 * Represents the state of the grind session
 */
data class GrindState(
  val session: Session,
  val title: String,
  val tournaments: Int,
  val cash: String,
  val buyIn: String,
  val avgBuyIn: String,
  val profit: String,
  val roi: String,
)