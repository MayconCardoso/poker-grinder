package com.mctech.pokergrinder.grind.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "grind_session_tournament_flip",
  indices = [
    Index(
      value = ["idSession"],
      name = "grind_session_tournament_flip_session_index",
    ),
  ],
)
public data class SessionTournamentFlipRoomEntity(
  @PrimaryKey
  val id: String,
  val idSession: String,
  val tournament: String,
  val heroHand: String,
  val villainHand: String,
  val board: String,
  val won: Boolean,
)