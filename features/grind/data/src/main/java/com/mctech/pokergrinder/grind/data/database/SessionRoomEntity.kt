package com.mctech.pokergrinder.grind.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "grind_session",
  indices = [
    Index(
      value = ["isOpened"],
      name = "grind_session_is_opened_index",
    ),
  ]
)
data class SessionRoomEntity(
  @PrimaryKey
  val id: String,
  val title: String,
  val isOpened: Boolean,
  val startTimeInMs: Long,
)
