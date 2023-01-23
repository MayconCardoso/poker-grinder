package com.mctech.pokergrinder.grind_tournament.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "grind_session_tournament",
  indices = [
    Index(
      value = ["idSession"],
      name = "grind_session_tournament_session_index",
    ),
  ],
)
data class SessionTournamentRoomEntity(
  @PrimaryKey
  val id: String,
  val idSession: String,
  val idTransactionProfit: String?,
  val idTransactionBuyIn: String,
  val title: String,
  val buyIn: Double,
  val profit: Double,
  val startTimeInMs: Long,
)
