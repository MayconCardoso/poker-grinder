package com.mctech.pokergrinder.tournaments.domain.entities

data class Tournament(
  val id: String,
  val type: TournamentType,
  val buyIn: Float,
  val title: String,
  val countReBuy: Int,
  val guaranteed: Int,
  val isBounty: Boolean,
  val startTimeInMs: Long,
)
