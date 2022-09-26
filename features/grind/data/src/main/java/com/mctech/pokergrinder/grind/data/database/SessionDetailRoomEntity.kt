package com.mctech.pokergrinder.grind.data.database

import androidx.room.DatabaseView

@DatabaseView(
  viewName = "grind_session_detail",
  value = "" +
    "SELECT " +
    "gs.id, gs.title, gs.isOpened, COUNT(1) tournaments, SUM(buyIn) buyIn, SUM(profit) cash, gs.startTimeInMs " +
    "FROM grind_session_tournament gst " +
    "INNER JOIN grind_session gs ON gs.id = gst.idSession " +
    "GROUP  BY idSession" +
    "")
public data class SessionDetailRoomEntity(
  val id: String,
  val title: String,
  val isOpened: Boolean,
  val cash: Double,
  val buyIn: Double,
  val tournaments: Int,
  val startTimeInMs: Long,
)
