package com.mctech.pokergrinder.grind.data.database

import androidx.room.DatabaseView

@DatabaseView(
  viewName = "grind_session_detail",
  value = "" +
    "SELECT " +
    "gs.id, gs.title, gs.isOpened, COUNT(1) tournaments, COALESCE(SUM(buyIn), 0) buyIn, COALESCE(SUM(profit), 0) cash, COALESCE(AVG(buyIn), 0) avgBuyIn, gs.startTimeInMs " +
    "FROM grind_session gs " +
    "LEFT JOIN grind_session_tournament gst ON gs.id = gst.idSession " +
    "GROUP BY gs.id" +
    "")
data class SessionDetailRoomEntity(
  val id: String,
  val title: String,
  val isOpened: Boolean,
  val cash: Double,
  val buyIn: Double,
  val avgBuyIn: Double,
  val tournaments: Int,
  val startTimeInMs: Long,
)
