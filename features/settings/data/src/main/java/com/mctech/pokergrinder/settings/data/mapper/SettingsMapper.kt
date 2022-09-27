package com.mctech.pokergrinder.settings.data.mapper

import com.mctech.pokergrinder.settings.data.database.SettingsRoomEntity
import com.mctech.pokergrinder.settings.domain.entities.Settings

/**
 * Converts a list of settings database entity onto a business one known by the modules.
 */
internal fun List<SettingsRoomEntity>.asBusinessSettings(): List<Settings> {
  return this.map { dbEntity ->
    dbEntity.asBusinessSetting()
  }
}

/**
 * Converts a setting database entity onto a business one known by the modules.
 */
internal fun SettingsRoomEntity.asBusinessSetting() = Settings(
  key = settingKey,
  value = settingValue,
  title = settingTitle,
)

/**
 * Converts a business setting onto a database one.
 */
internal fun Settings.asDatabaseTransaction() = SettingsRoomEntity(
  settingKey = key,
  settingValue = value,
  settingTitle = value,
)
