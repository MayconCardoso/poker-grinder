package com.mctech.pokergrinder.summary.data.database

import androidx.room.ColumnInfo

public data class TournamentSummaryRoomEntity(
  @ColumnInfo(name = "title")
  val title: String,
  @ColumnInfo(name = "cash")
  val cash: Double,
  @ColumnInfo(name = "buyIn")
  val buyIn: Double,
  @ColumnInfo(name = "profit")
  val profit: Double,
  @ColumnInfo(name = "tournaments")
  val tournaments: Int,
)