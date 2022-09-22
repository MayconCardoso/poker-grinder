package com.mctech.pokergrinder.grind.domain.entities

import java.io.Serializable

data class Session(
  val id: String,
  val title: String,
  val outcome: Double,
  val isOpened: Boolean,
  val startTimeInMs: Long,
): Serializable
