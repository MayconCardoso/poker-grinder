package com.mctech.pokergrinder.ranges_practice.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "range_practice",
  indices = [
    Index(
      value = ["heroPosition"],
      name = "range_practice_hero_position_index",
    ),
    Index(
      value = ["effectiveStack"],
      name = "range_practice_stack_index",
    ),
  ]
)
data class RangePracticeRoomEntity(
  @PrimaryKey
  val id: String,
  val cards: String,
  val action: String,
  val heroPosition: String,
  val villainPosition: String?,
  val effectiveStack: Int,
  val isAnswerCorrect: Boolean,
  val dateInMs: Long,
)
