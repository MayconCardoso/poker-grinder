package com.mctech.pokergrinder.summary.domain.entities

data class SessionSummary(
  val total: Int,
  val upDays: Int,
  val downDays: Int,
  val tournaments: Int,
)