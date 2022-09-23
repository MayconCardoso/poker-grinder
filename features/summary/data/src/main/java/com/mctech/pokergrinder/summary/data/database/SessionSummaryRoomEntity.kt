package com.mctech.pokergrinder.summary.data.database

import androidx.room.ColumnInfo

public data class SessionSummaryRoomEntity(
  @ColumnInfo(name = "total")
  val total: Int,
  @ColumnInfo(name = "upDays")
  val upDays: Int,
  @ColumnInfo(name = "downDays")
  val downDays: Int,
  @ColumnInfo(name = "tournaments")
  val tournaments: Int,
)