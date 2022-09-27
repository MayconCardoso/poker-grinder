package com.mctech.pokergrinder.settings.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
public data class SettingsRoomEntity(
  @PrimaryKey
  val settingKey: String,
  val settingValue: String,
  val settingTitle: String,
)
