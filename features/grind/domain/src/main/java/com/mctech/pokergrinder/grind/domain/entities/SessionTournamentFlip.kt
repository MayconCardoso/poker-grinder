package com.mctech.pokergrinder.grind.domain.entities

data class SessionTournamentFlip(
  val id: String,
  val idSession: String,
  val tournament: String,
  val heroHand: String,
  val villainHand: String,
  val board: String,
  val won: Boolean,
)