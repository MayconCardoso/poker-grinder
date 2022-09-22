package com.mctech.pokergrinder.tournament.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
  tableName = "tournament"
)
public data class TournamentRoomEntity(
  @PrimaryKey
  val id: String,
  val type: String,
  @ColumnInfo(name = "buy_in")
  val buyIn: Float,
  val title: String,
  @ColumnInfo(name = "count_rebuy")
  val countReBuy: Int,
  val guaranteed: Int,
  @ColumnInfo(name = "is_bounty")
  val isBounty: Boolean,
  @ColumnInfo(name = "start_time")
  val startTimeInMs: Long,
)
