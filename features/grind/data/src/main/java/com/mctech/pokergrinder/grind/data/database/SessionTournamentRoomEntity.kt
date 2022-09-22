package com.mctech.pokergrinder.grind.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
  tableName = "grind_session_tournament",
  primaryKeys = [
    "id", "idSession"
  ],
  indices = [
    Index(
      value = ["idSession"],
      name = "grind_session_tournament_session_index",
    ),
  ],
  foreignKeys = [
    ForeignKey(
      entity = SessionRoomEntity::class,
      parentColumns = arrayOf("id"),
      childColumns = arrayOf("idSession"),
      onDelete = ForeignKey.CASCADE,
      onUpdate = ForeignKey.CASCADE
    )
  ]
)
public data class SessionTournamentRoomEntity(
  val id: String,
  val idSession: String,
  val title: String,
  val buyIn: Double,
  val profit: Double,
  val startTimeInMs: Long,
)
