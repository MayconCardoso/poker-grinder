package com.mctech.pokergrinder.summary.data.database

import androidx.room.ColumnInfo

public data class InvestmentSummaryRoomEntity(
  @ColumnInfo(name = "profit")
  val profit: Double,
  @ColumnInfo(name = "cash")
  val cash: Double,
  @ColumnInfo(name = "buyIn")
  val buyIn: Double,
)