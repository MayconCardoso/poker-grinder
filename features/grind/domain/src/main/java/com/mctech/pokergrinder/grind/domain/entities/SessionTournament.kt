package com.mctech.pokergrinder.grind.domain.entities

import java.io.Serializable

data class SessionTournament(
  val id: String,
  val idSession: String,
  val title: String,
  val buyIn: Double,
  val profit: Double,
  val startTimeInMs: Long,
) : Serializable
